#include "SortedMap.h"
#include "SMIterator.h"
#include <iostream>

int SortedMap::hashFunction(TKey key) const
{
	return abs(key % this->cap);
}

SortedMap::SortedMap(Relation _r): rel{ _r }
{
	this->cap = 5;
	this->hashTable = new Node*[this->cap];
	for (int i = 0; i < this->cap; i++)
		this->hashTable[i] = NULL;
}

void SortedMap::resize()
{
	this->cap *= 2;
	Node** aux = new Node * [this->cap];
	for (int i = 0; i < cap; i++)
		aux[i] = NULL;

	for (int i = 0; i < this->cap / 2; i++) 
	{
		Node* currentNode = this->hashTable[i];
		Node* nextNode;
		while (currentNode != NULL)
		{
			nextNode = currentNode->next;
			int pos = this->hashFunction(currentNode->elem.first);
			if (aux[pos] == NULL)
			{
				currentNode->next = NULL;
				aux[pos] = currentNode;
			}
			else
			{
				Node* newNode = aux[pos];
				if (newNode->next == NULL)
				{
					if (rel(currentNode->elem.first, newNode->elem.first))
					{
						currentNode->next = newNode;
						aux[pos] = currentNode;
					}
					else
					{
						newNode->next = currentNode;
						currentNode->next = NULL;
					}
				}
				while (newNode->next != NULL && !rel(currentNode->elem.first, newNode->next->elem.first))
					newNode = newNode->next;
				if (newNode->next == NULL)
				{
					newNode->next = currentNode;
					currentNode->next = NULL;
				}
				else
				{
					Node* node = newNode->next;
					newNode->next = currentNode;
					currentNode->next = node;
				}
			}
			currentNode = nextNode;
		}
	}
	delete[] this->hashTable;
	this->hashTable = aux;
}

TValue SortedMap::add(TKey k, TValue v)
{
	//if (this->size() / this->cap >= LOAD_FACTOR)
		//resize();

	int pos = this->hashFunction(k);
	Node* nodeToAdd = new Node;
	nodeToAdd->elem.first = k;
	nodeToAdd->elem.second = v;

	Node* currentNode = this->hashTable[pos];
	if (currentNode == NULL)
	{
		currentNode = nodeToAdd;
		currentNode->next = NULL;
		this->hashTable[pos] = currentNode;
		this->length++;
		return NULL_TVALUE;
	}

	if (currentNode->next == NULL)
	{
		if (currentNode->elem.first == k)
		{
			TValue temp = currentNode->elem.second;
			currentNode->elem.second = v;
			return temp;
		}
		if (rel(k, currentNode->elem.first))
		{
			nodeToAdd->next = currentNode;
			this->hashTable[pos] = nodeToAdd;
			this->length++;
			return NULL_TVALUE;
		}
		currentNode->next = nodeToAdd;
		nodeToAdd->next = NULL;
		this->length++;
		return NULL_TVALUE;
	}

	if (rel(k, currentNode->elem.first))
	{
		if (k == currentNode->elem.first)
		{
			TValue temp = currentNode->elem.second;
			currentNode->elem.second = v;
			return temp;
		}
		nodeToAdd->next = currentNode;
		this->hashTable[pos] = nodeToAdd;
		this->length++;
		return NULL_TVALUE;
	}

	while (currentNode->next != NULL && !rel(k, currentNode->next->elem.first))
	{	
		currentNode = currentNode->next;
	}

	if (currentNode->next != NULL && currentNode->next->elem.first == k)
	{
		TValue temp = currentNode->next->elem.second;
		currentNode->next->elem.second = v;
		return temp;
	}
	if (currentNode->next == NULL)
	{
		currentNode->next = nodeToAdd;
		nodeToAdd->next = NULL;
		this->length++;
		return NULL_TVALUE;
	}
	else
	{
		Node* node = currentNode->next;
		currentNode->next = nodeToAdd;
		nodeToAdd->next = node;
		this->length++;
		return NULL_TVALUE;
	}
}

TValue SortedMap::search(TKey k) const
{
	int pos = this->hashFunction(k);
	Node* currentNode = this->hashTable[pos];
	while (currentNode != NULL)
	{
		if (currentNode->elem.first == k)
			return currentNode->elem.second;
		currentNode = currentNode->next;
	}
	return NULL_TVALUE;
}

TValue SortedMap::remove(TKey k)
{
	int pos = this->hashFunction(k);
	Node* currentNode = this->hashTable[pos];
	Node* prevNode = NULL;
	if (currentNode == NULL)
		return NULL_TVALUE;
	if (currentNode->next == NULL)
	{ 
		int temp = currentNode->elem.second;
		currentNode = NULL;
		this->hashTable[pos] = currentNode;
		this->length--;
		return temp;
	}
	while (currentNode->next != NULL)
	{
		if (currentNode->elem.first == k)
		{
			//if (currentNode == this->hashTable[pos])
			if (prevNode == NULL)
			{
				TValue temp = currentNode->elem.second;
				currentNode = currentNode->next;
				this->hashTable[pos] = currentNode;
				this->length--;
				return temp;
			}
			else
			{
				prevNode->next = currentNode->next;
				this->length--;
				return currentNode->elem.second;
			}
		}
		prevNode = currentNode;
		currentNode = currentNode->next;
	}
	return NULL_TVALUE;
}

int SortedMap::size() const
{
	return this->length;
}

bool SortedMap::isEmpty() const
{
	if (size() == 0)
		return true;
	return false;
}

SMIterator SortedMap::iterator() const
{
	return SMIterator(*this);
}

SortedMap::~SortedMap()
{
	for (int i = 0; i < this->cap; i++)
		delete this->hashTable[i];
	delete[] this->hashTable;
}
