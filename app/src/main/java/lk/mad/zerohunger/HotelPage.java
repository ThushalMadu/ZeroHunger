package lk.mad.zerohunger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HotelPage extends AppCompatActivity {

    private Button btnbook;
    private TextView txtname;
    DatabaseReference reference;
    private TextView txtviewphoto;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnbook = (Button)findViewById(R.id.btnplace);
        txtname = (TextView)findViewById(R.id.txtname);
        txtviewphoto = (TextView)findViewById(R.id.txtviewphoto);
        Intent i = this.getIntent();

        final String email = i.getExtras().getString("email");



        reference = FirebaseDatabase.getInstance().getReference();
        final Query emailq = FirebaseDatabase.getInstance().getReference("Hotel").orderByChild("email").equalTo(email);


        emailq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    key=ds.getKey();
                    String name = ds.child("name").getValue().toString();
                    txtname.setText(name);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnUpdate();
        txtviewphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ViewImage.class);
                startActivity(i);
            }
        });

    }

    public void btnUpdate(){
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference= FirebaseDatabase.getInstance().getReference();

                reference.child("Hotel").child(key).child("avaliablity").setValue("Avaliable");
                new SweetAlertDialog(HotelPage.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("You Succesfully Added To Qeue")
                        .show();

            }
        });
    }

}
