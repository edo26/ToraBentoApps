package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.Controller.HistoryTransaksiAdapter;
import edu.edo.torabentoapps.R;

/**
 * Created by anggy on 02/10/2017.
 */

public class HistoryTransaksiLayout extends AppCompatActivity {

    BootstrapEditText idtransaksi;
    BootstrapButton cari;
    RecyclerView rvTransaksi;
    RecyclerView.LayoutManager rvL;
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
        adapter = new HistoryTransaksiAdapter();
        rvTransaksi.setAdapter(adapter);
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
