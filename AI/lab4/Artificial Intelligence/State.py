import itertools
import random


class State:
    def __init__(self, size):
        self._size = size

        a = itertools.product(range(1, size + 1), range(1, size + 1))
        a = [x for x in a]
        random.shuffle(a)

        self.m = []
        for i in range(size):
            self.m.append([])
            for j in range(size):
                self.m[i].append(a[0])
                a = a[1:]

    def __str__(self):
        return '\n' + '\n'.join(' | '.join(map(str, row)) for row in self.m)

    __repr__ = __str__

    @property
    def board(self):
        return self.m

    @board.setter
    def board(self, value):
        self.m = value

    @property
    def size(self):
        return self._size

    def fitness(self):
        mistakes = 0
        line = 0
        while line < self._size:
            for i in range(self._size):
                for j in range(i + 1, self._size):
                    if i != j:
                        if self.m[line][i][0] == self.m[line][j][0]:
                            mistakes += 1
                        if self.m[line][i][1] == self.m[line][j][1]:
                            mistakes += 1
            line += 1

        column = 0
        while column < self._size:
            for i in range(self._size):
                for j in range(i + 1, self._size):
                    if i != j:
                        if self.m[i][column][0] == self.m[j][column][0]:
                            mistakes += 1
                        if self.m[i][column][1] == self.m[j][column][1]:
                            mistakes += 1

            column += 1

        mylist = []
        for i in range(self.size):
            for j in range(self.size):
                if self.m[i][j] not in mylist:
                    mylist.append(self.m[i][j])

        return mistakes + abs(len(mylist) - self._size ** 2)

    def isSolution(self):
        return self.fitness() == 0

