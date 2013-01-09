#include "Statistics.h"
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

Statistics::Statistics() {
	this->initialAntCount = 0;
	this->gameActCount = 0;
	this->foodTakenCunt = 0;
	this->foodBroughtCount = 0;
	this->noFoodBroughtCount = 0;
}

Statistics::~Statistics() {
}

void Statistics::showStats() {

	cout << "\n-----------------------------------------------------\n";
	cout << "** Simulation finished! Here are some statistics: **\n\n";
	cout << "-> " << gameActCount       << " acts were made before all ants died\n";
	cout << "-> " << initialAntCount    << " ants were initially born\n";
	cout << "-> " << foodTakenCunt      << " times ants took food from food place(s)\n";
	cout << "-> " << foodBroughtCount   <<" times food has been brought to the hill(s)\n";
	cout << "-> " << noFoodBroughtCount <<" times hill(s) have been visited without food";
	cout << "\n-----------------------------------------------------";
	cout << "\n (C) 2012/2013 Stefan Huber & Daniel Komohorov";
}

void Statistics::setInitialAntCount(int initialAntCount) {
	// if there is more than one hill...
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

