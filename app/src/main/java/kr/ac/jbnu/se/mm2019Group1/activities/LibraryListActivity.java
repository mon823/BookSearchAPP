package kr.ac.jbnu.se.mm2019Group1.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.ac.jbnu.se.mm2019Group1.R;
import kr.ac.jbnu.se.mm2019Group1.adapters.LibraryAdapter;
import kr.ac.jbnu.se.mm2019Group1.models.Library;
import kr.ac.jbnu.se.mm2019Group1.net.LibraryClient;

public class LibraryListActivity extends AppCompatActivity {
    public static final String LIBRARY_DETAIL_KEY = "book";
    private ListView libraryList;
    private LibraryAdapter libraryAdapter;
    private LibraryClient client;
    private ProgressBar progress;
    private double latitude;
    private double longitude;
    private LocationManager lm;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }



    public void findLocation() {
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            double longitude_ = location.getLongitude();
            double latitude_ = location.getLatitude();
            longitude = longitude_;
            latitude = latitude_;
            Log.d("location","실행행");

            fetchLibrary(" ");
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
            Log.d("location","실패");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        progress = (ProgressBar) findViewById(R.id.progressBar_library);
        progress.setVisibility(ProgressBar.VISIBLE);
        TedPermission.with(LibraryListActivity.this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("현위치를 가져오기 위해서 위치 정보 권한이 필요합니다.")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LibraryListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getIntent().getSerializableExtra(BookDetailActivity.isbn);

//        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//
//        Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//        if(lastKnownLocation != null){
//            double longitude_ = lastKnownLocation.getLongitude();
//            double latitude_ = lastKnownLocation.getLatitude();
//            Log.d("gps","isGPs"+lastKnownLocation.getLongitude());
//            longitude = longitude_;
//            Log.d("gps","isGPs"+longitude);
//            latitude = latitude_;
//
//        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                100,
                1,
                gpsLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                gpsLocationListener);


    }

    @Override
    protected void onStart() {
        super.onStart();


        libraryList = (ListView) findViewById(R.id.libraryList);
        ArrayList<Library> arrLibrary = new ArrayList<Library>();
        libraryAdapter = new LibraryAdapter(this, arrLibrary);
        libraryList.setAdapter(libraryAdapter);

        setupLibrarySelectedListener();
    }

    public void setupLibrarySelectedListener() {
        libraryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(LibraryListActivity.this, BookDetailActivity.class);
                intent.putExtra(LIBRARY_DETAIL_KEY, libraryAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void fetchLibrary(String query) {
        // Show progress bar before making network request
        lm.removeUpdates(gpsLocationListener);
        client = new LibraryClient();
        findLocation();
        String location ="location=" +latitude +","+ longitude  + "&radius=5500&type=library&key=";
        Log.d("library",location);
        client.getLibrary(query,location, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // hide progress bar
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if (response != null) {
                        // Get the docs json array

                        Log.d("librarytag", "result:" + response.toString());
                        docs = response.getJSONArray("results");

                        // Parse json array into array of model objects
                        final ArrayList<Library> libraries = Library.fromJson(docs);
                        // Remove all books from the adapter
                        libraryAdapter.clear();

                        // Load model objects into the adapter
                        for (Library library : libraries) {
                            libraryAdapter.add(library); // add book through the adapter
                        }
                        libraryAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.d("librarytag", "result:" + response.toString(), e);
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("librarytag", "result:" + response.toString(), throwable);

                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_library_list, menu);
        LibraryListActivity.this.setTitle("주변 도서관 정보");
        final MenuItem searchItem = menu.findItem(R.id.action_share_library);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share_library) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(LibraryListActivity.this,"권한 허가",Toast.LENGTH_SHORT).show();
            Log.d("loglog","허가허가");
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(LibraryListActivity.this,"권한 거부\n" + deniedPermissions.toString(),Toast.LENGTH_SHORT).show();
            Log.d("loglog","거부거부");
        }
    };

}
