#include  <iostream>
#include  "Environment.h"

using namespace std;

Environment* environment;

int main(int argc, char* argv[]) {

	environment = new Environment();

	environment->addTestItems(1, 1);
	environment->addTestItems(1, 1);
	environment->addTestItems(1, 1);

	environment->addTestItems(4, 2);
	environment->addTestItems(4, 0);

	environment->addTestItems(15, 14);

	environment->act();

	return 0;
}
