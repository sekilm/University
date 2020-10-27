import copy

from State import State


class HillClimbing:
    def __init__(self, boardSize, maxGeneration):
        self._boardSize = boardSize
        self._maxGen = maxGeneration

        self._currentGen = 0
        self._currentState = State(boardSize)

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
    def currentGen(self):
        return self._currentGen

    def getMinFitness(self):
        return self._currentState.fitness()

    def getAvgFitness(self):
        return self._currentState.fitness()

    def getStdevFitness(self):
        return 0

    def isSolved(self):
        return self.getMinFitness() == 0

    def getOneNeighbour(self, line1, line2, col1, col2):
        s = copy.deepcopy(self._currentState)
        s.m[line1][col1] = s.m[line2][col2]
        return s

    def generateAllCoordinates(self):
        coordinates = []
        for i in range(self._boardSize):
            for j in range(self._boardSize):
                for k in range(self._boardSize):
                    for l in range(self._boardSize):
                        coordinates.append((i, j, k, l))

        return coordinates

    def getAllNeighbours(self):
        neighbours = []

        coordinates = self.generateAllCoordinates()
        for c in coordinates:
            neighbours.append(self.getOneNeighbour(c[0], c[1], c[2], c[3]))

        return neighbours

    def newGeneration(self):
        neighbours = self.getAllNeighbours()
        m = min(x.fitness() for x in neighbours)

        if m < self._currentState.fitness():
            for i in neighbours:
                if m == i.fitness():
                    self._currentState = i
        else:
            self._currentState = State(self.boardSize)

        self._currentGen += 1

    def getBestSol(self):
        return self._currentState

    def validate(self):
        while self._currentGen != 1000:
            self._currentState = State(self._boardSize)
            self._currentGen += 1

            return self.getAvgFitness(), self.getStdevFitness()
