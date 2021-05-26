package kr.ac.jbnu.mobileAppProgramming.group10;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.ScheduleDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dao.TripDAO;
import kr.ac.jbnu.mobileAppProgramming.group10.database.dto.TripDTO;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private Context context;
    private List<TripDTO> trips;

    TripAdapter(Context context, List<TripDTO> trips) {
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
    public void onBindViewHolder(final TripViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.trip_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScheduleListActivity.class);
                intent.putExtra("tripId", trips.get(position).getTrip_id());
                context.startActivity(intent);
            }
        });
        holder.trip_tripNameText.setText(trips.get(position).getTrip_name());
        holder.trip_locationText.setText(trips.get(position).getTrip_location());
        holder.trip_startDateText.setText(trips.get(position).getTrip_start_date_year() + "." + trips.get(position).getTrip_start_date_month() + "." +
                trips.get(position).getTrip_start_date_day());
        holder.trip_endDateText.setText(trips.get(position).getTrip_end_date_year() + "." + trips.get(position).getTrip_end_date_month() + "." +
                trips.get(position).getTrip_end_date_day());
        holder.trip_currentTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trips.get(position).getTrip_is_current() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("현재 여행 설정").setMessage("현재 진행중인 여행으로 선택하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("currentTrip", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("tripId", trips.get(position).getTrip_id());
                            editor.commit();

                            TripDAO tripDAO = new TripDAO(context);
                            tripDAO.resetCurrentTrip();

                            TripDTO trip = trips.get(position);
                            trip.setTrip_is_current(1);
                            tripDAO.updateTrip(trip);
                            ((Activity)context).finish();
                            ((Activity)context).startActivity(((Activity)context).getIntent());
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("현재 여행 설정").setMessage("현재 여행에서 해제하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("currentTrip", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("tripId", -1);
                            editor.commit();

                            TripDAO tripDAO = new TripDAO(context);
                            tripDAO.resetCurrentTrip();

                            TripDTO trip = trips.get(position);
                            trip.setTrip_is_current(0);
                            tripDAO.updateTrip(trip);
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        holder.trip_deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("경고").setMessage("정말로 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TripDAO tripDAO = new TripDAO(context);
                        tripDAO.deleteTrip(trips.get(position).getTrip_id());
                        ScheduleDAO scheduleDAO = new ScheduleDAO(context);
                        scheduleDAO.deleteScheduleByTrip(trips.get(position).getTrip_id());
                        ((Activity)context).startActivity(((Activity)context).getIntent());
                        ((Activity)context).finish();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        TextView trip_tripNameText, trip_locationText, trip_startDateText, trip_endDateText;
        ImageButton trip_currentTripBtn, trip_deleteBtn;
        CardView trip_list;
        public TripViewHolder(View itemView) {
            super(itemView);
            trip_list = itemView.findViewById(R.id.trip_list);
            trip_tripNameText = itemView.findViewById(R.id.trip_tripNameText);
            trip_locationText = itemView.findViewById(R.id.trip_locationText);
            trip_startDateText = itemView.findViewById(R.id.trip_startDateText);
            trip_endDateText = itemView.findViewById(R.id.trip_endDateText);
            trip_currentTripBtn = itemView.findViewById(R.id.trip_currentTripBtn);
            trip_deleteBtn = itemView.findViewById(R.id.trip_deleteBtn);
        }
    }
}
