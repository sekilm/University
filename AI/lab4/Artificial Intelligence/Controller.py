from Evolutionary import Evolutionary
from HillClimbing import HillClimbing
from ParticleSwarmOptimisation import PSO


class Controller:
    def __init__(self, algorithm, boardSize, maxGens, popSize=1, mutationChance=0, crossoverChance=0,
                 weight=0, c1=0, c2=0, neighbourhoodSize=0):
        self._algorithm = algorithm
        self._boardSize = boardSize
        self._maxGens = maxGens

        if algorithm == 1:
            self._popSize = popSize
            self._mutationChance = mutationChance
            self._crossoverChance = crossoverChance
            self._problem = Evolutionary(boardSize, maxGens, popSize, mutationChance, crossoverChance)

        elif algorithm == 2:
            self._problem = HillClimbing(boardSize, maxGens)

        elif algorithm == 3:
            self._weight = weight
            self._c1 = c1
            self._c2 = c2
            self._neighbourhoodSize = neighbourhoodSize
            self._problem = PSO(boardSize, maxGens, popSize, weight, c1, c2, neighbourhoodSize)

    def runOneStep(self):
        self._problem.newGeneration()

        if self._problem.currentGen == self._maxGens:
            reachedMaxGen = True
        else:
            reachedMaxGen = False
        isSolution = self._problem.isSolved()

        d = {"reachedMaxGen": reachedMaxGen, "isSolution": isSolution, "currentGen": self._problem.currentGen,
                "minFitness": self._problem.getMinFitness(), "avgFitness": self._problem.getAvgFitness(),
                "stdev": self._problem.getStdevFitness(), "bestSol": self._problem.getBestSol()}
        return d
