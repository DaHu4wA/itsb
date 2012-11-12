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

	if(lifetime > 0){
		lifetime--;
	}else{
		cout << "Ant died " << lifetime << endl;
	}

	cout << "		> Ant acting, " << lifetime << " moves left!" << endl;

movePosition();
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

void Ant::movePosition() {

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
		cout << "Ant.cpp --> random count to high! " << i << endl;
		break;
	}

	currentField->removeItem(this);  // HERE IS A BUG!
	if(movingTo != NULL){
		movingTo->addItem(this);
	}else{
		cout << "Ant could not move!" << endl;
	}
}

void Ant::setLifetime(unsigned int lifetime) {
	this->lifetime = lifetime;
}
}

