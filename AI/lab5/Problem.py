import itertools
import random
import statistics
from typing import Dict

from Ant import Ant


class ACO:
    def __init__(self, _boardSize, _popSize):
        self.boardSize = _boardSize
        self.popSize = _popSize

        permutations = list(itertools.permutations(list(range(1, self.boardSize + 1))))
        permutations = list(tuple(x) for x in permutations)

        self.graph: Dict[Dict[int]] = dict()
        for perm in permutations:
            self.graph[perm] = {other: 0 for other in permutations if other != perm}

        self.population = [Ant(self.boardSize, self.graph, random.choice(list(self.graph.keys()))) for _ in
                           range(self.popSize)]

    def currentGen(self):
        nr = 0
        for ant in self.population:
            if ant.isDone():
                nr += 1
        return nr

    def getMinFitness(self):
        fitnesses = [s.fitness() for s in self.population]
        return min(fitnesses)

    def getAvgFitness(self):
        return statistics.mean([s.fitness() for s in self.population])

    def getStdev(self):
        return round(statistics.stdev([s.fitness() for s in self.population]), 2)

    def isSolved(self):
        return self.getMinFitness() == 0

    def getBestSol(self):
        bestFitness = self.getMinFitness()
        for i in self.population:
            if i.fitness() == bestFitness:
                return i.path

    def moveAnt(self, ant):
        while not ant.isDone():
            ant.oneStep()

        for node in self.graph.keys():
            for neighbour in self.graph[node].keys():
                percentage = self.graph[node][neighbour] * 5 / 100
                self.graph[node][neighbour] -= percentage
                if self.graph[node][neighbour] < 0:
                    self.graph[node][neighbour] = 0

    def newGen(self):
        firstAnt = None
        for ant in self.population:
            if not ant.isDone():
                firstAnt = ant
                break
        if firstAnt is not None:
            self.moveAnt(firstAnt)
