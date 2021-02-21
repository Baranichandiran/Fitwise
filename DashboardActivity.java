package com.example.fitwise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;

public class DashboardActivity extends AppCompatActivity {

    int i = 0;
    public int[] heartbeat;
    public int[] temperature;
    public int[] bloodPressure;
    public Button refBut;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView temp = findViewById(R.id.tempValue);
        TextView heartBeat = findViewById(R.id.heartValue);
        TextView BP = findViewById(R.id.bpValue);
        refBut  = findViewById(R.id.refButton);


        heartbeat = new int[]{88, 78, 90, 73, 88, 90, 77, 97, 77, 87, 89, 76, 81, 71, 85, 74, 79, 83, 94, 87, 81, 85};
        temperature = new int[]{91, 93, 96, 101, 104, 108, 89, 99, 100, 107, 108, 103, 99, 94, 97, 93, 90, 87, 93, 95, 98, 105};
        bloodPressure = new int[]{88, 90, 78, 73, 56, 90, 77, 93, 77, 87, 89, 74, 81, 71, 80, 74, 56, 83, 94, 87, 81, 85};


        refBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                Random r = new Random();
                int i = r.nextInt(100);
                int j = r.nextInt(80);
                int k = r.nextInt(90);

                if(i>50){
                    sendSMSMessage();
                }

                ProgressDialog dialog = ProgressDialog.show(DashboardActivity.this, "Update", "Please Wait while your data is updating",
                        true);
                dialog.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                }, 3000);
                temp.setText(i +"");
                heartBeat.setText(j +"");
                BP.setText(k +"");
            }
        });

    }

    private void addNotification() {
        String message = "This is a Notification Message";
        @Deprecated
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
          DashboardActivity.this)
                .setSmallIcon(R.drawable.ic_baseline_person_24)
                .setContentTitle("Abnormal Alert")
                .setContentText(message)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        notificationManager.notify(0,builder.build());

    }

    protected void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+919003330816", null, "Abnormal Alert !!!. Your Body is in abnormal condition.", null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                return;
            }
        }

    }


}