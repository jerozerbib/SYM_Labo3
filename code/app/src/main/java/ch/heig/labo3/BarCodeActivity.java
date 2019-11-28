package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class BarCodeActivity extends AppCompatActivity {

    private Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        scan = findViewById(R.id.scan);
        scan.setOnClickListener(v -> {
//            IntentIntegrator integrator = new IntentIntegrator(BarCodeActivity.this);
        });
    }
}
