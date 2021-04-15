package com.example.fridge_bridge.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.example.fridge_bridge.Model.DataModel;
import com.example.fridge_bridge.Model.RecipeModel;
import com.example.fridge_bridge.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<DataModel> itemList;
    private List<RecipeModel> recipeModelList;
    private Context context;
    private int type;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.itemName);
            imageView = v.findViewById(R.id.imageView);
        }
    }

    public RecyclerViewAdapter(List<DataModel> myDataset,List<RecipeModel> recipeModels,Context c,int t) {
        context=c;
        type=t;
        if(type==1) {
            itemList = myDataset;
        }
        else if(type==3) {
            recipeModelList=recipeModels;
        }else {
            itemList=myDataset;
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        if(type==1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerviewsampleview, parent, false);
            return new MyViewHolder(itemView);
        }else if(type==2){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fridgeview, parent, false);
            return new MyViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipeview, parent, false);
            return new MyViewHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        if(type==1) {
            holder.textView.setText(itemList.get(position).getItemname());
            Picasso.get().load(itemList.get(position).getUrl()).placeholder(circularProgressDrawable).into(holder.imageView);
        }else if(type==2) {
            Picasso.get().load(itemList.get(position).getUrl()).placeholder(circularProgressDrawable).into(holder.imageView);
        }else if(type==3){
            holder.textView.setText(recipeModelList.get(position).title);
            Picasso.get().load(recipeModelList.get(position).image).placeholder(circularProgressDrawable).into(holder.imageView);
        }
    }



    @Override
    public int getItemCount() {
       if(type==3)
           return recipeModelList.size();
       else
           return itemList.size();
    }
}
