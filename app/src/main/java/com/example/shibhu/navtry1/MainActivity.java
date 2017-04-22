package com.example.shibhu.navtry1;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, Broadcast.BroadcastListner, Recieve.RecieveListner {

    int flagBroadcast, flagRecieved;
    String channelNameStringSent, channelNameStringRecieved;

    @Override
    public void onButtonService(int flag, String channelName){
        flagBroadcast = flag;
        channelNameStringSent = channelName;
    }

    @Override
    public void onRecieveButtonService(int flag, String channelName) {
        flagRecieved =flag;
        channelNameStringRecieved =channelName;
    }

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    MapsActivityListner activityCommander;

    public interface MapsActivityListner{
        public void sendCoordinates(double lat, double lon);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));

                //System.out.println(center + " center " + location.getLatitude() + " latitude " + location.getLongitude() + " longitude ");

                /**LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                 mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in MyLocation"));
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));**/
                System.out.println("abcdefgh " + flagRecieved + " 2345");
                System.out.println("chnnlname=" + channelNameStringRecieved + "flggg=" + flagRecieved);
                if (flagBroadcast == 1) {
                    LatLng broadcastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    BroadcastClient c = new BroadcastClient();
                    c.BroadClient(channelNameStringSent, location.getLatitude(), location.getLongitude());
                    c.execute();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(broadcastLocation));
                }

                if(flagRecieved ==1) {
                    System.out.println("abcdefgh" + flagRecieved + "eatshit");
                    RecieveClient rc=new RecieveClient();
                    rc.RecieveClient(channelNameStringRecieved);
                    System.out.println(channelNameStringRecieved);
                    rc.execute();
                    double latit=rc.getLatitude();
                    double longit=rc.getLongitude();
                    System.out.println(latit+" latit");
                    System.out.println(longit + " longit");

                    LatLng sydney = new LatLng(latit, longit);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in MyLocation"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                    System.out.println("executed");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getFragmentManager();

        if (id == R.id.broadcast) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                    , new Broadcast()).commit();

        } else if (id == R.id.recieve) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Recieve()).commit();

        } else if (id == R.id.nav_three) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new ThridFrag()).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}