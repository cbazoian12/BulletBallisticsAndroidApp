/*To ADD IMAGE LOGO
add image file to BulletBallistics/app/src/main/res/drawable-mdpi and add the source path from the
IDE

TO INSTALL
build - Generate signed apk
then email the apk to your phone and open the attachment. should start installing.
 */


package com.wou.cbazoian12.bulletballisticsapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity {


    //declare variables

    //variables to be used by algorithm
    double travelTime = 0;
    int distance = 0;
    double bulletVelocity = 0;
    double acceleration = 32;
    double drop = 0;
    MediaPlayer mySound;

    public void playSound(View view)
    {
        mySound.start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySound = MediaPlayer.create(this, R.raw.sound);

        //implement calculate button and functionality

        System.out.println("Application has started successfully and reached first println");


        final Button calcButton = (Button) findViewById(R.id.calcButton);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pull text from app input and results fields
               final TextView resultTV = (TextView) findViewById(R.id.resultTextView);
                TextView inputTV = (TextView) findViewById(R.id.userInputDistance);

                //clear results field in app
                resultTV.setText("");


                //get user distance input
                CharSequence distanceInput = null;
                distanceInput = inputTV.getText();

                //turn string version of input into an int
                distance = Integer.parseInt(distanceInput.toString());

                //don't let user input more than 1500 yards to stay realistic
                if ((distance < 1) || (distance > 1500))
                {
                    resultTV.setText("");
                    resultTV.setText("Enter a realistic distance");
                }
                else
                {
                    //clear previous results
                    resultTV.setText("");

                    //distance originally input as yards. So convert distance to feet
                    distance = distance * 3;

                    //get the radio group that contains the caliber choices
                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bulletTypeRadioGroup);

                    //get the checked radio button
                    int id = radioGroup.getCheckedRadioButtonId();

                    //get selected radio button


                    //check which caliber is selected and set the velocity as that bullet's
                    //velocity.

                    if(id == R.id.bulletType1) //if .22 is selected
                    {
                        bulletVelocity = 1280;
                    }

                    else if(id == R.id.bulletType2) // if 5.56 is selected
                    {
                        bulletVelocity = 2800;
                    }

                    else if(id == R.id.bulletType3) //if 7.62 is selected
                    {
                        bulletVelocity = 2400;
                    }

                    else if(id == R.id.bulletType4) //if .308 is selected
                    {
                        bulletVelocity = 2600;
                    }

                    else if(id == R.id.bulletType5) //if .50 is selected
                    {
                        bulletVelocity = 2900;
                    }
                    else  //otherwise error
                    {
                        resultTV.setText("error");
                    }

                        /*
                         * The Algorithms:
                         *
                         * calculate travel time with this formula:
                         * travel time = distance / velocity
                         *
                         * calculate the bullet drop with the following linear acceleration formula:
                         * x = (1/2) * acceleration due to gravity * time^2
                         *
                         * acceleration due to gravity: 32 feet/second^2 a.k.a. 9.8 meters/sec^2
                         *
                         *
                         *
                         * https://answers.yahoo.com/question/index;_ylt=AwrTHRFRRRtVwUIARg1XNyoA;_ylu=X3oDMTEzdmU4dDA4BGNvbG8DZ3ExBHBvcwMxBHZ0aWQDVklQNTgyXzEEc2VjA3Ny?qid=20121225223003AAT3aHL
                          * */

                    travelTime = distance / bulletVelocity;

                    drop = 0.5 * acceleration * (travelTime * travelTime);

                    //drop should be in units of feet, so convert to inches
                    drop = drop * 12;

                    //Format the decimal to three places
                    DecimalFormat df = new DecimalFormat("##0.00");

                    //cast the drop to a string so it can be written to text field
                    String result = df.format(drop);

                    //clear the text field
                    resultTV.setText("");

                    //set result as the bullet drop
                    resultTV.setText(result + " inches");
                    //String button = String.valueOf(checkedRadioButton);
                    //resultTV.setText("Checked button: " + button);


                }

                playSound(v);

            }

        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


}
