import concurrent.futures
import random

from Graph import Graph
import Task


def generateGraph(size):
    graph = Graph(size)
    nodes = graph.nodes
    random.shuffle(nodes)

    for i in range(1, len(nodes)):
       graph.addEdge(nodes[i - 1], nodes[i])
    graph.addEdge(nodes[-1], nodes[0])

    for i in range(30):
        graph.addEdge(random.randint(0, size - 1), random.randint(0, size - 1))

    return graph


def hamiltonianPath(graph, threadNumber):
    with Task.mutex:
        Task.finalPath = []
    with concurrent.futures.ThreadPoolExecutor(threadNumber) as threadPool:
        nodes = graph.nodes
        random.shuffle(nodes)
        for node in nodes:
            threadPool.submit(Task.Task(graph, node).run)


if __name__ == '__main__':
    graph = generateGraph(30)
    hamiltonianPath(graph, 5)
    print(graph)

    with Task.mutex:
        print("Hamiltonian path:", Task.finalPath)
