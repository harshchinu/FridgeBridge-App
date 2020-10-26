package com.example.fridge_bridge.TabFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fridge_bridge.Adapter.RecyclerViewAdapter;
import com.example.fridge_bridge.Model.DataModel;
import com.example.fridge_bridge.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


public class ItemFragment extends Fragment {
    private List<DataModel> movieList = new ArrayList<>();
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

        progressBar=root.findViewById(R.id.progressBar);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(movieList,getContext(),1);
        recyclerView.setAdapter(mAdapter);


        prepareData();

    }

    private void prepareData() {
        progressBar.setVisibility(View.VISIBLE);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child("random");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    movieList.add(new DataModel(ds.getKey()));

                }

                temp();
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void temp() {

        progressBar.setVisibility(View.VISIBLE);
        for (final DataModel ds : movieList) {
            StorageReference load = FirebaseStorage.getInstance().getReference().child("img").child(ds.getItemname());
            //System.out.println(load.toString());
            load.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                        for(StorageReference item:listResult.getItems()){
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageuri = uri.toString();
                                    ds.setUrl(imageuri);
                                    mAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                            break;
                        }
                }
            });
        }
    }

}