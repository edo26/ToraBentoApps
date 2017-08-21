package edu.edo.torabentoapps.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.Model.transaksiModel;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.adapter.transaksiItemAdapter;

/**
 * Created by anggy on 20/08/2017.
 */

public class TransaksiFragment extends Fragment {

    RecyclerView rvTransaksi;
    RecyclerView.LayoutManager rvL;
    transaksiItemAdapter adapter;

    public TransaksiFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_transaksi, container, false);
        rvTransaksi = (RecyclerView)v.findViewById(R.id.recyclerviewTransaksi);
        rvTransaksi.setHasFixedSize(true);
        rvL = new LinearLayoutManager(getActivity());
        rvTransaksi.setLayoutManager(rvL);
        adapter = new transaksiItemAdapter();
        rvTransaksi.setAdapter(adapter);
        return v;
    }
}
