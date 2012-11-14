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
	cout << "Field died!" << endl;
	for (list<Item*>::iterator i = items->begin(); i != items->end(); ++i) {
		delete *i;
	}
}

void Field::addItem(Item* item) {

	Ant* ant = dynamic_cast<Ant*>(item);

	if(ant != NULL) {
		if (ant->isHasFood()) {
			pheromonStrength = pheromonStrength + 8;
		} else {
			pheromonStrength = pheromonStrength + 4;
		}
		cout << "Ant added to field Strength: "
				<< pheromonStrength << endl;
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

	for (list<Item*>::iterator i = items->begin(); i != items->end(); ++i) {
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
