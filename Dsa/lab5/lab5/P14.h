#pragma once

#include <iostream>
#include <vector>
using namespace std;

typedef int TElem;

//computes the product of the k greatest elements from the vector.
//if k <= 0, throws an exception
//n is the number of descendants for the heap
long long computeProduct(vector<TElem> vector, int k, int n);