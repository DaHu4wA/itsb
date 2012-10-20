#ifndef FOOD_H_
#define FOOD_H_

#include "Item.h"

namespace std {

class Food: public Item {
public:
	Food();
	~Food();
	void act();
};

}
#endif
