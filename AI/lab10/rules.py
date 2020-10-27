class FuzzyRule:
    """
        Define a conjunctive fuzzy rule
        X and Y and ... => Z
    """

    def __init__(self, input, out):
        """
            Receives the set of inputs and expected output
        """
        self.input = input
        self.output = out

    def evaluate(self, input):
        """
            Receives a dictionary of all the input values and returns the conjunction
            of their values
        """
        return [self.output, min([input[description_name][var_name] for description_name, var_name in self.input.items()])]
