package com.androidsms.com.androidsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import it.sauronsoftware.ftp4j.FTPClient;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

        //if (alarmReceiver()) {
            try {
                GmailSender sender = new GmailSender("email", "password");
                sender.sendMail("AndroidSMS", "This is a test email.", "from email", "to email");
                Toast.makeText(context, "Mail Sent Successfully.....", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        //}
        //else {
            //Toast.makeText(context, "Flag not found.", Toast.LENGTH_LONG).show();
        //}

    }


    public void uploadFile(File fileName){

        final String serialNumber = Build.SERIAL;
        final String FTP_HOST = "ftpserver";
        final String FTP_USER = "username";
        final String FTP_PASS = "password";

        FTPClient client = new FTPClient();

        try {

            client.connect(FTP_HOST,21);
            client.login(FTP_USER, FTP_PASS);
            client.setType(FTPClient.TYPE_BINARY);
            client.changeDirectory("/htdocs/" + serialNumber);

            client.upload(fileName);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
