class Graph:
    def __init__(self, nodeNumber):
        self.nodes = []
        self.neighbours = []

        for i in range(nodeNumber):
            self.nodes.append(i)
            self.neighbours.append([])

    def addEdge(self, source, target):
        if source in self.nodes and target not in self.neighbours[source]:
            self.neighbours[source].append(target)

    def getNeighbours(self, node):
        return self.neighbours[node]

    def getSize(self):
        return len(self.nodes)

    def __str__(self):
        res = ""

        for i in range(len(self.neighbours)):
            res += str(i) + " -> " + str(self.neighbours[i]) + "\n"

        return res
