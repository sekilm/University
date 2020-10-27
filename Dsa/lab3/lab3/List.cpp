#include "List.h"
#include <stddef.h>
#include <exception>

IteratedList::IteratedList()
{
	this->head = NULL;
}

int IteratedList::size() const
{
	int s = 0;
	Node* currentNode = this->head;
	while (currentNode != NULL)
	{
		s++;
		currentNode = currentNode->next;
	}
	return s;
}

bool IteratedList::isEmpty() const
{
	if (size() == 0)
		return true;
	return false;
}

ListIterator IteratedList::first() const
{
	return IteratedList(*this);
}

TElem IteratedList::getElement(ListIterator pos) const
{
	TElem elem;
	if (pos.node == NULL)
		throw std::exception();
	Node* currentNode = this->head;
	while (currentNode != NULL)
	{
		if (pos.node->info == currentNode->info)
			elem = currentNode->info;
	}
	return elem;
}

TElem IteratedList::setElement(ListIterator pos, TElem e)
{
	TElem elem;
	if (pos.node == NULL)
		throw std::exception();
	Node* currentNode = this->head;
	while (currentNode != NULL)
	{
		if (pos.node->info == currentNode->info)
		{
			elem = currentNode->info;
			currentNode->info = e;
		}
	}
	return elem;
}

void IteratedList::addToEnd(TElem e)
{
	if (this->head == NULL)
	{
		Node* newNode = new Node;
		newNode->info = e;
		newNode->next = NULL;
		this->head = newNode;
	}
	else
	{
		Node* currentNode = this->head;
		while (currentNode->next != NULL)
			currentNode = currentNode->next;
		Node* newNode = new Node;
		newNode->info = e;
		newNode->next = NULL;
		currentNode->next = newNode;
	}
}

void IteratedList::addToPosition(ListIterator pos, TElem e)
{
	if (pos.node != NULL)
		throw std::exception();
	else
	{
		if (pos.node == this->head)
		{
			Node *newNode = new Node;
			newNode->info = e;
			newNode->next = this->head;
			this->head = newNode;
		}
		Node *currentNode = new Node;
		currentNode = this->head;
		while (currentNode != pos.node && currentNode != NULL)
			currentNode = currentNode->next;
		if (currentNode != NULL)
		{
			Node* newNode = new Node;
			newNode->info = e;
			newNode->next = currentNode->next;
			currentNode->next = newNode;
		}
		else
			throw std::exception();
	}
}

TElem IteratedList::remove(ListIterator pos)
{
	TElem elem;
	Node* currentNode = new Node;
	Node* prevNode = new Node;
	currentNode = this->head;
	prevNode = NULL;
	while (currentNode != NULL && currentNode->info != pos.node->info)
	{
		prevNode = currentNode;
		currentNode = currentNode->next;
	}
	if (currentNode != NULL && prevNode == NULL)
	{
		elem = this->head->info;
		this->head = this->head->next;
	}
	else if (currentNode != NULL)
	{
		elem = currentNode->info;
		prevNode->next = currentNode->next;
		currentNode->next = NULL;
	}
	return elem;
}

ListIterator IteratedList::search(TElem e) const
{
	Node *currentNode = new Node;
	currentNode = this->head;
	while (currentNode != NULL && currentNode->info != e)
		currentNode = currentNode->next;
	return ListIterator(*this, currentNode);
}

IteratedList::~IteratedList()
{
}
