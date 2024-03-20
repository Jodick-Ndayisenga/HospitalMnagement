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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {
    Context context;
    ArrayList<UserMessage> arrayList;
    OnItemClickListener onItemClickListener;

    public MessageAdapter(Context context, ArrayList<UserMessage> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(MessageFormat.format("Name: {0}",
                arrayList.get(position).getUserName())
        );
        holder.email.setText(MessageFormat.format("Email: {0}",
                        arrayList.get(position).getUserEmail()
                )
        );
        holder.message.setText(MessageFormat.format("{0}",
                        arrayList.get(position).getUserMessage()
                )
        );
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userNameMessage);
            email = itemView.findViewById(R.id.messageItself);
            message = itemView.findViewById(R.id.messageBox);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(UserMessage message);
    }
}