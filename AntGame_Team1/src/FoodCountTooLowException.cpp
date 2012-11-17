#include "FoodCountTooLowException.h"

using namespace std;

FoodCountTooLowException::FoodCountTooLowException(int foodCount) {
	this->foodCount = foodCount;
}

const char* FoodCountTooLowException::what() const throw () {
	return "Initial foodCount too low to handle simulation. It has to be at least 50! Your is: ";

}

int FoodCountTooLowException::getFoodCount() {
	return foodCount;
}
