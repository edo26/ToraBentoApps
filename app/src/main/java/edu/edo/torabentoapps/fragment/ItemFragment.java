package edu.edo.torabentoapps.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.adapter.gridItemAdapter;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static edu.edo.torabentoapps.utilitize.SampleAPI.BASE_URL;

public class ItemFragment extends Fragment {
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    private ProgressDialog pd;
    private List<itemModel> imMakanan = new ArrayList<>();

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
        mAdapter = new gridItemAdapter(imMakanan);
        mRecyler.setAdapter(mAdapter);
        //isi load
        loadMakanan();
        pd.dismiss();
        return view;

    }
    private void loadMakanan(){
        SampleAPI.Factory.getIstance(getActivity()).viewMakanan().enqueue(new Callback<itemRespon>() {
            @Override
            public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                if(response.body().getNilai().equals(1)){
                    imMakanan = response.body().getLiModel();
                    mAdapter = new gridItemAdapter(imMakanan);
                    mRecyler.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<itemRespon> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
    });

    }

}
