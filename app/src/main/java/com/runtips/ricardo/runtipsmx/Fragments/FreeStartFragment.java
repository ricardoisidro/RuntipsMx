package com.runtips.ricardo.runtipsmx.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.runtips.ricardo.runtipsmx.Activities.RunActivity;
import com.runtips.ricardo.runtipsmx.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeStartFragment extends Fragment {


    private Button btnRun;
    private Context thisContext;

    public FreeStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free_start, container, false);

        thisContext = container.getContext();

        btnRun = view.findViewById(R.id.buttonFreeRun);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisContext, RunActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        return view;
    }

}
