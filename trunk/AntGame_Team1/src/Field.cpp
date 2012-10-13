#include "Field.h"
#include  <iostream>

using namespace std;

Field::Field() {
	south = north = west = east = NULL;
}

Field::Field(Field* south, Field* north, Field* west, Field* east) {

	this->south = south;
	this->north = north;
	this->west = west;
	this->east = east;

}

Field::~Field() {
	cout << "Field died!" << endl;
}

void Field::addItem(Item* item) {
	items.push_back(item);
}

void Field::act() {
	cout << "	> Field acting" << endl;
	for (list<Item*>::iterator i = items.begin(); i != items.end(); ++i) {
		(*i)->act();
	}
}

//------------ Getters and Setters ---------------------------

Field *Field::getEast() {
	return east;
}

list<Item*> Field::getItems() {
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

void Field::setItems(list<Item*> items) {
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
