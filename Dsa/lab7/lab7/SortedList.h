#pragma once

typedef int TComp;

typedef bool (*Relation)(TComp, TComp);

class ListIterator;

typedef int TElem;

#define NULL_TELEM -1

struct Node
{
	TElem info;
	int left, right;
	int leftNodes;
	int pos;
};

class SortedList
{
	friend class ListIterator;

private:
	//representation of SortedList
	Node* bst;
	int root;
	int firstEmpty;
	int cap, length;
	Relation rel;

public:
	// constructor
	SortedList(Relation r);

	// returns the size of the list
	int size() const;

	//checks if the list is empty
	bool isEmpty() const;

	// returns an element from a position
	//throws exception if the position is not valid
	TComp getElement(int pos) const;

	// adds an element
	//throws an exception if the position is not valid
	void add(TComp e);

	//resizes the array
	void resize();

	//checks if element on position pos is leaf
	bool isLeaf(int pos);

	// removes an element from a given position
	//returns the removed element
	//throws an exception if the position is not valid
	TComp remove(int pos);

	// searches for an element and returns the first position where the element appears or -1 if the element is not in the list
	int search(TComp e) const;

	// returns an iterator set to the first element of the list or invalid if the list is empty
	ListIterator iterator();

	//destructor
	~SortedList();
};
