class FuzzySystem:
    # Receives variable descriptions and rules and outputs the defuzzified result of the system

    def __init__(self, rules):
        self.in_descriptions = {}
        self.out_description = None
        self.rules = rules

    def add_in_description(self, name, description):
        self.in_descriptions[name] = description

    def add_out_description(self, description):
        self.out_description = description

    def compute(self, input):
        fuzzy_values = self.compute_descriptions(input)
        rule_values = self.compute_rules_fuzzy(fuzzy_values)

        fuzzy_out_vars = [(list(description[0].values())[0], description[1]) for description in rule_values]

        total_weight = 0
        weight_sum = 0

        for var in fuzzy_out_vars:
            weight_sum += var[1]
            total_weight += self.out_description.defuzzify(var[0], var[1]) * var[1]

        return total_weight / weight_sum

    def compute_descriptions(self, input):
        return {
            var_name: self.in_descriptions[var_name].fuzzify(input[var_name])
            for var_name, val in input.items()
        }

    def compute_rules_fuzzy(self, fuzzy_values):
        # Returns the fuzzy output of all rules

        return [rule.evaluate(fuzzy_values) for rule in self.rules
                if rule.evaluate(fuzzy_values)[1] != 0]
