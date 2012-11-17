#include "AntHill.h"
#include <iostream>
#include "Creator.h"
#include "Ant.h"
#include "FoodCountTooLowException.h"

using namespace std;

AntHill::AntHill(Field* currentField, int foodAtHillCount) :
		Item(currentField) {

	this->foodAtHillCount = foodAtHillCount;
}

AntHill::~AntHill() {

}

void AntHill::act() {
	cout << "		> AntHill acting" << endl;
}

void AntHill::antVisits(Ant* ant) {
	/* "recharge" ant */
	ant->setLifetime(Creator::Instance()->randomLifetime());

	if (ant->isHasFood()) {
		foodAtHillCount++;
		ant->setHasFood(false);
	}
	cout << "Ant visited the hill!" << cout;
}

void AntHill::birthInitialAnts() {

	if(foodAtHillCount < 50){
		throw FoodCountTooLowException(foodAtHillCount);
	}

	int i = 0;
	for (i = 0; i < foodAtHillCount; i++) {
		Creator::Instance()->createAnt(currentField);
	}
	foodAtHillCount = 0;
}

int AntHill::getFoodAtHillCount() {
	return foodAtHillCount;
}

