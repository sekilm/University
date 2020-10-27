def trap_region(a, b, c, d):
    return lambda x: max(0, min((x - a) / (b - a), 1, (d - x) / (d - c)))


def triang_region(a, b, c):
    return lambda x: max(0, min((x - a) / (b - a), 1, (c - x) / (c - b)))


def inverse_line(a, b):
    return lambda val: val * (b - a) + a


def inverse_tri(a, b, c):
    return lambda val: (inverse_line(a, b)(val) + inverse_line(c, b)(val)) / 2
