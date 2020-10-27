from Controller import Controller


class UI:
    def __init__(self):
        self.n = int(input("Give a board size (n x n): "))
        self.alg = int(input("Give the desired algorithm's number: "))
        self.maxGens = int(input("Give the maximum number of generations: "))

        if self.alg == 1:
            self.popSize = int(input("Give the population size: "))
            self.mutationChance = float(input("Give the mutation probability: "))
            self.crossoverChance = float(input("Give the crossover probability: "))

            self.controller = Controller(self.alg, self.n, self.maxGens, self.popSize, self.mutationChance,
                                         self.crossoverChance)

        elif self.alg == 2:
            self.controller = Controller(self.alg, self.n, self.maxGens)

        elif self.alg == 3:
            self.popSize = int(input("Give the population size: "))
            self.weight = float(input("Give the starting weight: "))
            self.c1 = int(input("Give the first coordinate: "))
            self.c2 = int(input("Give the second coordinate: "))
            self.neighbourhoodSize = int(input("Give the neighbourhood size: "))

            self.controller = Controller(self.alg, self.n, self.maxGens, self.popSize, 0, 0, self.weight, self.c1, self.c2,
                                         self.neighbourhoodSize)

    def run(self):
        while 1:
            d = self.controller.runOneStep()
            for key in d.keys():
                print(key + " = " + str(d[key]))
            print()
            if d["reachedMaxGen"] or d["isSolution"]:
                break
