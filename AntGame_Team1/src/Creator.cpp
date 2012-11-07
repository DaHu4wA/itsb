#include "Creator.h"
#include "Ant.h"
#include "AntHill.h"
#include "Food.h"
#include <iostream>

using namespace std;

Creator* Creator::pCreator = 0;

Creator* Creator::Instance() {

	if (pCreator == 0) {
		pCreator = new Creator();
	}
	return pCreator;
}

Creator::Creator() {
}

Creator::~Creator() {
}

Item* Creator::createAnt() {
	return new Ant();
}
Item* Creator::createFood() {
	return new Food();
}
Item* Creator::createAntHill() {
	return new AntHill();
}

