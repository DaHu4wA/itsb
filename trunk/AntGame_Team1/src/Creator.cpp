#include "Creator.h"
#include "Ant.h"
#include "AntHill.h"
#include "Food.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <sstream>
#include <typeinfo>

/**
 * The factory for Ants, the Hill etc.
 */
using namespace std;

Creator* Creator::pCreator = 0;
int antCount = 0;

Creator* Creator::Instance() {

	if (pCreator == 0) {
		pCreator = new Creator();
		srand(time(NULL));
	}

	return pCreator;
}

Creator::Creator() {
}

Creator::~Creator() {
}

Ant* Creator::createAnt(Field* currentField) {

	Ant* ant = new Ant(currentField, randomLifetime());
	ant->setName(getAntName());
	currentField->addItem(ant);

	incrementAntCount();
	return ant;
}
Food* Creator::createFood(Field* currentField) {

	Food* food = new Food(currentField, randomFoodCount());

	currentField->addItem(food);
	return food;
}

AntHill* Creator::createAntHill(Field* currentField) {

	int foodAtHillCount = rand() % 50 + 50;

	AntHill* hill = new AntHill(currentField, foodAtHillCount);

	currentField->addItem(hill);
	hill->birthInitialAnts();

	return hill;
}

int Creator::randomFoodCount() {

	return rand() % 40 + 10;
}

int Creator::randomLifetime() {

	return rand() % 50 + 100;
}

void Creator::decrementAntCount() {
	antCount--;
}

void Creator::incrementAntCount() {
	antCount++;
}

bool Creator::antsAreAlive() {
	return antCount > 0;
}

char* Creator::getAntName() {

	std::stringstream s;

	s << "Ant_Nr." << antCount;

	string asd = s.str();

	char *a = new char[asd.size() + 1];a
	[asd.size()] = 0;
	memcpy(a, asd.c_str(), asd.size());

	return a;
}
