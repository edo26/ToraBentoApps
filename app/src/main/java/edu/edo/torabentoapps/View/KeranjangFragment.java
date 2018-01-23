package edu.edo.torabentoapps.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.Controller.KeranjangActivity;
import edu.edo.torabentoapps.Controller.KeranjangAdapter;
import edu.edo.torabentoapps.Model.Keranjang.DataArray1;
import edu.edo.torabentoapps.R;

/**
 * Created by anggy on 20/08/2017.
 */

public class KeranjangFragment extends Fragment {

    RecyclerView rvTransaksi;
    RecyclerView.LayoutManager rvL;
    List<DataArray1> databaru = new ArrayList<>();
    KeranjangAdapter adapter;

    public KeranjangFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_transaksi, container, false);
        rvTransaksi = (RecyclerView)v.findViewById(R.id.recyclerviewTransaksi);
        //HalamanAwalLayout.searchView.setEnabled(false);
        BootstrapButton checkout = (BootstrapButton)v.findViewById(R.id.checkoutbutton);
        rvTransaksi.setHasFixedSize(true);
        rvL = new LinearLayoutManager(v.getContext());
        rvTransaksi.setLayoutManager(rvL);
        databaru = new KeranjangActivity().getFavorites(v.getContext());
        if(databaru == null) {
            Toast.makeText(v.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
        }else{
            tambahMakanan(v);
        }
        rvTransaksi.setAdapter(adapter);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(databaru == null) {
                    Toast.makeText(view.getContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(view.getContext(),BiodataPembeliLayout.class));
                }
            }
        });
        return v;
    }

    private void tambahMakanan(View v) {
        adapter = new KeranjangAdapter(databaru, v.getContext());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }
}
