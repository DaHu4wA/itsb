#include  <iostream>
#include "SimulationResult.h"
#include "Environment.h"
#include "Creator.h"
#include "FoodCountTooLowException.h"
#include "Statistics.h"

using namespace std;

Environment* environment;

std::SimulationResult* runSimulation();

int main(int argc, char* argv[]) {

	std::SimulationResult* result1 = runSimulation();
	environment->reinitialize();
	std::SimulationResult* result2 = runSimulation();

	cout << "\n\n** Simulation finished! Here are some statistics: **";
	cout << "\n\nSimulation 1:";
	result1->showStats();
	cout << "\n\nSimulation 2:";
	result2->showStats();

	cout << "\n\n (C) 2012/2013 Stefan Huber & Daniel Komohorov";

	return 0;
}

/**
 * Runs the whole simulation once (including output of final statistics)
 */
std::SimulationResult* runSimulation() {
	try {
		environment = Environment::Instance();
		environment->placeFoodPlace(13, 13);
		environment->placeFoodPlace(2, 3);

		environment->placeAntHill(7, 7);

		Creator *c = Creator::Instance();
		// The game continues until all ants died.
		// To protect a loop, game stops after 10.000 runs
		long count = 0;
		while (c->antsAreAlive() && count < 10000) {
			environment->actAll();
			count++;
		}
		Statistics::Instance()->setGameActCount(count);
		return Statistics::Instance()->buildSimulationResult();

	} catch (FoodCountTooLowException & e) {
		cout << e.what() << e.getFoodCount() << endl;
	} catch (...) {
		cout << "Some exception happened" << endl;
	}
	return NULL;
}

