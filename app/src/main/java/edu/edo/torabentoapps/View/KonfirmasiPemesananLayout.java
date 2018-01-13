package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import edu.edo.torabentoapps.R;

/**
 * Created by Edo on 1/7/2018.
 */

public class KonfirmasiPemesananLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_pemesanan);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
