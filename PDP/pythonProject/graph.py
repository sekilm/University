import threading


class Graph:
    def __init__(self, edges, N):
        self.size = N
        self.neighbors = {}
        self.locks = {}

        # add edges to the undirected graph
        for (src, dest) in edges:
            if src not in self.neighbors:
                self.neighbors[src] = []
                self.locks[src] = threading.Lock()
            if dest not in self.neighbors:
                self.neighbors[dest] = []
                self.locks[dest] = threading.Lock()

            self.neighbors[src].append(dest)
            self.neighbors[dest].append(src)
