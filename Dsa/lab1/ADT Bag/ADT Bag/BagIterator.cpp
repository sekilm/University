#include "BagIterator.h"
#include <exception>

BagIterator::BagIterator(const Bag & bag): bag { bag }
{
	indexP = 0;
}

void BagIterator::first()
{
	indexP = 0;
}

void BagIterator::next()
{
	if (valid() == false)
		throw std::exception();
	else
		indexP++;
}

bool BagIterator::valid() const
{
	if (indexP < bag.P.length)
		return true;
	return false;
}

TElem BagIterator::getCurrent() const
{
	if (valid() == false)
		throw std::exception();
	int index = bag.P.elems[indexP];
	return bag.U.elems[index];
}
