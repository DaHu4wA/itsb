#include  <iostream>
#include  "Environment.h"

using namespace std;

Environment* environment;

int main(int argc, char* argv[]) {

	//Instantiate environment with initialized fields and correctly set pointers

	environment = Environment::Instance();

	environment->addTestItems(1, 1); // Add a simple Item to field [1][1]
	environment->addTestItems(1, 1);
	environment->addTestItems(1, 1);

	environment->addTestItems(4, 2);
	environment->addTestItems(4, 0);

	environment->addTestItems(15, 14);

	environment->act(); // Let the whole game move 1 step

	return 0;
}
