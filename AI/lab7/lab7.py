import csv
import numpy as np

rows = 497
partial_rows = rows * 4 // 5


def read_file(filename):
    values = []
    result = []

    with open(filename) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            record = [1, float(row[0]), float(row[1]), float(row[2]), float(row[3]), float(row[4])]
            output = [float(row[5])]
            values.append(record)
            result.append(output)

    return values, result


def get_weights(values, result):
    # W = (transposed(X) * X) ^ (-1) * transposed(X) * Y
    transposed_values = np.transpose(values)

    a = np.linalg.inv(np.matmul(transposed_values, values))
    b = np.matmul(a, transposed_values)
    c = np.matmul(b, result)

    return c


def get_predictions(values, weights):
    predicted = []

    for i in range(rows - partial_rows):
        p = 0
        for j in range(6):
            p += values[i][j] * weights[j][0]
        predicted.append([p])

    return predicted


def test_acc(predicted, actual):
    loss = 0
    for i in range(rows - partial_rows):
        row_loss = (actual[i][0] - predicted[i][0]) ** 2  # used squared loss for better precision
        loss += row_loss

    return loss


if __name__ == '__main__':
    values, result = read_file(r'H:\School\Facultate\AI\lab7\bdate2.csv')
    values_to_work_on = values[:partial_rows]
    result_to_work_on = result[:partial_rows]

    npvalues = np.array([np.array(v) for v in values_to_work_on])
    npresult = np.array([np.array(v) for v in result_to_work_on])

    w = get_weights(npvalues, npresult)

    values_for_testing = values[partial_rows:]
    result_for_testing = result[partial_rows:]

    p = get_predictions(values_for_testing, w)

    for i in range(rows - partial_rows):
        print("Predicted: ", p[i][0], "; Actual: ", result_for_testing[i][0])

    print("\nSquared loss: ")
    print(f'{test_acc(p, result_for_testing):0.41f}')
