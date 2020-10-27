#include "ListIterator.h"

ListIterator::ListIterator(const SortedList& _list): list{ _list }
{
	this->stack = std::stack<int>();
	this->currentPos = list.root;
	while (currentPos != -1)
	{
		stack.push(currentPos);
		currentPos = list.bst[currentPos].left;
	}
	if (stack.empty() == false)
		currentPos = stack.top();
	else
		currentPos = -1;
}

void ListIterator::first()
{
	this->stack = std::stack<int>();
	this->currentPos = list.root;
	while (currentPos != -1)
	{
		stack.push(currentPos);
		currentPos = list.bst[currentPos].left;
	}
	if (stack.empty() == false)
		currentPos = stack.top();
	else
		currentPos = -1;
}

void ListIterator::next()
{
	if (valid() == false)
		throw std::exception();
	currentPos = stack.top();
	stack.pop();
	if (list.bst[currentPos].right != -1)
	{
		currentPos = list.bst[currentPos].right;
		while (currentPos != -1)
		{
			stack.push(currentPos);
			currentPos = list.bst[currentPos].left;
		}
	}
	if (stack.empty() == false)
		currentPos = stack.top();
	else
		currentPos = -1;
}

bool ListIterator::valid() const
{
	if (currentPos == -1)
		return false;
	return true;
}

TElem ListIterator::getCurrent() const
{
	if (valid() == false)
		throw std::exception();
	return list.bst[currentPos].info;
}
