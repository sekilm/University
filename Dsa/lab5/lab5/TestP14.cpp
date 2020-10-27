#include "TestP14.h"
#include <vector>
#include <assert.h>
#include <numeric>
#include <iostream>
#include <exception>
#include "P14.h"

using namespace std;

vector<TElem> randomInterval(int min, int max, int k) {
	vector<int> values;
	for (int v = min; v <= max; v++) {
		values.push_back(v);
	}
	int n = values.size();
	for (int i = 0; i < n - 1; i++) {
		int j = i + rand() % (n - i);
		swap(values[i], values[j]);
	}
	for (int i = values.size() - 1; i >= k; i--) {
		values.pop_back();
	}
	return values;
}

void testException(int n) {
	vector<int> v = { 1, 2, 3, 4, 5 };
	vector<int> kNegative = randomInterval(-50, -1, 10);
	for (int i = 0; i < (int)kNegative.size(); i++) {
		try {
			computeProduct(v, kNegative[i], n);
			assert(false);
		}
		catch (exception & ex) {
			assert(true);
		}
	}
}

void testProduct(int k, int n) {
	vector<int> v;

	int min1 = -500;
	int max1 = 500;

	int min2 = max1 + 1;
	int max2 = min2 + k - 1;

	vector<int> valuesI1 = randomInterval(min1, max1, max1 - min1 + 1);
	for (int i = 0; i < (int)valuesI1.size() / 2; i++) {
		v.push_back(valuesI1[i]);
	}

	vector<int> valuesI2 = randomInterval(min2, max2, k);
	for (int i = 0; i < k; i++) {
		v.push_back(valuesI2[i]);
	}

	for (int i = valuesI1.size() / 2; i < (int)valuesI1.size(); i++) {
		v.push_back(valuesI1[i]);
	}

	//long long produs = std::accumulate(valuesI2.begin(), valuesI2.end(), 1, std::multiplies<int>());
	long long product = 1;
	for (int i = 0; i < valuesI2.size(); i++) {
		product *= valuesI2[i];
	}
	try {
		assert(computeProduct(v, k, n) == product);
	}
	catch (exception & e) {
		assert(false);
	}
}

void testProductAll(int n) {
	int min = -500;
	int max = 500;
	int kMin = 100;
	vector<int> v = randomInterval(min, max, kMin);
	long sum = std::accumulate(v.begin(), v.end(), 1, std::multiplies<int>());
	for (int k = kMin; k <= kMin + 100; k++) {
		try {
			assert(computeProduct(v, k, n) == sum);
		}
		catch (exception & e) {
			assert(false);
		}
	}

}

void testP14() {
	for (int i = 3; i < 10; i++) {
		cout << "Testing for " << i << endl;
		testException(i);
		testProduct(10, i);
		testProduct(20, i);
		testProduct(100, i);
		testProduct(1000, i);
		testProductAll(i);
	}
}
