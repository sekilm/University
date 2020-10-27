#pragma once
#include "List.h"
#include <exception>

//unidirectional iterator for a container
class ListIterator 
{
	friend class IteratedList;

private:

	//Constructor receives a reference of the container.
	//after creation the iterator will refer to the first element of the container, or it will be invalid if the container is empty
	ListIterator(const IteratedList& List);
	ListIterator(const IteratedList& List, Node* nnode);

	//contains a reference of the container it iterates over

	const IteratedList& list;

	/* representation specific for the iterator*/
	Node* node;

public:

	//sets the iterator to the first element of the container
	void first();

	//moves the iterator to the next element
	//throws exception if the iterator is not valid
	void next();

	//checks if the iterator is valid
	bool valid() const;

	//returns the value of the current element from the iterator
	// throws exception if the iterator is not valid
	TElem getCurrent() const;
};


