package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.github.nitrico.lastadapter.Holder;
import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.edo.torabentoapps.BR;
import edu.edo.torabentoapps.Model.Makanan.ModelStokMakanan;
import edu.edo.torabentoapps.Model.Reseller.DataArray;
import edu.edo.torabentoapps.Model.Reseller.ModelLogin;
import edu.edo.torabentoapps.Model.Makanan.itemRespon;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.databinding.ItemResellerBinding;
import edu.edo.torabentoapps.Controller.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 25/08/2017.
 */

public class LoginLayout extends AppCompatActivity {

    BootstrapButton buttonTambah,buttonLogout;
    RecyclerView mRecyler;
    RecyclerView.LayoutManager mLayout;
    RecyclerView.Adapter mAdapter;
    //public int posisi=0;
    //HalamanAwalLayout main = new HalamanAwalLayout();
    private List<itemRespon> imMakanan = new ArrayList<>();
    private List<DataArray> items = new ArrayList<>();
    private ProgressDialog pd;
    TextView ebifurai;
    TextView chickenkatsu;
    TextView beefteriyaki;
    TextView ekado;
    TextView shrimproll;
    TextView chickenspicy;
    TextView kaniroll;
    TextView eggchickenroll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        setTitle("Reseller Dashboard");

        pd = new ProgressDialog(this);
        pd.setMessage("Sedang mengambil data....");

        mRecyler = (RecyclerView) findViewById(R.id.recyclerviewuntukreseller);
        mRecyler.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mRecyler.setLayoutManager(mLayout);
        pd.show();
        loadReseller();
        increaseStok();
//        mAdapter = new gridItemAdapter2(imMakanan,this);
//        mRecyler.setAdapter(mAdapter);
        //loadMakanan();

