#include "DynamicArray.h"

DynamicArray::DynamicArray()
{
	this->length = 0;
	this->capacity = 1;
	this->elems = new TElem[this->capacity];
}

int DynamicArray::size() const
{
	return this->length;
}

void DynamicArray::resize()
{
	this->capacity *= 2;
	TElem* newArray = new TElem[this->capacity];
	for (int i = 0; i < this->length; i++)
	{
		newArray[i] = this->elems[i];
	}
	delete[] this->elems;
	this->elems = newArray;
}

TElem DynamicArray::getElement(int pos) const
{
	for (int i = 0; i < this->length; i++)
	{
		if (pos == i)
			return this->elems[i];
	}
	return 0;
}

TElem DynamicArray::setElement(int pos, TElem newElem)
{
	TElem oldElem;
	for (int i = 0; i < this->length; i++)
	{
		if (pos == i)
		{
			oldElem = this->elems[i];
			this->elems[i] = newElem;
		}
	}
	return oldElem;
}

void DynamicArray::addToEnd(TElem newElem)
{
	if (this->length == this->capacity)
		resize();
	this->elems[this->length++] = newElem;
}

TElem DynamicArray::remove(int pos)
{
	TElem elem = this->elems[pos];
	for (int i = pos; i < this->length - 1; i++)
		this->elems[i] = this->elems[i + 1];
	this->length--;
	return elem;
}

DynamicArray::~DynamicArray()
{
	delete[] this->elems;
}
