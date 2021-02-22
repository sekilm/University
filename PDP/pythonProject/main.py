from graph import Graph
from graph_coloring import Coloring

if __name__ == '__main__':
    colors = ["BLUE", "RED", "YELLOW", "GREEN", "PURPLE", "ORANGE", "PINK", "BROWN", "TURQUOISE"]

    N = 10
    edges = [(0, 1), (0, 4), (0, 6), (1, 2), (1, 7), (2, 3), (2, 8), (3, 4), (3, 9), (4, 5), (5, 7), (5, 8), (6, 8),
             (6, 9), (7, 9)]

    graph = Graph(edges, N)

    color = Coloring(colors, graph)
    color.color_graph_threads()
    # color.color_graph_MPI()
