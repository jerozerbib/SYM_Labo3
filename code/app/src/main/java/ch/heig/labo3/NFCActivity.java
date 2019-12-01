/*============================================================
 *
 * Name : BarCodeActivity.java
 * Date : 01/12/2019
 * Authors : Lionel Burgbacher, David Jaquet, Jeremy Zerbib
 * Version : 1.0
 * Description : Activity class that allows scanning a NFC tag
 *
 *===========================================================*/

package ch.heig.labo3;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;

public class NFCActivity extends NFC {

    private Button connec;
    private EditText username;
    private EditText password;
    private TextView flagNfc;

    //seconds
    private final int MAX_TIME = 10;
    private String nfcResult;

    //Only for labo, really bad thing to do.
    private static final String USERNAME = "lio";
    private static final String PASSWORD = "lio";
    private static final String NFC_SECRET = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        flagNfc = findViewById(R.id.nfc_status);
        connec = findViewById(R.id.nfc_button);
        username = findViewById(R.id.username_nfc);
        password = findViewById(R.id.password_nfc);

        timestamp = Instant.now().getEpochSecond();

        connec.setOnClickListener(v -> {

            String usernameS = username.getText().toString();
            String passwordS = password.getText().toString();

            if(Instant.now().getEpochSecond() - timestamp > MAX_TIME){
                Toast.makeText(this, R.string.noNfc, Toast.LENGTH_LONG).show();
                return;
            }

            if(!login(usernameS, passwordS, nfcResult)){
                Toast.makeText(this, R.string.wrongCreds, Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(NFCActivity.this, NFCSecurityActivity.class);
            startActivity(intent);
        });

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, R.string.nfcDoesntExist, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, R.string.nfcNotEnable, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        handleIntent(getIntent());
    }

    @Override
    void onPostExecuteOverride(String result){
        timestamp = Instant.now().getEpochSecond();
        nfcResult = result;
        flagNfc.setText(R.string.nfcIsUp);
        flagNfc.setTextColor(Color.GREEN);
        startTimer();
    }

    /**
     * Check if data are correct
     *
     * @param username  username
     * @param password  password
     * @param result    nfcresult
     * @return true if all data are correct
     */
    private boolean login(String username, String password, String result){

        return USERNAME.equals(username) && PASSWORD.equals(password) && NFC_SECRET.equals(result);
    }

    /**
     * Change text and color after MAX_TIME and clean nfc data.
     */
    private void startTimer(){
        CountDownTimer counter = new CountDownTimer(MAX_TIME*1000, 1000){
            public void onTick(long millisUntilDone){}
            public void onFinish() {
                nfcResult = "";
                flagNfc.setText(R.string.nfcIsDown);
                flagNfc.setTextColor(Color.RED);
            }
        }.start();
    }
}