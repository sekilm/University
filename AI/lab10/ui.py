import json

from controller import Controller
from descriptions import FuzzyDescription
from regions import *
from rules import FuzzyRule


class UI:
    def __init__(self, filename):
        data = self.read_data(filename)

        temperature = data[0]['temperature']
        humidity = data[0]['humidity']
        time = data[1]
        rules = data[2]

        self.controller = Controller(temperature, humidity, time, rules)

    def read_data(self, filename):
        # reads the data from the json file and return the description 

        with open(filename, 'r') as f:
            data = json.load(f)

            descriptions_in = {}
            for description, details in data['in_descriptions'].items():
                d = FuzzyDescription()
                for region in details:
                    if region[1] == 'trapezoid':
                        d.add_region(str(region[0]),
                                     trap_region(int(region[2][0]), int(region[2][1]), int(region[2][2]),
                                                 int(region[2][3])))
                    if region[1] == 'triangular':
                        d.add_region(str(region[0]),
                                     triang_region(int(region[2][0]), int(region[2][1]), int(region[2][2])))
                descriptions_in[description] = d

            descriptions_out = FuzzyDescription()
            for description, details in data['out_descriptions'].items():
                for region in details:
                    if region[1] == 'trapezoid':
                        if region[3] == 'inverse_line':
                            descriptions_out.add_region(str(region[0]),
                                                        trap_region(int(region[2][0]), int(region[2][1]),
                                                                    int(region[2][2]), int(region[2][3])),
                                                        inverse_line(int(region[4][0]), int(region[4][1])))
                        if region[3] == 'inverse_triangle':
                            descriptions_out.add_region(str(region[0]),
                                                        trap_region(int(region[2][0]), int(region[2][1]),
                                                                    int(region[2][2]), int(region[2][3])),
                                                        inverse_tri(int(region[4][0]), int(region[4][1]),
                                                                    int(region[4][2])))
                    if region[1] == 'triangular':
                        if region[3] == 'inverse_line':
                            descriptions_out.add_region(str(region[0]),
                                                        triang_region(int(region[2][0]), int(region[2][1]),
                                                                      int(region[2][2])),
                                                        inverse_line(int(region[4][0]), int(region[4][1])))
                        if region[3] == 'inverse_triangle':
                            descriptions_out.add_region(str(region[0]),
                                                        triang_region(int(region[2][0]), int(region[2][1]),
                                                                      int(region[2][2])),
                                                        inverse_tri(int(region[4][0]), int(region[4][1]),
                                                                    int(region[4][2])))

            rules = []
            for rule in data['rules']:
                r = FuzzyRule(rule['in'], rule['out'])
                rules.append(r)

            return descriptions_in, descriptions_out, rules


if __name__ == '__main__':
    ui = UI('problem.in')
    with open('input.in', 'r') as inf, open('output.out', 'w') as of:
        data = json.load(inf)
        for test_data in data:
            result = ui.controller.predict(test_data)
            of.write(result + '\n')
