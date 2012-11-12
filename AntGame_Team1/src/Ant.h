#ifndef ANT_H_
#define ANT_H_

#include "Item.h"

namespace std {

class Ant: public Item {
public:
	Ant(Field* currentField, unsigned int lifetime);
	~Ant();
	void act();
	unsigned int getLifetime();
	bool isHasFood();
	void setHasFood(bool hasFood);
    void setLifetime(unsigned int lifetime);

private:
	void movePosition();

	unsigned int lifetime;
	bool hasFood;
};

}
#endif
