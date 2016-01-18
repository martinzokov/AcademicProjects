/* 
 * File:   StreamHandler.h
 * Author: martin
 *
 * Created on 28 March 2014, 08:25
 */

#ifndef STREAMHANDLER_H
#define	STREAMHANDLER_H
using namespace std;
class StreamHandler {
public:
    
    #define STREAM_END  0
    #define SUCCESS  1
    #define LOCATION_RECEIVED  2
    #define GSV_RECEIVED 3

    bool isSignalGood;

    Location currentLocation;

    void readGSVSequence(string sequence[], int gsvCount);
    void readRMC(string& rmc);
    void RMCToDecimal(string lat, string lng);
    int processSentence();
    
    StreamHandler(const string& filename);
    StreamHandler(const StreamHandler& orig);
    virtual ~StreamHandler();
private:
    ifstream reader;
    
};

#endif	/* STREAMHANDLER_H */

