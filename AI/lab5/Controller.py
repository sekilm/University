from Problem import ACO


class Controller:
    def __init__(self, _boardSize, _popSize):
        self.boardSize = _boardSize
        self.popSize = _popSize
        self.problem = ACO(_boardSize, _popSize)

    def runOneStep(self):
        self.problem.newGen()

        if self.problem.currentGen() == self.popSize:
            reachedMaxGen = True
        else:
            reachedMaxGen = False
        isSolution = self.problem.isSolved()

        d = {"reachedMaxGen": reachedMaxGen, "isSolution": isSolution, "currentGen": self.problem.currentGen(),
                "minFitness": self.problem.getMinFitness(), "avgFitness": self.problem.getAvgFitness(),
                "stdev": self.problem.getStdev(), "bestSol": self.problem.getBestSol()}
        return d
