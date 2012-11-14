#include "Food.h"
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

	cout << "		> Food acting" << endl;
}

void Food::takeFood(Ant* ant) {

	if (foodCount > 0) {
		ant->setHasFood(true);
		foodCount--;
	} else {
		cout << "NO MORE FOOD !!! " << endl;
	}

	cout << "An ant found some food!" << cout;
}

unsigned int Food::getFoodCount() {
	return foodCount;
}

void Food::setFoodCount(unsigned int foodCount) {
	this->foodCount = foodCount;
}

