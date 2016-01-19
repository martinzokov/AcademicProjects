package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import android.util.Base64;

/**
 * This class stores information about a single image
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)ImageInformation.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class ImageInformation {

   /**
    * The name of the actual file, is has to be unique to all other photos
    * stored in the phones gallery.
    */
   private String fileName;

   /**
    * Creates the ImageInformation and set name
    * 
    * @param fileName the name of the new photo
    */
   public ImageInformation(String filename) {
      fileName = filename;
   }

   /**
    * Open file and returns it as Base64 string.
    * 
    * @return file contents as Base64
    */
   public String getImageAsString() {
      byte[] fileData = null;
      String retval = null;
      try {
         File f = new File(fileName);

         fileData = new byte[(int) f.length()];
         
         //reads in file
         BufferedInputStream buf = new BufferedInputStream(new FileInputStream(
               f));
         buf.read(fileData, 0, fileData.length);
         buf.close();
         //converts to base64
         retval = Base64.encodeToString(fileData, Base64.NO_WRAP);
      } catch (Exception exc) {
      }

      return retval;
   }
}
