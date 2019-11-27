package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class NFCSecondActivity extends AppCompatActivity {

    private Button maxSecurity;
    private Button mediumSecurity;
    private Button minSecurity;


    private final int MAX_LEVEL = 30;
    private final int MEDIUM_LEVEL = 15;
    private final int MIN_LEVEL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcsecond);

        maxSecurity = findViewById(R.id.nfc_button_sec_1);
        mediumSecurity = findViewById(R.id.nfc_button_sec_2);
        minSecurity = findViewById(R.id.nfc_button_sec_3);

        maxSecurity.setOnClickListener(v -> {
            display(MAX_LEVEL, 29);
        });

        mediumSecurity.setOnClickListener(v ->{
            display(MEDIUM_LEVEL, 26);
        });

        minSecurity.setOnClickListener(v -> {
            display(MIN_LEVEL, 0);
        });
    }

    private void display(int min, int current){

        if(current > min) {
            Toast.makeText(this, "Nice", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Nope", Toast.LENGTH_LONG).show();
        }
    }
}
