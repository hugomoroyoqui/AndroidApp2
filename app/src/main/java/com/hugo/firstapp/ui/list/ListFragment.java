package com.hugo.firstapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hugo.firstapp.LocationAdapter;
import com.hugo.firstapp.LocationSchema;
import com.hugo.firstapp.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    View root;
    FirebaseFirestore db;

    ArrayList<LocationSchema> locationArray;
    LocationAdapter locationAdapter;

    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_list, container, false);

        db = FirebaseFirestore.getInstance();
        locationArray = new ArrayList<>();
        listView = root.findViewById(R.id.listview);

        getLocations();

        return root;
    }

    private void getLocations() {
        locationArray.clear();
        db.collection("Locations")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //document.getId()

                        LocationSchema locationSchema = new LocationSchema(
                                document.getId(),
                                document.getString("name"),
                                Double.parseDouble(String.valueOf(document.get("latitude"))),
                                Double.parseDouble(String.valueOf(document.get("longitude")))
                        );

                        locationArray.add(locationSchema);
                    }

                    locationAdapter = new LocationAdapter(getActivity(), locationArray);
                    listView.setAdapter(locationAdapter);
                } else {
                    Toast.makeText(getContext(), "" +
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}