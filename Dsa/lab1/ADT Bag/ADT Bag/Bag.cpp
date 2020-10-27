#include "Bag.h"
#include "BagIterator.h"
#include "DynamicArray.h"

Bag::Bag()
{
}

void Bag::add(TElem e)
{
	if (U.length == 0)
	{
		U.addToEnd(e);
		P.addToEnd(0);
		return;
	}
	for (int i = 0; i < U.length; i++)
	{
		if (e == U.elems[i])
		{
			P.addToEnd(i);
			return;
		}
	}

	U.addToEnd(e);
	P.addToEnd(U.length - 1);
}

bool Bag::remove(TElem e)
{
	
	bool removed = false;
	int index = -1;
	for (int i = 0; i < U.length; i++)
	{
		if (e == U.elems[i])
		{
			index = i;
			break;
		}
	}
	if (index == -1)
		return removed;
	for (int i = 0; i < P.length; i++)
	{
		if (index == P.elems[i])
		{
			P.remove(i);
			removed = true;
			break;
		}
	}
	bool found = false;
	for (int i = 0; i < P.length; i++)
	{
		if (P.elems[i] == index)
			found = true;
	}
	if (found == false)
	{
		U.remove(index);
		for (int i = index; i < P.length; i++)
			P.elems[i]--;
	}
	return removed;
}

bool Bag::search(TElem e) const
{
	for (int i = 0; i < U.length; i++)
	{
		if (e == U.elems[i])
			return true;
	}
	return false;
}

int Bag::nrOccurrences(TElem e) const
{
	int counter = 0, index = -1;
	for (int i = 0; i < U.length; i++)
	{
		if (e == U.elems[i])
		{
			index = i;
		}
	}
	for (int i = 0; i < P.length; i++)
	{
		if (index == P.elems[i])
			counter++;
	}
	return counter;
}

int Bag::size() const
{
	return P.length;
}

bool Bag::isEmpty() const
{
	if (U.length == 0)
		return true;
	return false;
}

BagIterator Bag::iterator() const
{
	return BagIterator(*this);
}


Bag::~Bag()
{
}
