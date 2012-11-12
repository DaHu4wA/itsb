#include  <iostream>
#include  "Environment.h"

using namespace std;

Environment* environment;

int main(int argc, char* argv[]) {

	environment = Environment::Instance();

	environment->placeFoodPlace(10,10);
	environment->placeAntHill(4,4);

	environment->actAll(); // Let the whole game move 1 step

	return 0;
}
