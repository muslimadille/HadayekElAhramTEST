package com.kobtan.fahmy.hadayekelahram;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Double newString , newStringtwo ;
    private DatabaseReference mCustomerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getDouble("STRING_I_NEED");
            }
        } else {
            newString= (Double) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newStringtwo= null;
            } else {
                newStringtwo= extras.getDouble("STRING_I_NEED2");
            }
        } else {
            newStringtwo= (Double) savedInstanceState.getSerializable("STRING_I_NEED2");
        }
        //Toast.makeText(this, newString +  " - " + newStringtwo, Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                // Add a marker in Sydney and move the camera
                if (newString!=null && newStringtwo!=null) {

                    if (newString.equals(Double.valueOf(01)) && newStringtwo.equals(Double.valueOf(01))){
                        Toast.makeText(MapsActivity.this, "مقر الشركة" , Toast.LENGTH_LONG).show();
                        LatLng sydney = new LatLng(29.9818735, 31.1083652);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("مقر الشركة"));
                        mMap.setBuildingsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
                    }else if (newStringtwo.equals(Double.valueOf(0)) && newStringtwo.equals(Double.valueOf(0))) {
                        LatLng sydney = new LatLng(29.9818735, 31.1083652);
                        Toast.makeText(MapsActivity.this, "جاري التجهيز" , Toast.LENGTH_LONG).show();
                        mMap.addMarker(new MarkerOptions().position(sydney).title("جاري الادخال"));
                        mMap.setBuildingsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 15.0f));
                    }
                    else {
                        LatLng sydney = new LatLng(newString, newStringtwo);
                        //Toast.makeText(MapsActivity.this, newString + " - " + newStringtwo , Toast.LENGTH_LONG).show();
                        mMap.addMarker(new MarkerOptions().position(sydney).title("موقع المكان"));
                        mMap.setBuildingsEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney , 15.0f));
                    }


                }
                else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    LatLng sydney = new LatLng(29.9818735, 31.1083652);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("جاري الادخال"));
                    mMap.setBuildingsEnabled(true);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
                }

            }








}
