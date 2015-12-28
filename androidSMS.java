
package com.androidsms.com.androidsms;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//public class androidSMS extends AppCompatActivity {
public class androidSMS extends Activity {

    private boolean flag;
    private AlarmManager manager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sms);

        Intent alarmIntent = new Intent(androidSMS.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(androidSMS.this, 0, alarmIntent, 0);
        startAlarm();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //ftp();
        }
        try {
            Process su = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_android_sms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Null object reference
    public boolean status() {
        File dir = getFilesDir();
        File file = new File(dir, "flag");
        if(file.exists()) {
            flag = true;
        }
        else {
            flag = false;
        }
        return flag;
    }

    public void deleteFlag() {
        File dir = getFilesDir();
        File file = new File(dir, "flag");

        if (file.exists()) {
            Toast.makeText(this, "Flag deleted.", Toast.LENGTH_LONG).show();
            boolean deleted = file.delete();
        }
    }

    public void onClickBtn(View view) throws IOException {

        Boolean frequencyFlag = null;
        Boolean emailFlag = null;

        EditText email = (EditText) findViewById(R.id.editText);
        String emailString = email.getText().toString();
        if (!(emailString.matches("[a-z0-9].*@[a-z]*.com"))){
            Toast.makeText(this, "Please enter a properly formatted email address.", Toast.LENGTH_LONG).show();
            email.setText("");
            emailFlag = false;
        }
        else if (emailString.matches("")) {
            Toast.makeText(this, "Please enter an email address. Field cannot be empty.", Toast.LENGTH_LONG).show();
            email.setText("");
            emailFlag = false;
        } else {
            Toast.makeText(this, email.getText().toString(), Toast.LENGTH_LONG).show();
            emailFlag = true;
        }

        EditText frequency = (EditText) findViewById(R.id.frequencyText);
        String frequencyString = frequency.getText().toString();
        if (!(frequencyString.matches(""))) {
            Toast.makeText(this, frequency.getText().toString(), Toast.LENGTH_LONG).show();
            frequencyFlag = true;
        } else {
            Toast.makeText(this, "This field cannot be empty.", Toast.LENGTH_LONG).show();
            frequency.setText("");
            frequencyFlag = false;
        }

        if (emailFlag && frequencyFlag) {
            updateConfig("frequency:" + frequencyString + "," + "email:" + emailString + "\n");
        }

    }

    public void updateConfig(String string) throws IOException {

        String file = "config";
        FileOutputStream fileout = openFileOutput(file, Context.MODE_PRIVATE);
        try {
            fileout.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void placeCall() {
        String posted_by = "0005559090";

        String uri = "tel:" + posted_by.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    public void startAlarm() {
        //public final static long REPEAT_TIME = 1000 * 60;
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 60000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Checking for new messages.", Toast.LENGTH_SHORT).show();
    }

}
