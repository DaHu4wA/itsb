#include "Creator.h"
#include "Statistics.h"
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
 * Also does the randomize things
 */
using namespace std;

Creator* Creator::pCreator = 0;

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

	Statistics::Instance()->incrementCurrentAntCount();
	return ant;
}
Food* Creator::createFood(Field* currentField) {

	Food* food = new Food(currentField, randomFoodCount());

	currentField->addItem(food);
	return food;
}

AntHill* Creator::createAntHill(Field* currentField) {

	int foodAtHillCount = rand() % 100 + 20;

	AntHill* hill = new AntHill(currentField, foodAtHillCount);

	currentField->addItem(hill);
	hill->birthInitialAnts();

	return hill;
}

int Creator::randomFoodCount() {

	return rand() % 50 + 10;
}

int Creator::randomLifetime() {

	return rand() % 50 + 25;
}

char* Creator::getAntName() {

	std::stringstream s;

	s << "Ant_Nr." << Statistics::Instance()->getCurrentAntCount();

	string asd = s.str();

	char *a = new char[asd.size() + 1];a
	[asd.size()] = 0;
	memcpy(a, asd.c_str(), asd.size());

	return a;
}
