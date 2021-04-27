package com.example.ratemybeer;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData;


    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @Override
    public CommentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_comment,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder( CommentViewHolder holder, int position) {

        FirebaseUser firebaseUser;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Comment currentComment =  mData.get(position);

        //Glide.with(mContext).load(mData.get(position).getUimg()).into(holder.img_user);
        holder.tv_name.setText(currentComment.getUname()+":");
        holder.tv_content.setText(currentComment.getContent());
        holder.tv_date.setText(timestampToString((String.valueOf(currentComment.getTimestamp()))));

        String currentBeer = currentComment.getBeername();
        String idComment = currentComment.getId();

        if(!firebaseUser.getUid().equals(currentComment.getOwner())){
            holder.tv_del.setVisibility(View.INVISIBLE);
        }
        else {
            holder.tv_del.setVisibility(View.VISIBLE);
        }

        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("Comment").child(currentBeer).child(idComment).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView img_user;
        TextView tv_name,tv_content,tv_date;
        ImageButton tv_del;

        public CommentViewHolder(View itemView) {
            super(itemView);
            //img_user = itemView.findViewById(R.id.comment_user_img);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_date = itemView.findViewById(R.id.comment_date);
            tv_del = itemView.findViewById(R.id.delb);
        }
    }



    private String timestampToString(String time) {

        Long Longtime = Long.parseLong(time);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Longtime);
        String date = DateFormat.format("dd/MM/yyyy 'à' HH:mm",calendar).toString();
        return date;
    }
}
