
#include "Field.h"

#define SIZE_X 16
#define SIZE_Y 16

#ifndef ANTGAME_H_
#define ANTGAME_H_

class Environment {
public:

	Environment(void);
	virtual ~Environment();
	void act();
	void addTestItems(int x, int y);

	//TODO getGameField() and make gameField private ... getter for multi dimensional array ?!
	Field* gameField[SIZE_X][SIZE_Y];

private:

    void initField();
	void initArray();
	void setFieldPointers();

};

#endif
