#include "SortedMapIterator.h"

SMIterator::SMIterator(const SortedMap& map) : sm{ map }
{
	this->currentPos = -1;
	this->currentNode = NULL;
	while (currentPos < sm.cap && sm.hashTable[currentPos] == NULL)
		currentPos++;
	if (currentPos < sm.cap)
		currentNode = sm.hashTable[currentPos];
	else
		currentNode = NULL;
}

void SMIterator::first()
{
	this->currentPos = -1;
	while (currentPos < sm.cap && sm.hashTable[currentPos] == NULL)
		currentPos++;
	if (currentPos < sm.cap)
		currentNode = sm.hashTable[currentPos];
	else
		currentNode = NULL;
}

void SMIterator::next()
{
	if (valid() == false)
		throw std::exception();
	if (this->currentNode->next != NULL)
		this->currentNode = this->currentNode->next;
	else
	{
		int aux = -1;
		while (currentPos < sm.cap && sm.hashTable[currentPos] == NULL)
		{
			this->currentPos++;
			if (sm.hashTable[currentPos] != NULL)
				this->currentNode = sm.hashTable[currentPos]->next;
			aux = currentPos;
		}
		if (aux == sm.cap - 1)
			this->currentPos = sm.cap;
	}
}

bool SMIterator::valid() const
{
	if (this->currentPos < 0 || this->currentPos >= sm.cap)
		return false;
	return true;
}

TElem SMIterator::getCurrent() const
{
	if (valid() == false)
		throw std::exception();
	TElem elem;
	elem.first = this->currentNode->elem.first;
	elem.second = this->currentNode->elem.second;
	return elem;
}
