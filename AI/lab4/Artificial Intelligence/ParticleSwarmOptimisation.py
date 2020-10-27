import copy
import itertools
import random
import statistics

from State import State


class Particle(State):
    def __init__(self, size):
        super().__init__(size)

        self._velocity = [[0, 0] for _ in range(size)]
        self._bestChild = copy.deepcopy(self.m)

    @property
    def board(self):
        return self.m

    @board.setter
    def board(self, value):
        self.m = value

    @property
    def size(self):
        return self._size

    @property
    def velocity(self):
        return self._velocity

    @velocity.setter
    def velocity(self, value):
        self._velocity = value

    @property
    def bestChild(self):
        return self._bestChild

    @bestChild.setter
    def bestChild(self, value):
        self._bestChild = value


def getDistance(p1, p2, k):
    p1 = list(p1)
    p2 = list(p2)
    dist = 0
    for i in range(len(p1)):
        dist += abs(p1[i][k] - p2[i][k])
    return dist


class PSO:
    def __init__(self, boardSize, maxGeneration, popSize, weight, c1, c2, neighbourhoodSize):
        self._boardSize = boardSize
        self._maxGen = maxGeneration
        self._popSize = popSize
        self._weight = weight
        self._c1 = c1
        self._c2 = c2
        self._neighbourhoodSize = neighbourhoodSize

        self._currentGen = 0
        self._population = [Particle(self._boardSize) for _ in range(self._popSize)]

    @property
    def boardSize(self):
        return self._boardSize

    @boardSize.setter
    def boardSize(self, value):
        self._boardSize = value

    @property
    def popSize(self):
        return self._popSize

    @popSize.setter
    def popSize(self, value):
        self._popSize = value

    @property
    def maxGen(self):
        return self._maxGen

    @maxGen.setter
    def maxGen(self, value):
        self._maxGen = value

    @property
    def currentGen(self):
        return self._currentGen

    def getMinFitness(self):
        fitnesses = [s.fitness() for s in self._population]
        return min(fitnesses)

    def getAvgFitness(self):
        return statistics.mean([s.fitness() for s in self._population])

    def getStdevFitness(self):
        return round(statistics.stdev([s.fitness() for s in self._population]), 2)

    def isSolved(self):
        return self.getMinFitness() == 0

    def getBestSol(self):
        bestFitness = self.getMinFitness()
        for i in self._population:
            if i.fitness() == bestFitness:
                return i

    def generateNeighbours(self):
        if self._neighbourhoodSize > self.popSize:
            self._neighbourhoodSize = self.popSize

        neighbours = []
        for i in range(self._popSize):
            localNeighbor = []
            for j in range(self._neighbourhoodSize):
                x = random.randint(0, self._neighbourhoodSize - 1)
                while x in localNeighbor:
                    x = random.randint(0, self._neighbourhoodSize - 1)
                localNeighbor.append(x)
            neighbours.append(localNeighbor.copy())

        return neighbours

    def newGeneration(self):
        neighbours = self.generateNeighbours()

        # getting the best neighbours
        bestNeighbours = []
        for i in range(self._popSize):
            bestNeighbours.append(neighbours[i][0])
            for j in range(1, len(neighbours[i])):
                if self._population[bestNeighbours[i]].fitness() > self._population[neighbours[i][j]].fitness():
                    bestNeighbours[i] = neighbours[i][j]

        # computing new velocity
        for i in range(self._popSize):
            for j in range(self._boardSize):
                for k in range(2):
                    newVelocity = self._weight * self._population[i].velocity[j][k]
                    newVelocity += self._c1 * random.random() * getDistance(self._population[bestNeighbours[i]].m[j], self._population[i].m[j], k)
                    newVelocity += self._c2 * random.random() * getDistance(self._population[i].bestChild[j], self._population[i].m[j], k)
                    self._population[i].velocity[j][k] = newVelocity

        # creating new generation
        for i in range(self._popSize):
            newChild = []
            for j in range(self._boardSize):
                for k in range(2):
                    if random.random() < self._population[i].velocity[j][k]:
                        newChild.append(self._population[i].bestChild[j])
                    else:
                        newChild.append(self._population[i].m[j])
            self._population[i].board = newChild

        self._currentGen += 1

        if not self.isSolved():
            self._population = [Particle(self._boardSize) for _ in range(self._popSize)]

    def validate(self):
        while self._currentGen != 1000:
            self._population = [Particle(self._boardSize) for _ in range(self._popSize)]
            self._currentGen += 1

            return self.getAvgFitness(), self.getStdevFitness()
