#ifndef FIELD_H_
#define FIELD_H_

#include <list>
#include <iostream>
#include "Item.h"

using namespace std;

class Field {

public:
    Field();
    ~Field();

    void addItem(Item* item);
    void removeItem(Item* item);
    void act();

    list<Item*>* getItems();
	Field *getEast();
    Field *getNorth();
    Field *getSouth();
    Field *getWest();

    void setItems(list<Item*>* items);

    void setEast(Field *east);
    void setNorth(Field *north);
    void setSouth(Field *south);
    void setWest(Field *west);

private:

	list<Item*>* items;

	Field* south;
	Field* north;
	Field* west;
	Field* east;

	// es muss irgendwie eine Abnahme der Spur geben

	// nicht immer der Spur folgen ... ? mit etwas zufall dumme, abweichende Ameisen?

	// weg wird besser mit der Zeit?

	// eventuell kann Ameise nur bestimme ANzahl Pheromone abgeben?

	int pheromonStrength; // +irgendwas when ant moves here, -1 on normal act method
};

#endif
