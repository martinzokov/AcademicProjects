/* 
 * File:   StreamHandler.cpp
 * Author: martin
 * 
 * StreamHandler objects that are used to process all incoming data from
 * the receivers.
 * 
 */
#include <string>
#include <math.h>
#include <sstream>
#include <string.h>
#include <ctime>
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <vector>
#include "Location.h"
#include "StreamHandler.h"

using namespace std;

/*
 * Processes a single sentence from the stream and choses what to do.
 * If a GSV or an RMC reading is acquired, it is processed.
 */
int StreamHandler::processSentence() {
    string line;

    if (!getline(reader, line)) {
        return -1;
    } else if (line.empty()) {
        return -1;
    }

    if (strstr(line.c_str(), "GSV")) {
        stringstream strstream(line.substr(7, 1));

        int gsvCount;
        strstream >> gsvCount;

        string gsvSequence[gsvCount];

        gsvSequence[0] = line;

        int i = 1;

        for (i; i < gsvCount; i++) {
            getline(reader, line);
            gsvSequence[i] = line;
        }

        readGSVSequence(gsvSequence, gsvCount);
        return GSV_RECEIVED;

    } else if (strstr(line.c_str(), "RMC")) {
        readRMC(line);
        return LOCATION_RECEIVED;
    }
    return SUCCESS;
}

/*
 * Reads an RMC sentence and set it as the current location for the receiver.
 */
void StreamHandler::readRMC(string& rmcSentence) {
    stringstream asterixIndex(rmcSentence);
    getline(asterixIndex, rmcSentence, '*');

    vector<string> rmcData;

    stringstream RMCmsg(rmcSentence);

    string rmcField;

    while (getline(RMCmsg, rmcField, ',')) {
        rmcData.push_back(rmcField);
    }

    stringstream time(rmcData.at(1));
    string rmcTime;

    getline(time, rmcTime, '.');

    rmcTime += rmcData.at(9);

    strptime(rmcTime.c_str(), "%H%M%S%d%m%y", &this->currentLocation.date);

    RMCToDecimal(rmcData.at(3), rmcData.at(5));
    currentLocation.date.tm_isdst = 0;

    if (rmcData.at(4) == "S") {
        currentLocation.latitude *= -1;
    }
    if (rmcData.at(6) == "W") {
        currentLocation.longitude *= -1;
    }

}

/*
 * Reads a full sequence of up to 3 GSV sentences and determines if there is a 
 * good quality GPS fix for the receiver.
 */
void StreamHandler::readGSVSequence(string sequence[], int gsvCount) {
    int satelitesOK = 0;
    int i;

    for (i = 0; i < gsvCount; i++) {
        stringstream asterixIndex(sequence[i]);
        getline(asterixIndex, sequence[i], '*');

        vector<string> gsvSentence;

        stringstream singleSentence(sequence[i]);
        string currSentence;

        while (getline(singleSentence, currSentence, ',')) {
            gsvSentence.push_back(currSentence);
        }

        int j;
        for (j = 7; j < gsvSentence.size() && !gsvSentence.at(j).empty(); j += 4) {
            int snr;
            stringstream sateliteSNR(gsvSentence.at(j));
            sateliteSNR >> snr;

            if (snr >= 30) {
                satelitesOK++;
            }
        }


    }
    if (satelitesOK >= 3) {
        isSignalGood = true;
        return;
    } else {
        isSignalGood = false;
    }
}

/*
 * Converts the format of the coordinates from the RMC message from
 * NMEA standard to decimal lat/lng coordinates.
 */
void StreamHandler::RMCToDecimal(string lat, string lng) {
    stringstream convertlat(lat);
    double latitude;
    convertlat >>latitude;
    int lat_degrees = (int) latitude / 100;
    double lat_minutes = (latitude - lat_degrees * 100) / 60.0;

    stringstream convertlng(lng);
    double longitude;
    convertlng >> longitude;
    int lng_degrees = (int) longitude / 100;
    double lng_minutes = (longitude - lng_degrees * 100) / 60.0;

    currentLocation.latitude = round((lat_degrees + lat_minutes) * 1000000) / 1000000;
    currentLocation.longitude = round((lng_degrees + lng_minutes) * 1000000) / 1000000;

}

/*
 * Default constructor for the class which calls the open() function on the 
 * reader.
 */
StreamHandler::StreamHandler(const string& filename) {
    reader.open(filename.c_str());
}

/* 
 * Default copy constructor.
 */
StreamHandler::StreamHandler(const StreamHandler& orig) {
}

/*
 * Destructor for the class.
 */
StreamHandler::~StreamHandler() {
    reader.close();
}

