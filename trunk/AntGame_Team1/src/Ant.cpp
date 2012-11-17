#include "Ant.h"
#include "Food.h"
#include "AntHill.h"
#include <iostream>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

namespace std {

Ant::Ant(Field* currentField, unsigned int lifetime) :
		Item(currentField) {
	this->lifetime = lifetime;
	this->hasFood = false;
	srand(time(NULL));
}

Ant::~Ant() {

}

void Ant::act() {

	if (lifetime > 0) {
		lifetime--;

		cout << "		> Ant acting, lifetime: " << lifetime;

		checkOwnField();
		movePosition();
	} else {
		cout << "Ant has no lifetime: " << lifetime << endl;
	}
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

void Ant::checkOwnField() {

	for (list<Item*>::iterator i = currentField->getItems()->begin();
			i != currentField->getItems()->end(); ++i) {
		if ((*i) != NULL) {

			Food* food = dynamic_cast<Food*>((*i));
			if (food != NULL) {
				food->takeFood(this);
			}

			AntHill* hill = dynamic_cast<AntHill*>((*i));
			if (hill != NULL) {
				hill->antVisits(this);
			}
		}
	}
}

void Ant::movePosition() {

	Field* movingTo = NULL;

	//bestimmung der nächsten position
	if(!hasFood){

	movedFields->push_back(currentField);

	int i = rand() % 4;

	switch (i) {
	case 0:
		movingTo = currentField->getNorth();
		cout << " - Moving North";
		break;
	case 1:
		movingTo = currentField->getSouth();
		cout << " - Moving South";
		break;
	case 2:
		movingTo = currentField->getEast();
		cout << " - Moving East";
		break;
	case 3:
		movingTo = currentField->getWest();
		cout << " - Moving West";
		break;
	default:
		movingTo = NULL;
		cout << "Ant.cpp --> random count to high! " << i << endl;
		break;
	}

	}

	else{
		movingTo = movedPositions->pop_back();
	}


	//eigentliches move

	if (movingTo != NULL) {
		cout << " to " << movingTo << endl;
		movingTo->addItem(this);
		currentField->getItems()->remove(this);
		currentField = movingTo;
	} else {
		cout << "Ant could not move!" << endl;
	}
}

void Ant::setLifetime(unsigned int lifetime) {
	this->lifetime = lifetime;
}
}

