import concurrent.futures
import threading


class MyGlobals:
    coeff = []
    mutexes = []

    @classmethod
    def create_lists(cls, n):
        cls.coeff = [0] * n
        cls.mutexes = [threading.Lock()] * n


class Polynomial:
    def __init__(self, coefficients):
        self.min = 0
        for i in range(len(coefficients)):
            if coefficients[i] != 0:
                self.min = i
                break

        self.max = len(coefficients) - 1
        for i in range(len(coefficients) - 1, 1, -1):
            if coefficients[i] != 0:
                self.max = i
                break

        self.coefficients = coefficients

    def __str__(self):
        res = ''

        for i in range(self.max, self.min - 1, -1):
            if self.coefficients[i] != 0:
                res += str(self.coefficients[i]) + 'x^' + str(i) + ' + '

        return res[:-2]

    def __add__(self, other):
        length = max(self.max, other.max)
        res = []
        for i in range(length + 1):
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
        for i in range(length + 1):
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
    for i in range(len(q.coefficients)):
        q_elem_index = i + q.min
        r_elem_index = q_elem_index + p_elem_index
        with MyGlobals.mutexes[r_elem_index]:
            MyGlobals.coeff[r_elem_index] += p_elem * q[q_elem_index]


def sequential_multiplication2(p, q):
    MyGlobals.create_lists(p.max + q.max)
    for i in range(len(p.coefficients)):
        for j in range(len(q.coefficients)):
            MyGlobals.coeff[i + j] += p[i] * q[j]
    return Polynomial(MyGlobals.coeff)


def sequential_multiplication(p, q):
    MyGlobals.create_lists(p.max + q.max)
    for k in range(p.min, p.max):
        _compute_elem(p[k], k, q)
    return Polynomial(MyGlobals.coeff)


def parallelized_multiplication(p, q):
    MyGlobals.create_lists(p.max + q.max)
    threads = []
    for _ in range(p.max):
        threads.append(None)

    for k in range(p.min, p.max):
        threads[k] = threading.Thread(target=_compute_elem, args=(p[k], k, q))

    for k in range(p.min, p.max):
        threads[k].start()

    for k in range(p.min, p.max):
        threads[k].join()

    return Polynomial(MyGlobals.coeff)


def karatsuba_sequential(p, q):
    if p.max < 2 or q.max < 2:
        return sequential_multiplication(p, q)

    length = min(p.max, q.max) // 2
    lowP = Polynomial(p.coefficients[:length])
    highP = Polynomial(p.coefficients[length:])
    lowQ = Polynomial(q.coefficients[:length])
    highQ = Polynomial(q.coefficients[length:])

    z1 = karatsuba_sequential(lowP, lowQ)
    z2 = karatsuba_sequential(lowP + highP, lowQ + highQ)
    z3 = karatsuba_sequential(highP, highQ)

    r1 = shift(z3, 2 * length)

    r2 = shift(z2 - z3 - z1, length)
    return r1 + r2 + z1


def karatsuba_parallelized(p, q, depth):
    if depth > 4:
        return karatsuba_sequential(p, q)

    if p.max < 2 or q.max < 2:
        return sequential_multiplication(p, q)

    length = min(p.max, q.max) // 2
    lowP = Polynomial(p.coefficients[:length])
    highP = Polynomial(p.coefficients[length:])
    lowQ = Polynomial(q.coefficients[:length])
    highQ = Polynomial(q.coefficients[length:])

    with concurrent.futures.ThreadPoolExecutor() as executor:
        z1 = executor.submit(karatsuba_parallelized, lowP, lowQ, depth + 1).result()
        z2 = executor.submit(karatsuba_parallelized, lowP + highP, lowQ + highQ, depth + 1).result()
        z3 = executor.submit(karatsuba_parallelized, highP, highQ, depth + 1).result()

    r1 = shift(z3, 2 * length)

    r2 = shift(z2 - z3 - z1, length)
    return r1 + r2 + z1


if __name__ == '__main__':
    p = Polynomial([1, 3, 2, 1, 5, 9, 8, 2, 1, 2, 1, 5, 6, 3, 4, 4])
    print("p =", p)

    q = Polynomial([2, 3, 4, 4, 2, 2, 2, 1, 9, 5, 1, 3, 1, 8, 2, 1])
    print("q =", q)

    print("Sequential multiplication =", sequential_multiplication(p, q))
    print("Parallelized multiplication =", parallelized_multiplication(p, q))
    print("Karatsuba sequential multiplication =", karatsuba_sequential(p, q))
    print("Karatsuba parallelized multiplication =", karatsuba_parallelized(p, q, 1))
