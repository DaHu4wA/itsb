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
};

#endif
