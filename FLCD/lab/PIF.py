class PIF:
    def __init__(self):
        self.items = []

    def genPIF(self, token, st_pos):
        self.items.append((token, st_pos))

    def __str__(self):
        s = ""
        for i in self.items:
            s += str(i[0]) + " -> " + str(i[1]) + "\n"
        return s
