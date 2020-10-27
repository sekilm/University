#include "ShortTests.h"

#include <assert.h>
#include <exception>
#include <iostream>

#include "List.h"
#include "ListIterator.h"



using namespace std;

void testAll() {
	IteratedList list = IteratedList();
	assert(list.size() == 0);
	assert(list.isEmpty());

	list.addToEnd(1);
	assert(list.size() == 1);
	assert(!list.isEmpty());

	ListIterator it = list.search(1);
	assert(it.valid());
	assert(it.getCurrent() == 1);
	it.next();
	assert(!it.valid());
	it.first();
	assert(it.valid());
	cout << it.getCurrent() << "\n";
	assert(it.getCurrent() == 1);

	assert(list.remove(it) == 1);
	assert(list.size() == 0);
	assert(list.isEmpty());
}


