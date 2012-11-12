#include "Environment.h"
#include "Ant.h"
#include "AntHill.h"
#include "Food.h"
#include "Item.h"
#include "Item.h"
#include "Creator.h"
#include  <iostream>

using namespace std;

Environment* Environment::pEnvironment = 0;

Environment* Environment::Instance() {

	if (pEnvironment == 0) {
		pEnvironment = new Environment();
	}
	return pEnvironment;
}

Environment::Environment() {
	initField();
}

Environment::~Environment() {
	int x = 0, y = 0;
	for (x = 0; x < SIZE_X; x++) {
		for (y = 0; y < SIZE_Y; y++) {
			delete gameField[x][y];
		}
	}
}

void Environment::actAll() {
	cout << "> Environment acting" << endl;
	int x = 0, y = 0;

	for (x = 0; x < SIZE_X; x++) {
		for (y = 0; y < SIZE_Y; y++) {
			gameField[x][y]->act();
		}
	}
}

void placeFoodPlace(int x, int y) {

	// TODO

}
void placeAntHill(int x, int y) {

	// TODO

}

void Environment::addTestItems(int x, int y) {

	Item* food = Creator::Instance()->createFood(gameField[x][y]);
	gameField[x][y]->addItem(food);

	Item* ant = Creator::Instance()->createAnt(gameField[x][y]);
	gameField[x][y]->addItem(ant);

	Item* antHill = Creator::Instance()->createAntHill(gameField[x][y]);
	gameField[x][y]->addItem(antHill);
}

void Environment::initField() {
	initArray();
	setFieldPointers();
}

void Environment::initArray() {

	int x = 0, y = 0;

	for (x = 0; x < SIZE_X; x++) {
		for (y = 0; y < SIZE_Y; y++) {
			gameField[x][y] = new Field();
		}
	}
}

void Environment::setFieldPointers() {

	int x = 0, y = 0;

	for (x = 0; x < SIZE_X; x++) {
		for (y = 0; y < SIZE_Y; y++) {

			if (y > 0)
				gameField[x][y]->setNorth(gameField[x][y - 1]);
			if (y < SIZE_Y - 1)
				gameField[x][y]->setSouth(gameField[x][y + 1]);

			if (x > 0)
				gameField[x][y]->setWest(gameField[x - 1][y]);
			if (x < SIZE_X - 1)
				gameField[x][y]->setEast(gameField[x + 1][y]);
		}
	}
}
