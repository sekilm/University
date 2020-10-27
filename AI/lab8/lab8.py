import copy
import csv
import numpy as np
import matplotlib.pyplot as mpl

rows = 497
partial_rows = rows * 4 // 5


def read_file(filename):
    values = []
    result = []

    with open(filename) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            record = [float(row[0]), float(row[1]), float(row[2]), float(row[3]), float(row[4])]
            output = [float(row[5])]
            values.append(record)
            result.append(output)

    return values, result


# activation function
def linear(a):
    return a


# derivative of the activation function
def linear_derivative(_):
    return 1.0


class ANN:
    def __init__(self, input_data, output_data, hidden_neurons):
        self.input_layer = input_data
        self.output_layer = output_data
        self.neurons = hidden_neurons

        self.weights1 = np.asarray(np.random.rand(self.input_layer.shape[1], self.neurons))
        self.weights2 = np.asarray(np.random.rand(self.neurons, 1))
        self.layer1 = None
        self.output = None
        self.loss = []

    def feed_forward(self):
        self.layer1 = linear(np.dot(self.input_layer, self.weights1))
        self.output = linear(np.dot(self.layer1, self.weights2))

    def backpropagation(self, learning_rate):
        diff = 2 * (self.output_layer - self.output)

        d_weights2 = np.asarray(np.dot(self.layer1.T, (diff * linear_derivative(self.output))))
        d_weights1 = np.asarray(np.dot(self.input_layer.T, (np.dot(diff * linear_derivative(self.output),
                                                                   self.weights2.T) * linear_derivative(self.layer1))))

        self.weights1 += (1 / self.input_layer.shape[0]) * learning_rate * d_weights1
        self.weights2 += (1 / self.input_layer.shape[0]) * learning_rate * d_weights2

        self.loss.append(sum((self.output_layer - self.output) ** 2))

    def compute_value(self, row):
        layer1 = linear(np.dot(row, self.weights1))
        output = linear(np.dot(layer1, self.weights2))
        return output


if __name__ == '__main__':
    values, result = read_file(r'H:\School\Facultate\AI\lab8\bdate.csv')
    values_to_work_on = values[:partial_rows]
    result_to_work_on = result[:partial_rows]

    npvalues = np.array(values_to_work_on)
    npresult = np.array(result_to_work_on)

    values_for_testing = values[partial_rows:]
    result_for_testing = result[partial_rows:]

    testnpvalues = np.array(values_for_testing)
    testnpresult = np.array(result_for_testing)

    ann = ANN(npvalues, npresult, 10)

    for i in range(partial_rows):
        ann.feed_forward()
        ann.backpropagation(0.00001)
        ann.input_layer = copy.deepcopy(npvalues)
        ann.output_layer = copy.deepcopy(npresult)

    print(ann.output)
    mpl.plot(list(range(partial_rows)), ann.loss, label='loss value vs iteration')
    mpl.xlabel('Iterations')
    mpl.ylabel('loss function')
    mpl.legend()
    mpl.show()

    loss = 0
    for i in range(rows - partial_rows):
        value = ann.compute_value(testnpvalues[i])[0]
        loss += abs((testnpresult[i][0] - value))
        print(f'Expected {testnpresult[i][0]}--- Actual {value}')

    print(f'Squared loss: {loss / (rows - partial_rows):.5f}')
