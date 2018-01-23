package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.List;

import edu.edo.torabentoapps.Controller.HistoryTransaksiAdapter;
import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Model.Transaksi.DataArray;
import edu.edo.torabentoapps.Model.Transaksi.ModelTransaksi;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 02/10/2017.
 */

public class HistoryTransaksiLayout extends AppCompatActivity {

    BootstrapEditText idtransaksi;
    BootstrapButton cari;
    RecyclerView rvTransaksi;
    RecyclerView.LayoutManager rvL;
    List<DataArray> data;
    HistoryTransaksiAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historitransaksi);
        idtransaksi = (BootstrapEditText)findViewById(R.id.cariidtransaksi);
        cari = (BootstrapButton)findViewById(R.id.caritombol);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Cek status pemesanan makanan anda.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Riwayat Transaksi");

        rvTransaksi = (RecyclerView)findViewById(R.id.recyclerviewCaritransaksi);
        rvTransaksi.setHasFixedSize(true);
        rvL = new LinearLayoutManager(this);
        rvTransaksi.setLayoutManager(rvL);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idtransaksi.getText().toString().equals("")){
                    Toast.makeText(HistoryTransaksiLayout.this, "Tolong isi ID Transaksi", Toast.LENGTH_SHORT).show();
                }else{
                    loadHistory(idtransaksi.getText().toString());
                }
            }
        });
    }

    private void loadHistory(String idtransaksi){
        SampleAPI.Factory.getIstance(HistoryTransaksiLayout.this).viewHistory(idtransaksi).enqueue(new Callback<ModelTransaksi>() {
            @Override
            public void onResponse(Call<ModelTransaksi> call, Response<ModelTransaksi> response) {
                if(response.isSuccessful()){
                    if(response.body().getNilai().equals(1)){
                        data = response.body().getDataArray();
                        adapter = new HistoryTransaksiAdapter(data,HistoryTransaksiLayout.this);

                        //Silahkan set Adapter
                        rvTransaksi.setAdapter(adapter);
                    }else{
                        Toast.makeText(HistoryTransaksiLayout.this, "Nilai 0", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(HistoryTransaksiLayout.this, "Unsuccesfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelTransaksi> call, Throwable t) {
                Toast.makeText(HistoryTransaksiLayout.this, "onfailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
