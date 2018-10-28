package com.runtips.ricardo.runtipsmx.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.runtips.ricardo.runtipsmx.activities.RunActivity;
import com.runtips.ricardo.runtipsmx.activities.SpeedActivity;
import com.runtips.ricardo.runtipsmx.activities.StrengthActivity;
import com.runtips.ricardo.runtipsmx.classes.MyAdapter;
import com.runtips.ricardo.runtipsmx.classes.Session;
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
    private SharedPreferences prefs;
    private TextView txtInstructions;



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
        numberDays.add("3");
        numberDays.add("4");
        numberDays.add("5");
        numberDays.add("6");
        numberDays.add("7");
        numberDays.add("8");
        numberDays.add("9");

        principaltrain = new ArrayList<String>();
        principaltrain.add("Fuerza");
        principaltrain.add("Intervalos");
        principaltrain.add("Correr");
        principaltrain.add("Fuerza");
        principaltrain.add("Correr");
        principaltrain.add("Intervalos");
        principaltrain.add("Descanso");

        secondarytrain = new ArrayList<String>();
        secondarytrain.add("4 series 15 repeticiones");
        secondarytrain.add("1:30, descanso 1:00, por 40:00");
        secondarytrain.add("45:00");
        secondarytrain.add("4 series 15 repeticiones");
        secondarytrain.add("35:00");
        secondarytrain.add("0:45, descanso 1:00, por 6 km");
        secondarytrain.add("");

        thisContext = container.getContext();
        prefs = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        txtInstructions = view.findViewById(R.id.txtStartWelcome);
        txtInstructions.setText(getString(R.string.txtStartPaidWelcome, Session.getUserNamePrefs(prefs)));


        MyAdapter adapter = new MyAdapter(thisContext, R.layout.list_item, letterDays, numberDays,
                principaltrain, secondarytrain);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(thisContext, "Clicked: " + principaltrain.get(position), Toast.LENGTH_LONG).show();
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(thisContext, StrengthActivity.class);
                        break;
                    case 1:
                        intent = new Intent(thisContext, SpeedActivity.class);
                        break;
                    case 2:
                        intent = new Intent(thisContext, RunActivity.class);
                        break;
                    case 3:
                        intent = new Intent(thisContext, StrengthActivity.class);
                        break;
                    case 4:
                        intent = new Intent(thisContext, RunActivity.class);
                        break;
                    case 5:
                        intent = new Intent(thisContext, SpeedActivity.class);
                        break;
                    case 6:
                        break;
                }
                intent.putExtra("position", 1);
                startActivity(intent);

            }
        });



        return view;

    }


}
