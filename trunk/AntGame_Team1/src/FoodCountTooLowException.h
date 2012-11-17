#ifndef FOODCOUNTTOLOWEXCEPTION
#define FOODCOUNTTOLOWEXCEPTION_H_

#include <exception>

class FoodCountTooLowException : public std::exception{
public:
	FoodCountTooLowException(int foodCount);
	virtual const char* what() const throw();
	int getFoodCount();
private:
	int foodCount;
};
#endif
