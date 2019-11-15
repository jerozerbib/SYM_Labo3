package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button nfc;
    private Button bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfc = findViewById(R.id.nfc);
        nfc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NFCActivity.class);
            startActivityForResult(intent, 1);
        });

        bluetooth = findViewById(R.id.barcode);
        bluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarCodeActivity.class);
            startActivityForResult(intent, 1);
        });

    }
}
