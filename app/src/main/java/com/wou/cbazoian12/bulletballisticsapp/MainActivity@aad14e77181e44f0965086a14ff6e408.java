/*To ADD IMAGE LOGO
add image file to BulletBallistics/app/src/main/res/drawable-mdpi and add the source path from the
IDE
 */



package com.wou.cbazoian12.bulletballisticsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity  {


    //declare variables

    //variables to be used by algorithm
    double travelTime = 0;
    int distance = 0;
    double bulletVelocity = 0;
    double acceleration = 32;
    double drop = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //implement calculate button and functionality

        System.out.println("Application has started successfully and reached first println");


        final Button calcButton = (Button) findViewById(R.id.calcButton);
        calcButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //pull text from app input and results fields
                final TextView resultTV = (TextView) findViewById(R.id.resultTextView);
                TextView inputTV = (TextView) findViewById(R.id.userInputDistance);

                //clear results field in app
                resultTV.setText("");



                //get user distance input
                CharSequence distanceInput = inputTV.getText();


                //convert user input to string so that it can be turned into an int
                String distanceToTarget = distanceInput.toString();

                //if input is even a number
                if(validateInput(distanceToTarget))
                {
                    //turn string version of input into an int
                    distance = Integer.parseInt(distanceToTarget);

                    //turn distance into a double


                    //don't let user input more than 1500 yards to stay realistic
                    if(distance > 1500)
                    {
                        //clear previous results
                        resultTV.setText("");

                        //user entered unrealistic distance
                        resultTV.setText("Enter realistic distance");
                    }
                    else
                    {

                        //clear previous results
                        resultTV.setText("");


                        //distance originally input as yards. So convert distance to feet
                        distance = distance * 3;


                        //radio button bullet type listener
                        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bulletTypeRadioGroup);

                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                        {
                            //inner class
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId)
                            {
                                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                                //String text = checkedRadioButton.getText().toString();
                                //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                //String debug = checkedRadioButton.getText().toString();

                                //if .22 is selected...
                                if(checkedRadioButton.getText().toString().equals(".22"))
                                {
                                    bulletVelocity = 1280;
                                }
                                if(checkedRadioButton.getText().toString().equals("5.56"))
                                {
                                    bulletVelocity = 2800;
                                }
                                if(checkedRadioButton.getText().toString().equals("7.62"))
                                {
                                    bulletVelocity = 2400;
                                }
                                if(checkedRadioButton.getText().toString().equals(".308"))
                                {
                                    bulletVelocity = 2600;
                                }
                                //if .50BMG is selected...
                                else if(checkedRadioButton.getText().toString().equals(".50 BMG"))
                                {
                                    bulletVelocity = 2900;
                                }
                                else
                                {
                                    resultTV.setText("Error. No bullet type selected.");
                                }
                            }
                        });//end of radio button listener





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
                        DecimalFormat df = new DecimalFormat("0.000");
                        df.format(drop);

                        //cast the drop to a string so it can be written to text field
                        String result = Double.toString(drop);

                        //set result as the bullet drop
                        resultTV.setText(result + " inches");
                    }
                }

                else
                {
                    resultTV.setText("Enter number");
                }

            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private boolean validateInput(String input)
    {
        try{
            Integer.parseInt(input);
            return true;
        }catch(Exception e)
        {
            return false;
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
