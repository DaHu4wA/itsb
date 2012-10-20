#include "Field.h"

#define SIZE_X 16
#define SIZE_Y 16

#ifndef ANTGAME_H_
#define ANTGAME_H_

class Environment {
public:

	virtual ~Environment();
	void act();
	void addTestItems(int x, int y);

	//TODO getGameField() and make gameField private ...
	Field* gameField[SIZE_X][SIZE_Y];

	static Environment* Instance();

private:
	Environment();
	static Environment* pEnvironment;

	void initField();
	void initArray();
	void setFieldPointers();

};

#endif
