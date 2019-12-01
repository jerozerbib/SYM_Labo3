/*============================================================
 *
 * Name : MainActivity.java
 * Date : 01/12/2019
 * Authors : Lionel Burgbacher, David Jaquet, Jeremy Zerbib
 * Version : 1.0
 * Description : Activity class that allows navigating to the two other activities for this lab
 *
 *===========================================================*/

package ch.heig.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nfc = findViewById(R.id.scan_me_button);
        nfc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NFCActivity.class);
            startActivityForResult(intent, 1);
        });

        Button bluetooth = findViewById(R.id.barcode);
        bluetooth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BarCodeActivity.class);
            startActivityForResult(intent, 1);
        });

    }
}
