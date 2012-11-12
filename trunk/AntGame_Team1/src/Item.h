#include  <iostream>
#include "Field.h"

#ifndef ITEM_H_
#define ITEM_H_

class Field;

class Item {
public:
	Item(Field* currentField);

	virtual ~Item();
	virtual void act() = 0;
	Field *getCurrentField();
	void setCurrentField(Field *currentField);

protected:
	Field* currentField;
};

#endif
