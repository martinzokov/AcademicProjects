package uk.ac.aber.cs221.group07.walktourcreator.views;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import uk.ac.aber.cs221.group07.walktourcreator.model.WalkModel;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * The popup that is shown when the walk is being edited
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)EditWalkView.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class EditWalkView extends DialogView {

   /** Reference to the current walk's object */
   private WalkModel walk;
   /** Reference to the view which needs to be shown */
   private View layout;

   /**
    * Constructor for the popup.
    * 
    * @param context
    *           the activity that created the popup
    * @param viewLayout
    *           the id of the layout that will be displayed
    * @param w
    *           the current walk object
    */
   public EditWalkView(WalkScreen context, int viewLayout, WalkModel w) {
      super(context, viewLayout);
      LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      // layout = inflater.inflate(viewLayout, null);
      this.layout = this.getLayout();
      walk = w;
   }

   /** Implementation of the listener's onClick method 
    * @param dialog the dialog that is shown
    * @param which the id of the button that is pressed
    * */
   @Override
   public void onClick(DialogInterface dialog, int which) {
      if (which == DialogInterface.BUTTON_POSITIVE)
         editWalk();
   }

   /**
    * Called if the OK button is pressed. Gets the text from the EditText fields
    * on the screen and sets the walk's title, short and long description
    */
   public void editWalk() {
      String walkTitle = ((EditText) layout.findViewById(R.id.edit_walk_title))
            .getText().toString();
      String shortDesc = ((EditText) layout
            .findViewById(R.id.edit_walk_short_description)).getText()
            .toString();
      String longDesc = ((EditText) layout
            .findViewById(R.id.edit_walk_long_description)).getText()
            .toString();

      // changes title if a valid one has been entered
      if (walkTitle.length() == 0)
         ; // do nothing
      else
         walk.setTitle(walkTitle);

      // changes shot description if a valid one has been entered
      if (shortDesc.length() == 0)
         ;// do nothing
      else
         walk.setShortDescription(shortDesc); // set new description

      // changes long description if a valig one has been entered
      if (longDesc.length() == 0)
         ; // do nothing
      else
         walk.setLongDescription(longDesc);

   }

}
