package com.example.shibhu.navtry1;

/**
 * Created by Shibhu on 14-Mar-17.
 */
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdate;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AsyncTask<Void, Void, String> {
    static Socket s = null;
    CameraUpdate cen;
    double lat, lon;

    public Client(CameraUpdate center, double latitude, double longitude) {
        cen=center;
        lat=latitude;
        lon=longitude;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            if (s == null) {
                System.out.println("if block running");
                s = new Socket("192.168.1.5", 3070);
            }
            //response.setText("HELLOOOOOO1234!!");
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());//Creates an ObjectOutputStream that writes to the specified OutputStream
            ObjectOutputStream oos1 = new ObjectOutputStream(s.getOutputStream());
            //response.setText("HELLO1!!");
            System.out.println(cen + " center " + lat + " latitude " + lon + " longitude ");
            //oos.writeObject(cen);//sends message to sever read above-
            oos.writeObject(lon);
            oos1.writeObject(lat);

            //response.setText("HELLO!!2");
            //editTextPort.setText("HELLOoo!!");
            //l.add("Me:-"+t.getText()); //to put up my messages anywhere
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}