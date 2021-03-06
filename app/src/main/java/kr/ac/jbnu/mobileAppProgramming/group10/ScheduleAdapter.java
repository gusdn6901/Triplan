package kr.ac.jbnu.mobileAppProgramming.group10;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.DBService;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.ScheduleDTO;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context context;
    private List<ScheduleDTO> schedules;

    ScheduleAdapter(Context context, List<ScheduleDTO> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_layout, viewGroup, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        final ScheduleDTO schedule = schedules.get(position);

        String hourString = String.valueOf(schedules.get(position).getSchedule_hour());
        String minuteString = String.valueOf(schedules.get(position).getSchedule_minute());
        if(Integer.parseInt(hourString) < 10) hourString = "0" + hourString;
        if(Integer.parseInt(minuteString) < 10) minuteString = "0"+ minuteString;
        holder.scheduleList_hourText.setText(hourString);
        holder.scheduleList_minuteText.setText(minuteString);
        holder.scheduleList_scheduleText.setText(String.valueOf(schedules.get(position).getSchedule_name()));
        holder.scheduleList_modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageScheduleActivity.class);
                intent.putExtra("scheduleId", schedule.getSchedule_id());
                intent.putExtra("tripId", ((Activity)context).getIntent().getIntExtra("tripId", -1));
                intent.putExtra("name", schedule.getSchedule_name());
                intent.putExtra("year", schedule.getSchedule_date_year());
                intent.putExtra("month", schedule.getSchedule_date_month());
                intent.putExtra("day", schedule.getSchedule_date_day());
                intent.putExtra("hour", schedule.getSchedule_hour());
                intent.putExtra("minute", schedule.getSchedule_minute());
                intent.putExtra("isModify", true);
                ((Activity)context).startActivityForResult(intent, ScheduleListActivity.SCHEDULE_MODIFIED_REQUEST);
            }
        });
        holder.scheduleList_deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("??????").setMessage("????????? ?????????????????????????");
                builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBService.getInstance(context).deleteSchedule(schedule.getSchedule_id());
                        ((Activity)context).startActivity(((Activity)context).getIntent());
                        ((Activity)context).finish();
                    }
                });
                builder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        if(DBService.getInstance(context).getCurrentTrip().getTrip_id() == null)
            return;
        else if(DBService.getInstance(context).getCurrentTrip().getTrip_id() != ((Activity)context).getIntent().getIntExtra("tripId", -1))
            return;

        Date now = new Date(System.currentTimeMillis());
        String getTime = new SimpleDateFormat("yyyy/MM/dd/HH/mm").format(now);
        int nowYear = Integer.parseInt(getTime.split("/")[0]);
        int nowMonth = Integer.parseInt(getTime.split("/")[1]);
        int nowDay = Integer.parseInt(getTime.split("/")[2]);
        int nowHour = Integer.parseInt(getTime.split("/")[3]);
        int nowMinute = Integer.parseInt(getTime.split("/")[4]);

        if(schedule.getSchedule_date_year() < nowYear) changeTextColor(holder);
        else if(schedule.getSchedule_date_month() < nowMonth) changeTextColor(holder);
        else if(schedule.getSchedule_date_day() < nowDay) changeTextColor(holder);
        else {
            if(schedule.getSchedule_hour() < nowHour) changeTextColor(holder);
            else if(schedule.getSchedule_hour() <= nowHour && schedule.getSchedule_minute() < nowMinute) changeTextColor(holder);
        }
    }

    private void changeTextColor(ScheduleViewHolder holder) {
        holder.scheduleList_hourText.setTextColor(Color.GRAY);
        holder.scheduleList_middleText.setTextColor(Color.GRAY);
        holder.scheduleList_minuteText.setTextColor(Color.GRAY);
        holder.scheduleList_scheduleText.setTextColor(Color.GRAY);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView scheduleList_hourText, scheduleList_middleText, scheduleList_minuteText, scheduleList_scheduleText;
        Button scheduleList_modifyBtn, scheduleList_deleteBtn;
        public ScheduleViewHolder(View itemView) {
            super(itemView);
            scheduleList_hourText = itemView.findViewById(R.id.scheduleList_hourText);
            scheduleList_middleText = itemView.findViewById(R.id.scheduleList_middleText);
            scheduleList_minuteText = itemView.findViewById(R.id.scheduleList_minuteText);
            scheduleList_scheduleText = itemView.findViewById(R.id.scheduleList_scheduleText);
            scheduleList_modifyBtn = itemView.findViewById(R.id.scheduleList_modifyBtn);
            scheduleList_deleteBtn = itemView.findViewById(R.id.scheduleList_deleteBtn);
        }
    }
}
