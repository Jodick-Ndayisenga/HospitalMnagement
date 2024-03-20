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

public class MedecineAdapter extends RecyclerView.Adapter<MedecineAdapter.ViewHolder>  {
    Context context;
    ArrayList<PrescriptionMedecine> arrayList;
    OnItemClickListener onItemClickListener;

    public MedecineAdapter(Context context, ArrayList<PrescriptionMedecine> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prescription_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(MessageFormat.format("{0}",
                arrayList.get(position).getFullName())
        );
        holder.field.setText(MessageFormat.format("{0}",
                        arrayList.get(position).getField()
                )
        );
        holder.prescription.setText(MessageFormat.format("{0}",
                        arrayList.get(position).getRecommendation()
                )
        );
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, field, prescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prescriber);
            field = itemView.findViewById(R.id.prescriberField);
            prescription = itemView.findViewById(R.id.prescribedMedecines);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(PrescriptionMedecine prescription);
    }
}