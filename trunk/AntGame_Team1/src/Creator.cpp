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
		pCreator = new Creator();	srand(time(NULL));
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

	Food* food = new Food(currentField, randomFoodCount());

	currentField->addItem(food);
	return food;
}
AntHill* Creator::createAntHill(Field* currentField) {

	int count = rand() % 25 + 25;

	AntHill* hill = new AntHill(currentField, count);

	currentField->addItem(hill);
	hill->birthInitialAnts();

	return hill;
}

int Creator::randomFoodCount() {

	return rand() % 40 + 10;
}

int Creator::randomLifetime() {

	return rand() % 25 + 50;
}

