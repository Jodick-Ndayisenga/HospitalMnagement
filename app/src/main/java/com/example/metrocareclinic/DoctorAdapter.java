package com.example.metrocareclinic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.MessageFormat;
import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>  {
    Context context;
    ArrayList<Appointment> arrayList;
    ArrayList<User> arrayUser;
    OnItemClickListener onItemClickListener;

    public DoctorAdapter(Context context, ArrayList<Appointment> arrayList, ArrayList<User> arrayUser) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayUser = arrayUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_list_patients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(MessageFormat.format("{0}",
                arrayUser.get(position).getEmail())
        );

        holder.utime.setText(MessageFormat.format("{0}",
                arrayList.get(position).getTime())
        );

        holder.lname.setText(MessageFormat.format("{0}",
                arrayUser.get(position).getFirstName()
                        + " " +
                        arrayUser.get(position).getLastName())
        );

        holder.residence.setText(MessageFormat.format("{0}",
                        arrayUser.get(position).getResidence()
                )
        );
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position), arrayUser.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lname, utime, email, residence;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lname = itemView.findViewById(R.id.list_item_name);
            utime = itemView.findViewById(R.id.list_item_time);
            email = itemView.findViewById(R.id.list_item_email);
            residence = itemView.findViewById(R.id.list_item_location);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Appointment appointment, User user);
    }
}