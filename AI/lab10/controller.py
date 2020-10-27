from system import FuzzySystem


class Controller:
    def __init__(self, temperature, humidity, time, rules):
        self.system = FuzzySystem(rules)
        self.system.add_in_description('temperature', temperature)
        self.system.add_in_description('humidity', humidity)
        self.system.add_out_description(time)

    def predict(self, input):
        return "If we have the humidity: " + str(input['humidity']) + \
               " and the temperature: " + str(input['temperature']) + \
               " it will probably have the operating time: " + str(self.system.compute(input))
