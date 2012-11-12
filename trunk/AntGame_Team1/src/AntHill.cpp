#include "AntHill.h"
#include <iostream>

namespace std {

AntHill::AntHill(Field* currentField) :
		Item(currentField) {

}

AntHill::~AntHill() {

}

void AntHill::act() {
	cout << "		> AntHill acting" << endl;
}

}
