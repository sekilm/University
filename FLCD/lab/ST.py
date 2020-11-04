from hashtable import HashTable


class SymbolTable:
    def __init__(self):
        self.table = HashTable()

    def insert(self, token):
        toAdd = True

        index = self.table.hash(token)
        node = self.table.slots[index]

        while node is not None and node.identifier != token:
            node = node.next

        if node is not None and node.identifier == token:
            toAdd = False

        if toAdd:
            self.table.insert(token)

    def find(self, token):
        return self.table.find(token)

    def __str__(self):
        s = ""
        for i in self.table.slots:
            if i is not None:
                j = i
                while j is not None:
                    s += str(j.identifier) + " -> " + str(self.table.find(j.identifier)) + "\n"
                    j = j.next
            else:
                pass
        return s
