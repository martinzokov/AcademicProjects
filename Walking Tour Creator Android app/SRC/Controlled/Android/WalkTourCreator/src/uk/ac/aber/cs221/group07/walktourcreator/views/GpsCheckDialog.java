package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Displays a popup if the GPS is not activated and asks the user to enable it
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)GpsCheckDialog.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class GpsCheckDialog implements DialogInterface.OnClickListener {

   /** Reference to the activity that created the popup */
   private WalkScreen activity;

   /**
    * Constructor for the popup.
    * 
    * @param context the activity that created the popup
    */
   public GpsCheckDialog(WalkScreen context) {
      activity = context;
      new AlertDialog.Builder(context)
            .setTitle("Your GPS appears to be switched off. Switch it on?")
            .setPositiveButton(android.R.string.yes, this)
            .setNegativeButton(android.R.string.no, this).show();
   }

   /**
    * Implementation of the listener's onClick method. If the OK button is
    * pressed, creates a new activity which shows the GPS settings on the device
    * so that the user can swich it on
    * @param dialog the dialog that is pressed
    * @param which the id of the button that is pressed
    */
   @Override
   public void onClick(DialogInterface dialog, int which) {
      if (which == DialogInterface.BUTTON_POSITIVE) {
         activity.startActivity(new Intent(
               android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
      } else if (which == DialogInterface.BUTTON_NEGATIVE) {
         activity.returnToStart(true);
      }
   }

}
