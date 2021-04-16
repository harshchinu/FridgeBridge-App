package com.example.fridge_bridge.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.fridge_bridge.Model.RecipeDetail;
import com.example.fridge_bridge.Model.RecipeModel;
import com.example.fridge_bridge.NetworkCalls.APIClient;
import com.example.fridge_bridge.NetworkCalls.JsonApiCalls;
import com.example.fridge_bridge.R;
import com.example.fridge_bridge.WebView;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recipeModelList.get(position).id!=-1){
                        JsonApiCalls jsonApiCalls = APIClient.getClient().create(JsonApiCalls.class);
                        Call<RecipeDetail> call = jsonApiCalls.getRecipeDetail(recipeModelList.get(position).id,"d226126a736f419a8fdb64eb8491f034");
                        call.enqueue(new Callback<RecipeDetail>() {
                            @Override
                            public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response) {
                                if(response.isSuccessful()){
                                    Intent intent = new Intent(context, WebView.class);
                                    intent.putExtra("url", response.body().getSourceUrl());
                                    context.startActivity(intent);

                                }
                            }

                            @Override
                            public void onFailure(Call<RecipeDetail> call, Throwable t) {

                            }
                        });




                    }
                }
            });
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
