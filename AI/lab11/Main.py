from Controller import read_data, genetic_search

if __name__ == '__main__':
    dataset, labels = read_data('input.in')

    pop_size = int(input("Population size = ") or 1000)
    replacement_percentage = float(input("Replacement percentage = ") or 0.2)
    tournament_percentage = float(input("Tournament percentage = ") or 0.05)
    mutation_chance = float(input("Mutation chance = ") or 0.1)
    epsilon = float(input("Epsilon target = ") or 0.65)
    genetic_search(dataset, labels, pop_size, replacement_percentage, tournament_percentage, mutation_chance, epsilon)
