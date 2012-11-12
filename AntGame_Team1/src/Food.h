#ifndef FOOD_H_
#define FOOD_H_

#include "Item.h"

namespace std {

class Food: public Item {
public:
	Food(Field* currentField, unsigned int foodCount);
	~Food();
	void act();

	void foodTaken();

private:
	unsigned int foodCount;
};

}
#endif
