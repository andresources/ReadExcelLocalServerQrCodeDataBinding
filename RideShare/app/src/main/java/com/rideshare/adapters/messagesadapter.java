package com.rideshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rideshare.R;
import com.rideshare.models.msgs;

import java.util.List;

public class messagesadapter extends RecyclerView.Adapter<messagesadapter.MessagesViewHolder> {

    private final Context context;

    public messagesadapter(List<msgs> msg, String from, Context context){
        this.from=from;
        this.context=context;
        this.msg=msg;

    }
    List<msgs> msg;
    String from;
    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =null;
        if(viewType==1){
            itemView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.messagesview, parent, false);
            return new MessagesViewHolder(itemView);
        }else{
            itemView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msgview, parent, false);
            return new MessagesViewHolder(itemView);
        }

    }

    @Override
    public int getItemViewType(int position) {
       if(msg.get(position).getFrm().equals(from)){
           return 1;
       }
       else{
           return 2;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
msgs m=msg.get(position);

holder.msgfield.setText(m.getMessage());
    }



    @Override
    public int getItemCount() {
        return msg.size();
    }

    class MessagesViewHolder extends  RecyclerView.ViewHolder{
TextView msgfield;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
msgfield=itemView.findViewById(R.id.msgs);
        }

    }


}

