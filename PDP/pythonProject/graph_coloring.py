import threading
import numpy
from mpi4py import MPI

from graph import Graph

NR_THREADS = 5


class Coloring:
    def __init__(self, colors, graph: Graph):
        self.__colors = colors
        self.__graph = graph

    def __lock_neighbors(self, vertex):
        neighbors = self.__graph.neighbors[vertex]
        for v in neighbors:
            self.__graph.locks[v].acquire()

    def __unlock_neighbors(self, vertex):
        neighbors = self.__graph.neighbors[vertex]
        for v in neighbors:
            self.__graph.locks[v].release()

    def __get_neighbors_colors(self, vertex, result):
        neighbors = self.__graph.neighbors[vertex]
        assigned = set()
        for v in neighbors:
            if result[v] != -1 and result[v] not in assigned:
                assigned.add(result[v])
        return assigned

    def thread_function(self, result, start, end):
        if end > self.__graph.size:
            end = self.__graph.size
        for vertex in range(start, end):
            self.__lock_neighbors(vertex)
            assigned = self.__get_neighbors_colors(vertex, result)

            color = 0
            while color in assigned:
                color += 1

            result[vertex] = color
            self.__unlock_neighbors(vertex)
        return result

    def color_graph_threads(self):
        step = (self.__graph.size + 1) // NR_THREADS
        if step == 0:
            step = 1
        threads = []
        counter = 0
        result = [-1 for _ in range(self.__graph.size)]

        while counter < self.__graph.size:
            start = counter
            end = counter + step
            counter += step
            if end + step > self.__graph.size + 1:
                end = self.__graph.size + 1
                counter = self.__graph.size + 2
            t = threading.Thread(target=self.thread_function, args=(result, start, end))
            threads.append(t)
            t.start()

        for t in threads:
            t.join()

        for v in range(len(result)):
            print("Color assigned to vertex", v, "is", self.__colors[result[v]])

    def color_graph_MPI(self):
        comm = MPI.COMM_WORLD
        rank = comm.Get_rank()
        size = comm.Get_size()
        step = (self.__graph.size + 1) // size
        if step == 0:
            step = 1
        result = numpy.array([-1 for _ in range(self.__graph.size)])

        start = 0
        end = 0
        if rank == 0:
            start = 0
            end = step
            self.thread_function(result, start, end)

            comm.isend(result, 1)
            comm.isend(end, 1)

        elif rank == size - 1:
            comm.irecv(result, rank-1)
            comm.irecv(end, rank-1)

            start = end
            end = self.__graph.size

            self.thread_function(result, start, end)

            for v in range(self.__graph.size):
                print("Color assigned to vertex", v, "is", self.__colors[result[v]])
        else:
            comm.irecv(result, rank - 1)
            comm.irecv(end, rank - 1)

            start = end
            end += step
            dest = rank + 1

            self.thread_function(result, start, end)
            comm.isend(result, dest)
            comm.isend(end, dest)

