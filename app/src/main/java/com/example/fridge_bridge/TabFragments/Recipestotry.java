package com.example.fridge_bridge.TabFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fridge_bridge.Adapter.RecyclerViewAdapter;
import com.example.fridge_bridge.Model.DataModel;
import com.example.fridge_bridge.Model.RecipeList;
import com.example.fridge_bridge.Model.RecipeModel;
import com.example.fridge_bridge.NetworkCalls.APIClient;
import com.example.fridge_bridge.NetworkCalls.JsonApiCalls;
import com.example.fridge_bridge.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Recipestotry extends Fragment {
    private List<RecipeModel> recipestotryList = new ArrayList();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ProgressBar progressBar;
    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_item, container, false);
        initView();
        return root;

    }

    private void initView() {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
         Paper.init(getContext());
        progressBar = root.findViewById(R.id.progressBar);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new RecyclerViewAdapter(null,recipestotryList, getContext(), 3);
//        recyclerView.setAdapter(mAdapter);
        prepareData(50);

    }

    private void prepareData(int number) {


        List<DataModel> dataModel=Paper.book("food").read("foodlist");
        String str="";
        for(int i=0;i<dataModel.size();i++) {
             if(i!=dataModel.size()-1) {
                 str+=dataModel.get(i).getItemname()+",+";
             }
             else {
                 str+=dataModel.get(i).getItemname();
             }
        }
        Log.e(TAG, "prepareData-----"+str );
        JsonApiCalls jsonApiCalls = APIClient.getClient().create(JsonApiCalls.class);
        Call<List<RecipeModel>> call = jsonApiCalls.getRecipe("d226126a736f419a8fdb64eb8491f034",str,number);
        call.enqueue(new Callback<List<RecipeModel>>() {
            @Override
            public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {
                if(response.isSuccessful()){
                   // System.out.println(response.body());
                    Log.e("Recipe----","-------------------"+Thread.currentThread().getName());
                     Log.e("Recipec", "nsasafsafsabfsafsaeffore: "+recipestotryList.size() );
                    recipestotryList=response.body();
                    Log.e("Recipec", "sfsafsafsafsafsafsafsaf: "+recipestotryList.size() );
                      mAdapter = new RecyclerViewAdapter(null,recipestotryList, getContext(), 3);
                      recyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                Log.d("recipe",t.toString());
            }
        });


    }


}