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

Item* Creator::createAnt(Field* currentField) {
	return new Ant(currentField, randomLifetime());
}
Item* Creator::createFood(Field* currentField) {
	return new Food(currentField, randomFoodCount());
}
Item* Creator::createAntHill(Field* currentField) {
	return new AntHill(currentField);
}

int Creator::randomFoodCount() {

	srand(time(NULL));

	return rand() % 20 + 10;
}

int Creator::randomLifetime() {

	srand(time(NULL));

	return rand() % 50 + 50;
}

