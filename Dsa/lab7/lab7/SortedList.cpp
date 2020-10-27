#include "SortedList.h"
#include "ListIterator.h"
#include <stack>
#include <exception>

SortedList::SortedList(Relation r) : rel{ r }
{
	this->cap = 2;
	this->length = 0;
	this->bst = new Node[cap];
	this->root = -1;
	this->firstEmpty = 0;
	for (int i = 0; i < cap; i++)
	{
		if (i + 1 == cap)
			bst[i].left = -1;
		else
			bst[i].left = i + 1;
	}
}

int SortedList::size() const
{
	return this->length;
}

bool SortedList::isEmpty() const
{
	if (this->length == 0)
		return true;
	return false;
}

TComp SortedList::getElement(int pos) const
{
	if (pos < 0 || pos >= this->length)
		throw std::exception();
	std::stack<int> stack;
	int position = 0;
	int currentPos = root;
	while (currentPos != -1)
	{
		stack.push(currentPos);
		currentPos = bst[currentPos].left;
	}
	while (stack.empty() == false)
	{
		currentPos = stack.top();
		stack.pop();
		if (position == pos)
			return bst[currentPos].info;
		position++;
		currentPos = bst[currentPos].right;
		while (currentPos != -1)
		{
			stack.push(currentPos);
			currentPos = bst[currentPos].left;
		}
	}
}

void SortedList::add(TComp e)
{
	if (firstEmpty == -1)
		this->resize();
	if (this->root == -1)
	{
		bst[firstEmpty].info = e;
		this->root = firstEmpty;
		firstEmpty = bst[firstEmpty].left;
		bst[root].left = -1;
		bst[root].right = -1;
		this->length++;
	}
	else
	{
		int currentPos = this->root;
		int prev = -1;
		while (currentPos != -1)
		{
			prev = currentPos;
			if (rel(e, bst[currentPos].info))
				currentPos = bst[currentPos].left;
			else
				currentPos = bst[currentPos].right;
		}
		int temp = firstEmpty;
		firstEmpty = bst[firstEmpty].left;
		bst[temp].info = e;
		this->length++;

		bst[temp].left = -1;
		bst[temp].right = -1;

		if (rel(e, bst[prev].info))
			bst[prev].left = temp;
		else
			bst[prev].right = temp;
	}
}

void SortedList::resize()
{
	this->cap *= 2;
	Node* temp = new Node[this->cap];
	for (int i = 0; i < this->cap / 2; i++)
	{
		temp[i] = this->bst[i];
	}
	for (int i = this->cap / 2; i < this->cap; i++)
	{
		if (i + 1 == this->cap)
			temp[i].left = -1;
		else
			temp[i].left = i + 1;
	}
	delete[] this->bst;
	this->bst = temp;
	this->firstEmpty = this->cap / 2;
}

bool SortedList::isLeaf(int pos)
{
	return bst[pos].left == -1 && bst[pos].right == -1;
}

TComp SortedList::remove(int pos)
{
	int current = root;
	TElem e = this->getElement(pos);
	int p = -1; //parent or prev of current
	if (bst[current].info == e and isLeaf(current))
	{
		bst[root].left = firstEmpty;
		firstEmpty = root;
		root = -1;
		this->length--;
		return bst[current].info;
	}
	while (current != -1)
	{
		if (bst[current].info == e)
		{
			if (isLeaf(current))
			{
				if (rel(e, bst[p].info))
					bst[p].left = -1;
				else
					bst[p].right = -1;
			}
			else
			{
				if (bst[current].right == -1)
				{
					if (p != -1)
					{
						if (rel(e, bst[p].info))
							bst[p].left = bst[current].left;
						else
							bst[p].right = bst[current].left;
					}
					else
						root = bst[current].left;
				}
				else
				{
					if (bst[current].left == -1)
					{
						if (p != -1)
						{
							if (rel(e, bst[p].info))
								bst[p].left = bst[current].right;
							else
								bst[p].right = bst[current].right;
						}
						else
							root = bst[current].right;
					}
					else
					{
						int seeker = bst[current].left;
						int prev = current;
						while (seeker != -1)
						{
							prev = seeker;
							seeker = bst[seeker].right;
						}
						if (prev == current)
							bst[prev].left = bst[seeker].left;
						else
							bst[prev].right = bst[seeker].left;
						bst[current].info = bst[seeker].info;
					}
				}
			}
			this->length--;
			bst[current].left = firstEmpty;
			firstEmpty = current;
			return bst[current].info;
		}
		p = current;
		if (rel(e, bst[p].info))
			current = bst[current].left;
		else
			current = bst[current].right;
	}
	throw std::exception();
}

int SortedList::search(TComp e) const
{
	int currentPos = root;
	while (currentPos != -1 && bst[currentPos].info != e)
	{
		if (rel(e, bst[currentPos].info))
			currentPos = bst[currentPos].left;
		else
			currentPos = bst[currentPos].right;
	}
	if (bst[currentPos].info == e)
		return currentPos;
	else
		return -1;
}

ListIterator SortedList::iterator()
{
	return ListIterator(*this);
}

SortedList::~SortedList()
{
}
