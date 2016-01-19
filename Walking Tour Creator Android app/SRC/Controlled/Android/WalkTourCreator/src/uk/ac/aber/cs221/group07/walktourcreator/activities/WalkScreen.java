package uk.ac.aber.cs221.group07.walktourcreator.activities;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import uk.ac.aber.cs221.group07.walktourcreator.model.FileTransferManager;
import uk.ac.aber.cs221.group07.walktourcreator.model.ImageHandler;
import uk.ac.aber.cs221.group07.walktourcreator.model.ImageInformation;
import uk.ac.aber.cs221.group07.walktourcreator.model.LocationPoint;
import uk.ac.aber.cs221.group07.walktourcreator.model.PointOfInterest;
import uk.ac.aber.cs221.group07.walktourcreator.model.WalkModel;
import uk.ac.aber.cs221.group07.walktourcreator.services.RouteRecorder;
import uk.ac.aber.cs221.group07.walktourcreator.views.CancelWalkView;
import uk.ac.aber.cs221.group07.walktourcreator.views.EditWalkView;
import uk.ac.aber.cs221.group07.walktourcreator.views.GpsCheckDialog;
import uk.ac.aber.cs221.group07.walktourcreator.views.PoiDialogView;
import uk.ac.aber.cs221.group07.walktourcreator.views.WalkFinishedView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

/**
 * This class is responsible for displaying the walk screen, and reacting to button presses.
 * @author Harry Buckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)WalkScreen.java 0.1 2014-01-31
 * 
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class WalkScreen extends Activity {


    /**The walk that is being */
    private static WalkModel walk;

    /**SHOULD BE IMPROVED JUST USED NOW TO MAKE IT WORK*/
    public PointOfInterest nextPoi;

    /**SHOULD BE IMPROVED JUST USED NOW TO MAKE IT WORK*/
    public static String temp;

    /** holds the object that is responsible for tracking the path of the walk*/
    public RouteRecorder recorder;
    
    /** a dialog for the POI view*/
    private PoiDialogView poiDialog;

    /**
     * This method is called automatically when the activity is created, all it
     *  does is starts sets the layout as specified in res/layout/activity_map_screen.xml
     * 
     * @param savedInstanceState is not used in this case
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        setupIfNeeded();
    }
    
    /**
     * * 
     * method, called onCreate, if certain values are null then they will be initialized
     * else they'll be remain unchanged.
     */
    public void setupIfNeeded(){
        if(walk==null)
            walk = (WalkModel) getIntent().getSerializableExtra("walk");
        if(recorder==null){
            //location manager to get location data
            LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ))
                new GpsCheckDialog(this);
            recorder = new RouteRecorder(this,walk,manager);
        }
    }

    /**
    * creates and displays a AddPoiView.
    * @param v , is the object that called the method.
    * @param v, is the object that called the method.
    */
    public void addPOI(View v){
        if(v==null){
            poiDialog.show();
            return;
        }
        //used to test without gps location.
        LocationPoint point =  recorder.getLastKnownPosition();

        if(point==null){
            Toast.makeText(this,"Waiting for GPS\n",Toast.LENGTH_LONG).show();
            return;
        }
        if(poiDialog==null||nextPoi==null){
            poiDialog = new PoiDialogView(this,R.layout.activity_add_poi_dialog,point);
        }
        poiDialog.show();
    }


    /**
    * creates and displays a WalkFinishedView.
    * @param v, is the object that called the method.
    */
    public void finishWalk(View v){
        //walkFinishedDialog.show();
        new WalkFinishedView(this,R.layout.walk_finished_dialog, walk,this);
    }


    /**
    * creates and displays a EditWalkView.
    * @param v, is the object that called the method.
    */
    public void editWalkDialog(View v){
        new EditWalkView(this,R.layout.edit_walk_dialog,walk).show();
    }

    /**
    * creates and displays a CancelWalkView.
    * @param v, is the object that called the method.
    */
    public void cancelWalk(View v){
        new CancelWalkView(this, R.layout.cancel_walk_dialog).show();
    }

    /** Opens the gallery to add a picture to the current walk
     * @param v the object that called the method*/
    public void getFromGallery(View v){
        ImageHandler image = new ImageHandler(this);
        image.getPhotoFromLibrary();
        poiDialog.dismiss();

    }

    /** Opens the camera app to take a picture that will be added to the walk.
     * @param v the object that called the method*/
    public void getFromCamera(View v){
        ImageHandler image = new ImageHandler(this);
        image.getPhotoFromCamera();
        poiDialog.dismiss();
    }

    /** Adds a new point of interest*/
    public void addPoi(){
        if(nextPoi!=null){
            walk.addLocation(nextPoi);
            Toast.makeText(this,"Place of Interest "+nextPoi.getTitle()+"\n has been added",Toast.LENGTH_LONG).show();
        }
        nextPoi=null;
    }

    /** Starts the upload of a walk to the server.*/
    public void uploadWalk(){
        recorder.finishWalk();
        Toast.makeText(this,"Upload has Started, it may take a while",Toast.LENGTH_LONG).show();
        new FileTransferManager(this,walk);
    }

       /**
        * returns the user to the start screen, is called either after the upload has
        * finished or when the user cancels the walk.
        * @param status, 
        * */
    public void returnToStart(boolean status){
        if(recorder != null){
            recorder.finishWalk();
        }
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    /**
     * called automatically when the current activity is returned to after requesting a result.
     * here it is used to add the a picture (from the camera or gallery) to the walk.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(nextPoi==null)
                return;
            else if(requestCode == ImageHandler.CAMERA_ACTIVITY_RESULT_CODE)
                nextPoi.addImage(new ImageInformation(temp));
            else if(requestCode == ImageHandler.GALLERY_ACTIVITY_RESULT_CODE)
                nextPoi.addImage(new ImageInformation(getRealPathFromURI(data.getData())));
  
            Toast.makeText(this, "Image added to Location\n", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Image not added\n", Toast.LENGTH_LONG).show();
        }
        poiDialog.show();
    }
    
    /**
     * overides the default behavior of the back/up button and maps it to 
     * open a cancel walk dialog instead.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            cancelWalk(null);
            return true;
        }
        return false;
    }


    /*
     * FROM http://stackoverflow.com/questions/2789276/android-get-real-path-by-uri-getpath 
     */
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else { 
            cursor.moveToFirst(); 
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
            return cursor.getString(idx); 
        }
    }
    

}