#include "Field.h"

#define SIZE_X 16
#define SIZE_Y 16

#ifndef ENVIRONMENT_H_
#define ENVIRONMENT_H_

class Environment {
public:

	static Environment* Instance();
	virtual ~Environment();

	void actAll();

	void placeFoodPlace(int x, int y);
	void placeAntHill(int x, int y);

	Field* gameField[SIZE_X][SIZE_Y];

private:
	Environment();
	static Environment* pEnvironment;

	void initField();
	void initArray();
	void setFieldPointers();
};

#endif
