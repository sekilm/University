import math
import random


class Ant:
    def __init__(self, _size, _graph, startPos):
        self.size = _size
        self.currentPos = startPos
        self.path = [self.currentPos]
        self.graph = _graph

    def fitness(self):
        board = []
        for i in range(0, len(self.path), 2):
            try:
                board.append(list(zip(self.path[i], self.path[i + 1])))
            except IndexError:
                pass

        mistakes = 0

        column = 0
        while column < self.size:
            for i in range(len(board)):
                for j in range(i + 1, len(board)):
                    if i != j:
                        if board[i][column][0] == board[j][column][0]:
                            mistakes += 1
                        if board[i][column][1] == board[j][column][1]:
                            mistakes += 1

            column += 1

        mylist = []
        for i in range(len(board)):
            for j in range(self.size):
                if board[i][j] not in mylist:
                    mylist.append(board[i][j])

        return mistakes + abs(len(mylist) - self.size ** 2)

    def isDone(self):
        return len(self.path) == self.size * 2

    def oneStep(self):
        bestNeighbour = None
        bestValue = math.inf
        for key, value in self.graph[self.currentPos].items():
            if value < bestValue:
                bestNeighbour = key
                bestValue = value

        if random.random() > 0.8 and bestNeighbour is not None:
            self.path.append(bestNeighbour)
            self.currentPos = bestNeighbour
        else:
            self.currentPos = random.choice(list(self.graph[self.currentPos]))
            self.path.append(self.currentPos)

        self.update(len(self.path) - 2, len(self.path) - 1)

    def update(self, from_perm, to_perm):
        self.graph[self.path[from_perm]][self.path[to_perm]] += 100

    def solution(self):
        board = []
        for i in range(0, len(self.path), 2):
            try:
                board.append(list(zip(self.path[i], self.path[i + 1])))
            except IndexError:
                pass
        return board
