#include "AntHill.h"
#include <iostream>
#include "Creator.h"
#include "Ant.h"
#include "FoodCountTooLowException.h"
#include "Statistics.h"

/**
 * This representents the ant hill. There can also be more hills placed in the game.
 */
using namespace std;

AntHill::AntHill(Field* currentField, int foodAtHillCount) :
		Item(currentField) {

	this->foodAtHillCount = foodAtHillCount;
}

AntHill::~AntHill() {
}

void AntHill::act() {
	// Hill does not do anything.
}

void AntHill::antVisits(Ant* ant) {
	ant->setLifetime(Creator::Instance()->randomLifetime()); // "recharge" ants lifetime

	if (ant->isHasFood()) {
		foodAtHillCount++;
		ant->setHasFood(false);
		cout << ant->getName()<< " brought some food to the hill!\n" ;
		Statistics::Instance()->incrementFoodBroughtCount();
	}else{
		Statistics::Instance()->incrementNoFoodBroughtCount();
	}
}

/**
 * This does the initial birth of ants. One ant per food at hill is born.
 */
void AntHill::birthInitialAnts() {
	Statistics::Instance()->setInitialAntCount(foodAtHillCount);

	if (foodAtHillCount < 20) {
		throw FoodCountTooLowException(foodAtHillCount);
	}

	int i = 0;
	for (i = 0; i < foodAtHillCount ; i++) {
		Creator::Instance()->createAnt(currentField);
	}
	foodAtHillCount = 0;
}

int AntHill::getFoodAtHillCount() {
	return foodAtHillCount;
}

