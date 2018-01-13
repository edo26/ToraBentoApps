package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.json.JSONException;
import org.json.JSONObject;

import edu.edo.torabentoapps.Controller.UploadActivity;
import edu.edo.torabentoapps.R;

/**
 * Created by Edo on 1/3/2018.
 */

public class PembayaranLayout extends AppCompatActivity {

    String filePath,targetpath,namagambar,urlGambar="https://kptoratorabento.000webhostapp.com/AndroidUploadImage/uploads/";
    private Bitmap oldDrawable;
    public static String BASE_URL = "https://kptoratorabento.000webhostapp.com/AndroidUploadImage/upload.php";
    BootstrapButton selesaiPembayaran;
    Button uploadImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaranlayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Tahap pembayaran untuk menyelesaikan transaksi pembelian");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pembayaran");
        bindDatanih();
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
                imageUpload(filePath);
            }
        });
        selesaiPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadImage.isEnabled()){
                    Toast.makeText(PembayaranLayout.this, "Anda belum melakukan upload resi bukti pembayaran", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(PembayaranLayout.this,HalamanAwalLayout.class));
                    Toast.makeText(PembayaranLayout.this, "Transaksi pembelian telah berhasil, silahkan tunggu konfirmasi dari reseller untuk pengiriman makanan.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, 1);
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
        UploadActivity.getInstance().addToRequestQueue(smr);
        baru.dismiss();
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
