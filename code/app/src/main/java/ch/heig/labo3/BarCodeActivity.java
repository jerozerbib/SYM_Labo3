package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class BarCodeActivity extends AppCompatActivity {
    private Button scan = null;
    private TextView received = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        scan = findViewById(R.id.scan);
        received = findViewById(R.id.barcode_response);

        scan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            intentIntegrator.setPrompt("Bonjour");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setCameraId(0);
            intentIntegrator.initiateScan();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            if (scanResult.getContents() == null){
                Toast.makeText(this, "Received nothing", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned : " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                received.setText(scanResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}
