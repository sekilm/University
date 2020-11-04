class FiniteAutomata:
    def __init__(self, file):
        self.filename = file
        self.Q = []
        self.E = []
        self.P = {}
        self.q0 = ''
        self.F = []

        self.read()

    def read(self):
        with open(self.filename, 'r') as file:
            line = file.readline()
            self.Q = [i.strip() for i in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]

            line = file.readline()
            self.E = [i.strip() for i in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]

            self.q0 = file.readline().split('=')[1].strip()

            line = file.readline()
            self.F = [i.strip() for i in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]

            remaining_lines = []
            for line in file:
                remaining_lines.append(line)

            rl = ''.join(remaining_lines)
            S = [t.strip() for t in rl.strip().split('=')[1].strip()[1:-1].strip().split(',')]

            transitions = []
            for i in range(0, len(S), 2):
                transitions.append(S[i] + ',' + S[i + 1])

            for t in transitions:
                lhs, rhs = t.split('->')
                state2 = rhs.strip()
                state1, route = [t.strip() for t in lhs.strip()[1:-1].split(',')]

                self.P[(state1, route)] = state2

    def check_Q(self):
        for i in range(len(self.Q)):
            for j in range(len(self.Q)):
                if i != j and self.Q[i] == self.Q[j]:
                    return False
        return True

    def check_E(self):
        for i in range(len(self.E)):
            for j in range(len(self.E)):
                if i != j and self.E[i] == self.E[j]:
                    return False
        return True

    def check_P(self):
        for k, v in self.P.items():
            if k[0] in self.Q and k[1] in self.E and v in self.Q:
                return True
        return False

    def check_q0(self):
        return self.q0 is not list and self.q0 is not dict

    def check_F(self):
        for i in range(len(self.F)):
            for j in range(len(self.F)):
                if i != j and self.F[i] == self.F[j]:
                    return False
        return True

    def print_Q(self):
        s = ""
        for i in range(len(self.Q)):
            s += self.Q[i]
            if i != len(self.Q) - 1:
                s += ", "
        return s

    def print_E(self):
        s = ""
        for i in range(len(self.E)):
            s += self.E[i]
            if i != len(self.E) - 1:
                s += ", "
        return s

    def print_P(self):
        s = ""
        for key, value in self.P.items():
            s += "(" + str(key[0]) + ", " + str(key[1]) + ")" + " -> " + str(value) + ", "
        return s[:-2]

    def print_q0(self):
        s = str(self.q0)
        return s

    def print_F(self):
        s = ""
        for i in range(len(self.F)):
            s += self.F[i]
            if i != len(self.F) - 1:
                s += ", "
        return s


if __name__ == '__main__':
    fa = FiniteAutomata("fa.in")

    print("1 - Show the set of states")
    print("2 - Show the alphabet")
    print("3 - Show the transitions")
    print("4 - Show the initial state")
    print("5 - Show the final states")
    print("x - Exit\n")

    while True:
        choice = input()
        if choice == "1":
            print(fa.print_Q())
            if fa.check_Q() is False:
                print("The set of states should contain unique states")
        if choice == "2":
            print(fa.print_E())
            if fa.check_E() is False:
                print("The alphabet should contain unique symbols")
        if choice == "3":
            print(fa.print_P())
            if fa.check_P() is False:
                print("The transitions should contain only triplets")
        if choice == "4":
            print(fa.print_q0())
            if fa.check_q0() is False:
                print("The initial state should be a single state")
        if choice == "5":
            print(fa.print_F())
            if fa.check_F() is False:
                print("The set of final states should contain unique states")
        if choice == "x":
            break
