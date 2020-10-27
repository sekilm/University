class Node:
    def __init__(self, identifier):
        self.identifier = identifier
        self.next = None


class HashTable:
    def __init__(self):
        self.capacity = 25654
        self.size = 0
        self.slots = [None for _ in range(self.capacity)]

    def hash(self, identifier):
        ascii_values = [ord(i) for i in identifier]

        hash_val = sum(ascii_values)

        return hash_val % self.capacity

    def insert(self, identifier):
        self.size += 1
        idx = self.hash(identifier)
        node = self.slots[idx]

        if node is None:
            self.slots[idx] = Node(identifier)
            return

        prev = node
        while node is not None:
            prev = node
            node = node.next

        prev.next = Node(identifier)

    def find(self, identifier):
        index = self.hash(identifier)
        node = self.slots[index]

        while node is not None and node.identifier != identifier:
            node = node.next

        if node is None:
            return None
        else:
            ascii_values = [ord(i) for i in identifier]
            return sum(ascii_values)


# if __name__ == '__main__':
#     ht = HashTable()
#     ht.insert("lemonade")
#     ht.insert("water")
#     ht.insert("beer")
#     ht.insert("juice")
#
#     print(ht.find("lemonade"))
#     print(ht.find("water"))
#     print(ht.find("beer"))
#     print(ht.find("juice"))
