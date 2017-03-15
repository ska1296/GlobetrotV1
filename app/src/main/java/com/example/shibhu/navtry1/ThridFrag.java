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
public class ThridFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView;
        myView=inflater.inflate(R.layout.chat,container,false);

        Button SendButton= (Button) myView.findViewById(R.id.SendButton);

        SendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );

        return myView;
    }

    private void buttonClicked(View view) {

        Toast.makeText(getActivity(), "Send button clicked", Toast.LENGTH_LONG).show();
    }
}