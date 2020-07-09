package sg.edu.rp.c346.p08ps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    Spinner spnLocation;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("P08-LocatingAPlace");

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                LatLng poi_Singapore = new LatLng(1.287953, 103.851784);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,11));

                LatLng poi_North = new LatLng(1.461708, 103.813500);
                final Marker north = map.addMarker(new MarkerOptions().position(poi_North).title("HQ - North").snippet("Block 333, Admiralty Ave 3, 765654\nOperating hours: 10am-5pm\nTel:65433456\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                LatLng poi_Central = new LatLng(1.300592, 103.841226);

                final Marker central = map.addMarker(new MarkerOptions().position(poi_Central).title("Central").snippet("Block 3A, Orchard Ave 3, 134542 \nOperating hours: 11am-8pm\nTel:67788652\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                LatLng poi_East = new LatLng(1.350057, 103.939452);

                final Marker east = map.addMarker(new MarkerOptions().position(poi_East).title("East").snippet("Block 555, Tampines Ave 3, 287788 \nOperating hours: 9am-5pm\nTel:66776677\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED){
                    map.setMyLocationEnabled(true);
                }else{
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(north)){
                            north.showInfoWindow();
                            Toast.makeText(MainActivity.this,"HQ - North", Toast.LENGTH_SHORT).show();
                        } else if(marker.equals(central)){
                            central.showInfoWindow();
                            Toast.makeText(MainActivity.this,"Central", Toast.LENGTH_SHORT).show();
                        } else if (marker.equals(east)){
                            east.showInfoWindow();
                            Toast.makeText(MainActivity.this,"East", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                        return true;
                    }
                });

            }
        });

        final String[] location = new String[]{
                "Singapore",
                "North",
                "Central",
                "East"
        };

        spnLocation = findViewById(R.id.spnLocation);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,location);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLocation.setAdapter(aa);

        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnLocation.getSelectedItem().toString().equalsIgnoreCase("Singapore")){
                    LatLng poi_Singapore = new LatLng(1.3421, 103.851784);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Singapore,11));
                } else if (spnLocation.getSelectedItem().toString().equalsIgnoreCase("North")){
                    LatLng poi_North = new LatLng(1.461708, 103.813500);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_North,15));
                } else if (spnLocation.getSelectedItem().toString().equalsIgnoreCase("Central")){
                    LatLng poi_Central = new LatLng(1.300592, 103.841226);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_Central,15));
                } else if (spnLocation.getSelectedItem().toString().equalsIgnoreCase("East")){
                    LatLng poi_East = new LatLng(1.350057, 103.939452);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_East,15));
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
