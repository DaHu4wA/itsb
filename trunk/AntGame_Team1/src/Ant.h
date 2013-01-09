#ifndef ANT_H_
#define ANT_H_

#include "Item.h"
#include <list>
#include "Field.h"

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
    void setName(char* name);
    char* getName();
    void setPheromones(unsigned int pheromones);

private:
    Field* checkPheromones();
    Field* getRandomWay();
	void movePosition();
	void checkOwnField();

	list<Field*>* fieldHistory;

	unsigned int lifetime;
	unsigned int pheromones;
	char* antName;
	bool hasFood;
};

}
#endif
