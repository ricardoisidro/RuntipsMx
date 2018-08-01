package com.runtips.ricardo.runtipsmx.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
        listView = view.findViewById(R.id.paidStartListview);

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

        /**listView.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void
        });**/

        thisContext = container.getContext();

        MyAdapter adapter = new MyAdapter(thisContext, R.layout.list_item, letterDays, numberDays,
                principaltrain, secondarytrain);
        listView.setAdapter(adapter);

        return view;

    }

}
