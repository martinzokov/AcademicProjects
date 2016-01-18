/* 
 * File:   main.cpp
 * Author: martin
 *
 * The main file is the entry point for the program.
 */

#include <iostream>
#include <cstdlib>
#include <fstream>
#include <ctime>
#include <string>
#include <vector>
#include <fstream>
#include "Location.h"
#include "StreamHandler.h"
#include "Reader.h"

using namespace std;

/*
 * Entry point for the program. Creates a Reader object which starts listening
 * for GPS data.
 */
int main(int argc, char** argv) {

    Reader gpsReader("gps_1.dat", "gps_2.dat");

    return 0;
}

