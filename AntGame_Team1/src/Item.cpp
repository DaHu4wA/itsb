#include "Item.h"

/**
 * Abstract base class for all items (Ants etc)
 */
using namespace std;

Item::Item(Field* currentField) {
	this->currentField = currentField;
}

Item::~Item() {
}

Field *Item::getCurrentField() {
	return currentField;
}

void Item::setCurrentField(Field *currentField) {
	this->currentField = currentField;
}

