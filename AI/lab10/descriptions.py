class FuzzyDescription:
    # Encapsulates a description of a fuzzy variable

    def __init__(self):
        self.regions = {}
        self.inverse = {}

    def add_region(self, var_name, membership_function, inverse=None):
        # Adds a region with a given membership function

        self.regions[var_name] = membership_function
        self.inverse[var_name] = inverse

    def fuzzify(self, value):
        # Return the fuzzified values for each region

        return {name: membership_function(value)
                for name, membership_function in self.regions.items()}

    def defuzzify(self, var_name, value):
        return self.inverse[var_name](value)
