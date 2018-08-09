package com.runtips.ricardo.runtipsmx.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.runtips.ricardo.runtipsmx.Activities.RunActivity;
import com.runtips.ricardo.runtipsmx.Activities.SpeedActivity;
import com.runtips.ricardo.runtipsmx.Activities.StartActivity;
import com.runtips.ricardo.runtipsmx.Classes.MyAdapter;
import com.runtips.ricardo.runtipsmx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaidStartFragment extends Fragment {

    private ListView listView;
    private Context thisContext;
    private List<String> letterDays, numberDays, principaltrain, secondarytrain;


    public PaidStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_paid_start, container, false);
        listView = (ListView) view.findViewById(R.id.paidStartListview);
        final ViewPager viewPager = view.findViewById(R.id.pager);

        letterDays = new ArrayList<String>();
        letterDays.add("M");
        letterDays.add("T");
        letterDays.add("W");
        letterDays.add("T");
        letterDays.add("F");
        letterDays.add("S");
        letterDays.add("S");

        numberDays = new ArrayList<String>();
        numberDays.add("30");
        numberDays.add("31");
        numberDays.add("1");
        numberDays.add("2");
        numberDays.add("3");
        numberDays.add("4");
        numberDays.add("5");

        principaltrain = new ArrayList<String>();
        principaltrain.add("Descanso");
        principaltrain.add("Fuerza");
        principaltrain.add("Intervalos");
        principaltrain.add("Correr");
        principaltrain.add("Fuerza");
        principaltrain.add("Correr");
        principaltrain.add("Correr");

        secondarytrain = new ArrayList<String>();
        secondarytrain.add("");
        secondarytrain.add("4 series");
        secondarytrain.add("8 x 700 mts");
        secondarytrain.add("10 kms");
        secondarytrain.add("4 series");
        secondarytrain.add("15 km");
        secondarytrain.add("45 mins");

        thisContext = container.getContext();

        MyAdapter adapter = new MyAdapter(thisContext, R.layout.list_item, letterDays, numberDays,
                principaltrain, secondarytrain);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(thisContext, "Clicked: " + principaltrain.get(position), Toast.LENGTH_LONG).show();
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 3:
                        Intent intent = new Intent(thisContext, RunActivity.class);
                        intent.putExtra("position", 1);
                        startActivity(intent);
                        //getActivity().finish();
                        break;
                    case 2:
                        Intent intent2 = new Intent(thisContext, SpeedActivity.class);
                        intent2.putExtra("position", 1);
                        startActivity(intent2);
                        //getActivity().finish();
                        break;

                }
            }
        });



        return view;

    }


}
