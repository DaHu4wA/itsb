#ifndef FOOD_H_
#define FOOD_H_

#include "Item.h"
#include "Ant.h"

namespace std {

class Food: public Item {
public:
	Food(Field* currentField, unsigned int foodCount);
	~Food();
	void act();

	void takeFood(Ant* ant);
    unsigned int getFoodCount();
    void setFoodCount(unsigned int foodCount);

private:
	unsigned int foodCount;
};

}
#endif
