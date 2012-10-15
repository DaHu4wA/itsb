#include "Item.h"

using namespace std;

Item::Item(Field* currentField) {
	this.currentField = currentField;
}

Item::~Item() {
}

void Item::act() {
	cout << "		> Item acting" << endl;
}
