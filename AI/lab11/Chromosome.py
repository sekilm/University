import math
import random


class Chromosome:

    def __init__(self, max_depth, terminals, functions, constants):
        self._constants = constants
        self._max_depth = max_depth
        self._terminals = terminals
        self._functions = functions
        self._representation = [0 for _ in range(2 ** (self._max_depth + 1) - 1)]
        self._fitness = 0
        self._accuracy = 0
        self._size = 0
        self.grow_expression()
        self._representation = [x for x in self.representation if x != 0]

    @property
    def representation(self):
        return self._representation

    @representation.setter
    def representation(self, value):
        self._representation = value

    @property
    def size(self):
        return self._size

    @size.setter
    def size(self, value):
        self._size = value

    @property
    def accuracy(self):
        return self._accuracy

    @accuracy.setter
    def accuracy(self, value):
        self._accuracy = value

    @property
    def fitness(self):
        return self._fitness

    @fitness.setter
    def fitness(self, value):
        self._fitness = value

    def get_terminal_by_position(self, pos):
        return self._terminals[self._representation[pos] - 1]

    def get_function_by_position(self, pos):
        return self._functions[-self._representation[pos] - 1]

    # initializes randomly an expression
    def grow_expression(self, pos=0, depth=0):
        if pos == 0 or depth < self._max_depth:
            if pos != 0 and random.random() < 0.4:
                # Assign the current node a positive value, making it a leaf node
                # The first node can't be a leaf one
                self._representation[pos] = random.randint(1, len(self._terminals))
                self.size = pos + 1
                return pos + 1
            else:
                # Assign the current node a negative value, making it a mathematical function node
                self._representation[pos] = -random.randint(1, len(self._functions))
                if self.get_function_by_position(pos) in ['cos', 'sin']:
                    # sin and cos are unary functions
                    child_end_index = self.grow_expression(pos + 1, depth + 1)
                    return child_end_index
                else:
                    # otherwise we have a binary function
                    first_child_end_index = self.grow_expression(pos + 1, depth + 1)
                    second_child_end_index = self.grow_expression(first_child_end_index, depth + 1)
                    return second_child_end_index
        else:
            # Max depth reached, we can only have leaf nodes
            self._representation[pos] = random.randint(1, len(self._terminals))
            self.size = pos + 1
            return pos + 1

    # the expression value for some specific terminals
    def evaluate_expression(self, pos, data_row):
        pos = min(pos, len(self._representation) - 1)
        if self._representation[pos] > 0:
            return data_row[self._representation[pos] - 1], pos
        elif self._representation[pos] < 0:
            node_function = self.get_function_by_position(pos)
            if node_function == '+':
                auxFirst = self.evaluate_expression(pos + 1, data_row)
                auxSecond = self.evaluate_expression(auxFirst[1] + 1, data_row)
                return auxFirst[0] + auxSecond[0], auxSecond[1]
            elif node_function == '-':
                auxFirst = self.evaluate_expression(pos + 1, data_row)
                auxSecond = self.evaluate_expression(auxFirst[1] + 1, data_row)
                return auxFirst[0] - auxSecond[0], auxSecond[1]
            elif node_function == '*':
                auxFirst = self.evaluate_expression(pos + 1, data_row)
                auxSecond = self.evaluate_expression(auxFirst[1] + 1, data_row)
                return auxFirst[0] * auxSecond[0], auxSecond[1]
            elif node_function == 'sin':
                aux = self.evaluate_expression(pos + 1, data_row)
                return math.sin(aux[0]), aux[1]
            elif node_function == 'cos':
                aux = self.evaluate_expression(pos + 1, data_row)
                return math.cos(aux[0]), aux[1]

    def get_output_class(self, data_row):
        output = self.evaluate_expression(0, data_row)[0]
        if output < 2:
            return 1
        if output < 4:
            return 2
        if output < 6:
            return 3
        if output < 8:
            return 4
        return 5

    def compute_fitness(self, dataset, labels):
        absolute_error = 0.0
        correct_values, total_values = 0, 0
        for i in range(len(dataset)):
            error = abs(labels[i] - self.get_output_class(dataset[i]))
            absolute_error += error
            if error == 0:
                correct_values += 1
            total_values += 1
        self.fitness = absolute_error
        self.accuracy = correct_values / total_values

    def traverse(self, pos):
        if self._representation[pos] > 0:
            return min(pos + 1, len(self._representation) - 1)
        else:
            return self.traverse(self.traverse(pos + 1))

    # function that allows using the chromosome's representation as a list
    def __getitem__(self, item):
        return self._representation[item]

    # same purpose as the one above
    def __setitem__(self, key, value):
        self._representation[key] = value

    def __len__(self):
        return len(self._representation)

    def __str__(self):
        result = ''
        for pos in range(len(self._representation)):
            if self.representation[pos] < 0:
                result += self.get_function_by_position(pos) + ' '
            else:
                result += self.get_terminal_by_position(pos) + ' '
        return result
