#ifndef ANTHILL_H_
#define ANTHILL_H_

#include "Item.h"
#include "Ant.h"

namespace std {

class AntHill: public Item {
public:
	AntHill(Field* currentField, int foodAtHillCount);

	void birthInitialAnts();

	void rechargeAnt(Ant* ant);

	~AntHill();
	void act();
    int getFoodAtHillCount();

private:
	int foodAtHillCount;

	void consumeFood();
};

}
#endif
