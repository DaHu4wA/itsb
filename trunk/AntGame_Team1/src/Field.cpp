#include "Field.h"
#include <typeinfo>
#include "Ant.h"

using namespace std;

Field::Field() {
	south = north = west = east = NULL;
	items = new list<Item*>;
	pheromonStrength = 0;
}

Field::~Field() {
	for (list<Item*>::iterator i = items->begin(); i != items->end(); ++i) {
		delete *i;
	}
}

void Field::addItem(Item* item) {

	Ant* ant = dynamic_cast<Ant*>(item);

	if (ant != NULL) {
		if (ant->isHasFood()) {
			pheromonStrength = pheromonStrength + 8;
		} else {
			pheromonStrength = pheromonStrength + 4;
		}
	}
	items->push_back(item);
}

void Field::removeItem(Item* item) {
	items->remove(item);
}

void Field::act() {

	if (pheromonStrength > 0) {
		pheromonStrength--;
	}

	list<Item*> tempItems = *items;
	for (list<Item*>::iterator i = tempItems.begin(); i != tempItems.end();
			++i) {
		if ((*i) != NULL) {
			(*i)->act();
		}
	}
}

//------------ Getters and Setters ---------------------------

Field *Field::getEast() {
	return east;
}

list<Item*>* Field::getItems() {
	return items;
}

Field *Field::getNorth() {
	return north;
}

Field *Field::getSouth() {
	return south;
}

Field *Field::getWest() {
	return west;
}

void Field::setEast(Field *east) {
	this->east = east;
}

void Field::setItems(list<Item*>* items) {
	this->items = items;
}

void Field::setNorth(Field *north) {
	this->north = north;
}

void Field::setSouth(Field *south) {
	this->south = south;
}

void Field::setWest(Field *west) {
	this->west = west;
}
