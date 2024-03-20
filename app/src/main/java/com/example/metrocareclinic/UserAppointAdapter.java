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

public class UserAppointAdapter extends RecyclerView.Adapter<UserAppointAdapter.ViewHolder>  {
    Context context;
    ArrayList<Appointment> arrayList;
    OnItemClickListener onItemClickListener;

    public UserAppointAdapter(Context context, ArrayList<Appointment> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_appoint_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(MessageFormat.format("{0}",
                arrayList.get(position).getDocFullName())
        );
        holder.docField.setText(MessageFormat.format("{0}",
                        arrayList.get(position).getField()
                )
        );
        holder.time.setText(MessageFormat.format("{0}",
                        arrayList.get(position).getTime()
                )
        );
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, docField, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_item_name);
            docField = itemView.findViewById(R.id.list_item_field);
            time = itemView.findViewById(R.id.list_item_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Appointment appointment);
    }
}