package edu.edo.torabentoapps.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.MainActivity;
import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.adapter.gridItemAdapter;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    MainActivity main = new MainActivity();
    private List<itemModel> imMakanan = new ArrayList<>();
    //public boolean tesDulu = false;

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mRecyler = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyler.setHasFixedSize(true);
        mLayout = new GridLayoutManager(getActivity(), 2);
        mRecyler.setLayoutManager(mLayout);
        mAdapter = new gridItemAdapter(imMakanan);
        mRecyler.setAdapter(mAdapter);
        //isi load
        loadMakanan();
        return view;

    }

    private void loadMakanan() {
        SampleAPI.Factory.getIstance(getActivity()).viewMakanan().enqueue(new Callback<itemRespon>() {
            @Override
            public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                if (response.body().getNilai().equals(1)) {
                    //Toast.makeText(getActivity(), "onSuccessfully Response", Toast.LENGTH_SHORT).show();
                    imMakanan = response.body().getLiModel();
                    mAdapter = new gridItemAdapter(imMakanan);
                    mRecyler.setAdapter(mAdapter);
                    ((MainActivity)getActivity()).pd.dismiss();
                    ((MainActivity)getActivity()).pd.cancel();
                } else {
                    ((MainActivity)getActivity()).pd.dismiss();
                    Toast.makeText(getActivity(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<itemRespon> call, Throwable t) {
                //Log.d("Error",t.getMessage());
                ((MainActivity)getActivity()).pd.dismiss();
                Toast.makeText(getActivity(), "onFailure : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}
