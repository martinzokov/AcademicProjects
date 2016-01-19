package uk.ac.aber.cs221.group07.walktourcreator.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.cs221.group07.walktourcreator.model.LocationPoint;
import uk.ac.aber.cs221.group07.walktourcreator.model.PointOfInterest;
import uk.ac.aber.cs221.group07.walktourcreator.model.WalkModel;

/**
 * This class is responsible for conducting JUnit tests, mostly for the methods
 * from WalkModel class, but there is also a test for one method from
 * LocationPoint class.
 * 
 * @author Maciek Dobrzanski
 */
public class AllTests {

   WalkModel wm;

   @Before
   public void init() {
      wm = new WalkModel();
   }


   /**
    * Checks if the vector returned by the getRoutePath method is the same as
    * the test vector.
    */
   @Test
   public void testGetRoutePath() {
      Vector<LocationPoint> testPath = new Vector<LocationPoint>();
      assertEquals(testPath, wm.getRoutePath());
   }

   /**
    * Checks if the total distance of the walk is returned properly.
    */
   @Test
   public void testGetDistance() {
      LocationPoint lp = new LocationPoint(52.413, -4.081); // Aberystwyth
                                                            // coordinates
                                                            // according to
                                                            // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=UKWACARD
      LocationPoint lp2 = new LocationPoint(51.481, -3.174); // Cardiff
                                                             // coordinates
                                                             // according to
                                                             // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=UKWACARD
      LocationPoint lp3 = new LocationPoint(-12.05, -77.05); // Lima (Peru)
                                                             // coordinates
                                                             // according to
                                                             // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=PELILIMA
      double testDistance = LocationPoint.distBetween(lp, lp2)
            + LocationPoint.distBetween(lp2, lp3);
      wm.addLocation(lp);
      wm.addLocation(lp2);
      wm.addLocation(lp3);

      assertEquals(testDistance, wm.getDistance(), 0);
   }

   /**
    * Checks if it is possible to set certain strings as a title and if the
    * title that was set is returned properly by getTitle method
    */
   @Test
   public void testTitle() {
      String title2 = "title";
      wm.setTitle(title2);
      assertEquals(title2, wm.getTitle());

   }

   /**
    * Checks if it is possible to set certain strings as a short description and
    * if the short description that was set is returned properly by
    * getShortDescription method
    */
   @Test
   public void testShortDescription() {
      String testString2 = "test string";
      wm.setShortDescription(testString2);
      assertEquals(testString2, wm.getShortDescription());
   }

   /**
    * Checks if it is possible to set certain strings as a long description and
    * if the long description that was set is returned properly by
    * getLongDescription method
    */
   @Test
   public void testlongDescription() {
	  String testString2 = "Hello";
      wm.setLongDescription(testString2);
      assertEquals(testString2, wm.getLongDescription());
   }

   /**
    * Checks if the location added to the vector is saved there properly.
    */
   @Test
   public void testAddLocation() {
      LocationPoint lp = new LocationPoint(100, 100);
      Vector<LocationPoint> testPath = new Vector<LocationPoint>();
      testPath.add(lp);
      assertEquals(1, testPath.size());
   }


   /**
    * Checks if the distance between two points on the map is calculated
    * properly.
    */
   @Test
   public void testDistBetween() {
      LocationPoint lp = new LocationPoint(52.413, -4.081); // Aberystwyth
                                                            // coordinates
                                                            // according to
                                                            // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=UKWACARD
      LocationPoint lp2 = new LocationPoint(51.481, -3.174); // Cardiff
                                                             // coordinates
                                                             // according to
                                                             // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=UKWACARD
      LocationPoint lp3 = new LocationPoint(-12.05, -77.05); // Lima (Peru)
                                                             // coordinates
                                                             // according to
                                                             // http://www.geobytes.com/CityDistance.htm?d&pt_1=UKWAABER&pt_2=PELILIMA

      // the tolerance used for the distances is 1% of the expected distance
      // between the cities
      assertEquals(120, LocationPoint.distBetween(lp, lp2), 1.2);
      assertEquals(9957, LocationPoint.distBetween(lp, lp3), 99.57);
   }

}
