package com.runtips.ricardo.runtipsmx.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.runtips.ricardo.runtipsmx.activities.RunActivity;
import com.runtips.ricardo.runtipsmx.classes.Session;
import com.runtips.ricardo.runtipsmx.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeStartFragment extends Fragment {


    private Context thisContext;
    private TextView txtInstructions;
    private SharedPreferences prefs;

    private TextView txtDate;
    private TextView txtVO2;
    private TextView txtHealth;

    public FreeStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free_start, container, false);

        thisContext = container.getContext();
        prefs = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        //btnRun = view.findViewById(R.id.buttonFreeRun);
        txtInstructions = view.findViewById(R.id.txtStartWelcome);
        txtDate = view.findViewById(R.id.txtStartDateResult);
        txtVO2 = view.findViewById(R.id.txtStartVO2Result);
        txtHealth = view.findViewById(R.id.txtStartHealtResult);

        setTexts();

        return view;
    }

    private void setTexts() {
        txtInstructions.setText(getString(R.string.txtStartInitialWelcome, Session.getUserNamePrefs(prefs)));
        txtDate.setText("2019/02/02");
        txtVO2.setText("41.95 ml/kg/min");
        txtHealth.setText("Bueno");
    }

}
