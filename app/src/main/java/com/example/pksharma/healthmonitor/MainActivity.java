package com.example.pksharma.healthmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;


public class MainActivity extends AppCompatActivity implements AccelerometerListener {

    Button enterBtn,botBtn;
    LinearLayout linearLayout;
    String Disease="";
    TextView textView;
    int val=0;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout=findViewById(R.id.main_linear);
        botBtn=findViewById(R.id.botBtn);
        enterBtn=findViewById(R.id.enterBtn);
        textView=findViewById(R.id.tvDis);

        botBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ChatBotActivity.class);
                startActivity(intent);
            }
        });

       final Button b=new Button(MainActivity.this);
        final EditText editText=new EditText(MainActivity.this);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (linearLayout != null) {
                    linearLayout.removeView(enterBtn);
                }
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                editText.setId(0);
                linearLayout.addView(editText);

                b.setText("Enter");
                b.setBackgroundColor(Color.parseColor("#3aa8c1"));
               // b.setId(1);
                linearLayout.addView(b);



            }
        });

        Intent i=getIntent();
        Disease=i.getStringExtra("Dis");

        if(Disease!=null) {
            if (Disease.trim().equals("Heart") || Disease.trim().equals("heart")) {
                val = 1;
                //    Toast.makeText(this, "value", Toast.LENGTH_SHORT).show();
            } else if (Disease.trim().equals("Depression") || Disease.trim().equals("depression"))
                val = 2;
            else if (Disease.trim().equals("Diabetes") || Disease.trim().equals("diabetes"))
                val = 3;
            else if (Disease.trim().equals("Asthama") || Disease.trim().equals("asthama"))
                val = 4;
            else if (Disease.trim().equals("Cold") || Disease.trim().equals("cold"))
                val=5;
            else if(Disease.trim().equals("Cancer") || Disease.trim().equals("cancer"))
                val=6;
            else
                val = 0;
        }
        Toast.makeText(MainActivity.this, "value: "+Disease, Toast.LENGTH_SHORT).show();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Disease= editText.getText().toString();

                String check="Heart";
                if(Disease.trim().equals(check.trim()))
                {
                    val=1;
                    //    Toast.makeText(this, "value", Toast.LENGTH_SHORT).show();
                }else if(Disease.trim().equals("Depression") || Disease.trim().equals("depression"))
                    val=2;
                else if(Disease.trim().equals("Diabities") || Disease.trim().equals("diabetes"))
                    val=3;
                else if(Disease.trim().equals("Asthama") || Disease.trim().equals("asthama"))
                    val=4;
                else if (Disease.trim().equals("Cold") || Disease.trim().equals("cold"))
                    val=5;
                else if(Disease.trim().equals("Cancer") || Disease.trim().equals("cancer"))
                    val=6;
                else
                    val=0;


                if(Disease!=null)
                    textView.setText("Detected Disease: "+Disease);

                Toast.makeText(MainActivity.this, "value: "+val, Toast.LENGTH_SHORT).show();
               // Toast.makeText(MainActivity.this, "Disease:"+Disease, Toast.LENGTH_SHORT).show();
//                try {
//                    d.Dis="Heart";
//                }catch (NullPointerException ex){
//                    Toast.makeText(MainActivity.this, "dhinchuk", Toast.LENGTH_SHORT).show();
//                }
//
//                try {
//                    Toast.makeText(getApplicationContext(), "Disease: "+d.Dis, Toast.LENGTH_SHORT).show();
//                }catch (NullPointerException ex){
//                    Toast.makeText(MainActivity.this, "Ooops", Toast.LENGTH_SHORT).show();
//                }
//
             }
        });

        if(Disease!=null)
            textView.setText("Detected Disease: "+Disease);



        Log.d("MainActivity","value:"+val);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent=new Intent(MainActivity.this,showProgress.class);
                Bundle bundle=new Bundle();
                bundle.putString("Disease",Disease);
                bundle.putInt("val",val);
                intent.putExtras(bundle);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "yayyy", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub

    }

    public void onShake(float force) {

        String messageToSend = "EMERGENCY SOS \n I have sent an emergency msg. \n Please help!!! ";
        String number = "8800375120";
        if(!flag) {
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            flag=true;
        }
        Toast.makeText(getBaseContext(), "Motion detected",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
                Toast.LENGTH_SHORT).show();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
