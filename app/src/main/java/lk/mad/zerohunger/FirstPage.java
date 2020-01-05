package lk.mad.zerohunger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    Button btnhotel,btnorg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnhotel = (Button) findViewById(R.id.btnhotel);
        btnorg = (Button) findViewById(R.id.btnorg);

        btnhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HotelLogin.class);
                startActivity(i);
            }
        });
        btnorg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),DriverLogin.class);
                startActivity(i);
            }
        });


    }
}
