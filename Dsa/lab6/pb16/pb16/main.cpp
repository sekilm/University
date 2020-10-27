#include "ShortTest.h"
#include "ExtendedTest.h"
#include <iostream>

using namespace std;

int main()
{
	testAll();
	cout << "Short test done!\n";
	testAllExtended();
	cout << "Extended test done!\n";
	return 0;
}