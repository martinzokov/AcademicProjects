package cs225.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class StreamHandler.
 */
public class StreamHandler {

	/** The reader for the stream. */
	private BufferedReader readStream = null;

	/** The Constant SPLITTER used to split the CSV messages. */
	private static final String SPLITTER = ",";

	/** The Constant MAX_GSV_SENTANCES. */
	private static final int MAX_GSV_SENTANCES = 3;

	/** Keeps track of the quality of the GPS fix. */
	private boolean isSignalGood;

	/** The gsv sentence counter. */
	private int gsvCounter = 0;

	/** The expected number of GSV messages. */
	private int gsvExpected = 0;

	/** The GSV sequence. */
	private String GSVSequence[] = new String[MAX_GSV_SENTANCES];

	/** Return code to check result of sentence reading. */
	public static final int STREAM_END = 0;

	/** Return code to check result of sentence reading. */
	public static final int EXIT_SUCCESS = 1;

	/** Return code to check result of sentence reading. */
	public static final int LOCATION_RECEIVED = 2;

	/** Return code to check result of sentence reading. */
	public static final int GSV_RECEIVED = 3;

	/** The current location. */
	private Location currentLocation;

	/**
	 * Instantiates a new stream handler.
	 * 
	 * @param filename
	 *            the filename
	 */
	public StreamHandler(String filename) {
		try {

			readStream = new BufferedReader(new FileReader(filename));
			currentLocation = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if is signal good.
	 * 
	 * @return true, if signal is good
	 */
	public boolean isSignalGood() {
		return isSignalGood;
	}

	/**
	 * Process a single sentance.
	 * 
	 * @return the int with the return code of the method.
	 */
	public int processSentance() {
		String line;
		String tempGSV[], tempRMC[];
		try {
			line = readStream.readLine();
			if (line == null) {
				return -1;
			}
			if (!line.isEmpty()) {
				if (line.contains("GSV")) {

					tempGSV = new String[line.split(SPLITTER).length];
					tempGSV = line.split(SPLITTER);
					gsvExpected = Integer.parseInt(tempGSV[1]);
					if (Integer.parseInt(tempGSV[2]) <= gsvExpected) {
						GSVSequence[gsvCounter++] = line;
						if (Integer.parseInt(tempGSV[2]) == gsvExpected) {
							gsvCounter = 0;
							readGSVSequence();
							return GSV_RECEIVED;
						}
					}
				} else if (line.contains("RMC")) {
					tempRMC = line.split(SPLITTER);
					readRMC(tempRMC);
					return LOCATION_RECEIVED;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EXIT_SUCCESS;
	}

	/**
	 * Read a whole GSV sequence to determine GPS receiver quality.
	 */
	public void readGSVSequence() {
		int satelitesOK = 0;
		int numOfSatelites = 0;

		for (int i = 0; i < gsvExpected; i++) {
			int checksumIndex = GSVSequence[i].indexOf("*");
			String[] singleSentance = GSVSequence[i]
					.substring(0, checksumIndex).split(SPLITTER);
			numOfSatelites = Integer.parseInt(singleSentance[3]);

			for (int k = 7; k < singleSentance.length && k <= 19; k += 4) {
				if (!singleSentance[k].equals("")
						&& Integer.parseInt(singleSentance[k]) > 30) {
					satelitesOK++;
				}
			}
		}
		if (satelitesOK >= 3) {
			isSignalGood = true;
		} else {
			isSignalGood = false;
		}

	}

	/**
	 * Reads an RMC message and sets the receiver's current location to that
	 * position.
	 * 
	 * @param RMCSentance
	 *            the RMC sentance
	 */
	public void readRMC(String RMCSentance[]) {
		String time = RMCSentance[1].substring(0, 6);
		String date = RMCSentance[9];

		Date locationDate = null;
		try {
			locationDate = new SimpleDateFormat("HHmmssddMMyy").parse(time
					+ date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Location newLoc = new Location(Double.parseDouble(RMCSentance[3]),
				Double.parseDouble(RMCSentance[5]), locationDate);
		currentLocation = RMCToDecimal(newLoc);

		if (RMCSentance[4].equals("S")) {
			currentLocation.setLatitude(currentLocation.getLatitude() * -1);
		}
		if (RMCSentance[6].equals("W")) {
			currentLocation.setLongitude(currentLocation.getLongitude() * -1);
		}
	}

	/**
	 * Converts NMEA coordinates format to decimal.
	 * 
	 * @param loc
	 *            the location
	 * @return the location with converted coordinates
	 */
	public Location RMCToDecimal(Location loc) {
		int latDegree = (int) loc.getLatitude() / 100;
		double latMin = (loc.getLatitude() - (latDegree * 100)) / 60.0;
		double lat = (latDegree + latMin);
		loc.setLatitude((double) Math.round(lat * 1000000) / 1000000.0);

		int lngDegree = (int) loc.getLongitude() / 100;
		double lngMin = (loc.getLongitude() - (lngDegree * 100)) / 60.0;
		double lng = (lngDegree + lngMin);
		loc.setLongitude((double) Math.round(lng * 1000000) / 1000000.0);
		return loc;
	}

	/**
	 * Gets the current location.
	 * 
	 * @return the current location
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}

}
