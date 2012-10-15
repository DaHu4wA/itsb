//#include "Field.h"
#include  <iostream>

#ifndef ITEM_H_
#define ITEM_H_

class Item {
public:
	//Item(Field* currentField); not working :(
	Item();
	~Item();
	void act();

private:
	//Field* currentField;
};

#endif
