#ifndef ANTHILL_H_
#define ANTHILL_H_

#include "Item.h"

namespace std {

class AntHill: public Item {
public:
	AntHill(Field* currentField);
	~AntHill();
	void act();

private:
	int foodAtHillCount;
	int createdAntsCount;

	void consumeFood();
	void birthAnts(); //Add ants to the field where the AntHill is placed on
};

}
#endif
