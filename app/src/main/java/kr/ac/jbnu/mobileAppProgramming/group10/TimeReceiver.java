package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TimeReceiver extends BroadcastReceiver {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 0;
    private Context context;

    private String contentText;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_TIME_TICK))
        {
            this.context = context;

            SharedPreferences sharedPreferences = context.getSharedPreferences("currentTrip", Context.MODE_PRIVATE);
            int tripId = sharedPreferences.getInt("tripId", -1);
            if(tripId != -1) {
                createNotificationChannel();

                ScheduleDAO scheduleDAO = new ScheduleDAO(context);
                Date now = new Date(System.currentTimeMillis());
                String getTime = new SimpleDateFormat("yyyy/MM/dd/HH/mm").format(now);
                int nowYear = Integer.parseInt(getTime.split("/")[0]);
                int nowMonth = Integer.parseInt(getTime.split("/")[1]);
                int nowDay = Integer.parseInt(getTime.split("/")[2]);
                int nowHour = Integer.parseInt(getTime.split("/")[3]);
                int nowMinute = Integer.parseInt(getTime.split("/")[4]);

                List<ScheduleDTO> schedules = scheduleDAO.getSchedulesForDate(tripId, nowYear, nowMonth, nowDay);
                Collections.sort(schedules);
                Iterator<ScheduleDTO> iter = schedules.iterator();
                contentText = null;
                while(iter.hasNext()) {
                    ScheduleDTO schedule = iter.next();
                    if(schedule.getSchedule_hour() == nowHour && schedule.getSchedule_minute() == nowMinute)
                        contentText = schedule.getSchedule_name();
            }
                if(contentText != null) sendNotification();
            }
        }

    }

    private void createNotificationChannel() {
        notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Test Notification", notificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification Description");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle("일정 알림")
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.logo);
        return notifyBuilder;
    }

    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notificationManager.notify(NOTIFICATION_ID,notifyBuilder.build());
    }
}
