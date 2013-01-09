#include "Ant.h"
#include "Field.h"
#include "Food.h"
#include "AntHill.h"
#include "Statistics.h"
#include <iostream>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include "Creator.h"

/**
 * This represents an ant.
 * The ant knows it's lifetime, but not it's position.
 * Ant knows a history of moved fields. When it found some food, it orients on the moved way.
 * Ant also follows pheromones
 */
namespace std {

Ant::Ant(Field* currentField, unsigned int lifetime) :
		Item(currentField) {
	this->lifetime = lifetime;
	this->hasFood = false;
	fieldHistory = new list<Field*>;
	srand(time(NULL));
}

Ant::~Ant() {
}

void Ant::act() {

	if (lifetime > 0) {
		checkOwnField();
		movePosition();
	} else {
		cout << antName << " died :(" << endl;
		Statistics::Instance()->decrementCurrentAntCount();
		currentField->getItems()->remove(this);
		delete this;
	}
	lifetime--;
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

			// Here we use RTTI
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

	//determine next field ant is moving to
	if (!hasFood) {

		// add the fields to the history
		fieldHistory->push_back(currentField);

		// 75% Probability to follow pheromones
		int i = rand() % 4;
		if (i < 3) {
			movingTo = checkPheromones();
		} else {
			movingTo = getRandomWay();
		}

	}

	else {
		if (pheromones > 0) {
			currentField->incrementPheromonStrength(pheromones);
			pheromones = pheromones - 5;
		}

		// if ant has got food, it moves back the way it went before
		if (!fieldHistory->empty()) {
			movingTo = fieldHistory->back();
			fieldHistory->pop_back();
		}
	}

	if (movingTo != NULL) {
		currentField->getItems()->remove(this);
		currentField = movingTo;
		movingTo->addItem(this);
	}
}

void Ant::setLifetime(unsigned int lifetime) {
	this->lifetime = lifetime;
}

void Ant::setName(char* name) {
	this->antName = name;
}

char* Ant::getName() {
	return this->antName;
}

void Ant::setPheromones(unsigned int pheromones) {
	this->pheromones = pheromones;
}

/**
 * Find strongest pheromones on neighbour fields
 */
Field* Ant::checkPheromones() {

	// if strength of two fields equal -> we go to north
	int north = currentField->getNorth() == NULL ? -1 : currentField->getNorth()->getPheromonStrength();
	int south = currentField->getSouth() == NULL ? -1 : currentField->getSouth()->getPheromonStrength();
	int east =  currentField->getEast()  == NULL ? -1 : currentField->getEast()->getPheromonStrength();
	int west =  currentField->getWest()  == NULL ? -1 : currentField->getWest()->getPheromonStrength();

	int result = north;

	if (south > result) {
		result = south;
	}
	if (east > result) {
		result = east;
	}
	if (west > result) {
		result = west;
	}

	if (result == south) {
		return currentField->getSouth();
	} else if (result == east) {
		return currentField->getEast();
	} else if (result == west) {
		return currentField->getWest();
	} else {
		return currentField->getNorth();
	}
}

/**
 * Find a random way
 */
Field* Ant::getRandomWay() {

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
		break;
	}
	return movingTo;
}

}

