package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.io.File;
import java.io.IOException;

import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * Handles interaction with the system camera and photo gallery access, and
 * saves the resulting image
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)ImageHandler.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class ImageHandler {

   /** Result code for getting a photo from the camera */
   public static int CAMERA_ACTIVITY_RESULT_CODE = 1984;

   /** Result code for getting a phoyo from the gallery */
   public static int GALLERY_ACTIVITY_RESULT_CODE = 1993;

   /** the activity that initialized the camera/gallery access */
   private WalkScreen owner;

   /**
    * Creates a new ImageHandler object
    * 
    * @param owner, the activity that initialized the object
    */
   public ImageHandler(WalkScreen owner) {
      this.owner = owner;
   }

   /**
    * Starts the system photo gallery browser, where the user can select an
    * image.then will return to the 'owner'
    */
   public void getPhotoFromLibrary() {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      owner.startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            GALLERY_ACTIVITY_RESULT_CODE);
   }

   /**
    * Starts a take-picture-intent that saves the taken picture and passes the
    * filename to 'owner'
    */
   public void getPhotoFromCamera() {
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      File temp = createFile();
      WalkScreen.temp = temp.getAbsolutePath();
      takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(temp));
      owner.startActivityForResult(takePictureIntent,
            CAMERA_ACTIVITY_RESULT_CODE);
   }

   /**
    * Creates the file that the camera will save the taken picture to.
    * The filename is just the current system time in milliseconds.
    * 
    * @return a newly created file used to save the new photo
    */
   private File createFile() {

      File file = new File(Environment.getExternalStorageDirectory(),
            Long.toString(System.currentTimeMillis()) + ".jpeg");
      try {
         file.createNewFile();

      } catch (IOException e) {
      }
      return file;
   }

}
