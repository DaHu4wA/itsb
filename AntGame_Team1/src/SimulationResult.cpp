#include "SimulationResult.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

SimulationResult::SimulationResult(int gameActCount, int initialAntCount,
		int foodTakenCunt, int foodBroughtCount, int noFoodBroughtCount) {

	this->initialAntCount = initialAntCount;
	this->gameActCount = gameActCount;
	this->foodTakenCunt = foodTakenCunt;
	this->foodBroughtCount = foodBroughtCount;
	this->noFoodBroughtCount = noFoodBroughtCount;
}

void SimulationResult::showStats() {

	cout << "\n-----------------------------------------------------\n";
	cout << "-> " << gameActCount << " acts were made before all ants died\n";
	cout << "-> " << initialAntCount << " ants were initially born\n";
	cout << "-> " << foodTakenCunt
			<< " times ants took food from food place(s)\n";
	cout << "-> " << foodBroughtCount
			<< " times food has been brought to the hill(s)\n";
	cout << "-> " << noFoodBroughtCount
			<< " times ant hill(s) have been visited without food";
	cout << "\n-----------------------------------------------------";
}

int SimulationResult::getFoodBroughtCount() {
	return foodBroughtCount;
}

int SimulationResult::getFoodTakenCunt() {
	return foodTakenCunt;
}

int SimulationResult::getGameActCount() {
	return gameActCount;
}

int SimulationResult::getInitialAntCount() {
	return initialAntCount;
}

int SimulationResult::getNoFoodBroughtCount() {
	return noFoodBroughtCount;
}

void SimulationResult::setNoFoodBroughtCount(int noFoodBroughtCount) {
	this->noFoodBroughtCount = noFoodBroughtCount;
}

