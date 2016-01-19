package uk.ac.aber.cs221.group07.walktourcreator.services;

import uk.ac.aber.cs221.group07.walktourcreator.model.LocationPoint;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * A listener class which reacts to gathered location data.
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)PositionListener.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class PositionListener implements LocationListener {

   /**
    * A reference to the recorder in order to add the location information to a walk
    */
   private RouteRecorder recorder;

   /**
    * creates a positionListener that will be attached to a locationManager,
    * 
    * @param RouteRecorder the recorder for the current walk.
    */
   public PositionListener(RouteRecorder rec) {
      recorder = rec;
   }

   /**
    * Method that is called automatically each time a location is received
    * 
    * @param Location the location that needs to be added
    */
   @Override
   public void onLocationChanged(Location loc) {
      // Create a LocationPoint and set lat and lng to the recieved location
      LocationPoint pos = new LocationPoint(loc.getLongitude(),
            loc.getLatitude());

      // send it to the recorder which saves it in the WalkModel
      recorder.newLocation(pos);
   }

   /** needed is class implements LocationListener but not used */
   @Override
   public void onProviderDisabled(String arg0) {
   }

   /** needed is class implements LocationListener but not used */
   @Override
   public void onProviderEnabled(String arg0) {
   }

   /** needed is class implements LocationListener but not used */
   @Override
   public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
   }
}
