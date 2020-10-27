from Evolutionary import Evolutionary
from HillClimbing import HillClimbing

import matplotlib.pyplot as plt

from ParticleSwarmOptimisation import PSO


def validateEvolutionary(size, maxGens, popSize, mutation, crossover):
    std = []
    mean = []
    for i in range(30):
        alg = Evolutionary(size, maxGens, popSize, mutation, crossover)
        std1, mean1 = alg.validate()
        std.append(std1)
        mean.append(mean1)
    std = plt.plot(std, color='m', label='Standard Deviation')
    mean = plt.plot(mean, color='y', label='Average')
    plt.legend(handles=[std[0], mean[0]])
    plt.show()


def validateHillClimbing(size, maxGens):
    std = []
    mean = []
    for i in range(30):
        alg = HillClimbing(size, maxGens)
        std1, mean1 = alg.validate()
        std.append(std1)
        mean.append(mean1)
    std = plt.plot(std, color='m', label='Standard Deviation')
    mean = plt.plot(mean, color='y', label='Average')
    plt.legend(handles=[std[0], mean[0]])
    plt.show()


def validatePSO(size, maxGens, popSize, weight, c1, c2, neighbourhoodSize):
    std = []
    mean = []
    for i in range(30):
        alg = PSO(size, maxGens, popSize, weight, c1, c2, neighbourhoodSize)
        std1, mean1 = alg.validate()
        std.append(std1)
        mean.append(mean1)
    std = plt.plot(std, color='m', label='Standard Deviation')
    mean = plt.plot(mean, color='y', label='Average')
    plt.legend(handles=[std[0], mean[0]])
    plt.show()


if __name__ == '__main__':
    validateEvolutionary(4, 50, 100, 0.2, 0.9)
    validateHillClimbing(4, 50)
