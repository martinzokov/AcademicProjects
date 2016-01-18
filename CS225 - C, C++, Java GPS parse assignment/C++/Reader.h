/* 
 * File:   Reader.h
 * Author: martin
 *
 * Created on 28 March 2014, 08:25
 */

#ifndef READER_H
#define	READER_H

using namespace std;

class Reader {
public:
    
    int synchroniseStreams();
    void processStreams();
    void calculateOffset();
    void getLocationWithOffset();
    void saveToGPX(vector<Location> route);
    
    
    
    vector<Location> route;
    
    
    
    Reader(const string& file_one, const string& file_two);

    virtual ~Reader();

private:
    StreamHandler streamOne;
    StreamHandler streamTwo;
    
    long latOffset;
    long lngOffset;
    ofstream writer;
};

#endif	/* READER_H */

