package com.hugo.firstapp.ui.crud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hugo.firstapp.R;

import java.util.HashMap;
import java.util.Map;

public class CrudFragment extends Fragment {

    View root;
    private FusedLocationProviderClient fusedLocationClient;

    FloatingActionButton send;
    EditText locationName;

    double lat = 0, lng = 0;

    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_crud, container, false);

        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        send = root.findViewById(R.id.btnLocationSend);
        locationName = root.findViewById(R.id.etLocationName);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLocation(locationName.getText().toString(), lat, lng);
            }
        });

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                Toast.makeText(getContext(), "Ubicacion precisa.", Toast.LENGTH_SHORT).show();
                                getLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Toast.makeText(getContext(), "Ubicacion aproximada.", Toast.LENGTH_SHORT).show();
                                getLocation();
                            } else {
                                Toast.makeText(getContext(), "No acepto los permisos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        return root;
    }

    private void sendLocation(String name, double lat, double lng) {
        // Create a new user with a first and last name
        Map<String, Object> locations = new HashMap<>();
        locations.put("name", name);
        locations.put("latitude", lat);
        locations.put("longitude", lng);

// Add a new document with a generated ID
        db.collection("Locations")
        .add(locations)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "" +
                        documentReference.getId(), Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),
                        "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            /*Toast.makeText(getContext(), ""
                                    + location.getLatitude() + ", " + location.getLongitude()
                                    , Toast.LENGTH_SHORT).show();*/
                            lat = location.getLatitude();
                            lng = location.getLongitude();

                        }
                    }
                });
    }

}