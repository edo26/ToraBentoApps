package edu.edo.torabentoapps.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.adapter.gridItemAdapter;

public class ItemFragment extends Fragment {
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    private ProgressDialog pd;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setTitle("Pesan");
        pd.setMessage("Sedang mengambil data...");
        pd.setCancelable(false);
        pd.show();
        mRecyler = (RecyclerView)view.findViewById(R.id.recyclerview);
        mRecyler.setHasFixedSize(true);
        mLayout = new GridLayoutManager(getActivity(),2);
        mRecyler.setLayoutManager(mLayout);
        mAdapter = new gridItemAdapter();
        mRecyler.setAdapter(mAdapter);
        pd.dismiss();
        return view;

    }

}
