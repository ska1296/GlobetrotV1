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
import android.widget.Toast;

/**
 * Created by Shibhu on 25-Feb-17.
 */
public class Recieve extends Fragment {

    RecieveListner activityCommander;

    public interface RecieveListner {
        public void onRecieveButtonService(int flag, String channelName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander=(RecieveListner) activity;
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
        myView=inflater.inflate(R.layout.recieve,container,false);

        Button GoButton= (Button) myView.findViewById(R.id.GoButton);
        ChannelNameString = (EditText) myView.findViewById((R.id.channel_name));
        GoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goButtonClicked(v);
                    }
                }
        );
        return myView;
    }

    private void goButtonClicked(View view) {
        System.out.println("STTTART!!!!!!!!!");
        activityCommander.onRecieveButtonService(1, ChannelNameString.getText().toString());
        /**RecieveClient c=new RecieveClient();
        c.RecClient(ChannelNameString.getText().toString());
        c.execute();*/
        Toast.makeText(getActivity(), "Search button clicked", Toast.LENGTH_SHORT).show();
    }
}