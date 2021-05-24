package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dto2.ScheduleDTO;

public class SchedulePrevAdapter extends RecyclerView.Adapter<SchedulePrevAdapter.SchedulePrevViewHolder> {
    private Context context;
    private List<ScheduleDTO> schedules;

    SchedulePrevAdapter(Context context, List<ScheduleDTO> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public SchedulePrevViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prev_schedule_layout, viewGroup, false);
        SchedulePrevViewHolder viewHolder = new SchedulePrevViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SchedulePrevViewHolder holder, int position) {
        holder.prevScheduleList_hourText.setText(String.valueOf(schedules.get(position).getSchedule_hour()));
        holder.prevScheduleList_minuteText.setText(String.valueOf(schedules.get(position).getSchedule_minute()));
        holder.prevScheduleList_scheduleText.setText(String.valueOf(schedules.get(position).getSchedule_name()));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class SchedulePrevViewHolder extends RecyclerView.ViewHolder {
        TextView prevScheduleList_hourText, prevScheduleList_minuteText, prevScheduleList_scheduleText;
        public SchedulePrevViewHolder(View itemView) {
            super(itemView);
            prevScheduleList_hourText = itemView.findViewById(R.id.prevScheduleList_hourText);
            prevScheduleList_minuteText = itemView.findViewById(R.id.prevScheduleList_minuteText);
            prevScheduleList_scheduleText = itemView.findViewById(R.id.prevScheduleList_scheduleText);
        }
    }
}
