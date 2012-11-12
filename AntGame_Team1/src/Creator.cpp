#include "Creator.h"
#include "Ant.h"
#include "AntHill.h"
#include "Food.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

using namespace std;

Creator* Creator::pCreator = 0;

Creator* Creator::Instance() {

	if (pCreator == 0) {
		pCreator = new Creator();
	}
	return pCreator;
}

Creator::Creator() {
}

Creator::~Creator() {
}

Ant* Creator::createAnt(Field* currentField) {

	Ant* ant = new Ant(currentField, randomLifetime());

	currentField->addItem(ant);
	return ant;
}
Food* Creator::createFood(Field* currentField) {

	Food* food = new Food(currentField, randomLifetime());

	currentField->addItem(food);
	return food;
}
AntHill* Creator::createAntHill(Field* currentField) {

	AntHill* hill = new AntHill(currentField);

	currentField->addItem(hill);

	hill->birthInitialAnts(25);

	return hill;
}

int Creator::randomFoodCount() {

	srand(time(NULL));

	return rand() % 20 + 10;
}

int Creator::randomLifetime() {

	srand(time(NULL));

	return rand() % 50 + 50;
}

