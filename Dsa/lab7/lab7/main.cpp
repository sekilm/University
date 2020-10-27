//ADT SortedList – using a BST with linked representation on an array. Every node of the BST will retain the number of elements to the left of the node as well.

#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"

int main()
{
	testAll();
	std::cout << "Short test done!\n";
	testAllExtended();
	std::cout << "Extended test done!\n";
	return 0;
}