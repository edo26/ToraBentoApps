package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.edo.torabentoapps.Controller.KonfirmasiPesananAdapter;
import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Model.Transaksi.DataArray;
import edu.edo.torabentoapps.Model.Transaksi.ModelTransaksi;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Edo on 1/7/2018.
 */

public class KonfirmasiPemesananLayout extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager rvL;
    List<DataArray> data;
    KonfirmasiPesananAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_pemesanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Konfirmasi pesanan pembeli.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Konfirmasi Pemesanan");
        recyclerView = (RecyclerView)findViewById(R.id.rvuntukkonfirmasipesanan);
        recyclerView.setHasFixedSize(true);
        rvL = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvL);

        loadTransaksi();
    }

    private void loadTransaksi(){

        SampleAPI.Factory.getIstance(KonfirmasiPemesananLayout.this).viewTransaksi().enqueue(new Callback<ModelTransaksi>() {
            @Override
            public void onResponse(Call<ModelTransaksi> call, Response<ModelTransaksi> response) {
                if(response.isSuccessful()){
                    if(response.body().getNilai().equals(1)){
                        data = response.body().getDataArray();
                        adapter = new KonfirmasiPesananAdapter(data,KonfirmasiPemesananLayout.this);

                        //Silahkan set Adapter
                        recyclerView.setAdapter(adapter);
                    }else{
                        Toast.makeText(KonfirmasiPemesananLayout.this, "Nilai 0", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(KonfirmasiPemesananLayout.this, "Unsuccesfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTransaksi> call, Throwable t) {
                Toast.makeText(KonfirmasiPemesananLayout.this, "onfailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
