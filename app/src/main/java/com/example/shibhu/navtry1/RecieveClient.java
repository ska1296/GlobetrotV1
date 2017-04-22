package com.example.shibhu.navtry1;

/**
 * Created by Shibhu on 11-Apr-17.
 */

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RecieveClient extends AsyncTask<Void, Void, String> {
    static Socket s = null;
    static String channelName;
    static double lat, lon;

    public void RecieveClient(String ChannelName) {
        channelName=ChannelName;
    }

    @Override
        protected String doInBackground(Void... params) {
        try {
            if (s == null) {
                s = new Socket("192.168.137.1", 3072);
            }
            while(true) {
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(channelName);

                //System.out.println("HELLO "+channelName+" wuddup");
                ObjectInputStream fl=new ObjectInputStream(s.getInputStream());
                //System.out.println("100");
                int flag=Integer.parseInt(fl.readObject().toString());
                //System.out.println("101");
                //System.out.println(flag);
                //System.out.println("102");
                if(flag==1){
                    //System.out.println("103");
                    //System.out.println(flag+" flag1");
                    System.out.println(channelName);

                    ObjectInputStream oisLat=new ObjectInputStream(s.getInputStream());
                    String strLat=oisLat.readObject().toString();
                    ObjectInputStream oisLon=new ObjectInputStream(s.getInputStream());
                    String strLon=oisLat.readObject().toString();

                    lon=Double.parseDouble(strLat);
                    lat=Double.parseDouble(strLon);

                    System.out.println(channelName + " Channel Name " + lat + " latitude " + lon + " longitude ");

                    //setLatitude(lat);
                   //setLongitude(lon);

                    latGetter=getLatitude();
                    lonGetter=getLongitude();
                    System.out.println(lonGetter + " longitude recieveclient()");
                    System.out.println(latGetter + " latitude recievecleint()");
                }
                else if (flag==0) {
                    System.out.println(flag+"flag0");
                    System.out.println(channelName);
                    ObjectInputStream msg=new ObjectInputStream(s.getInputStream());
                    String message=msg.readObject().toString();
                    System.out.println(message);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
            return null;
       }
    double latSetter,lonSetter,latGetter,lonGetter;
    public double setLatitude(double lat) {
        this.latSetter=lat;
        return  latSetter;
    }
    public double setLongitude(double lon) {
        this.lonSetter=lon;
        return  lonSetter;
    }
    public double getLatitude() {
        setLatitude(lon);
        return latSetter;
    }
    public double getLongitude() {
        setLongitude(lat);
        return lonSetter;
    }
}