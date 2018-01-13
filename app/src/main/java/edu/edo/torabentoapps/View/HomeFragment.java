package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Controller.MakananAdapter;
import edu.edo.torabentoapps.Model.Makanan.itemRespon;
import edu.edo.torabentoapps.Model.Makanan.ModelMakanan;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    //HalamanAwalLayout main = new HalamanAwalLayout();
    private List<itemRespon> imMakanan = new ArrayList<>();
    //public boolean tesDulu = false;

    public HomeFragment() {
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
        mAdapter = new MakananAdapter(imMakanan,getContext());
        mRecyler.setAdapter(mAdapter);
        //isi load
        loadMakanan();
        return view;

    }

    private void loadMakanan() {
        SampleAPI.Factory.getIstance(getActivity()).viewMakanan().enqueue(new Callback<ModelMakanan>() {
            @Override
            public void onResponse(Call<ModelMakanan> call, Response<ModelMakanan> response) {
                //noinspection ConstantConditions
                try{
                    if (response.body().getNilai().equals(1)) {
                        //Toast.makeText(getActivity(), "onSuccessfully Response", Toast.LENGTH_SHORT).show();
                        imMakanan = response.body().getLiModel();
                        mAdapter = new MakananAdapter(imMakanan,getContext());
                        if(mAdapter.getItemCount() == 0){
                            ((HalamanAwalLayout)getActivity()).pd.dismiss();
                            Toast.makeText(getActivity(), "List Kosong", Toast.LENGTH_SHORT).show();
                        }else{
                            ((HalamanAwalLayout)getActivity()).pd.dismiss();
                            mRecyler.setAdapter(mAdapter);
                            //   ((HalamanAwalLayout)getActivity()).pd.dismiss();
//                    ((HalamanAwalLayout)getActivity()).pd.cancel();
                        }
                    } else {
                        ((HalamanAwalLayout)getActivity()).pd.dismiss();
                        Toast.makeText(getActivity(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle("PESAN")
                                .setMessage("PERIKSA LAGI KONEKSI INTERNET ANDA!")
                                .show();
                    }
                }catch (NullPointerException e){
                    //((HalamanAwalLayout)getActivity()).pd.dismiss();
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("PESAN")
//                            .setMessage("PERIKSA LAGI KONEKSI INTERNET ANDA!")
//                            .show();
//                    Toast.makeText(getContext(), "PESAN : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelMakanan> call, Throwable t) {
                //Log.d("Error",t.getMessage());
//                ((HalamanAwalLayout)getActivity()).pd.dismiss();
//                if (t.getMessage().equals("Connection closed by peer")){
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("PESAN")
//                            .setMessage("PERIKSA LAGI KONEKSI INTERNET ANDA!")
//                            .show();
//                }
                Toast.makeText(getActivity(), "onFailure bego : " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


}
