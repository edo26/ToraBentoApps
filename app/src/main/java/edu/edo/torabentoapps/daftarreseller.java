package edu.edo.torabentoapps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.Model.DataArray;
import edu.edo.torabentoapps.Model.ResellerModel;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class daftarreseller extends AppCompatActivity {

    BootstrapEditText nama,alamat,nomorhp,email,username,password;
    BootstrapButton submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarreseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Mendaftarkan diri sebagai Reseller Tora - tora bento.");
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pendaftaran Reseller");

        //Prosedur binding data
        bindData();

        //Prosedur untuk input data
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInputan();
            }
        });
    }

    public void cekInputan(){
        if (getNama().equals("")
                || getAlamat().equals("")
                || getNomorhp().equals("")
                || getEmail().equals("")
                || getUsername().equals("")
                || getPassword().equals("")){
            Toast.makeText(this, "Data ada yang belum lengkap.", Toast.LENGTH_SHORT).show();
        }else{
            inputData();
            bersihkan();
        }
    }

    public void inputData(){
        SampleAPI objek = SampleAPI.Factory.getIstance(daftarreseller.this);
        objek.insertData(nama.getText().toString(),alamat.getText().toString(),nomorhp.getText().toString(),email.getText().toString(),username.getText().toString(),password.getText().toString()).enqueue(new Callback<ResellerModel>() {
            @Override
            public void onResponse(Call<ResellerModel> call, Response<ResellerModel> response) {
                //Toast.makeText(daftarreseller.this, "Berhasil ?", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    Toast.makeText(daftarreseller.this, "Data berhasil di tambahkan, Tapi ada pesan "+response.message(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(daftarreseller.this, getNama()+getAlamat()+getEmail()+getNomorhp(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(daftarreseller.this, "Not Successful : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResellerModel> call, Throwable t) {
                Toast.makeText(daftarreseller.this, "onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bersihkan(){

        nama.setText("");
        alamat.setText("");
        nomorhp.setText("");
        email.setText("");
        username.setText("");
        password.setText("");

    }

    private void bindData(){
        nama = (BootstrapEditText)findViewById(R.id.namareseller);
        alamat = (BootstrapEditText)findViewById(R.id.alamatreseller);
        nomorhp = (BootstrapEditText)findViewById(R.id.nomorhpreseller);
        email = (BootstrapEditText)findViewById(R.id.emailreseller);
        username = (BootstrapEditText)findViewById(R.id.usernamereseller);
        password = (BootstrapEditText)findViewById(R.id.passwordreseller);
        submit = (BootstrapButton)findViewById(R.id.daftarreseller);
    }

    public String getNama() {
        return nama.getText().toString();
    }

    public String getAlamat() {
        return alamat.getText().toString();
    }

    public String getNomorhp() {
        return nomorhp.getText().toString();
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
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
