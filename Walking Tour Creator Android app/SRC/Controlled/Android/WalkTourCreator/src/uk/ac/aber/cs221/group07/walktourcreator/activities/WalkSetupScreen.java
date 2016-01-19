package uk.ac.aber.cs221.group07.walktourcreator.activities;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import uk.ac.aber.cs221.group07.walktourcreator.model.WalkModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class is responsible for displaying the walk setup screen which sets the
 * title and descriptions for a walk
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)WalkSetupScreen.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class WalkSetupScreen extends Activity {

   /**
    * This method is called automatically when the activity is created, all it
    * does is starts sets the layout as specified in
    * res/layout/activity_walk_setup_screen.xml
    * 
    * @param savedInstanceState is not used.
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_walk_setup_screen);
   }

   /**
    * Starts a new WalkScreen activity, displays it to the user and starts
    * recording GPS data. The details that the user has input, are passed to the
    * new activity.
    * 
    * @param v  is the View that called this method
    */
   public void startWalk(View v) {
      // get input from text fields
      String title = ((EditText) findViewById(R.id.title_input)).getText()
            .toString();
      String short_desc = ((EditText) findViewById(R.id.short_description_input))
            .getText().toString();
      String long_desc = ((EditText) findViewById(R.id.long_description_input))
            .getText().toString();

      //if any of the fields are empty then fail and display message
      if (title.length() == 0) {
         Toast.makeText(this,
               "A title must contains no white spaces\nAnd Must not be empty",
               Toast.LENGTH_LONG).show();
      } else if (short_desc.length() == 0) {
         Toast.makeText(
               this,
               "Description has to be under 100 characters\nAnd Must not be empty",
               Toast.LENGTH_LONG).show();
      } else if (long_desc.length() == 0) {
         Toast.makeText(
               this,
               "Detailed Description has to be under 1000 characters\nAnd Must not be empty",
               Toast.LENGTH_LONG).show();
      } else {
         
         //create and set values in a walk.
         WalkModel walk = new WalkModel();
         walk.setTitle(title);
         walk.setShortDescription(short_desc);
         walk.setLongDescription(long_desc);
         
         // go to walk screen and pass it the newly created walk
         Intent intent = new Intent(this, WalkScreen.class);
         intent.putExtra("walk", walk);
         startActivity(intent);
         finish();
      }
   }
}
