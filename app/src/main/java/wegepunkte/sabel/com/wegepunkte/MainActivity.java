package wegepunkte.sabel.com.wegepunkte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private Button btnShow;
    private Button btnRoute;
    private WegepunkteRepo wegepunkte;
    private LocationManager locationManager;
    private boolean isGPSEnabled;

    @Override
    protected void onStart() {
        super.onStart();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Berechtigung fehlt", Toast.LENGTH_SHORT).show();
            }
            requestPermission();
        } else {
            saveOK();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initComponents();
        initEvents();
        wegepunkte = new WegepunkteRepo();
        isGPSEnabled = false;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 4444 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveOK();
        }
    }

    private void requestPermission() {
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
        requestPermissions(permissions, 4444);
    }


    private void initEvents() {

        btnRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zeigeRoute();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d("***", wegepunkte.toString());
                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                intent.putExtra("WEGEPUNKTE", wegepunkte);
                startActivity(intent);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });
                }
                Log.d("wp", "######################################################################");
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    Wegepunkt wegepunkt = new Wegepunkt(new Date(), location.getLatitude(), location.getLongitude());
                    wegepunkte.addWegepunkt(wegepunkt);
                    Log.d("wp", wegepunkt.toString());
                    //Toast.makeText(MainActivity.this,wegepunkt.toString(),  Toast.LENGTH_LONG).show();
                }
                Log.d("wp", "######################################################################");
            }
        });
    }

    private void zeigeRoute() {

        if (wegepunkte.size() > 0) {


            Wegepunkt start = wegepunkte.getWegepunkt(0);
            Wegepunkt stop = wegepunkte.getWegepunkt(wegepunkte.size() - 1);
            double latStart = start.getLat();
            double lonStart = start.getLon();
            double latStop = stop.getLat();
            double lonStop = stop.getLon();

            Intent bwoserInten = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/dir/?api=1&origin=" + latStart + "," + lonStart + "&destination=" + latStop + "," + lonStop));

            startActivity(bwoserInten);
        }

    }

    private void initLocManager() {
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void initComponents() {
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setEnabled(false);
        btnShow = findViewById(R.id.btnShow);
        btnRoute = findViewById(R.id.btnRoute);
    }

    private void saveOK() {
        initLocManager();
        btnAdd.setEnabled(isGPSEnabled);
    }

}
