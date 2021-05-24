package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ScheduleNextAdapter extends RecyclerView.Adapter<ScheduleNextAdapter.ScheduleNextViewHolder> {
    private Context context;
    private List<String> schedules;

    ScheduleNextAdapter(Context context, List<String> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @Override
    public ScheduleNextViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.next_schedule_layout, viewGroup, false);
        ScheduleNextViewHolder viewHolder = new ScheduleNextViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleNextViewHolder holder, int position) {
        holder.nextScheduleList_hourText.setText(schedules.get(position));
        holder.nextScheduleList_minuteText.setText(schedules.get(position));
        holder.nextScheduleList_scheduleText.setText(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleNextViewHolder extends RecyclerView.ViewHolder {
        TextView nextScheduleList_hourText, nextScheduleList_minuteText, nextScheduleList_scheduleText;
        public ScheduleNextViewHolder(View itemView) {
            super(itemView);
            nextScheduleList_hourText = itemView.findViewById(R.id.nextScheduleList_hourText);
            nextScheduleList_minuteText = itemView.findViewById(R.id.nextScheduleList_minuteText);
            nextScheduleList_scheduleText = itemView.findViewById(R.id.nextScheduleList_scheduleText);
        }
    }
}
