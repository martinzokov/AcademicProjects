package cs225.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Reader. Used to read the two streams of GPS data.
 */
public class Reader {

	/** The stream two stream objects. */
	private StreamHandler streamOne, streamTwo;

	/** The route. */
	private List<Location> route;

	/** The latitude offset. */
	private long latOffset;

	/** The longitude offset. */
	private long lngOffset;

	/**
	 * Instantiates a new reader and starts listening to the data streams. When
	 * it stops listening, the route data is saved in GPX file.
	 */
	public Reader() {

		route = new ArrayList<Location>();
		streamOne = new StreamHandler("gps_1_dos.dat");
		streamTwo = new StreamHandler("gps_2_dos.dat");
		this.processStreams();

		try {
			saveToGPX();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Checks if a location signal is good.
	 * 
	 * @param stream
	 *            the stream
	 * @return true, if is location signal good
	 */
	public boolean isLocationSignalGood(StreamHandler stream) {
		return stream.isSignalGood();
	}

	/**
	 * Calculate the offset between two locations.
	 * 
	 * @param locOne
	 *            the first location
	 * @param locTwo
	 *            the second location
	 */
	public void calcOffset(Location locOne, Location locTwo) {
		latOffset = (long) (locOne.getLatitude() * 1000000)
				- (long) (locTwo.getLatitude() * 1000000);
		lngOffset = (long) (locOne.getLongitude() * 1000000)
				- (long) (locTwo.getLongitude() * 1000000);
	}

	/**
	 * Gets a more accurate location, based on the offset.
	 * 
	 * @param loc
	 *            the location without offset
	 * @return the location plus the offset
	 */
	public Location getLocationFromOffset(Location loc) {
		loc.setLatitude(loc.getLatitude() + ((double) latOffset) / 1000000);
		loc.setLongitude(loc.getLongitude() + ((double) lngOffset) / 1000000);
		return loc;
	}

	/**
	 * Synchronises the two streams to avoid loss of data.
	 * 
	 * @param one
	 *            the first stream
	 * @param two
	 *            the second stream
	 * @return true, if successful
	 */
	public boolean streamSync(StreamHandler one, StreamHandler two) {

		while (one.processSentance() != one.LOCATION_RECEIVED) {
			one.processSentance();
		}
		while (two.processSentance() != two.LOCATION_RECEIVED) {
			two.processSentance();
		}

		if (getLocationTime(one) < getLocationTime(two)) {
			while (getLocationTime(one) != getLocationTime(two)) {
				one.processSentance();
			}
		} else {
			while (getLocationTime(one) != getLocationTime(two)) {
				two.processSentance();
			}
		}

		return true;
	}

	/**
	 * Gets a location's time in milliseconds
	 * 
	 * @param stream
	 *            the stream
	 * @return the location time
	 */
	public long getLocationTime(StreamHandler stream) {
		return stream.getCurrentLocation().getDateObject().getTime();
	}

	/**
	 * Processes the streams. First synchronises the two GPS signals and then
	 * proceeds with an infinite loop until one of the streams stops.
	 */
	public void processStreams() {
		int streamOneStatus = 0, streamTwoStatus = 0;
		streamSync(streamOne, streamTwo);
		while (true) {

			if (isLocationSignalGood(streamOne)) {

				this.addLocation(streamOne.getCurrentLocation());
				calcOffset(streamOne.getCurrentLocation(),
						streamTwo.getCurrentLocation());
			} else if (isLocationSignalGood(streamTwo)) {
				this.addLocation(getLocationFromOffset(streamTwo
						.getCurrentLocation()));
			}

			do {
				streamOneStatus = streamOne.processSentance();
				if (streamOneStatus == -1) {
					return;
				}
			} while (streamOneStatus != 2);

			if (streamOneStatus == 0) {
				return;
			}
			do {
				streamTwoStatus = streamTwo.processSentance();
				if (streamTwoStatus == -1) {
					return;
				}
			} while (streamTwoStatus != 2);

			if (streamTwoStatus == 0) {
				return;
			}
		}
	}

	/**
	 * Adds a location to the ArrayList of locations
	 * 
	 * @param loc
	 *            the location
	 */
	public void addLocation(Location loc) {
		route.add(loc);
	}

	/**
	 * Saves the route data to a GPX file.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveToGPX() throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"resultJava.gpx")));

		writer.write("<?xml version=\"1.0\"?>\n"
				+ "<gpx\n"
				+ "version=\"1.0\"\n"
				+ "creator = \"Martin Zokov\"\n "
				+ "xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\">\n");

		for (Location l : route) {
			writer.write("<wpt lat=\"" + l.getLatitude() + "\" lon=\""
					+ l.getLongitude() + "\">\n " + "<time>" + l.getDate()
					+ "</time>" + "\n</wpt>\n");
		}
		writer.write("</gpx>");

		writer.close();
	}

}
