package lk.mad.zerohunger;

import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;



public class HotelDirection extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment,mapFragment1;
    SearchView searchView;
    TextView txtname,txtlocation,txtava;
    Button btnbook;

    DatabaseReference reference;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_direction);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        txtname = (TextView)findViewById(R.id.txtname);
        txtava = (TextView)findViewById(R.id.txtava);
        txtlocation = (TextView)findViewById(R.id.txtlocation);
        btnbook = (Button)findViewById(R.id.btnbook);

        Intent i = this.getIntent();

        final String name1 = i.getExtras().getString("name");

        txtname.setText(name1);



        reference = FirebaseDatabase.getInstance().getReference();
        final Query name = FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("name").equalTo(name1);

        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    key=ds.getKey();
                    String address = ds.child("address").getValue().toString();

//             String avaliablity = dataSnapshot.child("avaliablity").getValue().toString();
//             txtava.setText(avaliablity);
                    txtlocation.setText(address);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnUpdate();
        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location= searchView.getQuery().toString();
                List<Address> addressList = null;
                if(location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(HotelDirection.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

    }
    public void btnUpdate(){
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference= FirebaseDatabase.getInstance().getReference();
                reference.child("Hotel").child(key).child("avaliablity").setValue("Busy");
                Toast.makeText(getApplicationContext(), " Successfully ordered", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),UploadImage.class);
                startActivity(i);

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;

    }
}
