package edu.edo.torabentoapps.View;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.edo.torabentoapps.Controller.KeranjangActivity;
import edu.edo.torabentoapps.Controller.KeranjangAdapter;
import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Controller.UploadActivity;
import edu.edo.torabentoapps.Model.Keranjang.DataArray1;
import edu.edo.torabentoapps.Model.Keranjang.ModelKeranjangDB;
import edu.edo.torabentoapps.Model.Makanan.ModelMakanan;
import edu.edo.torabentoapps.Model.Pembeli.ModelPembeli;
import edu.edo.torabentoapps.Model.Transaksi.ModelDetailPesanan;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Edo on 1/3/2018.
 */

public class PembayaranLayout extends AppCompatActivity {

    String filePath,targetpath,namagambar,urlGambar="https://kptoratorabento.000webhostapp.com/AndroidUploadImage/uploads/";
    private Bitmap oldDrawable;
    public static String BASE_URL = "https://kptoratorabento.000webhostapp.com/AndroidUploadImage/upload.php";
    BootstrapButton selesaiPembayaran;
    Button uploadImage;
    RecyclerView rvTransaksi;
    RecyclerView.LayoutManager rvL;
    List<DataArray1> databaru = new ArrayList<>();
    List<String> listIdmakanan = new ArrayList<>();
    KeranjangAdapter adapter;
    TextView totalharga;
    int total = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaranlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        rvTransaksi = (RecyclerView)findViewById(R.id.rvuntuklistyangdibeli);
        totalharga = (TextView)findViewById(R.id.totalhargapembayaran);
        rvTransaksi.setHasFixedSize(true);
        rvL = new LinearLayoutManager(PembayaranLayout.this);
        rvTransaksi.setLayoutManager(rvL);
        databaru = new KeranjangActivity().getFavorites(PembayaranLayout.this);
        int [] subtotal = new int[databaru.size()];
        for(int i = 0; i < databaru.size(); i++){
            subtotal[i] = databaru.get(i).getHarga() * databaru.get(i).getQty();
            SampleAPI.Factory.getIstance(PembayaranLayout.this).getIdMakanan(databaru.get(i).getNamaMakanan()).enqueue(new Callback<ModelMakanan>() {
                @Override
                public void onResponse(Call<ModelMakanan> call, Response<ModelMakanan> response) {
                    if(response.isSuccessful()){
                        if(response.body().getDataArray().size() > 1){
                            Toast.makeText(PembayaranLayout.this, "Error ada email yang sama !", Toast.LENGTH_SHORT).show();
                        }else{
                            listIdmakanan.add(response.body().getDataArray().get(0).getIdMakanan());
                        }
                    }else{
                        Toast.makeText(PembayaranLayout.this, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelMakanan> call, Throwable t) {
                    Toast.makeText(PembayaranLayout.this, "Error on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        for( int num : subtotal) {
            total = total+num;
        }
        totalharga.setText("Rp. "+String.valueOf(total));
        String nama,nomorhp,email,alamat;
        nama = getIntent().getStringExtra("nama");
        nomorhp = getIntent().getStringExtra("nomorhp");
        SharedPreferences.Editor editor = getSharedPreferences("NOMOR HP",MODE_PRIVATE).edit();
        editor.putString("nomorhp", nomorhp);
        editor.apply();
        email = getIntent().getStringExtra("email");
        alamat = getIntent().getStringExtra("alamat");
        Log.d("Nama", nama);
        Log.d("Nomor hp", nomorhp);
        Log.d("Email", email);
        Log.d("Alamat", alamat);

        adapter = new KeranjangAdapter(databaru, PembayaranLayout.this);
        rvTransaksi.setAdapter(adapter);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Tahap pembayaran untuk menyelesaikan transaksi pembelian");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pembayaran");
        Log.d("NAMA MAKANAN :",databaru.get(0).getNamaMakanan());
        bindDatanih();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
                //Beri validasi

            }
        });
        selesaiPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadImage.isEnabled()){
                    Toast.makeText(PembayaranLayout.this, "Anda belum melakukan upload resi bukti pembayaran", Toast.LENGTH_SHORT).show();
                }else{
                    if(filePath != null){
                        imageUpload(filePath);
                        Toast.makeText(PembayaranLayout.this, "Alamat resi : "+urlGambar+namagambar, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PembayaranLayout.this, "Filepath nya null", Toast.LENGTH_SHORT).show();
                    }

                    //Gathering data
                    /* Perlu diketahui
                    Bagian ini harus meng-INSERTkan ke t_keranjang,t_pembeli dan t_transaksi
                    GATHERING ALL DATA IN BELOW!
                     */

                    //Kebutuhan T_pembeli
                    String nama,nomorhp,email,alamat;
                    nama = getIntent().getStringExtra("nama");
                    nomorhp = getIntent().getStringExtra("nomorhp");
                    email = getIntent().getStringExtra("email");
                    alamat = getIntent().getStringExtra("alamat");
                    Log.d("Nama", nama);
                    Log.d("Nomor hp", nomorhp);
                    Log.d("Email", email);
                    Log.d("Alamat", alamat);
                    insertDataPembeli(nama,nomorhp,email,alamat);
                    insertDataPembeli(nama,nomorhp,email,alamat);

                    //Kebutuhan t_keranjang
                    Log.d("Nomor hp setelah insert", nomorhp);
                    getIdPembeli();
                    for(int i = 0 ; i < listIdmakanan.size(); i++){
                        masukkinKeKeranjang(i);
                    }

                    masukkinTransaksi();


                    //Toast.makeText(PembayaranLayout.this, "Transaksi pembelian telah berhasil, silahkan tunggu konfirmasi dari reseller untuk pengiriman makanan.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    private void masukkinTransaksi(){

        //Kebutuhan T_transaksi
        String id_reseller,tanggal_transaksi,urlgambarresi;
        tanggal_transaksi = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        urlgambarresi = urlGambar+namagambar;
        SharedPreferences espe = getSharedPreferences("id_reseller", MODE_PRIVATE);
        SharedPreferences espe2 = getSharedPreferences("ID PEMBELI", MODE_PRIVATE);
        id_reseller = espe.getString("id_reseller", null);
        String id_pembeli = espe2.getString("idpembeli",null);
        String nama = getIntent().getStringExtra("nama");
        String status = "Pending";

        Log.d("tanggal",tanggal_transaksi );
        Log.d("urlgambar",urlgambarresi );
        Log.d("id_reseller",id_reseller );

        //Log.d("id_pembeli",id_pembeli );
        Log.d("nama pembeli",nama );
        Log.d("status",status );

        SampleAPI.Factory.getIstance(PembayaranLayout.this).insertManualTrans(id_reseller,id_pembeli,status,tanggal_transaksi,urlgambarresi,nama).enqueue(new Callback<ModelDetailPesanan>() {
            @Override
            public void onResponse(Call<ModelDetailPesanan> call, Response<ModelDetailPesanan> response) {
                if(response.isSuccessful()){
                    if(response.body().getNilai().equals(1)){

                        //Toast.makeText(PembayaranLayout.this, "Alhamdulillah berhasil insert transaksinya", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PembayaranLayout.this);
                        alertDialog.setTitle("PESAN")
                                .setIcon(R.drawable.sukses)
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(PembayaranLayout.this,HalamanAwalLayout.class));
                                    }
                                })
                                .setMessage("Transaksi pembelian telah berhasil, silahkan tunggu konfirmasi dari reseller untuk pengiriman makanan.")
                                .create()
                                .show();

                    }else{
                        Toast.makeText(PembayaranLayout.this, "Nilai 0", Toast.LENGTH_SHORT).show();
                        Toast.makeText(PembayaranLayout.this, "Error : "+response.body().getError(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(PembayaranLayout.this, "Response is unsuccessfuly : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDetailPesanan> call, Throwable t) {
                Toast.makeText(PembayaranLayout.this, "Angger weh on failure teh : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*
        SampleAPI.Factory.getIstance(PembayaranLayout.this).insertTransaksi("INSERT INTO t_transaksi(id_reseller,id_pembeli,totalharga,status_transaksi,tanggal_transaksi,resipembayaran) VALUES("+id_reseller+","+id_pembeli+",(SELECT SUM(t_makanan.harga*t_keranjang.qty) FROM t_keranjang JOIN t_makanan ON t_keranjang.id_makanan = t_makanan.id_makanan JOIN t_pembeli ON t_keranjang.id_pembeli = t_pembeli.id_pembeli WHERE t_pembeli.nama_pembeli = '"+nama+"'),'Pending','"+tanggal_transaksi+"','"+urlgambarresi+"')")
                .enqueue(new Callback<ModelDetailPesanan>() {
                    @Override
                    public void onResponse(Call<ModelDetailPesanan> call, Response<ModelDetailPesanan> response) {
                        if(response.isSuccessful()){
                            if(response.body().getNilai().equals(1)){
                                //Pesan ini belakangan

                            }else{
                                Toast.makeText(PembayaranLayout.this, "Nilai 0", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(PembayaranLayout.this, "Respon are unsuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelDetailPesanan> call, @NonNull Throwable t) {
                        Toast.makeText(PembayaranLayout.this, "ini bener rror : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
        */

    }



    private void insertDataPembeli(String nama, String nomorhp, String email, String alamat) {
        SampleAPI.Factory.getIstance(PembayaranLayout.this).insertDataPembeli(nama, nomorhp, email, alamat).enqueue(new Callback<ModelPembeli>() {
            @Override
            public void onResponse(Call<ModelPembeli> call, Response<ModelPembeli> response) {
                if (response.isSuccessful()) {
                    //pd.dismiss();
                    if(response.body().getNilai().equals(1)){
                        Toast.makeText(PembayaranLayout.this, "Berhasil tambah data pembeli", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PembayaranLayout.this, "Ada yang salah!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //pd.dismiss();
                    Toast.makeText(PembayaranLayout.this, "Error response tidak sukses " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPembeli> call, Throwable t) {
                //pd.dismiss();
                Toast.makeText(PembayaranLayout.this, "Error on failure saat input data pembeli : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 1);
    }

    private void imageUpload(String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String message = jObj.getString("message");

                            targetpath = jObj.getString("target");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            //Toast.makeText(dashboardreseller.this, targetpath, Toast.LENGTH_SHORT).show();
                            Log.d("Target",targetpath);

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        smr.addFile("image", imagePath);
        UploadActivity.getInstance().addToRequestQueue(smr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == 1){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                namagambar = getFileName(picUri);

                //thumbnails.setImageURI(picUri);

                uploadImage.setText(namagambar);
                uploadImage.setEnabled(false);

            }

        }

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Get Path of selected image
    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(),    contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void bindDatanih(){
        selesaiPembayaran = (BootstrapButton)findViewById(R.id.selesaipembayaran);
        uploadImage = (Button)findViewById(R.id.uploadresibukti);
    }

    private void getIdPembeli(){
        SharedPreferences espe = getSharedPreferences("NOMOR HP",MODE_PRIVATE);
        String nomorhp = espe.getString("nomorhp",null);
        SampleAPI.Factory.getIstance(PembayaranLayout.this).getIdPembeli(nomorhp).enqueue(new Callback<ModelPembeli>() {
            @Override
            public void onResponse(@NonNull  Call<ModelPembeli> call, @NotNull  Response<ModelPembeli> response) {
                if(response.isSuccessful()){
                    if(response.body().getNilai() != null){
                        if(!response.body().getNilai().equals(1)){
                            Toast.makeText(PembayaranLayout.this, "Error nilai 0 !", Toast.LENGTH_SHORT).show();
                        }else{
                            try{
                                String tampung;
                                tampung = response.body().getDataArray().get(0).getIdPembeli();
                                Log.d("ID PEMBELI", tampung);
                                SharedPreferences.Editor editor = getSharedPreferences("ID PEMBELI",MODE_PRIVATE).edit();
                                editor.putString("idpembeli", tampung);
                                editor.apply();
                            }catch (Exception e){
                                Toast.makeText(PembayaranLayout.this, "Indikasi ID Pembeli kosong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(PembayaranLayout.this, "Null wae sa teh", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PembayaranLayout.this, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPembeli> call, Throwable t) {
                Toast.makeText(PembayaranLayout.this, "Erro on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void masukkinKeKeranjang(int i){
        SharedPreferences espe = getSharedPreferences("ID PEMBELI", MODE_PRIVATE);
        String tampung = espe.getString("idpembeli", null);
        SampleAPI.Factory.getIstance(PembayaranLayout.this).insertDataKeranjang(listIdmakanan.get(i),tampung,String.valueOf(databaru.get(i).getQty())).enqueue(new Callback<ModelKeranjangDB>() {
            @Override
            public void onResponse(Call<ModelKeranjangDB> call, Response<ModelKeranjangDB> response) {
                if(response.isSuccessful()){
                    if(response.body().getNilai().equals(1)){
                        Toast.makeText(PembayaranLayout.this, "Sukses T_keranjang !", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PembayaranLayout.this, "It's return nilai 0 !", Toast.LENGTH_SHORT).show();
                        Toast.makeText(PembayaranLayout.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PembayaranLayout.this, "Error unsuccessfuly", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelKeranjangDB> call, Throwable t) {
                Toast.makeText(PembayaranLayout.this, "Error onfailure "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
