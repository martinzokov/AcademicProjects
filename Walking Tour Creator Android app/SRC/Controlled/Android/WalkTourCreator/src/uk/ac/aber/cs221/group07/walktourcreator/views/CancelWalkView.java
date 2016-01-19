package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import android.content.DialogInterface;

/**
 * Popup class with a message warning that the walk will be deleted if the user
 * cancels it
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)CancelWalkView.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class CancelWalkView extends DialogView {

   /** A reference to the WalkScreen class */
   private WalkScreen walk;

   /**
    * Constructor for the popup. Uses the super constructor.
    * 
    * @param context
    *           the activity that created the popup
    * @param viewLayout
    *           the id of the layout
    */
   public CancelWalkView(WalkScreen context, int viewLayout) {
      super(context, viewLayout);
      walk = context;
   }

   /**
    * Implementation of the listner's onClick method that listens for button
    * presses
    */
   @Override
   public void onClick(DialogInterface dialog, int which) {
      if (which == DialogInterface.BUTTON_POSITIVE) {
         deleteWalk();
      }
   }

   /** Deletes the walk if the user chooses to cancel it */
   public void deleteWalk() {
      walk.returnToStart(true);
   }
}
