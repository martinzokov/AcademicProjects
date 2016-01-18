package cs225.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Location Class. Represents a single Location object which is stored.
 */
public class Location {

	/** The latitude. */
	private double latitude;

	/** The longitude. */
	private double longitude;

	/** The date. */
	private Date date;

	/**
	 * Instantiates a new location object.
	 * 
	 * @param lat
	 *            the latitude
	 * @param lng
	 *            the longitude
	 * @param dt
	 *            the date
	 */
	public Location(double lat, double lng, Date dt) {
		latitude = lat;
		longitude = lng;
		date = dt;
	}

	/**
	 * Gets the date object.
	 * 
	 * @return the date object
	 */
	public Date getDateObject() {
		return date;
	}

	/**
	 * Gets the latitude.
	 * 
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 * 
	 * @param latitude
	 *            the new latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 * 
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 * 
	 * @param longitude
	 *            the new longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the date as a string.
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date.toString();
	}

}
