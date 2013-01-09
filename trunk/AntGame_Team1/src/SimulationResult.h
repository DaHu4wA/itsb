#ifndef SIMULATIONRESULT_H_
#define SIMULATIONRESULT_H_

namespace std {

class SimulationResult {
public:
	SimulationResult(int gameActCount, int initialAntCount, int foodTakenCunt,
			int foodBroughtCount, int noFoodBroughtCount);
	int getFoodBroughtCount();
	int getFoodTakenCunt();
	int getGameActCount();
	int getInitialAntCount();
	int getNoFoodBroughtCount();
	void setNoFoodBroughtCount(int noFoodBroughtCount);

	void showStats();

private:

	int gameActCount;
	int initialAntCount;
	int foodTakenCunt;
	int foodBroughtCount;
	int noFoodBroughtCount;
};

}
#endif
