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

void Food::foodTaken() {

	if (foodCount > 0) {
		foodCount--;
	} else {
		cout << "NO MORE FOOD !!! " << endl;
	}

}

