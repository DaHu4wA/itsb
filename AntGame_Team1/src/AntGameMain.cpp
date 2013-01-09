#include  <iostream>
#include "SimulationResult.h"
#include "Environment.h"
#include "Creator.h"
#include "FoodCountTooLowException.h"
#include "Statistics.h"
#include <list>

using namespace std;

Environment* environment;

std::SimulationResult* runSimulation();

int determineSimulationCount();

int main(int argc, char* argv[]) {

	list<std::SimulationResult*>* simulationResults = new list<std::SimulationResult*>;

	int simulationCount = determineSimulationCount();

	if (simulationCount == 0) {
		return 0;
	}

	// Run the simulation x times (defined by user)
	for (int x = 0; x < simulationCount; x++) {
		simulationResults->push_back(runSimulation());
		if (x < simulationCount - 1) {
			environment->reinitialize();
		}
	}

	cout << "\n\n** Simulation finished! Here are the statistics: **";

	// Display statistics for x simulations at the end
	int count = 1;
	for (list<SimulationResult*>::iterator i = (*simulationResults).begin();
			i != (*simulationResults).end(); ++i) {
		if ((*i) != NULL) {
			cout << "\n\nSimulation " << count << ":";
			(*i)->showStats();
			count++;
		}
	}

	cout << "\n\n (C) 2012/2013 Stefan Huber & Daniel Komohorov";
	return 0;
}

/**
 * Runs the whole simulation once (including output of final statistics)
 */
std::SimulationResult* runSimulation() {
	try {
		// Ants are born before "act" was called first time
		environment = Environment::Instance();
		environment->placeFoodPlace(13, 13);
		environment->placeFoodPlace(2, 3);

		environment->placeAntHill(7, 7);

		// The game continues until all ants died.
		// To protect a loop, game stops after 10.000 runs
		long count = 0;
		while (Statistics::Instance()->antsAreAlive() && count < 10000) {
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

int determineSimulationCount() {
	int simulationCount = 0;
	cout
			<< "\n*** Welcome to the ultimate ant simulation v1.0!***\n\nHow many simulations do you want to run? \nPlease a number enter and press [return] (enter 0 to cancel): ";
	cin >> simulationCount;

	if (simulationCount == 0) {
		cout << "\n\nSimulation aborted by user.";
		return 0;
	} else if (simulationCount > 1000) {
		cout
				<< "\n\nSimulation aborted! Simulation count too high! \n(Maximum 1000 simulations are allowed)\n";
		return 0;
	}
	return simulationCount;
}
