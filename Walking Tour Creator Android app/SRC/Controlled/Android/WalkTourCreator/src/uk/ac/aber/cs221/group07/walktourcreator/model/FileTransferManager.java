package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;

/**
 * Handles the encoding and uploading of walk data.
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)FileTransferManager.java 0.1 2014-01-31
 * 
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class FileTransferManager {

   /** Value for success of an upload */
   private final static int UPLOAD_SUCCESS = 200;
  
   /** The URL of the server to which data is sent */
   private final static String dataServer = "http://users.aber.ac.uk/mda/csgp07/file_saver.php";
   
   /**
    * makes a connection to data server and uploads all files belonging to the
    * given file, the return values will be zero if the method succeeded without
    * problems.
    * 
    * @param screen, the walk screen of the current walk.
    * @param w, the current walk's object
    */
   public FileTransferManager(WalkScreen screen, WalkModel w) {
      //request system to run garbage collector,
      //as the upload can be very big in memory
      System.gc();
      new Uploader(screen, w).start();
   }

   /**
    * Packages all the WalkModel as a JSON object
    * 
    * @param data, the walk that is to be uploaded.
    * @return the walk encoded into a json object.
    */
   private static JSONObject packageData(WalkModel data) {
      JSONObject walkData = new JSONObject();
      try {
         // add walk data
         walkData.put("title", data.getTitle());
         walkData.put("short_desc", data.getShortDescription());
         walkData.put("long_desc", data.getLongDescription());
         walkData.put("hours", data.getTimeTaken());
         walkData.put("distance", data.getDistance());
         // add all location points
         walkData.put("route", packagepathData(data.getRoutePath()));

      } catch (JSONException e) {
      }
      return walkData;
   }

   /**
    * Packages the whole route as a JSON object
    * 
    * @param route that is being recorded
    * @return the route as a JSONArray
    * @throws JSONException
    */
   private static JSONArray packagepathData(Vector<LocationPoint> route)
         throws JSONException {
      JSONArray routeData = new JSONArray();
      for (LocationPoint point : route) {

         JSONObject location = new JSONObject();
         location.put("longitude", point.getLongitude());
         location.put("latitude", point.getLatitude());
         location.put("time", point.getTime());
         if (point instanceof PointOfInterest) {
            location.put("description",
                  ((PointOfInterest) point).getDescription());
            location.put("title", ((PointOfInterest) point).getTitle());
            JSONArray images = new JSONArray();
            for (ImageInformation image : ((PointOfInterest) point).getImages()) {
               JSONObject jsonImage = new JSONObject();
               jsonImage.put("file_data", image.getImageAsString());
               images.put(jsonImage);
            }
            location.put("images", images);
         }
         routeData.put(location);
      }
      return routeData;
   }

   /**
    * Sends the data as a POST message to the server
    * 
    * @param pakagedData the data to be sent.
    * @param walk, is the actual walk that will be posted/uploaded
    * @return a boolean status, true if upload succeeded.
    */
   private static boolean post(JSONObject pakagedData, WalkScreen walk) {
      boolean status = false;
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost post = new HttpPost(dataServer);
      try {
         // add walk data to message
         List<NameValuePair> dataPairs = new Vector<NameValuePair>();
         dataPairs.add(new BasicNameValuePair("JSON", pakagedData.toString()));

         post.setEntity(new UrlEncodedFormEntity(dataPairs));
         // send message
         HttpResponse response = httpclient.execute(post);
         
         status = response.getStatusLine().getStatusCode() == UPLOAD_SUCCESS;

      } catch (IOException e) {
      }
      
      return status;
   }


   /**
    * An inner class that keeps the upload working in a separate thread.
    */
   private class Uploader extends Thread {
      /** Holds the walk to be encoded, then uploaded */
      private WalkModel data;
      /** Holds the walk screen to be able to stop the recording after upload */
      private WalkScreen walk;

      /** set the walk data */
      public Uploader(WalkScreen screen, WalkModel walk) {
         this.data = walk;
         this.walk = screen;
      }

      /** start process of upload and quit to main menu */
      @Override
      public void run() {
         boolean statusCode = post(packageData(data), walk);
         walk.returnToStart(statusCode);

      }
   }
}
