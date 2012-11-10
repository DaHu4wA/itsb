#ifndef ANT_H_
#define ANT_H_

#include "Item.h"

namespace std {

class Ant: public Item {
public:
	Ant();
	~Ant();
	void act();

	// private: number zur wiedererkennung
	//			lebenszeit, in der factory zufällig gesetzt
};

}
#endif
