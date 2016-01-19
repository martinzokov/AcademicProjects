package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import uk.ac.aber.cs221.group07.walktourcreator.model.LocationPoint;
import uk.ac.aber.cs221.group07.walktourcreator.model.PointOfInterest;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)PoiDialogView.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */

public class PoiDialogView extends DialogView {

   /**
    * holds the inflator used to create the view from the xml file
    */
   private LayoutInflater inflater;
   /** reference to the view that is being shown*/
   private View view;
   /** reference to the activity that created the dialog*/
   private WalkScreen activity;

   /** Constructor for the popup
    * @param context the activity that created the popup
    * @param viewLayout the id of the layout that should be shown
    * @param point the LocationPoint that will be added
    * */
   public PoiDialogView(WalkScreen context, int viewLayout, LocationPoint point) {
      super(context, viewLayout);
      activity = context;
      this.setInflaterView(inflater, layout);
      if (activity.nextPoi == null) {
         activity.nextPoi = new PointOfInterest(point);
      } else {
         ((EditText) view.findViewById(R.id.poi_title))
               .setText(activity.nextPoi.getTitle());
         ((EditText) view.findViewById(R.id.poi_description))
               .setText(activity.nextPoi.getDescription());
      }
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
         setPointInfo();
      } else {
         activity.nextPoi = null;
         Toast.makeText(activity, "Place was not added\n", Toast.LENGTH_LONG)
               .show();
      }

   }
   
   /**
    * sets the inflater and the view
    * @param inf the inflater
    * @param v the view
    * */
   public void setInflaterView(LayoutInflater inf, View v) {
      inflater = inf;
      view = v;
   }
   
   /** 
    * sets the POI info. takes the information from the EditText fields in the dialog
    * */
   public void setPointInfo() {
      String poiTitle = ((EditText) view.findViewById(R.id.poi_title))
            .getText().toString();
      String poiDescription = ((EditText) view
            .findViewById(R.id.poi_description)).getText().toString();

      activity.nextPoi.setTitle(poiTitle);
      activity.nextPoi.setDescription(poiDescription);

      if (poiTitle.length() != 0 && poiDescription.length() != 0) {
         activity.addPoi();
         return;
      }
      Toast.makeText(activity,
            "Place was not added,\nPlace must have a title and description",
            Toast.LENGTH_LONG).show();
      activity.addPOI(null);

   }
}
