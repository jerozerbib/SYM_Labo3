package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class NFCActivity extends AppCompatActivity {


    private Button connec;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        connec = findViewById(R.id.nfc_button);
        username = findViewById(R.id.username_nfc);
        password = findViewById(R.id.password_nfc);

    }
}
