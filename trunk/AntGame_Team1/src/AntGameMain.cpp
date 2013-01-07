#include  <iostream>
#include  "Environment.h"
#include "Creator.h"
#include "FoodCountTooLowException.h"

using namespace std;

Environment* environment;

int main(int argc, char* argv[]) {
	try {
		environment = Environment::Instance();

		environment->placeFoodPlace(10, 10);
		environment->placeAntHill(4, 4);

		Creator* c = Creator::Instance();

		/* The game continues until all ants died. To protect a loop, game stops after 100000 runs */
		long count = 0;

		while (c->antsAreAlive() && count < 1000000) {
			environment->actAll();
			count++;
		}

		cout << "moves needed:" << count;

	} catch (FoodCountTooLowException& e) {
		cout << e.what() << e.getFoodCount() << endl;
	} catch (...) {
		cout << "Some exception happened" << endl;
	}

	return 0;
}
