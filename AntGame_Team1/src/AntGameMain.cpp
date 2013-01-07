#include  <iostream>
#include  "Environment.h"
#include "Creator.h"
#include "FoodCountTooLowException.h"

using namespace std;

Environment* environment;

void showStats(long count);

int main(int argc, char* argv[]) {
	try {
		environment = Environment::Instance();

		environment->placeFoodPlace(10, 10);
		environment->placeAntHill(4, 4);

		Creator* c = Creator::Instance();

		/* The game continues until all ants died.
		   To protect a loop, game stops after 10.000 runs */
		long count = 0;
		while (c->antsAreAlive() && count < 10000) {
			environment->actAll();
			count++;
		}
		showStats(count);

	} catch (FoodCountTooLowException& e) {
		cout << e.what() << e.getFoodCount() << endl;
	} catch (...) {
		cout << "Some exception happened" << endl;
	}

	return 0;
}


void showStats(long count){

	cout << "\n----------------------------------------\n";
	cout << "Simulation finished!   Stats:\n\n";
	cout << "-> "<< count << " acts were made before all ants died\n";
	cout << "-> xx ants were initially born\n";
	cout << "-> xx times ants took food\n";
	cout << "-> xx times food has been brought to the hill\n";
	cout << "-> xx times hill has been visited without food";

}

