package edu.edo.torabentoapps;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.json.JSONException;
import org.json.JSONObject;

import edu.edo.torabentoapps.Model.itemRespon;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anggy on 24/08/2017.
 */

public class dashboardreseller extends AppCompatActivity {

    BootstrapEditText namaMakanan,stok,harga;
    RadioGroup rg;
    RadioButton tersedia,tdktersedia;
    ImageView thumbnails;
    BootstrapButton tambah,edit;
    String filePath,targetpath,namagambar,urlGambar="https://kptoratorabento.000webhostapp.com/AndroidUploadImage/uploads/";
    private Bitmap oldDrawable;
    public static String BASE_URL = "https://kptoratorabento.000webhostapp.com/AndroidUploadImage/upload.php";
    ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Dashboard Reseller.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tambah Makanan");
        bindingData();
        pd = new ProgressDialog(this);
        pd.setTitle("Pesan");
        pd.setMessage("Sedang menambahkan...");
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                validasiData();
            }
        });
        thumbnails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == 1){
                Uri picUri = data.getData();

                filePath = getPath(picUri);

                namagambar = getFileName(picUri);

                thumbnails.setImageURI(picUri);

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



    private void validasiData(){
        oldDrawable = ((BitmapDrawable) thumbnails.getDrawable()).getBitmap();
        if(getNamaMakanan().equals("") || getStok().equals("") || getHarga().equals("") || !tersedia.isChecked() && !tdktersedia.isChecked() || oldDrawable != oldDrawable){
            pd.dismiss();
            Toast.makeText(this, "Lengkapi dulu data makanan nya.", Toast.LENGTH_SHORT).show();
        }else{
            insertDataMakanan();
        }
    }

    private void imageUpload(final String imagePath) {
        ProgressDialog baru = new ProgressDialog(this);
        baru.setTitle("Upload");
        baru.setMessage("Sedang melakukan upload gambar...");
        baru.show();
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
        MyApplication.getInstance().addToRequestQueue(smr);
        baru.dismiss();
    }

    public void insertDataMakanan(){
        if (filePath != null) {
            imageUpload(filePath);
            SampleAPI tambah = SampleAPI.Factory.getIstance(this);
            tambah.insertMakanan(getNamaMakanan(),getStok(),getHarga(),getStatus(),urlGambar+namagambar).enqueue(new Callback<itemRespon>() {
                @Override
                public void onResponse(Call<itemRespon> call, Response<itemRespon> response) {
                    if(response.isSuccessful()){
                        pd.dismiss();
                        Toast.makeText(dashboardreseller.this, "Berhasil menambah "+getNamaMakanan(), Toast.LENGTH_SHORT).show();
                    }else{
                        pd.dismiss();
                        Toast.makeText(dashboardreseller.this, "unSuccessfully : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<itemRespon> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(dashboardreseller.this, "onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
        }
    }

    private void bindingData(){
        namaMakanan = (BootstrapEditText) findViewById(R.id.namamakanan);
        stok = (BootstrapEditText) findViewById(R.id.stokmakanan);
        harga = (BootstrapEditText) findViewById(R.id.hargamakanan);
        rg = (RadioGroup)findViewById(R.id.radiogrupIni);
        tersedia = (RadioButton)findViewById(R.id.tersedia);
        tdktersedia = (RadioButton)findViewById(R.id.tidaktersedia);
        thumbnails = (ImageView)findViewById(R.id.thumbnailsMakanan);
        tambah = (BootstrapButton)findViewById(R.id.tomboltambah);
        edit = (BootstrapButton)findViewById(R.id.tomboledit);
    }

    public String getNamaMakanan() {
        return namaMakanan.getText().toString();
    }

    public String getStok() {
        return stok.getText().toString();
    }

    public String getHarga() {
        return harga.getText().toString();
    }

    public String getStatus(){

        String status;

        rg = (RadioGroup) findViewById(R.id.radiogrupIni);

        if(tersedia.isChecked()){
            status = "Tersedia";
        }else{
            status = "Tidak Tersedia";
        }

        return status;
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
