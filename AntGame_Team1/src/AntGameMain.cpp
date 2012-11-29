#include  <iostream>
#include  "Environment.h"
#include "FoodCountTooLowException.h"

using namespace std;

Environment* environment;

int main(int argc, char* argv[]) {
	try {
		environment = Environment::Instance();

		environment->placeFoodPlace(10, 10);
		environment->placeAntHill(4, 4);

		environment->actAll(); // Let the whole game move 1 step
		environment->actAll();

	} catch (FoodCountTooLowException& e) {
		cout << e.what() << e.getFoodCount() << endl;
	} catch (...) {
		cout << "Food to low to handle this" << endl;
	}

	return 0;
}
