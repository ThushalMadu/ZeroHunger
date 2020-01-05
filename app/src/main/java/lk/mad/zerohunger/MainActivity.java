package lk.mad.zerohunger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText txtname;
    private EditText txtadd;
    private EditText txtava;
    private EditText txtemail;
    private EditText txtpass;
    private Button btnsub;
    long maxid = 0;


    DatabaseReference reference;
    Hotel hotel;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        txtname = (EditText) findViewById(R.id.txtname);
        txtadd = (EditText) findViewById(R.id.txtadd);
        txtava = (EditText) findViewById(R.id.txtava);
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpass = (EditText) findViewById(R.id.txtpass);
        btnsub = (Button) findViewById(R.id.btnsubmit);


        hotel = new Hotel();

        Intent i = this.getIntent();

        reference = FirebaseDatabase.getInstance().getReference().child("Hotel");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotel.setName(txtname.getText().toString());
                hotel.setAddress(txtadd.getText().toString());
                hotel.setAvaliablity(txtava.getText().toString());
                hotel.setEmail(txtemail.getText().toString());
                hotel.setPassword(txtpass.getText().toString());

                reference.child(String.valueOf(maxid + 1)).setValue(hotel);
                //Toast.makeText(MainActivity.this,"Succesfully Registered",Toast.LENGTH_LONG).show();
                createFireBaseAuther();

                Intent i = new Intent(getApplicationContext(), HotelPage.class);
                i.putExtra("email", txtemail.getText().toString());
                startActivity(i);

            }
        });
    }

    public void createFireBaseAuther() {
        fAuth = FirebaseAuth.getInstance();
        fAuth.createUserWithEmailAndPassword(txtemail.getText().toString().trim(), txtpass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
