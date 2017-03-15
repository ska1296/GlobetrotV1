package com.example.shibhu.navtry1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Shibhu on 25-Feb-17.
 */
public class FirstFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView;
        myView=inflater.inflate(R.layout.start_stop,container,false);

        Button StartButton= (Button) myView.findViewById(R.id.start_service);
        Button StopButton= (Button) myView.findViewById(R.id.stop_service);
        StartButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startButtonClicked(v);
                    }
                }
        );
        StopButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopButtonClicked(v);
                    }
                }
        );

        return myView;
    }

    private void stopButtonClicked(View v) {
        Toast.makeText(getActivity(), "Stop button clicked", Toast.LENGTH_LONG).show();
    }

    private void startButtonClicked(View view) {
        Toast.makeText(getActivity(), "Start button clicked", Toast.LENGTH_LONG).show();
    }
}