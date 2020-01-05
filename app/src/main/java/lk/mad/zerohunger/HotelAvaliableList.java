package lk.mad.zerohunger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class HotelAvaliableList extends AppCompatActivity {

    private ListView listView;

    DatabaseReference reference;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    HotelInfo hotelInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_avaliable_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent i = this.getIntent();

        hotelInfo = new HotelInfo();

        listView=(ListView)findViewById(R.id.listview);


        reference = FirebaseDatabase.getInstance().getReference();
        final Query name = FirebaseDatabase.getInstance().getReference("Hotel").child("name");

        final Query query = FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("avaliablity").equalTo("Avaliable");

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);






        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(HotelInfo.class).toString();
                list.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View convertView, int position, long id) {




                String name = adapter.getItem(position).toString();
                //String address = adapter.getItem(position).toString();
                //convertView.setBackgroundColor(getColor(R.color. colorPrimaryDark )) ;


                Intent i = new Intent(getApplicationContext(),HotelDirection.class);
                i.putExtra("name",name);

                startActivity(i);

            }

        });




    }
}