        //buttonTambah = (BootstrapButton)findViewById(R.id.tomboltambah);
        buttonLogout = (BootstrapButton)findViewById(R.id.tombollogout);
        doLogout();

//        buttonTambah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(LoginLayout.this, dashboardreseller.class);
//                i.putExtra("ModeTambah","telahdiklik");
//                startActivity(i);
//            }
//        });

    }

    private void doLogout() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor sp = getSharedPreferences("Nama Reseller",MODE_PRIVATE).edit();
                sp.clear();
                sp.commit();
                startActivity(new Intent(LoginLayout.this, HalamanAwalLayout.class));
                finish();
                //Toast.makeText(LoginLayout.this, "Anjing", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadReseller() {
        SampleAPI.Factory.getIstance(this).viewReseller().enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.body().getNilai().equals(1)) {
                    //Toast.makeText(getActivity(), "onSuccessfully Response", Toast.LENGTH_SHORT).show();
                    items = response.body().getDataArray();
                    pd.dismiss();
                    ItemType<ItemResellerBinding> bookType = new ItemType<ItemResellerBinding>(R.layout.item_reseller) {
                        @Override
                        public void onBind(@NotNull final Holder<ItemResellerBinding> holder) {
                            //System.out.println("Bound " +holder.getBinding().getItem() + " at #" + holder.getAdapterPosition());
                         holder.itemView.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 //Toast.makeText(LoginLayout.this, "Apakah yang aku klik ini : "+holder.getBinding().getItem().getEmail()+" Posisi ke "+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                                //Email siapa kah ini ?
//                                 SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
//                                 final String email = espe.getString("username", null);

                                 SampleAPI.Factory.getIstance(LoginLayout.this).viewStokReseller(holder.getBinding().getItem().getEmail()).enqueue(new Callback<ModelStokMakanan>() {
                                     @Override
                                     public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                         if(response.isSuccessful()){
                                             //Toast.makeText(LoginLayout.this, email, Toast.LENGTH_SHORT).show();

                                             MaterialDialog mdog = new MaterialDialog.Builder(LoginLayout.this)
                                                     .customView(R.layout.dialog_stok,true)
                                                     .title("Lihat Jumlah Stok Reseller")
                                                     .positiveText("KEMBALI")
                                                     .buttonsGravity(GravityEnum.CENTER)
                                                     .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                         @Override
                                                         public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                             dialog.dismiss();
                                                         }
                                                     }).build();

                                             bindingDialog(mdog);

                                             if(response.body().getDataArray1().equals(null)){
                                                 Toast.makeText(LoginLayout.this, "Null euy, si dashboard belum keisi nih", Toast.LENGTH_SHORT).show();
                                             }else{
                                                 Toast.makeText(LoginLayout.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                                 ebifurai.setText(response.body().getDataArray1().get(0).getEbifuraiStok());
                                                 chickenkatsu.setText(response.body().getDataArray1().get(0).getChickenkatsuStok());
                                                 beefteriyaki.setText(response.body().getDataArray1().get(0).getKakinagaStok());
                                                 ekado.setText(response.body().getDataArray1().get(0).getEkkadoStok());
                                                 shrimproll.setText(response.body().getDataArray1().get(0).getShrimprollStok());
                                                 chickenspicy.setText(response.body().getDataArray1().get(0).getSpicychickenStok());
                                                 kaniroll.setText(response.body().getDataArray1().get(0).getKanirollStok());
                                                 eggchickenroll.setText(response.body().getDataArray1().get(0).getEggchickenrollStok());
                                             }

                                             //Toast.makeText(LoginLayout.this, "Ada ngak : "+response.body().getModelReseller().get(0).getEbifuraiStok(), Toast.LENGTH_SHORT).show();


                                             mdog.show();

                                         }else{
                                             Toast.makeText(LoginLayout.this, "Ada yang ngak beres "+response.errorBody()+" dan "+response.message(), Toast.LENGTH_SHORT).show();
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                         Toast.makeText(LoginLayout.this, "Error on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 });

                             }
                         });
                        }
                    };
                    new LastAdapter(items, BR.item)
                            .map(DataArray.class,bookType)
                            .into(mRecyler);
                    //Toast.makeText(LoginLayout.this, "Welcome Shit", Toast.LENGTH_SHORT).show();

                } else {
//                    ((HalamanAwalLayout)getApplicationContext()).pd.dismiss();
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                //Log.d("Error",t.getMessage());
//                ((HalamanAwalLayout)getApplicationContext()).pd.dismiss();
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "onFailure : " + t.getMessage(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginLayout.this);
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
        //loadMakanan();
    }

    private void bindingDialog(MaterialDialog view){
        ebifurai = (TextView)view.findViewById(R.id.jumlahdistok1);
        chickenkatsu = (TextView)view.findViewById(R.id.jumlahdistok2);
        beefteriyaki = (TextView)view.findViewById(R.id.jumlahdistok3);
        ekado = (TextView)view.findViewById(R.id.jumlahdistok4);
        shrimproll = (TextView)view.findViewById(R.id.jumlahdistok5);
        chickenspicy = (TextView)view.findViewById(R.id.jumlahdistok6);
        kaniroll = (TextView)view.findViewById(R.id.jumlahdistok7);
        eggchickenroll = (TextView)view.findViewById(R.id.jumlahdistok8);
    }

    private void increaseStok(){
        BootstrapCircleThumbnail ebifurai = (BootstrapCircleThumbnail)findViewById(R.id.ebifurai);
        BootstrapCircleThumbnail chickenkatsu = (BootstrapCircleThumbnail)findViewById(R.id.chickenkatsu);
        BootstrapCircleThumbnail beefteriyaki = (BootstrapCircleThumbnail)findViewById(R.id.beefteriyaki);
        BootstrapCircleThumbnail ekado = (BootstrapCircleThumbnail)findViewById(R.id.ekado);
        BootstrapCircleThumbnail shirmproll = (BootstrapCircleThumbnail)findViewById(R.id.shirmproll);
        BootstrapCircleThumbnail chickenspicy = (BootstrapCircleThumbnail)findViewById(R.id.spicychicken);
        BootstrapCircleThumbnail kaniroll = (BootstrapCircleThumbnail)findViewById(R.id.kaniroll);
        BootstrapCircleThumbnail eggchickenroll = (BootstrapCircleThumbnail)findViewById(R.id.eggchickenroll);

        ebifurai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,ebifurai.getText().toString(),"0","0","0","0","0","0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                                //Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();
                
            }
        });
        chickenkatsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0",ebifurai.getText().toString(),"0","0","0","0","0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                            //    Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        beefteriyaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0",ebifurai.getText().toString(),"0","0","0","0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                             //   Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        ekado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0","0",ebifurai.getText().toString(),"0","0","0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                             //   Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        shirmproll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0","0","0",ebifurai.getText().toString(),"0","0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                            //    Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        chickenspicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0","0","0","0",ebifurai.getText().toString(),"0","0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                            //    Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        kaniroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0","0","0","0","0",ebifurai.getText().toString(),"0")
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                            //    Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });
        eggchickenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialDialog mDoug = new MaterialDialog.Builder(LoginLayout.this)
                        .customView(R.layout.dialog_ubahstok,true)
                        .title("Ubah Jumlah Stok Makanan")
                        .build();
                final BootstrapEditText ebifurai = (BootstrapEditText)mDoug.findViewById(R.id.ubahjumlahstoknya);
                BootstrapButton ubah = (BootstrapButton)mDoug.findViewById(R.id.tombolubahstok);
                BootstrapButton batal = (BootstrapButton)mDoug.findViewById(R.id.tombolbatalubahstok);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDoug.dismiss();
                    }
                });
                ubah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
                        String email = espe.getString("username", null);
                        SampleAPI.Factory.getIstance(LoginLayout.this).updateStokReseller(email,"0","0","0","0","0","0","0",ebifurai.getText().toString())
                                .enqueue(new Callback<ModelStokMakanan>() {
                                    @Override
                                    public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getNilai().equals(1)){
                                            //    Toast.makeText(LoginLayout.this, "Berhasil tambah stok!", Toast.LENGTH_SHORT).show();
                                                new AlertDialog.Builder(LoginLayout.this)
                                                        .setTitle("PESAN")
                                                        .setIcon(R.drawable.sukses)
                                                        .setMessage("Mengubah Jumlah Stok Makanan Berhasil!")
                                                        .show();
                                            }else{
                                                Toast.makeText(LoginLayout.this, "Ada yang salah Nilai = 0", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginLayout.this, "Error aja "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                                        Toast.makeText(LoginLayout.this, "Error on failure "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                mDoug.show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menureseller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pesananpelanggan :
                startActivity(new Intent(LoginLayout.this,KonfirmasiPemesananLayout.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
