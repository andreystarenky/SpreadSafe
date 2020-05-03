package com.starenkysoftware.spreadsafe.ui.slideshow;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.starenkysoftware.spreadsafe.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_live_view, container, false);



        // SETUP OF FLOORPLAN DROPDOWN
        String[] DropdownOptionsArr = new String[] {"Floor 1", "Floor 2", "Floor 3"};

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        DropdownOptionsArr);

        AutoCompleteTextView floorDropdown =
                root.findViewById(R.id.floor_dropdown);
        floorDropdown.setText("Floor 1");
        floorDropdown.setAdapter(adapter);

        final ImageView imageView = root.findViewById(R.id.floor_plan_image);


        floorDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(adapter.getItem(position));
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.floor_plan_1);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.floor_plan_2);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.floor_plan_3);
                        break;
                }
            }
        });

        TextView liveViewTitle = root.findViewById(R.id.live_view_title);

        Typeface custom_font1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins_Light.ttf");
        liveViewTitle.setTypeface(custom_font1);
        floorDropdown.setTypeface(custom_font1);

        return root;
    }
}