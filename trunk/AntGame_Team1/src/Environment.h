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

	//TODO resize methode, um im default constructor eine größe angeben zu können?

	Field* gameField[SIZE_X][SIZE_Y];

	// Field ** gameField;.. dann mit new Field[] .
	// auf breite zugreifen: gameField
	// auf höhe zugreifen: gamefield[zeile]

private:
	Environment();
	static Environment* pEnvironment;

	void initField();
	void initArray();
	void setFieldPointers();

	void placeFoodPlace(int x, int y);
	void placeAntHill(int x, int y);
};

#endif
