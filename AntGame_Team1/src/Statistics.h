#include "SimulationResult.h"

#ifndef STATISTICS_H_
#define STATISTICS_H_

class Statistics {
public:

	bool antsAreAlive();
	int getCurrentAntCount();
	void incrementCurrentAntCount();
	void decrementCurrentAntCount();

	static Statistics* Instance();
	virtual ~Statistics();

	void showStats();

	void setInitialAntCount(int initialAntCount);
	int getFoodBroughtCount();
	int getFoodTakenCunt();
	int getGameActCount();
	int getInitialAntCount();
	int getNoFoodBroughtCount();

	void resetStats();
	void incrementFoodBroughtCount();
	void incrementFoodTakenCount();
	void setGameActCount(int gameActCount);
	void incrementNoFoodBroughtCount();

	std::SimulationResult* buildSimulationResult();

private:
	Statistics();

	static Statistics* pStatistics;

	int currentAntCount;

	int gameActCount;
	int initialAntCount;
	int foodTakenCunt;
	int foodBroughtCount;
	int noFoodBroughtCount;
};

#endif
