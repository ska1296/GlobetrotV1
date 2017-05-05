package com.example.shibhu.navtry1;

/**
 * Created by Shibhu on 14-Mar-17.
 */
import android.os.AsyncTask;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class BroadcastClient extends AsyncTask<Void, Void, String> {
    static Socket s = null;
    String channelName;
    double lat, lon;

    public void Client(String ChannelName, double latitude, double longitude) {
        lat=latitude;
        lon=longitude;
        channelName=ChannelName;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            if (s == null) {
                s = new Socket("192.168.137.1", 3070);
            }
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());//Creates an ObjectOutputStream that writes to the specified OutputStream
            ObjectOutputStream oos1 = new ObjectOutputStream(s.getOutputStream());
            ObjectOutputStream oos2 = new ObjectOutputStream(s.getOutputStream());

            //System.out.println(channelName + " Channel Name " + lat + " latitude " + lon + " longitude ");

            oos.writeObject(lon);
            oos1.writeObject(lat);
            oos2.writeObject(channelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}