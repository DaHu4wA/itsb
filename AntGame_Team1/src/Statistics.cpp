#include "Statistics.h"
#include "SimulationResult.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sstream>
#include <typeinfo>

/**
 * Statistics to use for displaying results of the simulation
 */
using namespace std;

Statistics* Statistics::pStatistics = 0;

Statistics* Statistics::Instance() {

	if (pStatistics == 0) {
		pStatistics = new Statistics();
	}

	return pStatistics;
}

void Statistics::resetStats() {
	this->initialAntCount = 0;
	this->gameActCount = 0;
	this->foodTakenCunt = 0;
	this->foodBroughtCount = 0;
	this->noFoodBroughtCount = 0;
	this->currentAntCount = 0;
}

Statistics::Statistics() {
	resetStats();
}

std::SimulationResult* Statistics::buildSimulationResult() {
	return new SimulationResult(gameActCount, initialAntCount, foodTakenCunt,
			foodBroughtCount, noFoodBroughtCount);
}

Statistics::~Statistics() {
}

void Statistics::decrementCurrentAntCount() {
	currentAntCount--;
}

void Statistics::incrementCurrentAntCount() {
	currentAntCount++;
}

int Statistics::getCurrentAntCount(){
	return currentAntCount;
}

bool Statistics::antsAreAlive() {
	return currentAntCount > 0;
}

void Statistics::setInitialAntCount(int initialAntCount) {
	// there might exist more than one hill....
	this->initialAntCount = this->initialAntCount + initialAntCount;
}

int Statistics::getFoodBroughtCount() {
	return foodBroughtCount;
}

int Statistics::getFoodTakenCunt() {
	return foodTakenCunt;
}

int Statistics::getGameActCount() {
	return gameActCount;
}

int Statistics::getInitialAntCount() {
	return initialAntCount;
}

int Statistics::getNoFoodBroughtCount() {
	return noFoodBroughtCount;
}

void Statistics::incrementFoodBroughtCount() {
	this->foodBroughtCount++;
}

void Statistics::incrementFoodTakenCount() {
	this->foodTakenCunt++;
}

void Statistics::setGameActCount(int gameActCount) {
	this->gameActCount = gameActCount;
}

void Statistics::incrementNoFoodBroughtCount() {
	this->noFoodBroughtCount++;
}

