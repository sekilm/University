import copy
import logging
from functools import reduce

import numpy

from Chromosome import *
import random

MAX_DEPTH = 7
TERMINALS = [str(i) for i in range(-165, 166, 15)]
FUNCTIONS = ['+', '-', '*', 'sin', 'cos']
UNARY_FUNCTIONS = ['sin', 'cos']
BINARY_FUNCTIONS = ['+', '-', '*']
CONSTANTS = []
CLASSES = {
    'Slight-Left-Turn': 0,
    'Move-Forward': 1,
    'Slight-Right-Turn': 2,
    'Sharp-Right-Turn': 3
}


def read_data(file):
    dataset, labels = [], []
    with open(file, 'r') as f:
        for line in f:
            clean_line = line.strip().split(',')
            *data_row, label = clean_line
            dataset.append([float(x) for x in data_row])
            labels.append(CLASSES[label])
    return dataset, labels


def crossover(first_parent, second_parent):
    offspring = Chromosome(max_depth=MAX_DEPTH, terminals=TERMINALS, functions=FUNCTIONS, constants=CONSTANTS)
    start_first_parent = random.randint(0, first_parent.size - 1)
    end_first_parent = first_parent.traverse(start_first_parent)

    start_second_parent = random.randint(0, second_parent.size - 1)
    end_second_parent = second_parent.traverse(start_second_parent)

    offspring.representation = copy.deepcopy(first_parent[:start_first_parent] +
                                             second_parent[start_second_parent:end_second_parent] +
                                             first_parent[end_first_parent:])
    offspring.size = len(offspring)
    return offspring


def mutate(chromosome):
    offspring = Chromosome(max_depth=MAX_DEPTH, terminals=TERMINALS, functions=FUNCTIONS, constants=CONSTANTS)
    pos = random.randint(0, chromosome.size - 1)
    offspring.representation = chromosome[:]
    offspring.size = chromosome.size
    if offspring[pos] > 0:
        offspring[pos] = random.randint(1, len(TERMINALS))
    else:
        node_function = offspring.get_function_by_position(pos)
        if node_function in UNARY_FUNCTIONS:
            offspring[pos] = 0 if node_function == 'cos' else -1
        elif node_function in BINARY_FUNCTIONS:
            offspring[pos] = -random.choice([x for x in [2, 3, 4] if x != FUNCTIONS.index(node_function)])

    return offspring


def genetic_search(dataset, labels, pop_size=1000, replacement_percentage=0.2, tournament_percentage=0.05,
                   mutation_chance=0.1, epsilon=0.65):
    dataset = [row + CONSTANTS for row in dataset]
    logging.basicConfig(filename='output.out', level=logging.DEBUG)
    logging.warning('Generating initial population tool...')

    population = [Chromosome(max_depth=MAX_DEPTH, terminals=TERMINALS, functions=FUNCTIONS, constants=CONSTANTS)
                  for _ in range(pop_size)]

    for i in population:
        i.compute_fitness(dataset, labels)

    tournament_size = int(pop_size * tournament_percentage)
    replacement_size = int(pop_size * replacement_percentage)

    get_best_fit = lambda a, b: a if a.fitness < b.fitness else b

    epoch_index = 0
    best_individual = None
    while best_individual is None or best_individual.accuracy < epsilon:
        logging.warning(f'Starting epoch {epoch_index + 1}')
        try:
            max_fitness = max(i.fitness for i in population)
            prob_distribution = [max_fitness - i.fitness for i in population]
            prob_distribution = [float(i) / sum(prob_distribution) for i in prob_distribution]
        except ZeroDivisionError:
            logging.error(f'Stuck in local optimum {best_individual.fitness} {best_individual.accuracy}')
            assert False

        new_gen = []
        for i in range(replacement_size):
            selected_pop = list(
                numpy.random.choice(population, size=tournament_size, replace=False, p=prob_distribution)) + \
                           list(numpy.random.choice(population, size=tournament_size, replace=False,
                                                    p=prob_distribution))

            first_parent = reduce(get_best_fit, selected_pop[:tournament_size])
            second_parent = reduce(get_best_fit, selected_pop[tournament_size:])

            offspring = crossover(first_parent, second_parent)

            if random.random() < mutation_chance:
                offspring = mutate(offspring)

            new_gen.append(offspring)
        logging.warning("Generated offsprings.")

        population.sort(key=lambda x: x.fitness)
        population = population[:pop_size]

        if best_individual is None or best_individual.accuracy < population[0].accuracy:
            best_individual = population[0]

        logging.warning(f'Best individual {best_individual.fitness}, {best_individual.accuracy} vs {epsilon}')
        epoch_index += 1

    logging.warning(f'Population size is {pop_size} with mutation chance = {mutation_chance}, replacement percentage = '
                    f'{replacement_percentage} and tournament percentage = {tournament_percentage}')
    logging.warning(
        f'Best individual: {best_individual} with fitness = {best_individual.fitness} and accuracy = {best_individual.accuracy}')
