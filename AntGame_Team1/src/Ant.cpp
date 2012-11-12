#include "Ant.h"
#include <iostream>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

namespace std {

Ant::Ant(Field* currentField, unsigned int lifetime) :
		Item(currentField) {
	this->lifetime = lifetime;
	this->hasFood = false;
}

Ant::~Ant() {

}

void Ant::act() {
	lifetime--;

	cout << "		> Ant acting, " << lifetime << " moves left!" << endl;
}

unsigned int Ant::getLifetime() {
	return lifetime;
}

bool Ant::isHasFood() {
	return hasFood;
}

void Ant::setHasFood(bool hasFood) {
	this->hasFood = hasFood;
}

void Ant::movePosition(){

	srand(time(NULL));
	Field* movingTo = NULL;

	int i = rand() % 4;

	switch (i) {
		case 0:
			movingTo = currentField->getNorth();
			break;
		case 1:
			movingTo = currentField->getSouth();
			break;
		case 2:
			movingTo = currentField->getEast();
			break;
		case 3:
			movingTo = currentField->getWest();
			break;
		default:
			movingTo = NULL;
			cout << "Ant.cpp --> rand to high! "<< i << endl;
			break;
	}
			currentField->removeItem(this);
			movingTo->addItem(this);
	}

    void Ant::setLifetime(unsigned int lifetime)
    {
        this->lifetime = lifetime;}
}


