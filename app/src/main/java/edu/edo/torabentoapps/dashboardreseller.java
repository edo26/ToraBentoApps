package edu.edo.torabentoapps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 24/08/2017.
 */

public class dashboardreseller extends AppCompatActivity {

    BootstrapEditText namaMakanan,stok,harga;
    RadioGroup rg;
    RadioButton tersedia,tdktersedia;
    ImageView thumbnails;
    BootstrapButton tambah,edit;
    String status="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Dashboard Reseller.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tambah Makanan");
        bindingData();
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiData();
            }
        });
    }

    private void validasiData(){
        if(getNamaMakanan().equals("") || getStok().equals("") || getHarga().equals("") || !tersedia.isChecked() && !tdktersedia.isChecked()){
            Toast.makeText(this, "Lengkapi dulu data makanan nya.", Toast.LENGTH_SHORT).show();
        }else{
            insertDataMakanan();
        }
    }

    private void insertDataMakanan(){
        SampleAPI tambah = SampleAPI.Factory.getIstance(this);
        tambah.insertMakanan(getNamaMakanan(),getStok(),getHarga(),getStatus()).enqueue(new Callback<itemRespon>() {
            @Override
            public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                if(response.isSuccessful()){
                    Toast.makeText(dashboardreseller.this, "Berhasil menambah "+getNamaMakanan(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(dashboardreseller.this, "unSuccessfully : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<itemRespon> call, Throwable t) {
                Toast.makeText(dashboardreseller.this, "onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindingData(){
        namaMakanan = (BootstrapEditText) findViewById(R.id.namamakanan);
        stok = (BootstrapEditText) findViewById(R.id.stokmakanan);
        harga = (BootstrapEditText) findViewById(R.id.hargamakanan);
        rg = (RadioGroup)findViewById(R.id.radiogrupIni);
        tersedia = (RadioButton)findViewById(R.id.tersedia);
        tdktersedia = (RadioButton)findViewById(R.id.tidaktersedia);
        thumbnails = (ImageView)findViewById(R.id.thumbnailsMakanan);
        tambah = (BootstrapButton)findViewById(R.id.tomboltambah);
        edit = (BootstrapButton)findViewById(R.id.tomboledit);
    }

    public String getNamaMakanan() {
        return namaMakanan.getText().toString();
    }

    public String getStok() {
        return stok.getText().toString();
    }

    public String getHarga() {
        return harga.getText().toString();
    }

    public String getStatus(){

        rg = (RadioGroup) findViewById(R.id.radiogrupIni);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.tersedia) {
                    status = "Tersedia";
                }else {
                    status = "Tidak Tersedia";
                }
            }

        });

        return status;
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
