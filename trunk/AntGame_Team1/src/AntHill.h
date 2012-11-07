#ifndef ANTHILL_H_
#define ANTHILL_H_

#include "Item.h"

namespace std {

class AntHill: public Item {
public:
	AntHill();
	~AntHill();
	void act();
};

}
#endif
