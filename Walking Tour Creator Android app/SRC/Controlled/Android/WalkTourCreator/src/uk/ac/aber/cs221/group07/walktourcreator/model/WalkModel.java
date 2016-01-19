package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * stores all information about a single walk
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)WalkModel.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class WalkModel implements Serializable {

   /** holds the name of the walk, is used as an identifier */
   private String title;

   /** store a long description of the walk, it is limited to 1000 characters */
   private String longDesc;

   /** store a short description of the walk, it is limited to 100 characters */
   private String shortDesc;

   /** stores the vector of LocationPoints that make up the walk */
   private Vector<LocationPoint> path;

   /**
    * Creates a new walking model with the title that is passed
    */
   public WalkModel() {
      path = new Vector<LocationPoint>();
   }

   /**
    *  @return the name of the walk.
    */
   public String getTitle() {
      return title;
   }

   /**
    * Sets the name of the walk
    * 
    * @param the new name of the walk.
    * */
   public void setTitle(String newTitle) {
      title = newTitle;
   }

   /**
    *  @return a short description of the walk 
    */
   public String getShortDescription() {
      return shortDesc;
   }

   /**
    * @param newShortDesc,set the short description of the walk. 
    */
   public void setShortDescription(String newShortDesc) {
      shortDesc = newShortDesc;
   }

   /** 
    * @return a long description of the walk.
    */
   public String getLongDescription() {
      return longDesc;
   }

   /**
    *  @param newShortDesc,set the long description for the walk.
    */
   public void setLongDescription(String newLongDesc) {
      longDesc = newLongDesc;
   }

   /**
    * @return a vector of all the LocationPoint in the walk. Including
    * PointsOfInterests
    */
   public Vector<LocationPoint> getRoutePath() {
      return path;
   }

   /** 
    * adds a LocationPoint to the walk. 
    * 
    * @param point, the location you want to add
    */
   public void addLocation(LocationPoint point) {
      path.add(point);
   }


   /**
    * works out the total distance traveled along the walk.
    * 
    * @return the running total of km traveled. 
    */
   public double getDistance() {
      double total = 0;
      for (int i = 0; i < path.size() - 1; i++) {
         total += LocationPoint.distBetween(path.get(i), path.get(i + 1));
      }
      return total;
   }

   /** 
    * works out the total time taken.
    * 
    * @return the elapsed time since the walk was started
    */
   public double getTimeTaken() {
      if (path.size() == 0) {
         return 0;
      } else {
         return path.get(path.size() - 1).getTime() - path.get(0).getTime();
      }
   }
}
