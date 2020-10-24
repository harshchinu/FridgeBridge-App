package com.example.fridge_bridge.TabFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.fridge_bridge.Adapter.RecyclerViewAdapter;
import com.example.fridge_bridge.Model.DataModel;
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

import java.util.ArrayList;
import java.util.List;

public class ToBuy extends Fragment {

    private List<DataModel> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ProgressBar progressBar;


    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_to_buy, container, false);

        initView();
        return root;
    }
    private void initView() {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);

        progressBar=root.findViewById(R.id.progressBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(movieList,getContext(),2);
        recyclerView.setAdapter(mAdapter);


        prepareData();

    }

    private void prepareData() {
        progressBar.setVisibility(View.VISIBLE);
            StorageReference load = FirebaseStorage.getInstance().getReference().child("lastimage.jpg");
            System.out.println(load.toString());
            load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    System.out.println(uri.toString());
                    DataModel dataModel = new DataModel("A");
                    dataModel.setUrl(uri.toString());
                    movieList.add(dataModel);
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });

    }

}