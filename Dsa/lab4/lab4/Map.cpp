#include "Map.h"
#include "MapIterator.h"

Map::Map()
{
	this->cap = 2;
	this->head = -1;
	this->tail = -1;
	this->length = 0;
	this->firstEmpty = 0;
	this->nodes = new DLLANode[this->cap];
	this->nodes[0].prev = -1;
	for (int i = 0; i < this->cap; i++)
	{
		this->nodes[i].next = i + 1;
		this->nodes[i].prev = i - 1;
	}
	this->nodes[this->cap - 1].next = -1;
}

void Map::resize()
{
	this->cap *= 2;
	DLLANode* aux = new DLLANode[this->cap];
	int i = this->head;
	while (i != -1)
	{
		aux[i] = this->nodes[i];
		i = this->nodes[i].next;
	}
	for (i = this->length; i < this->cap; i++)
	{
		aux[i].next = i + 1;
		aux[i].prev = i - 1;
	}
	aux[this->cap - 1].next = -1;
	delete[] this->nodes;
	this->nodes = aux;
	this->firstEmpty = this->length;
	this->nodes[this->length].prev = -1;
}

TValue Map::add(TKey c, TValue v)
{
	DLLANode newNode;
	newNode.info = v;
	newNode.key = c;
	if (firstEmpty == -1)
		resize();
	if (this->length == 0)
	{
		this->head = this->firstEmpty;
		this->tail = this->firstEmpty;
		this->nodes[firstEmpty].info = v;
		this->nodes[firstEmpty].key = c;
		this->firstEmpty = this->nodes[firstEmpty].next;
		this->length++;
		return NULL_TVALUE;
	}
	else
	{
		if (search(c) == NULL_TELEM)
		{
			this->nodes[firstEmpty].info = newNode.info;
			this->nodes[firstEmpty].key = newNode.key;
			this->nodes[firstEmpty].prev = this->tail;
			this->nodes[firstEmpty].next = -1;
			this->nodes[this->tail].next = firstEmpty;
			this->tail = firstEmpty;
			firstEmpty = this->nodes[firstEmpty].next;

			this->length++;
			return NULL_TVALUE;
		}
		else
		{
			int i = this->head;
			while (i != -1)
			{
				if (c == this->nodes[i].key)
				{
					int value = this->nodes[i].info;
					this->nodes[i].info = v;
					return value;
				}
				i = this->nodes[i].next;
			}
		}
	}
}

TValue Map::search(TKey c) const
{
	int i = this->head;
	while (i != -1)
	{
		if (c == this->nodes[i].key)
			return this->nodes[i].info;
		i = this->nodes[i].next;
	}
	return NULL_TELEM;
}

TValue Map::remove(TKey c)
{
	int retValue;
	if (search(c) == NULL_TELEM)
		return NULL_TELEM;
	if (c == this->nodes[this->head].key)
	{
		retValue = this->nodes[this->head].info;
		this->head = this->nodes[this->head].next;
		this->nodes[this->tail].prev = -1;
		this->length--;
		return retValue;
	}
	else if (c == this->nodes[this->tail].key)
	{
		retValue = this->nodes[this->tail].info;
		this->tail = this->nodes[this->tail].prev;
		this->nodes[this->head].next = -1;
		this->length--;
		return retValue;
	}
	else
	{
		int i = this->head, index;
		while (i != -1)
		{
			if (c == this->nodes[i].key)
			{
				index = i;
				retValue = this->nodes[i].info;
			}
			i = this->nodes[i].next;
		}
		this->nodes[this->nodes[index].prev].next = this->nodes[index].next;
		this->nodes[this->nodes[index].next].prev = this->nodes[index].prev;
		this->length--;
		return retValue;
	}
	return NULL_TELEM;
}

int Map::size() const
{
	return this->length;
}

bool Map::isEmpty() const
{
	return (this->length == 0);
}

MapIterator Map::iterator() const
{
	return MapIterator(*this);
}

Map::~Map()
{
}
