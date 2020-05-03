package com.starenkysoftware.spreadsafe.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.starenkysoftware.spreadsafe.MainActivity;
import com.starenkysoftware.spreadsafe.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView[] textViews = {root.findViewById(R.id.home_recent_cases),
                root.findViewById(R.id.home_total_cases),
                root.findViewById(R.id.home_new_cases),
                root.findViewById(R.id.home_total_cases_value),
                root.findViewById(R.id.home_new_cases_value),
                root.findViewById(R.id.home_last_5_days),
                root.findViewById(R.id.label1),
                root.findViewById(R.id.label2),
                        root.findViewById(R.id.label3),
                        root.findViewById(R.id.sublabel1),
                        root.findViewById(R.id.sublabel2),
                        root.findViewById(R.id.sublabel3)};

        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins_Light.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins_Thin.ttf");

        for (TextView t : textViews){
            t.setTypeface(custom_font1);
        }

        ((TextView)root.findViewById(R.id.home_total_cases_value)).setTypeface(custom_font2);
        ((TextView)root.findViewById(R.id.home_new_cases_value)).setTypeface(custom_font2);


        // GRAPH SETUP
        final GraphView graph = (GraphView) root.findViewById(R.id.home_graph);
        graph.setVisibility(View.VISIBLE);

        try {
            LineGraphSeries <DataPoint> series = new LineGraphSeries< >(new DataPoint[] {
                    new DataPoint(1, 90),
                    new DataPoint(2, 100),
                    new DataPoint(3, 105),
                    new DataPoint(4, 123),
                    new DataPoint(5, 136),
            });
            graph.addSeries(series);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return root;
    }
}