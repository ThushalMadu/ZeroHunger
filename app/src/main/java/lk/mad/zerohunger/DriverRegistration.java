package lk.mad.zerohunger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class DriverRegistration extends AppCompatActivity {

    private EditText txtname;
    private EditText txtaddress;
    private EditText txtemail;
    private EditText txtpass;
    private Button btnsub;

    long maxid=0;
    Driver driver;
    FirebaseAuth fAuth;




    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fAuth=FirebaseAuth.getInstance();

        txtname = (EditText)findViewById(R.id.txtname);
        txtaddress = (EditText)findViewById(R.id.txtaddress);
        txtemail = (EditText)findViewById(R.id.txtemail);
        txtpass = (EditText)findViewById(R.id.txtpassword);
        btnsub = (Button)findViewById(R.id.btnsubmit);

        driver = new Driver();
        Intent i = this.getIntent();
        reference = FirebaseDatabase.getInstance().getReference().child("Driver");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver.setName(txtname.getText().toString());
                driver.setAddress(txtaddress.getText().toString());
                driver.setEmail(txtemail.getText().toString());
                driver.setPassword(txtpass.getText().toString());

                reference.child(String.valueOf(maxid+1)).setValue(driver);
                Toast.makeText(DriverRegistration.this,"Succesfully Registered",Toast.LENGTH_LONG).show();
                createFireBaseAuth();

                Intent i = new Intent(getApplicationContext(),HotelAvaliableList.class);
                startActivity(i);


            }
        });

    }

    public void createFireBaseAuth()
    {
        fAuth.createUserWithEmailAndPassword(txtemail.getText().toString().trim(),txtpass.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(DriverRegistration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
