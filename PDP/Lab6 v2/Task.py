import threading

finalPath = []
mutex = threading.RLock()


class Task:
    def __init__(self, graph, startNode):
        self.graph = graph
        self.startNode = startNode
        self.path = []

    def visit(self, node):
        self.path.append(node)

        global mutex, finalPath
        with mutex:
            if finalPath:
                return

        if len(self.path) == self.graph.getSize():
            if self.startNode in self.graph.getNeighbours(node):
                self.path.append(self.startNode)

                with mutex:
                    finalPath = self.path
            return

        for neighbour in self.graph.getNeighbours(node):
            if neighbour not in self.path:
                self.visit(neighbour)

    def run(self):
        self.visit(self.startNode)
