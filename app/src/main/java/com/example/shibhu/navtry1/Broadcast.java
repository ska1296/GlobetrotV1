package com.example.shibhu.navtry1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shibhu on 25-Feb-17.
 */
public class Broadcast extends Fragment {


    BroadcastListner activityCommander;

    public interface BroadcastListner {
        public void onButtonService(int flag, String channelName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander=(BroadcastListner) activity;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    EditText ChannelNameString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView;
        myView=inflater.inflate(R.layout.broadcast,container,false);

        Button StartButton= (Button) myView.findViewById(R.id.start_service);
        Button StopButton= (Button) myView.findViewById(R.id.stop_service);
        ChannelNameString = (EditText) myView.findViewById((R.id.channel));

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
        Toast.makeText(getActivity(), "Stop button clicked", Toast.LENGTH_SHORT).show();
        ChannelNameString.setText("", TextView.BufferType.EDITABLE);
        activityCommander.onButtonService(0, "");
    }

    private void startButtonClicked(View view) {
        Toast.makeText(getActivity(), "Start button clicked", Toast.LENGTH_SHORT).show();
        activityCommander.onButtonService(1, ChannelNameString.getText().toString());
    }
}