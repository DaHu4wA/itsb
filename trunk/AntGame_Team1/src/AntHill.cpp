#include "AntHill.h"
#include <iostream>
#include "Creator.h"
#include "Ant.h"

using namespace std;

AntHill::AntHill(Field* currentField, int foodAtHillCount) :
		Item(currentField) {

	this->foodAtHillCount = foodAtHillCount;
}

AntHill::~AntHill() {

}

void AntHill::act() {
	cout << "		> AntHill acting" << endl;
}

void AntHill::rechargeAnt(Ant* ant){
	ant->setLifetime(Creator::Instance()->randomLifetime());
}

void  AntHill::birthInitialAnts(){
	int i = 0;
	for(i = 0; i < foodAtHillCount; i++ ){
		Creator::Instance()->createAnt(currentField);
	}
}

int AntHill::getFoodAtHillCount()
{
    return foodAtHillCount;
}





