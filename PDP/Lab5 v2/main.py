import concurrent.futures
import threading


class MyGlobals:
    c = []
    mutexes = []

    @classmethod
    def create_lists(cls, n):
        cls.c = [0] * n
        cls.mutexes = [threading.Lock()] * n


class Polynomial:
    def __init__(self, coefficients: list):
        self.min = 0
        self.max = len(coefficients)

        for i in range(len(coefficients)):
            if coefficients[i] != 0:
                self.min = i
                break
        for i in range(len(coefficients) - 1, 0, -1):
            if coefficients[i] != 0:
                self.max = i + 1
                break

        self.coefficients = coefficients

    def __str__(self):
        res = ''

        for i in range(self.max - 1, self.min + 1, -1):
            if self.coefficients[i] != 0:
                res += str(self.coefficients[i]) + 'x^' + str(i) + ' + '

        return res[:-2]

    def __add__(self, other):
        length = max(self.max, other.max)
        res = []
        for i in range(length):
            res.append(0)
            try:
                res[i] += self.coefficients[i]
            except IndexError:
                pass
            try:
                res[i] += other.coefficients[i]
            except IndexError:
                pass
        return Polynomial(res)

    def __sub__(self, other):
        length = max(self.max, other.max)
        res = []
        for i in range(length):
            res.append(0)
            try:
                res[i] += self.coefficients[i]
            except IndexError:
                pass
            try:
                res[i] -= other.coefficients[i]
            except IndexError:
                pass
        return Polynomial(res)

    def __getitem__(self, key):
        return self.coefficients[key]

    def __setitem__(self, key, value):
        self.coefficients[key] = value

    __repr__ = __str__


def shift(p, degree):
    res = [0] * degree + p.coefficients
    return Polynomial(res)


def _compute_elem(p_elem, p_elem_index, q):
    for i in range(q.max - q.min):
        b_elem_index = i + q.min
        c_elem_index = b_elem_index + p_elem_index
        with MyGlobals.mutexes[c_elem_index]:
            MyGlobals.c[c_elem_index] += p_elem * q[b_elem_index]


def sequential_multiplication(p, q):
    MyGlobals.create_lists(p.max + q.max)
    for k in range(p.min, p.max):
        _compute_elem(p[k], k, q)
    return Polynomial(MyGlobals.c)


def parallelized_multiplication(p, q):
    MyGlobals.create_lists(p.max + q.max)
    threads = [None] * p.max
    for k in range(p.min, p.max):
        threads[k] = threading.Thread(target=_compute_elem, args=(p[k], k, q))

    for k in range(p.min, p.max):
        threads[k].start()

    for k in range(p.min, p.max):
        threads[k].join()

    return Polynomial(MyGlobals.c)


def karatsuba_sequential(p, q):
    if p.max < 2 or q.max < 2:
        return sequential_multiplication(p, q)

    length = min(p.max, q.max) // 2
    lowA = Polynomial(p.coefficients[:length])
    highA = Polynomial(p.coefficients[length:])
    lowB = Polynomial(q.coefficients[:length])
    highB = Polynomial(q.coefficients[length:])

    z1 = karatsuba_sequential(lowA, lowB)
    z2 = karatsuba_sequential(lowA + highA, lowB + highB)
    z3 = karatsuba_sequential(highA, highB)

    r1 = shift(z3, 2 * length)

    r2 = shift(z2 - z3 - z1, length)
    return r1 + r2 + z1


def karatsuba_parallelized(p, q, depth):
    if depth > 4:
        return karatsuba_sequential(p, q)

    if p.max < 2 or q.max < 2:
        return sequential_multiplication(p, q)

    length = min(p.max, q.max) // 2
    lowA = Polynomial(p.coefficients[:length])
    highA = Polynomial(p.coefficients[length:])
    lowB = Polynomial(q.coefficients[:length])
    highB = Polynomial(q.coefficients[length:])

    with concurrent.futures.ThreadPoolExecutor() as executor:
        z1 = executor.submit(karatsuba_parallelized, lowA, lowB, depth + 1).result()
        z2 = executor.submit(karatsuba_parallelized, lowA + highA, lowB + highB, depth + 1).result()
        z3 = executor.submit(karatsuba_parallelized, highA, highB, depth + 1).result()

    r1 = shift(z3, 2 * length)

    r2 = shift(z2 - z3 - z1, length)
    return r1 + r2 + z1


if __name__ == '__main__':
    p = Polynomial([0, 3, 2, 1, 5, 9, 8, 0, 1, 2, 0, 5, 6, 0, 0])
    print("p =", p)

    q = Polynomial([2, 0, 4, 4, 2, 0, 2, 0, 9, 5, 1, 3, 0, 8, 0, 0])
    print("q =", q)

    print("Sequential multiplication =", sequential_multiplication(p, q))
    print("Parallelized multiplication =", parallelized_multiplication(p, q))
    print("Karatsuba sequential multiplication =", karatsuba_sequential(p, q))
    print("Karatsuba parallelized multiplication =", karatsuba_parallelized(p, q, 1))
