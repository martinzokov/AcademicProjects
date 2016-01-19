package uk.ac.aber.cs221.group07.walktourcreator.activities;

import uk.ac.aber.cs221.group07.walktourcreator.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This class is responsible for displaying the main menu screen, and reacting
 * to button presses. It is the first screen that is presented to the user.
 * 
 * @author HarryBuckley
 * @author Martin Zokov
 * @since 0.1
 * @version 0.1
 * @(#)MainMenu.java 0.1 2014-01-31
 *          Copyright (c) 2013 Aberystwyth University. All rights reserved.
 */
public class MainMenu extends Activity {

   /**
    * This method is called automatically when the activity is created, all it
    * does is starts sets the layout as specified in
    * res/layout/activity_main_menu.xml
    * 
    * @param savedInstanceState, is not used in this case
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_menu);
   }

   /**
    * Starts a new WalkSetupScreen activity, and displays it to the user. It is
    * called when the user presses the start walk button.
    * 
    * @param v is the View that is called the method.
    */
   public void startWalkSetupScreen(View v) {
      Intent intent = new Intent(this, WalkSetupScreen.class);
      startActivity(intent);
      finish();
   }
}
