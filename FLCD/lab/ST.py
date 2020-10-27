from lab2 import HashTable


class SymbolTable:
    def __init__(self):
        self.table = HashTable()

    def insert(self, token):
        self.table.insert(token)

    def find(self, token):
        self.table.find(token)

    def __str__(self):
        s = ""
        for i in self.table.slots:
            if i is not None:
                s += str(i.identifier) + " -> " + str(self.table.find(i.identifier)) + "\n"
            else:
                pass
        return s
