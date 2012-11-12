#include "AntHill.h"
#include <iostream>
#include "Creator.h"
#include "Ant.h"

using namespace std;

AntHill::AntHill(Field* currentField) :
		Item(currentField) {

}

AntHill::~AntHill() {

}

void AntHill::act() {
	cout << "		> AntHill acting" << endl;
}

void AntHill::birthAnt(){

	Creator::Instance()->createAnt(currentField);
}


void  AntHill::birthInitialAnts(int antCount){
	int i = 0;
	for(i = 0; i < antCount; i++ ){
		Creator::Instance()->createAnt(currentField);
	}
}


