package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public class ScheduleCurrentAdapter extends RecyclerView.Adapter<ScheduleCurrentAdapter.ScheduleCurrentViewHolder> {
    private Context context;
    private List<ScheduleDTO> schedules;

    ScheduleCurrentAdapter(Context context, List<ScheduleDTO> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ScheduleCurrentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_schedule_layout, viewGroup, false);
        ScheduleCurrentViewHolder viewHolder = new ScheduleCurrentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleCurrentViewHolder holder, int position) {
        holder.currentScheduleList_hourText.setText(String.valueOf(schedules.get(position).getSchedule_hour()));
        holder.currentScheduleList_minuteText.setText(String.valueOf(schedules.get(position).getSchedule_minute()));
        holder.currentScheduleList_scheduleText.setText(String.valueOf(schedules.get(position).getSchedule_name()));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleCurrentViewHolder extends RecyclerView.ViewHolder {
        TextView currentScheduleList_hourText, currentScheduleList_minuteText, currentScheduleList_scheduleText;
        public ScheduleCurrentViewHolder(View itemView) {
            super(itemView);
            currentScheduleList_hourText = itemView.findViewById(R.id.currentScheduleList_hourText);
            currentScheduleList_minuteText = itemView.findViewById(R.id.currentScheduleList_minuteText);
            currentScheduleList_scheduleText = itemView.findViewById(R.id.currentScheduleList_scheduleText);
        }
    }
}
