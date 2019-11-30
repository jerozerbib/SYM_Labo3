package ch.heig.labo3;

import androidx.appcompat.app.AlertDialog;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.time.Instant;

public class NFCSecurityActivity extends NFC {

    private Button maxSecurity;
    private Button mediumSecurity;
    private Button minSecurity;

    public static final String PERMISSION_MAX_OK = "You still have max permission for ";
    public static final String PERMISSION_MEDIUM_OK = "You still have medium permission for ";
    public static final String PERMISSION_MIN_OK = "You still have minimum permission for ";
    public static final String PERMISSION_NOT_OK = "You don't have permission. If you want permission again, use the nfc.";

    //seconds
    private final int MAX_LEVEL = 10;
    private final int MEDIUM_LEVEL = 20;
    private final int MIN_LEVEL = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcsecurity);

        maxSecurity = findViewById(R.id.nfc_button_sec_1);
        mediumSecurity = findViewById(R.id.nfc_button_sec_2);
        minSecurity = findViewById(R.id.nfc_button_sec_3);

        timestamp = Instant.now().getEpochSecond();

        maxSecurity.setOnClickListener(v -> {
            message(PERMISSION_MAX_OK, MAX_LEVEL);
        });

        mediumSecurity.setOnClickListener(v ->{
            message(PERMISSION_MEDIUM_OK, MEDIUM_LEVEL);
        });

        minSecurity.setOnClickListener(v -> {
            message(PERMISSION_MIN_OK, MIN_LEVEL);
        });

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, R.string.nfc_doesnt_exist, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(this, R.string.nfc_not_enable, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        handleIntent(getIntent());
    }

    /**
     * Diplay a message with a security level
     * @param message message to display
     * @param lvl     security level
     */
    private void message(String message, int lvl){

        if(Instant.now().getEpochSecond() - timestamp <  lvl){
            alert(message + (lvl - (Instant.now().getEpochSecond() - timestamp)) + " seconds.");
        }
        else{
            alert(PERMISSION_NOT_OK);
        }
    }

    /**
     * Pop-up with a message
     * @param message message
     */
    private void alert(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(NFCSecurityActivity.this).create();
        alertDialog.setTitle("NFC");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    protected void onPostExecuteOverride(String result){
        if(result != null) {
            timestamp = Instant.now().getEpochSecond();
            Toast.makeText(this, R.string.full_perm, Toast.LENGTH_LONG).show();
        }
    }
}