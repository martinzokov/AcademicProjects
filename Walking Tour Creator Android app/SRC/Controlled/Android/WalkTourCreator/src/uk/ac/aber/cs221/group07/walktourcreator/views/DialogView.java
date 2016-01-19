package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

/**
 * An abstract class to easily create different popup screens which can be
 * customzied. Implements the OnClickListener interface to repsond to key
 * presses.
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)DialogView.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public abstract class DialogView implements DialogInterface.OnClickListener {

   /** A reference to the activity (WalkScreen) that created the popup */
   protected WalkScreen activity;
   /** A reference to the current view in order to display the popup */
   protected View layout;
   /** The actual popup container which is displayed */
   protected AlertDialog dialog;

   /**
    * Constructor for a DialogView which takes the activity and the id of the
    * layout that needs to be displayed
    * 
    * @param context
    *           the activity that created the popup
    * @param viewLayout
    *           the id of the layout in the R file
    */
   public DialogView(WalkScreen context, int viewLayout) {
      LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View layout = inflater.inflate(viewLayout, null);
      this.layout = layout;
      activity = context;

      dialog = new AlertDialog.Builder(context).setView(layout)
            .setPositiveButton(android.R.string.yes, this)
            .setNegativeButton(android.R.string.no, this).create();
   }
   
   /** Displays the popup */
   public void show() {
      dialog.show();
   }

   /** Destroys the popup */
   public void dismiss() {
      dialog.dismiss();
   }

   /**
    * The onClick method that is called each time a button in the popup is
    * pressed
    * @param dialog the dialog that is shown
    * @param which the id of the button that is pressed
    */
   @Override
   public abstract void onClick(DialogInterface dialog, int which);

   /** Gets the layout of the popup */
   public View getLayout() {
      return layout;
   }

}
