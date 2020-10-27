#include "ListIterator.h"
//#include <stddef.h>

ListIterator::ListIterator(const IteratedList & List): list{ List }
{
	this->node = this->list.head;
}

ListIterator::ListIterator(const IteratedList & List, Node* nnode) : list{ List }, node { nnode }
{
}

void ListIterator::first()
{
	this->node = this->list.head;
}

void ListIterator::next()
{
	if (valid() == false)
		throw std::exception();
	this->node = this->node->next;
}

bool ListIterator::valid() const
{
	if (this->node != NULL)
		return true;
	return false;
}

TElem ListIterator::getCurrent() const
{
	TElem elem;
	if (valid() == false)
		throw std::exception();
	else
		elem = this->node->info;
	return elem;
}
