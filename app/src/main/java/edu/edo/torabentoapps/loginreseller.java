package edu.edo.torabentoapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.Model.DataArray;
import edu.edo.torabentoapps.Model.itemModel;
import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.adapter.gridItemAdapter2;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 25/08/2017.
 */

public class loginreseller extends AppCompatActivity {

    BootstrapButton buttonTambah,buttonLogout;
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    //public int posisi=0;
    //MainActivity main = new MainActivity();
    private List<itemModel> imMakanan = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        setTitle("Reseller Dashboard");

        mRecyler = (RecyclerView) findViewById(R.id.recyclerviewuntukreseller);
        mRecyler.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mRecyler.setLayoutManager(mLayout);
        mAdapter = new gridItemAdapter2(imMakanan,this);
        mRecyler.setAdapter(mAdapter);
        loadMakanan();

        buttonTambah = (BootstrapButton)findViewById(R.id.tomboltambah);
        buttonLogout = (BootstrapButton)findViewById(R.id.tombollogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor sp = getSharedPreferences("Nama Reseller",MODE_PRIVATE).edit();
                sp.clear();
                sp.commit();
                startActivity(new Intent(loginreseller.this, MainActivity.class));
                finish();
                //Toast.makeText(loginreseller.this, "Anjing", Toast.LENGTH_SHORT).show();

            }
        });

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginreseller.this, dashboardreseller.class);
                i.putExtra("ModeTambah","telahdiklik");
                startActivity(i);
            }
        });

    }

    private void loadMakanan() {
        SampleAPI.Factory.getIstance(this).viewMakanan().enqueue(new Callback<itemRespon>() {
            @Override
            public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                if (response.body().getNilai().equals(1)) {
                    //Toast.makeText(getActivity(), "onSuccessfully Response", Toast.LENGTH_SHORT).show();
                    imMakanan = response.body().getLiModel();
                    mAdapter = new gridItemAdapter2(imMakanan,getApplicationContext());
                    if(mAdapter.getItemCount() == 0){
//                        ((MainActivity)getApplicationContext()).pd.dismiss();
                        Toast.makeText(getApplicationContext(), "List Kosong", Toast.LENGTH_SHORT).show();
                    }else{
//                        ((MainActivity)this).pd.dismiss();
                        mRecyler.setAdapter(mAdapter);
                        //   ((MainActivity)getActivity()).pd.dismiss();
//                    ((MainActivity)getActivity()).pd.cancel();
                    }
                } else {
//                    ((MainActivity)getApplicationContext()).pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<itemRespon> call, Throwable t) {
                //Log.d("Error",t.getMessage());
//                ((MainActivity)getApplicationContext()).pd.dismiss();
                Toast.makeText(getApplicationContext(), "onFailure : " + t.getMessage(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(loginreseller.this);
                alertDialog.setTitle("PESAN")
                        .setMessage("PERIKSA LAGI KONEKSI INTERNET ANDA!")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMakanan();
    }
}
