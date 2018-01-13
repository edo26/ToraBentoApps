package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.Model.Keranjang.ModelKeranjangDB;
import edu.edo.torabentoapps.Model.Pembeli.ModelPembeli;
import edu.edo.torabentoapps.Model.Makanan.ModelMakanan;
import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    SampleAPI.Factory.getIstance(BiodataPembeliLayout.this).insertDataPembeli(getNama(), getNomorhp(), getEmail(), getAlamat()).enqueue(new Callback<ModelPembeli>() {
                        @Override
                        public void onResponse(Call<ModelPembeli> call, Response<ModelPembeli> response) {
                            if (response.isSuccessful()) {
                                pd.dismiss();
                                if(response.body().getNilai().equals(1)){
                                    Toast.makeText(BiodataPembeliLayout.this, "Berhasil tambah data pembeli", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(BiodataPembeliLayout.this, "Ada yang salah!", Toast.LENGTH_SHORT).show();
                                }
                                
                            } else {
                                pd.dismiss();
                                Toast.makeText(BiodataPembeliLayout.this, "Error response tidak sukses " + response.errorBody(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelPembeli> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(BiodataPembeliLayout.this, "Error on failure saat input data pembeli : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    pd.show();
                    //Send it to Pembayaran Layout
                    startActivity(new Intent(BiodataPembeliLayout.this, PembayaranLayout.class));
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

    private void insertDataKeranjang(){
            int idpembeli,idmakanan;

    }

    private String getIdPembeli(final Context konteks, String nomorhp){
        SampleAPI.Factory.getIstance((FragmentActivity) konteks).getIdPembeli(nomorhp).enqueue(new Callback<ModelPembeli>() {
            @Override
            public void onResponse(Call<ModelPembeli> call, Response<ModelPembeli> response) {
                if(response.isSuccessful()){
                    if(response.body().getDataArray().size() > 1){
                        Toast.makeText(konteks, "Error ada email yang sama !", Toast.LENGTH_SHORT).show();
                    }else{
                        tampung = response.body().getDataArray().get(0).getIdPembeli();
                    }
                }else{
                    Toast.makeText(konteks, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPembeli> call, Throwable t) {
                Toast.makeText(konteks, "Erro on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return tampung;
    }

    private String getIdMakanan(final Context konteks, String nama_makanan){
        SampleAPI.Factory.getIstance((FragmentActivity) konteks).getIdMakanan(nama_makanan).enqueue(new Callback<ModelMakanan>() {
            @Override
            public void onResponse(Call<ModelMakanan> call, Response<ModelMakanan> response) {
                if(response.isSuccessful()){
                    if(response.body().getLiModel().size() > 1){
                        Toast.makeText(konteks, "Error ada email yang sama !", Toast.LENGTH_SHORT).show();
                    }else{
                        tampung = response.body().getLiModel().get(0).getIdMakanan();
                    }
                }else{
                    Toast.makeText(konteks, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMakanan> call, Throwable t) {
                Toast.makeText(konteks, "Erro on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return tampung;
    }

    private String getIdKeranjang(final Context konteks, String id_pembeli){
        SampleAPI.Factory.getIstance((FragmentActivity) konteks).getIdKeranjang(id_pembeli).enqueue(new Callback<ModelKeranjangDB>() {
            @Override
            public void onResponse(Call<ModelKeranjangDB> call, Response<ModelKeranjangDB> response) {
                if(response.isSuccessful()){
                    if(response.body().getModelKeranjang().size() > 1){
                        Toast.makeText(konteks, "Error ada email yang sama !", Toast.LENGTH_SHORT).show();
                    }else{
                        tampung = response.body().getModelKeranjang().get(0).getIdKeranjang();
                    }
                }else{
                    Toast.makeText(konteks, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelKeranjangDB> call, Throwable t) {
                Toast.makeText(konteks, "Erro on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return tampung;
    }
}
