#include "Food.h"
#include "Statistics.h"
#include "Creator.h"
#include <iostream>
#include <typeinfo>

/**
 * This represents the food hill, where ants can take food from.
 */
using namespace std;

Food::Food(Field* currentField, unsigned int foodCount) :
		Item(currentField) { // Call constructor of base class
	this->foodCount = foodCount;
}

Food::~Food() {
}

void Food::act() {
	//food has nothing to act
}

void Food::takeFood(Ant* ant) {

	if (foodCount > 0) {
		Statistics::Instance()->incrementFoodTakenCount();
		ant->setHasFood(true);
		foodCount--;
	} else {
		cout << "NO MORE FOOD !!! " << endl;
	}

	ant->setLifetime(Creator::Instance()->randomLifetime()); // "recharge" ants lifetime

	cout << ant->getName() << " took sweets from food place!" << endl;
}

unsigned int Food::getFoodCount() {
	return foodCount;
}

void Food::setFoodCount(unsigned int foodCount) {
	this->foodCount = foodCount;
}

