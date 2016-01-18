
// Maximum amount of GSV sentences in one sequence
#define MAX_GSV_SENTENCES 3

//Buffer limit for the characters read from a line from a stream
#define SENTENCE_LIMIT 150

//Filename of the first and second stream. Also, filename of the output file.
#define STREAM_ONE "gps_1.dat"
#define STREAM_TWO "gps_2.dat"
#define OUTPUT_GPX "resultC.gpx"

//Return codes for the stream reading function
#define STREAM_END  0
#define SUCCESS  1
#define LOCATION_RECEIVED  2
#define GSV_RECEIVED 3

//A structure to hold the data for a single location that is received from a GPS
typedef struct loc{
    double latitude;
    double longitude;
    struct tm date;
} location;

//Stream Handler structure to keep checking if the locations received are good enough
typedef struct str_handler{
    _Bool satelites_ok;
    location curr_loc;
}str_h;

//A node structure for the linked list
typedef struct node_str {
 location loc;
 struct node_str * next;
}node;


//Processes a single line of the stream
int process_sentence(str_h * stream, FILE * stream_file);
//Processes a GSV sequence of sentences up to a maximum of 3 sentences
void read_gsv_sequence(char gsv_sequence[][SENTENCE_LIMIT], int gsv_count, str_h * stream);
//Reads the data from an RMC sentence
void read_rmc(char * sentence, str_h * stream);
//Converts coordinates from NMEA format to decimal N/S and E/W
void RMC_to_decimal(location * loc,char * lat, char * lng );
//Waits for a pair of RMC messages that have location data
int synchronise_streams(str_h * stream_one,FILE * str_one_file, str_h * stream_two,FILE * str_two_file);
//Reads data from the two streams and processes it accordingly
void process_streams(str_h * stream_one, str_h * stream_two,node * head);
//Calculates the offset between two position
void calc_offset(long * lat_offset, long * lng_offset, str_h * stream_one, str_h * stream_two);
//Adjusts the received location based on the offset.
void get_location_with_offset(str_h * stream, long lat_offset, long lng_offset);
//Saves all the route data to a GPX file
void save_to_gpx(node * head);
//Used to add data to the linked list with locations
void add_to_list(node ** list_head, location loc);


