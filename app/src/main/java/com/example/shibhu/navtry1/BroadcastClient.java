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

    public void BroadClient(String ChannelName, double latitude, double longitude) {
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
            ObjectOutputStream lonOOS = new ObjectOutputStream(s.getOutputStream());//Creates an ObjectOutputStream that writes to the specified OutputStream
            ObjectOutputStream latOOS = new ObjectOutputStream(s.getOutputStream());
            ObjectOutputStream chnameOOS = new ObjectOutputStream(s.getOutputStream());

            lonOOS.writeObject(lon);
            latOOS.writeObject(lat);
            chnameOOS.writeObject(channelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}