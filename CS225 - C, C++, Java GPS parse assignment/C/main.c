/* 
 * File:   main.c
 * Author: martin
 *
 * Created on 27 March 2014, 22:13
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "stream_handler.h"

/*
 * Main function which executes the stream reader.
 */
int main(int argc, char** argv) {
    
    str_h stream_one;
    str_h stream_two;
    node head;
    
    process_streams(&stream_one, &stream_two, &head);
    
    save_to_gpx(&head);

    return (EXIT_SUCCESS);
}


/*
 * Starts reading from the streams, does an initial synchronisation between
 * the two receivers and starts an infinite loop which is borken when the stream
 * ends. While iterating, new locations are added to the linked list,
 * based on the quality of the GPS fix.
 */
void process_streams(str_h * stream_one, str_h * stream_two, node * head){
    int str_one_status = 0, str_two_status = 0;
    FILE * file_stream_one = fopen(STREAM_ONE,"r");
    FILE * file_stream_two = fopen(STREAM_TWO,"r");
    
    stream_one->satelites_ok = 0;
    stream_two->satelites_ok = 0;
    
    long lat_offset = 0;
    long lng_offset = 0;
    
    synchronise_streams(stream_one,file_stream_one,stream_two,file_stream_two);
    
    while(1){
        if( stream_one->satelites_ok){
            add_to_list(&head,stream_one->curr_loc);
            calc_offset(&lat_offset, &lng_offset,stream_one,stream_two);
        }else if (stream_two->satelites_ok){
            get_location_with_offset(stream_two,lat_offset,lng_offset);
            add_to_list(&head,stream_two->curr_loc);
        }
        
        
        do{
            str_one_status = process_sentence(stream_one, file_stream_one);
            if(str_one_status == -1){
                return;
            }
        }while(str_one_status != 2);
        
        if(str_one_status == 0){
            return;
        }
        
        do{
            str_two_status = process_sentence(stream_two, file_stream_two);
            if(str_two_status == -1){
                return;
            }
        }while(str_two_status != 2);
        
        if(str_two_status == 0){
            return;
        }
    
    }
    fclose(file_stream_one);
    fclose(file_stream_two);
}

/*
 * Synchronises the two streams. Waits for RMC sentences with the same time, which
 * would mean that the two streams are synchronised.
 */
int synchronise_streams(str_h * stream_one,FILE * str_one_file, str_h * stream_two,FILE * str_two_file){
    int stream_one_status = 0,stream_two_status=0;
    while(stream_one_status != LOCATION_RECEIVED){
       stream_one_status = process_sentence(stream_one,str_one_file);
   } 
   while(stream_two_status != LOCATION_RECEIVED){
       stream_two_status = process_sentence(stream_two,str_two_file);
   }
   
   time_t time_one = mktime(&stream_one->curr_loc.date);
   time_t time_two = mktime(&stream_two->curr_loc.date);
   
   double time_difference = difftime(time_one,time_two);
   
   if(time_difference < 0){
       while(time_difference != 0){
           process_sentence(stream_one,str_one_file);
           time_t time_one = mktime(&stream_one->curr_loc.date);
           time_t time_two = mktime(&stream_two->curr_loc.date);
           time_difference = difftime(time_one,time_two);
       }
   }else{
       while(time_difference != 0) {
           process_sentence(stream_two,str_two_file);
           time_t time_one = mktime(&stream_one->curr_loc.date);
           time_t time_two = mktime(&stream_two->curr_loc.date);
           time_difference = difftime(time_one,time_two);
       }
   }

   return 1;
   
}

/*
 * Calculates the offset between two locations
 */
void calc_offset(long * lat_offset, long * lng_offset, str_h * stream_one, str_h * stream_two){ 
    *lat_offset = (long)(stream_one->curr_loc.latitude * 1000000) - (long)(stream_two->curr_loc.latitude * 1000000);
    *lng_offset = (long)(stream_one->curr_loc.longitude * 1000000) - (long)(stream_two->curr_loc.longitude * 1000000);
}

/*
 * Aligns a location based on the offset.
 */
void get_location_with_offset(str_h * stream, long lat_offset, long lng_offset){
    stream->curr_loc.latitude +=  ((double)lat_offset)/1000000;
    stream->curr_loc.longitude += ((double)lng_offset)/1000000;
}

/*
 * Saves all the route data from the linked list to a GPX file 
 */
void save_to_gpx(node * head){
    
    FILE * output = fopen(OUTPUT_GPX,"w");
    
    fprintf(output, "%s", "<?xml version=\"1.0\"?>\n"
				"<gpx \n"
				"version=\"1.0\"\n"
				"creator = \"Martin Zokov\"\n "
				"xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\">\n");
    
    node * curr = head;
    
    while(curr != NULL){
        fprintf(output,"<wpt lat=\"%lf\" lon=\"%lf\">"
                "\n<time>%s</time>\n</wpt>\n", curr->loc.latitude,curr->loc.longitude,
                asctime(&curr->loc.date));
        curr = curr->next;
    }
    fprintf(output,"%s","</gpx>");
    fclose(output);
    
}

