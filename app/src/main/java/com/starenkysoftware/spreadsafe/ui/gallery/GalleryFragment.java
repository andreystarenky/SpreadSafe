package com.starenkysoftware.spreadsafe.ui.gallery;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.starenkysoftware.spreadsafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_reports, container, false);


        TextView[] textViews = {
                root.findViewById(R.id.reports_title),
                root.findViewById(R.id.report_name),
                root.findViewById(R.id.report_condition),
                root.findViewById(R.id.reports_id),
                // Card 1
                root.findViewById(R.id.report_label_1),
                root.findViewById(R.id.report_sublabel_1),
                root.findViewById(R.id.report_id_1),
                // Card 2
                root.findViewById(R.id.report_label_2),
                root.findViewById(R.id.report_sublabel_2),
                root.findViewById(R.id.report_id_2),
                // Card 3
                root.findViewById(R.id.report_label_3),
                root.findViewById(R.id.report_sublabel_3),
                root.findViewById(R.id.report_id_3),
                // Card 4
                root.findViewById(R.id.report_label_4),
                root.findViewById(R.id.report_sublabel_4),
                root.findViewById(R.id.report_id_4),
                // Card 5
                root.findViewById(R.id.report_label_5),
                root.findViewById(R.id.report_sublabel_5),
                root.findViewById(R.id.report_id_5),
                // Card 6
                root.findViewById(R.id.report_label_6),
                root.findViewById(R.id.report_sublabel_6),
                root.findViewById(R.id.report_id_6),
                // Card 7
                root.findViewById(R.id.report_label_7),
                root.findViewById(R.id.report_sublabel_7),
                root.findViewById(R.id.report_id_7),
                // Card 8
                root.findViewById(R.id.report_label_8),
                root.findViewById(R.id.report_sublabel_8),
                root.findViewById(R.id.report_id_8),
                // Card 9
                root.findViewById(R.id.report_label_9),
                root.findViewById(R.id.report_sublabel_9),
                root.findViewById(R.id.report_id_9),
                // Card 10
                root.findViewById(R.id.report_label_10),
                root.findViewById(R.id.report_sublabel_10),
                root.findViewById(R.id.report_id_10),
                // Card 11
                root.findViewById(R.id.report_label_11),
                root.findViewById(R.id.report_sublabel_11),
                root.findViewById(R.id.report_id_11),
                // Card 12
                root.findViewById(R.id.report_label_12),
                root.findViewById(R.id.report_sublabel_12),
                root.findViewById(R.id.report_id_12)

        };

        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins_Light.ttf");

        for (TextView t : textViews){
            //Log.d("TV_DEBUG",t.getText().toString());
            t.setTypeface(custom_font1);
        }
        
        TextView[] namesT = {root.findViewById(R.id.report_label_1),root.findViewById(R.id.report_label_2),root.findViewById(R.id.report_label_3),root.findViewById(R.id.report_label_4),root.findViewById(R.id.report_label_5),root.findViewById(R.id.report_label_6),root.findViewById(R.id.report_label_7),root.findViewById(R.id.report_label_8),root.findViewById(R.id.report_label_9),root.findViewById(R.id.report_label_10),root.findViewById(R.id.report_label_11),root.findViewById(R.id.report_label_12)};
        TextView[] conditionsT = {root.findViewById(R.id.report_sublabel_1),root.findViewById(R.id.report_sublabel_2),root.findViewById(R.id.report_sublabel_3),root.findViewById(R.id.report_sublabel_4),root.findViewById(R.id.report_sublabel_5),root.findViewById(R.id.report_sublabel_6),root.findViewById(R.id.report_sublabel_7),root.findViewById(R.id.report_sublabel_8),root.findViewById(R.id.report_sublabel_9),root.findViewById(R.id.report_sublabel_10),root.findViewById(R.id.report_sublabel_11),root.findViewById(R.id.report_sublabel_12)};
        TextView[] idsT = {root.findViewById(R.id.report_id_1),root.findViewById(R.id.report_id_2),root.findViewById(R.id.report_id_3),root.findViewById(R.id.report_id_4),root.findViewById(R.id.report_id_5),root.findViewById(R.id.report_id_6),root.findViewById(R.id.report_id_7),root.findViewById(R.id.report_id_8),root.findViewById(R.id.report_id_9),root.findViewById(R.id.report_id_10),root.findViewById(R.id.report_id_11),root.findViewById(R.id.report_id_12)};


        // FIRESTORE ADD
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /*for(int x = 0; x<names.length; x++) {
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("name", names[x].getText());
            user.put("condition", conditions[x].getText());
            user.put("id", ids[x].getText());

// Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("FB_DEBUG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("FB_DEBUG", "Error adding document", e);
                        }
                    });
        }*/


        // RETRIEVING NAMES, CONDITIONS, AND IDs FROM FIRESTORE
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            final ArrayList<String> names = new ArrayList<String>();
                            final ArrayList<String> conditions = new ArrayList<String>();
                            final ArrayList<String> ids = new ArrayList<String>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FB_DEBUG",document.getData().get("name").toString());
                                names.add(document.getData().get("name").toString());
                                conditions.add(document.getData().get("condition").toString());
                                ids.add(document.getData().get("id").toString());
                            }

                            for (int i = 0; i<names.size();i++){
                                Log.d("FB_DEBUG","NAME: "+names.get(i) + " CONDITION: "+conditions.get(i)+" ID: "+ids.get(i));
                            }


                        } else {
                            Log.w("FB_DEBUG", "Error getting documents.", task.getException());
                        }
                    }
                });

        return root;
    }
}