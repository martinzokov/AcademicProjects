#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define __USE_XOPEN
#include <time.h>
#include <math.h>
#include "stream_handler.h"


/*
 * Processes a single sentence from the stream and choses what to do.
 * If a GSV or an RMC reading is acquired, it is processed.
 */
int process_sentence(str_h * stream, FILE * stream_file) {
    char line[SENTENCE_LIMIT];

    char * status = fgets(line, SENTENCE_LIMIT, stream_file);
    char * line_cpy = strdup(line);

    if (status == NULL) {
        return -1;
    }

    char * temp_line = strsep(&line_cpy, ",");
    
    if (strstr(temp_line, "GSV")) {
        char * gsv_sentence = strsep(&line_cpy, ",");

        int gsv_count = atoi(gsv_sentence);

        char gsv_sequence[gsv_count][SENTENCE_LIMIT];

        strcpy(gsv_sequence[0], line);

        int i;
        for (i = 1; i < gsv_count; i++) {
            fgets(line, SENTENCE_LIMIT, stream_file);
            strcpy(gsv_sequence[i], line);
        }

        read_gsv_sequence(gsv_sequence, gsv_count, stream);
        return GSV_RECEIVED;
    } else if (strstr(temp_line,"RMC")){
        read_rmc(line, stream);
        return LOCATION_RECEIVED;
    }
    return SUCCESS;
}

/*
 * Reads a full sequence of up to 3 GSV sentences and determines if there is a 
 * good quality GPS fix for the receiver.
 */
void read_gsv_sequence(char gsv_sequence[][SENTENCE_LIMIT], int gsv_count, str_h * stream) {
    int satelites_ok = 0;
    
    int i,k,m; 
    
    for(i = 0; i < gsv_count; i++){
        
        
        char * current_sentence = strdup(gsv_sequence[i]);
        char * asterix = strsep(&current_sentence,"*");
        char * current_gsv[20];
        for(m = 0; m<20; m++){
            current_gsv[m] = strsep(&asterix,",");
        }
        

	for ( k = 7; k < 20 && k <= 20; k += 4) {
            if (current_gsv[k]!='\0'
		&& atoi(current_gsv[k]) > 30) {
                    satelites_ok++;
            }
	}
        
    }
    if(satelites_ok >= 3){
        stream->satelites_ok = 1;
    }else{
        stream->satelites_ok = 0;
    }

}

/*
 * Reads an RMC sentence and set it as the current location for the receiver.
 */
void read_rmc(char * sentence, str_h * stream){
    char * rmc_data[10];
    
    char * temp_rmc = strdup(sentence);
    
    int i;
    
    for(i = 0; i<10; i++){
        rmc_data[i] = strsep(&temp_rmc, ",");
    }
    
    char * temp_time = strdup(rmc_data[1]);
    char * time = strsep(&temp_time,".");
    
    strcat(time, rmc_data[9]);
    
    strptime(time, "%H%M%S%d%m%y", &(stream->curr_loc.date));
    
    stream->curr_loc.date.tm_isdst = 0;
    
    RMC_to_decimal(&stream->curr_loc, rmc_data[3],rmc_data[5]);
    
    if(*rmc_data[4]=='S'){
        stream->curr_loc.latitude *= -1;
    }
    if(*rmc_data[6] == 'W'){
        stream->curr_loc.longitude *= -1;
    }
    
}

/*
 * Converts the format of the coordinates from the RMC message from
 * NMEA standard to decimal lat/lng coordinates.
 */
void RMC_to_decimal(location * loc,char * lat, char * lng ){
    double loc_lat = atof(lat);
    double loc_lng = atof(lng);
    
    int lat_degree = (int)loc_lat/100;
    double lat_mins = (loc_lat - (lat_degree*100))/60;
    double latitude = lat_degree + lat_mins;
    
    loc->latitude = (double)(round(latitude*1000000))/1000000.0;
    
    int lng_degree = (int)loc_lng/100;
    double lng_mins = (loc_lng - (lng_degree*100))/60;
    double longitude = lng_degree + lng_mins;
    
    loc->longitude = (double)(round(longitude*1000000))/1000000.0;
    
}


