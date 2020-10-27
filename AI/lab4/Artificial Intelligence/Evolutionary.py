import copy
import itertools
import random
import statistics

from State import State


class Evolutionary:
    def __init__(self, boardSize, maxGeneration, populationSize, mutationChance, crossoverChance):
        self._boardSize = boardSize
        self._maxGen = maxGeneration
        self._popSize = populationSize
        self._mutationChance = mutationChance
        self._crossoverChance = crossoverChance

        self._currentGen = 0
        self._population = [State(self._boardSize) for _ in range(self._popSize)]

    @property
    def boardSize(self):
        return self._boardSize

    @boardSize.setter
    def boardSize(self, value):
        self._boardSize = value

    @property
    def maxGen(self):
        return self._maxGen

    @maxGen.setter
    def maxGen(self, value):
        self._maxGen = value

    @property
    def popSize(self):
        return self._popSize

    @popSize.setter
    def popSize(self, value):
        self._popSize = value

    @property
    def mutationChance(self):
        return self._mutationChance

    @mutationChance.setter
    def mutationChance(self, value):
        self._mutationChance = value

    @property
    def crossoverChance(self):
        return self._crossoverChance

    @crossoverChance.setter
    def crossoverChance(self, value):
        self._crossoverChance = value

    @property
    def currentGen(self):
        return self._currentGen

    @currentGen.setter
    def currentGen(self, value):
        self._currentGen = value

    @property
    def population(self):
        return self._population

    @population.setter
    def population(self, value):
        self._population = value
        print("Population changed", [x.fitness() for x in value])

    def getMinFitness(self):
        fitnesses = [s.fitness() for s in self._population]
        return min(fitnesses)

    def getAvgFitness(self):
        return statistics.mean([s.fitness() for s in self._population])

    def getStdevFitness(self):
        return round(statistics.stdev([s.fitness() for s in self._population]), 2)

    def isSolved(self):
        return self.getMinFitness() == 0

    def mutate(self, state):
        """
        Generates a random number that represents a line in the state given and then replaces that line's elements with new ones
        :param state:
        :return:
        """
        nr = random.random()
        if nr > self.mutationChance:
            return state
        else:
            a = itertools.product(range(1, self.boardSize + 1), range(1, self.boardSize + 1))
            a = [x for x in a]
            random.shuffle(a)

            nr = random.randint(0, self.boardSize - 1)
            for i in range(self.boardSize):
                state.m[nr][i] = a[0]
                a = a[1:]
            return state

    def createChildState(self, state1, state2):
        """
        Generates and returns a new state that inherits lines from its parents
        :param state1:
        :param state2:
        :return:
        """
        nr = random.random()
        if nr > self.crossoverChance:
            nr = random.random()
            if nr > 0.5:
                return state2
            else:
                return state1
        else:
            s = State(self.boardSize)
            for i in range(s.size):
                nr = random.random()
                if nr > 0.5:
                    s.m[i] = state2.m[i]
                else:
                    s.m[i] = state1.m[i]
            return s

    def newGeneration(self):
        """
        Creates a new generation of states based on the best fitness results
        :return:
        """
        children = []
        for i in range(len(self.population)):
            for j in range(i + 1, len(self.population)):
                children.append(self.createChildState(self.population[i], self.population[j]))

        mutatedChildren = [self.mutate(copy.deepcopy(s)) for s in children]

        children = sorted(mutatedChildren, key=lambda s: s.fitness())

        tmp = [s for s in self.population]
        for s in children:
            tmp.append(s)
        tmp = sorted(tmp, key=lambda s: s.fitness())

        result = []
        for i in range(self._popSize):
            result.append(tmp[i])

        self.population = result

        self._currentGen += 1

    def getBestSol(self):
        return self.population[0]

    def validate(self):
        while self._currentGen != 1000:
            self._population = [State(self._boardSize) for _ in range(self._popSize)]
            self._currentGen += 1

            return self.getAvgFitness(), self.getStdevFitness()
