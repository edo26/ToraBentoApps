package edu.edo.torabentoapps;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Created by anggy on 25/08/2017.
 */

public class loginreseller extends AppCompatActivity {

    BootstrapButton buttonTambah;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        setTitle("Reseller Dashboard");

        buttonTambah = (BootstrapButton)findViewById(R.id.tomboltambah);
        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginreseller.this,dashboardreseller.class));
            }
        });
    }
}
