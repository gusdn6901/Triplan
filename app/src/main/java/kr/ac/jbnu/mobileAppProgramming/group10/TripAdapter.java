package kr.ac.jbnu.mobileAppProgramming.group10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private Context context;
    private List<String> trips;

    TripAdapter(Context context, List<String> trips) {
        this.context = context;
        this.trips = trips;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_layout, viewGroup, false);
        TripAdapter.TripViewHolder viewHolder = new TripAdapter.TripViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        holder.trip_tripNameText.setText(trips.get(position));
        holder.trip_locationText.setText(trips.get(position));
        holder.trip_startDateText.setText(trips.get(position));
        holder.trip_endDateText.setText(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        TextView trip_tripNameText, trip_locationText, trip_startDateText, trip_endDateText;
        public TripViewHolder(View itemView) {
            super(itemView);
            trip_tripNameText = itemView.findViewById(R.id.trip_tripNameText);
            trip_locationText = itemView.findViewById(R.id.trip_locationText);
            trip_startDateText = itemView.findViewById(R.id.trip_startDateText);
            trip_endDateText = itemView.findViewById(R.id.trip_endDateText);
        }
    }
}
