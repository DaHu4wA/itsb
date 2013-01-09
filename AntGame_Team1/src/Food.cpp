#include "Food.h"
#include "Statistics.h"
#include <iostream>
#include <typeinfo>

using namespace std;

Food::Food(Field* currentField, unsigned int foodCount) :
		Item(currentField) { // Konstruktor der Basisklasse aufrufen
	this->foodCount = foodCount;
}

Food::~Food() {
}

void Food::act() {

//	cout << "		> Food acting" << endl;
}

void Food::takeFood(Ant* ant) {

	if (foodCount > 0) {
		Statistics::Instance()->incrementFoodTakenCount();
		ant->setHasFood(true);
		foodCount--;
	} else {
		cout << "NO MORE FOOD !!! " << endl;
	}


	ant->setLifetime(ant->getLifetime()+50); //TODO remove: just a test if more ants find back home

	cout << ant->getName() <<" took sweets from foodplace!" << endl;
}

unsigned int Food::getFoodCount() {
	return foodCount;
}

void Food::setFoodCount(unsigned int foodCount) {
	this->foodCount = foodCount;
}

