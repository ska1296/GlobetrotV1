package com.example.shibhu.navtry1;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, Broadcast.BroadcastListner {

    int flagBroadcast, flagRecieved;
    String channelNameStringSent, channelNameStringRecieved;
    double oldLatit=0.0,oldLongig=0.0;

    private ViewPager viewPager;
    private IntroManager introManager;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] layouts;
    private TextView dots[];
    private LinearLayout dotsLayout;
    Button next,skip;

    int fl;
    String channelNameString;

    @Override
    public void onButtonService(int flag, String channelName){
        fl=flag;
        channelNameString = channelName;
    }

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        introManager=new IntroManager(this);
        if(!introManager.Check()) {
            introManager.setFirst(false);
            Intent i=new Intent (MainActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        if(Build.VERSION.SDK_INT>=21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }

        setContentView(R.layout.activity_main);

        viewPager=(ViewPager)findViewById(R.id.view_pager);
        dotsLayout=(LinearLayout)findViewById(R.id.layoutDots);
        skip=(Button)findViewById(R.id.btn_skip);
        next=(Button)findViewById(R.id.btn_next);
        layouts=new int[]{R.layout.activity_screen_1,R.layout.activity_screen_2,R.layout.activity_screen_3,R.layout.activity_screen_4};
        addBottomDots(0);
        changeStatusBarColor();
        viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current=getItem(+1);
                if(current<layouts.length) {
                    viewPager.setCurrentItem(current);
                }
                else {
                    Intent i=new Intent (MainActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

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

    MapsActivityListner activutyCommander;

    public interface MapsActivityListner{
        public void sendCoordinated(double lat, double lon);
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
<<<<<<< HEAD
                 mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in MyLocation"));
                 mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));**/

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
                    Marker mrkr;
                    LatLng sydney = new LatLng(latit, longit);
                    mrkr = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in MyLocation"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    if (oldLatit==latit || oldLongig==longit) {
                        mrkr.remove();
                        oldLatit=latit;
                        oldLongig=longit;
                        System.out.println("removed");
                    }
                        System.out.println(latit+" latit");
                        System.out.println(longit + " longit");
                        System.out.println(oldLatit+" old latit");
                        System.out.println(oldLongig + " old longit");
                        System.out.println(oldLatit + " new latit");
                        System.out.println(oldLongig + " new longit");
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        oldLatit=latit;
                    System.out.println("executed");
                }
                System.out.println("abcdefgh " + flagRecieved + " 2345");
                System.out.println("chnnlname=" + channelNameStringRecieved + "flggg=" + flagRecieved);
                if (flagBroadcast == 1) {
                    LatLng broadcastLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    BroadcastClient c = new BroadcastClient();
                    c.Client(channelNameStringSent, location.getLatitude(), location.getLongitude());
                    c.execute();
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(broadcastLocation));
                }


                /**mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in MyLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

                if (fl==1) {
                    BroadcastClient c = new BroadcastClient();
                    c.Client(channelNameString, location.getLatitude(), location.getLongitude());
                    c.execute();
                }
                mMap.moveCamera(center);
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

    private void addBottomDots(int position) {
        dots=new TextView[layouts.length];
        int[] colorActive=getResources().getIntArray(R.array.dot_active);
        int[] colorInactive=getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for (int i=0;i<dots.length;i++) {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&/8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length>0) {
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem (int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if(position==layouts.length-1) {
                next.setText("PROCEED");
                skip.setVisibility(View.GONE);
            }
            else {
                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.LOLLIPOP) {
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        public LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=(View)object;
            container.removeView(v);
        }
    }
}