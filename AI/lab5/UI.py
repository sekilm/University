from Controller import Controller


class Console:
    def __init__(self):
        self.n = int(input("Give a board size (n x n): "))
        self.popSize = int(input("Give the population size: "))

        self.controller = Controller(self.n, self.popSize)

    def run(self):
        while 1:
            d = self.controller.runOneStep()
            for key in d.keys():
                print(key + " = " + str(d[key]))
            print()
            if d["reachedMaxGen"] or d["isSolution"]:
                break


if __name__ == '__main__':
    c = Console()
    c.run()
