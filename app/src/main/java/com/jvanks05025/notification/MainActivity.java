package com.jvanks05025.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String CHANNEL_ID="important_mail_channel";
    Button simpleNotif, BigTextStyleNotif, BigPictureNotif, InboxStyleNotif, ActionNotif;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        simpleNotif=findViewById(R.id.simpleNotif);
        BigTextStyleNotif=findViewById(R.id.bigStyleNotif);
        BigPictureNotif=findViewById(R.id.bigPictureStyle);
        InboxStyleNotif=findViewById(R.id.InboxStyle);
        ActionNotif=findViewById(R.id.ActionNotification);

        simpleNotif.setOnClickListener(this);
        BigTextStyleNotif.setOnClickListener(this);
        BigPictureNotif.setOnClickListener(this);
        InboxStyleNotif.setOnClickListener(this);
        ActionNotif.setOnClickListener(this);

        notificationManagerCompat=NotificationManagerCompat.from(MainActivity.this);


    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="important_mail_channel";

            String description="this channel will show notification only to important people";

            int important= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,name,important);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        switch (viewId){
            case (R.id.simpleNotif):
                createSimpleNotification(getString(R.string.simple_notification_title),getString(R.string.simple_notification_text),1);
                break;
            case (R.id.bigStyleNotif):
                createBigTextNotification(getString(R.string.bigtext_notification_title),getString(R.string.bigtext_notification_text),2);
                break;
            case (R.id.bigPictureStyle):
                createBigPictureNotification(getString(R.string.bigpicture_notification_title),getString(R.string.bigpicture_notification_text),3);
                break;
            case (R.id.InboxStyle):
                createInboxNotification(getString(R.string.inbox_notification_title),getString(R.string.inbox_notification_text),4);
                break;
            case (R.id.ActionNotification):
                createActionNotification(getString(R.string.action_notification_title),getString(R.string.action_notification_text),5);
                break;
        }
    }

    private void createActionNotification(String title, String text, int notificationId) {
        notificationManagerCompat.cancelAll();
//        action
        Intent intent=new Intent(getApplicationContext(),Receiver.class);
        intent.setAction("ACTION_CANCEL");

        intent.putExtra("id",notificationId);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(),0,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);




        NotificationCompat.Action actiondismiss= new NotificationCompat
                .Action.Builder(0,"Dismiss Message",pendingIntent).build();
        NotificationCompat.Action actionDelete= new NotificationCompat
                .Action.Builder(0,"Delete Message",pendingIntent).build();

        Notification notification= new Notification.Builder(this,CHANNEL_ID)                .setSmallIcon(R.drawable.ic_launcher_background)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .addAction(new Notification.Action(0,"DISMISS",pendingIntent))
                .addAction(new Notification.Action(0,"CANCEL",pendingIntent))
                .build();
        notificationManagerCompat.notify(notificationId, notification);

    }

    private void createInboxNotification(String title, String text, int notificationId) {
        notificationManagerCompat.cancelAll();
        String line1="this is line 1";
        String line2="this is line 2";
        String line3="this is line 3";
        String line4="this is line 4";
        String line5="this is line 5";
        String line6="this is line 6";
        String line7="this is line 7";

        Notification.InboxStyle style=new Notification.InboxStyle()
                .addLine(line1)
                .addLine(line2)
                .addLine(line3)
                .addLine(line4)
                .addLine(line5)
                .addLine(line6)
                .addLine(line7)
                .setSummaryText("inbox style")
                .setBigContentTitle(null);

        Notification notification= new Notification.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(style)
                .build();
        notificationManagerCompat.notify(notificationId,notification);


    }

    private void createBigTextNotification(String title, String text, int notificationId) {
        notificationManagerCompat.cancelAll();
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

//        NotificationCompat.BigTextStyle style=new NotificationCompat.BigTextStyle().bigText(text+" long long text")
//                .setBigContentTitle(null)
//                .setSummaryText("BigTextStyle");

        Notification notification= new Notification.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new Notification.BigTextStyle().bigText(text+"and long text").setSummaryText("BigTextStyle").setBigContentTitle(null))
                .build();
        notificationManagerCompat.notify(notificationId,notification);
    }

    private void createBigPictureNotification(String title, String text, int notificationId) {
        notificationManagerCompat.cancelAll();
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);

        Notification.BigPictureStyle style=new Notification.BigPictureStyle()
                .bigPicture(bitmap)
                .setSummaryText("bigpicture")
                .setBigContentTitle(null);

        Notification notification= new Notification.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(style)
                .build();
        notificationManagerCompat.notify(notificationId,notification);
    }

    private void createSimpleNotification(String title, String text, int notificationId) {
        notificationManagerCompat.cancelAll();

        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://wwww.google.com/"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        Notification notification= new Notification.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(notificationId,notification);

    }


}