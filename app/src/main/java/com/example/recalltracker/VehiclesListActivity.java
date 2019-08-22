package com.example.recalltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recalltracker.Utils.DatabaseAPI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class VehiclesListActivity extends AppCompatActivity {

    private static final String TAG = "VehiclesListActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button addVehicleBtn;

    private String userId;
    private DatabaseAPI databaseAPI;

    ArrayList vehicleItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_list);

        addVehicleBtn = findViewById(R.id.btn_add_car);
        addVehicleBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Go to Search activity
                goToSearchActivity();
            }
        });

//        VehicleItem vehicleItem = new VehicleItem();
//
//        String carName = "2017 TOYOTA RAV4 HV";
//        String carVIN = "JTMDJREV6HD120994";
//
//        vehicleItem.setFullName(carName);
//        vehicleItem.setCarVIN(carVIN);
//
//        VehicleItem vehicleItem1 = new VehicleItem();
//
//        carName = "2018 NISSAN ROGUE SPORT";
//        carVIN = "JN1BJ1CRXJW288164";
//
//        vehicleItem1.setFullName(carName);
//        vehicleItem1.setCarVIN(carVIN);
//
//        vehicleItemList.add(vehicleItem);
//        vehicleItemList.add(vehicleItem1);

        recyclerView = findViewById(R.id.vins_rv);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        userId = getIntent().getStringExtra("USER_ID");
        databaseAPI = new DatabaseAPI(userId);

        databaseAPI.getUser(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    mAdapter = new VehiclesListAdapter(vehicleItemList, VehiclesListActivity.this);
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });

    }

    private void goToSearchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
