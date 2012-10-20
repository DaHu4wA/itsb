#include  <iostream>

#ifndef ITEM_H_
#define ITEM_H_

class Field;

class Item {
public:
	Item(); //TODO only one constructor with currentField
	~Item();
	virtual void act() = 0;

protected:
	Field* currentField;
};

#endif
