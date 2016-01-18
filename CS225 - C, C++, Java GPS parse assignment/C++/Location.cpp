/* 
 * File:   Location.cpp
 * Author: martin
 * 
 * 
 */

#include <ctime>
#include "Location.h"

/*
 * Constructor for the Location class. Initialises all members.
 */
Location::Location() {
    latitude = 0;
    longitude = 0;
    date.tm_hour = 0;
    date.tm_mday = 0;
    date.tm_min = 0;
    date.tm_sec = 0;
    date.tm_year = 0;
    date.tm_mon = 0;
    date.tm_gmtoff = 0;
}

Location::Location(const Location& orig) {
    latitude = orig.latitude;
    longitude = orig.longitude;
    date = orig.date;
}

Location::~Location() {
}

