#pragma once

typedef int TElem;

class DynamicArray 
{
public:
	int length, capacity;
	TElem *elems;

public:
	//constructor
	//throws exception if capacity is 0 or negative
	DynamicArray();

	//returns the size (number of elements) from the DynamicArray
	int size() const;

	//resizes the capacity of the DynamicArray
	void resize();

	//returns the element from a given position (indexing starts from 0)
	//throws exception if pos is not a valid position
	TElem getElement(int pos) const;

	//changes the element from a position to a different value
	//returns the old element from position poz
	//throws exception if pos is not a valid position
	TElem setElement(int pos, TElem newElem);

	//adds a new element to the end of the DynamicArray
	void addToEnd(TElem newElem);

	//removes an element from a given position
	//returns the removed element
	//throws exception if pos is not a valid position
	TElem remove(int pos);

	//destructor
	~DynamicArray();
};
