/* 
 * File:   Location.h
 * Author: martin
 *
 * Created on 28 March 2014, 08:25
 */

#ifndef LOCATION_H
#define	LOCATION_H

class Location {
public:
    
     
    double latitude;
    double longitude;
    struct tm date;
    
    Location();
    Location(const Location& orig);
    virtual ~Location();
private:

};

#endif	/* LOCATION_H */

