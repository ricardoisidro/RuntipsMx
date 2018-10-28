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

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeStartFragment extends Fragment {


    private Button btnRun;
    private Context thisContext;
    private TextView txtInstructions;
    private SharedPreferences prefs;

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

        btnRun = view.findViewById(R.id.buttonFreeRun);
        txtInstructions = view.findViewById(R.id.txtFreeStartWelcome);
        txtInstructions.setText(getString(R.string.txtStartFreeWelcome, Session.getUserNamePrefs(prefs)));

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisContext, RunActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

}
