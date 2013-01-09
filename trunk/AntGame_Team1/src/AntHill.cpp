#include "AntHill.h"
#include <iostream>
#include "Creator.h"
#include "Ant.h"
#include "FoodCountTooLowException.h"
#include "Statistics.h"

using namespace std;

AntHill::AntHill(Field* currentField, int foodAtHillCount) :
		Item(currentField) {

	this->foodAtHillCount = foodAtHillCount;
}

AntHill::~AntHill() {
}

void AntHill::act() {
//	cout << "		> AntHill acting" << endl;
}

void AntHill::antVisits(Ant* ant) {
	// "recharge" ant
	ant->setLifetime(Creator::Instance()->randomLifetime());

	if (ant->isHasFood()) {
		foodAtHillCount++;
		ant->setHasFood(false);
		cout << ant->getName()<< " brought some food to the hill!\n" ;
		Statistics::Instance()->incrementFoodBroughtCount();
	}else{
//		cout << "Ant visited the hill without food\n" ;
		Statistics::Instance()->incrementNoFoodBroughtCount();
	}
}

void AntHill::birthInitialAnts() {
	Statistics::Instance()->setInitialAntCount(foodAtHillCount);

	if (foodAtHillCount < 50) {
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

