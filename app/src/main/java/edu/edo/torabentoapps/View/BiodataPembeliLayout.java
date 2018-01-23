package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.R;

/**
 * Created by Edo on 1/3/2018.
 */

public class BiodataPembeliLayout extends AppCompatActivity {

    BootstrapEditText nama,alamat,email,nomorhp;
    BootstrapButton tombolcheckout;
    ProgressDialog pd;
    String tampung;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biodatapembeli);
        setTitle("Mengisi Biodata Pembeli");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Biodata pembeli untuk transaksi pembayaran.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Mengisi Biodata Pembeli");
        pd = new ProgressDialog(BiodataPembeliLayout.this);
        pd.setMessage("Silahkan tunggu...");
        bindButton();
        tombolcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                //Validation
                if(getNama().equals("") || getNomorhp().equals("") || getAlamat().equals("") || getEmail().equals("")){
                    pd.dismiss();
                    Toast.makeText(BiodataPembeliLayout.this, "Isi datanya terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else {

                    //Do action
                    //insertDataPembeli();

                    pd.show();
//                    String id_pembeli,id_makanan,qty;
////                    List<DataArray1> databaru = new ArrayList<>();
////                    databaru = new KeranjangActivity().getFavorites(BiodataPembeliLayout.this);
//                    id_pembeli = getIdPembeli(BiodataPembeliLayout.this,getNomorhp());

                    //Send it to Pembayaran Layout
                    Intent i = new Intent(BiodataPembeliLayout.this, PembayaranLayout.class);
                    //i.putExtra("id_pembeli",id_pembeli);
                    i.putExtra("nama",getNama());
                    i.putExtra("nomorhp",getNomorhp());
                    i.putExtra("email",getEmail());
                    i.putExtra("alamat",getAlamat());
                    startActivity(i);
                    pd.dismiss();
                }
            }
        });
    }



    private void bindButton(){
        nama = (BootstrapEditText) findViewById(R.id.namapembeli);
        alamat = (BootstrapEditText) findViewById(R.id.alamatpembeli);
        email = (BootstrapEditText) findViewById(R.id.emailpembeli);
        nomorhp = (BootstrapEditText) findViewById(R.id.nomortelponpembeli);
        tombolcheckout = (BootstrapButton)findViewById(R.id.tombolselesai);
    }

    public String getNama() {
        return nama.getText().toString();
    }

    public String getAlamat() {
        return alamat.getText().toString();
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getNomorhp() {
        return nomorhp.getText().toString();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
