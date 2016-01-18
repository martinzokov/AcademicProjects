/* 
 * File:   Reader.cpp
 * Author: martin
 * 
 */
#include <ctime>
#include <iostream>
#include <vector>
#include <cstdlib>
#include <string>
#include <fstream>
#include <iomanip> 
#include "Location.h"
#include "StreamHandler.h"
#include "Reader.h"


using namespace std;

/*
 * Constructor for the Reader class. takes two filenames for the two 
 * streams and creates two StreamHandler objects.
 */
Reader::Reader(const string& file_one, const string& file_two) : streamOne(file_one), streamTwo(file_two) {
    streamOne.isSignalGood = false;
    streamTwo.isSignalGood = false;

    this->processStreams();

    saveToGPX(route);
}

/*
 * Starts reading from the streams, does an initial synchronisation between
 * the two receivers and starts an infinite loop which is borken when the stream
 * ends. While iterating, new locations are added to the linked list,
 * based on the quality of the GPS fix.
 */
void Reader::processStreams() {

    int streamOneStatus = 0, streamTwoStatus = 0;
    streamOne.isSignalGood = false;
    streamTwo.isSignalGood = false;


    latOffset = 0;
    lngOffset = 0;

    this->synchroniseStreams();
    while (1) {
        if (this->streamOne.isSignalGood) {
            route.push_back(this->streamOne.currentLocation);
            this->calculateOffset();
        } else if (this->streamTwo.isSignalGood) {
            this->getLocationWithOffset();
            route.push_back(this->streamTwo.currentLocation);
        }



        do {
            streamOneStatus = streamOne.processSentence();
            if (streamOneStatus == -1) {
                return;
            }
        } while (streamOneStatus != 2);

        if (streamOneStatus == 0) {
            return;
        }

        do {
            streamTwoStatus = streamTwo.processSentence();
            if (streamTwoStatus == -1) {
                return;
            }
        } while (streamTwoStatus != 2);

        if (streamTwoStatus == 0) {
            return;
        }
    }


}

/*
 * Calculates the offset between two locations
 */
void Reader::calculateOffset() {
    latOffset = (long) (streamOne.currentLocation.latitude * 1000000) - (long) (streamTwo.currentLocation.latitude * 1000000);
    lngOffset = (long) (streamTwo.currentLocation.longitude * 1000000) - (long) (streamTwo.currentLocation.longitude * 1000000);
}

/*
 * Aligns a location based on the offset.
 */
void Reader::getLocationWithOffset() {
    streamTwo.currentLocation.latitude += ((double) latOffset) / 1000000;
    streamTwo.currentLocation.longitude += ((double) lngOffset) / 1000000;
}

/*
 * Synchronises the two streams. Waits for RMC sentences with the same time, which
 * would mean that the two streams are synchronised.
 */
int Reader::synchroniseStreams() {
    int streamOneStatus = 0, streamTwoStatus = 0;

    while (streamOneStatus != LOCATION_RECEIVED) {
        streamOneStatus = streamOne.processSentence();
    }
    while (streamTwoStatus != LOCATION_RECEIVED) {
        streamTwoStatus = streamTwo.processSentence();
    }
    time_t timeOne = mktime(&streamOne.currentLocation.date);
    time_t timeTwo = mktime(&streamTwo.currentLocation.date);

    double timeDiff = difftime(timeOne, timeTwo);

    if (timeDiff < 0) {
        while (timeDiff != 0) {
            streamOne.processSentence();
            timeOne = mktime(&streamOne.currentLocation.date);
            timeTwo = mktime(&streamTwo.currentLocation.date);
            timeDiff = difftime(timeOne, timeTwo);
        }
    } else {
        while (timeDiff != 0) {
            streamTwo.processSentence();
            timeOne = mktime(&streamOne.currentLocation.date);
            timeTwo = mktime(&streamTwo.currentLocation.date);
            timeDiff = difftime(timeOne, timeTwo);
        }
    }
    return 1;
}

/*
 * Saves all the route data from the vector to a GPX file 
 */
void Reader::saveToGPX(vector<Location> route) {

    string filename = "resultC++.gpx";
    writer.open(filename.c_str());
    writer << "<?xml version=\"1.0\"?>\n";
    writer << "<gpx version=\"1.0\"";
    writer << "\ncreator=\"Martin Zokov\"";
    writer << "\nxmlns:xsi=\"";
    writer << "http://www.w3.org/2001/XMLSchema-instance\">\n";

    for (int i = 0; i < route.size(); ++i) {

        writer << setprecision(10) <<
                "<wpt lat=\"" << route.at(i).latitude <<
                "\" ";


        writer << setprecision(10) <<
                "lon=\"" << route.at(i).longitude <<
                "\">";


        writer << "\n<time>" <<
                asctime(&route.at(i).date) <<
                "</time>\n</wpt>\n";

    }
    writer << "</gpx>";
    writer.close();
}

Reader::~Reader() {
}

