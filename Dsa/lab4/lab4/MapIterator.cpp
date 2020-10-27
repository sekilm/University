#include "MapIterator.h"

MapIterator::MapIterator(const Map& map) : map{ map }
{
	this->currentElement = this->map.head;
}

void MapIterator::first()
{
	this->currentElement = this->map.head;
}

void MapIterator::next()
{
	if (valid() == false)
		throw std::exception();
	this->currentElement = this->map.nodes[currentElement].next;
}

bool MapIterator::valid() const
{
	if (this->currentElement == -1)
		return false;
	return true;
}

TElem MapIterator::getCurrent() const
{
	if (valid() == false)
		throw std::exception();
	TElem elem(this->map.nodes[this->currentElement].key, this->map.nodes[this->currentElement].info);
	return elem;
}
