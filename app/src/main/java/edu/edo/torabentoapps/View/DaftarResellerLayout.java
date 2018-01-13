package edu.edo.torabentoapps.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Model.Makanan.ModelStokMakanan;
import edu.edo.torabentoapps.Model.Reseller.ModelLogin;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarResellerLayout extends AppCompatActivity {

    BootstrapEditText alamat,email,password;
    BootstrapButton submit;
    boolean emailsudahada = false;
    //String tampung="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarreseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Daftar jadi Reseller Tora Bento");
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
        String cekEmail = email.getText().toString();
        //Toast.makeText(this, "Tes", Toast.LENGTH_SHORT).show();
        if (email.getText().toString().equals("") || alamat.getText().toString().equals("")) {
            Toast.makeText(this, "Tidak bisa kosong", Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().contains("@")){
            Toast.makeText(this, "Format salah, harus berupa email", Toast.LENGTH_SHORT).show();
        }else {
//            int indeksat = email.getText().toString().indexOf("@");
//            String cekPass = email.getText().toString().substring(0, indeksat);
//            if(sudahAdaEmail(cekEmail,cekPass)){
//                Toast.makeText(this, "Email tersebut sudah terdaftar pada sistem", Toast.LENGTH_SHORT).show();
//            }else{
//                inputData();
//            }
            inputData();
        }
    }

//    private boolean sudahAdaEmail(String email, String pass) {
//        SampleAPI.Factory.getIstance(DaftarResellerLayout.this).validasii(email, pass).enqueue(new Callback<ModelLogin>() {
//            @Override
//            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getNilai().equals(1)) {
//                        emailsudahada = true;
//                    }
//                } else {
//                    Toast.makeText(DaftarResellerLayout.this, "Error euy " + response.errorBody(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelLogin> call, Throwable t) {
//                Toast.makeText(DaftarResellerLayout.this, "Erro on failure pak : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return emailsudahada;
//    }

    public void inputData(){
        SampleAPI objek = SampleAPI.Factory.getIstance(DaftarResellerLayout.this);
        int indeksat = email.getText().toString().indexOf("@");
        final String passwordnew = email.getText().toString().substring(0, indeksat);
        //Toast.makeText(this, "Password : "+passwordnew, Toast.LENGTH_SHORT).show();

        objek.insertDataReseller(alamat.getText().toString(), email.getText().toString().trim(), passwordnew).enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                //Toast.makeText(DaftarResellerLayout.this, "Berhasil ?", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    BackgroundMail.newBuilder(DaftarResellerLayout.this)
                            .withUsername("kptoratorabento@gmail.com")
                            .withPassword("toratorabento")
                            .withMailTo(email.getText().toString())
                            .withType(BackgroundMail.TYPE_PLAIN)
                            .withSubject("SELAMAT ANDA SUDAH MENJADI RESELLER")
                            .withBody("Terima kasih sudah melakukan pendaftaran sebagai Reseller di Tora - tora bento. Untuk informasi login Reseller gunakan Email anda dan Password : " + passwordnew)
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {
                                    //do some magic
                                    //insertDefaultstok();
                                    SampleAPI.Factory.getIstance(DaftarResellerLayout.this).submitSQL("SELECT * FROM t_reseller WHERE email = '"+email.getText().toString()+"';").enqueue(new Callback<ModelLogin>() {
                                        @Override
                                        public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                                            if(response.isSuccessful()){
                                                //tampung = response.body().getDataArray().get(0).getIdReseller();
                                                //Toast.makeText(DaftarResellerLayout.this, response.body().getDataArray().get(0).getIdReseller(), Toast.LENGTH_SHORT).show();
                                                insertDefaultstok(response.body().getDataArray().get(0).getIdReseller());
                                            }else{
                                                Toast.makeText(DaftarResellerLayout.this, "Error response unsuccesfully "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ModelLogin> call, Throwable t) {
                                            Toast.makeText(DaftarResellerLayout.this, "Gagal on failure tod "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Toast.makeText(DaftarResellerLayout.this, "Daftar reseller telah berhasil.", Toast.LENGTH_LONG).show();

                                }
                            })
                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                @Override
                                public void onFail() {
                                    //do some magic

                                    Toast.makeText(DaftarResellerLayout.this, "Gagal mengirim Notifikasi email. karena ", Toast.LENGTH_LONG).show();

                                }
                            })
                            .build().send();


                }else{
                    Toast.makeText(DaftarResellerLayout.this, "Not Successful : "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                Toast.makeText(DaftarResellerLayout.this, "onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //tampung = getIdReseller(DaftarResellerLayout.this,email.getText().toString());
//        SampleAPI.Factory.getIstance(DaftarResellerLayout.this).getIdReseller(email.getText().toString()).enqueue(new Callback<ModelLogin>() {
//            @Override
//            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
//                if(response.isSuccessful()){
//                    if(response.body().getDataArray().size() > 1 && !response.body().getNilai().equals(1)){
//                        Toast.makeText(DaftarResellerLayout.this, "Error ada email yang sama !", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(DaftarResellerLayout.this, "Ini telah terlewati....", Toast.LENGTH_SHORT).show();
//                        tampung = response.body().getDataArray().get(0).getIdReseller();
//                    }
//                }else{
//                    Toast.makeText(DaftarResellerLayout.this, "Error : "+response.errorBody() + "Pesan : "+response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelLogin> call, Throwable t) {
//                Toast.makeText(DaftarResellerLayout.this, "Erro on failure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        //Toast.makeText(this, "Ada ngak id reseller teh : "+tampung, Toast.LENGTH_SHORT).show();
    }

    private void insertDefaultstok(String id_reseller) {

        SampleAPI objek1 = SampleAPI.Factory.getIstance(DaftarResellerLayout.this);
        objek1.insertDataMakanan(id_reseller,email.getText().toString(), "0", "0", "0", "0", "0", "0", "0", "0").enqueue(new Callback<ModelStokMakanan>() {
            @Override
            public void onResponse(Call<ModelStokMakanan> call, Response<ModelStokMakanan> response) {
                if (response.isSuccessful()) {
                    if(response.body().getNilai().equals(1)){
                        Toast.makeText(DaftarResellerLayout.this, "Berhasil generate inventori", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DaftarResellerLayout.this, "Ada yang salah", Toast.LENGTH_SHORT).show();
                    }
                    

                } else {
                    Toast.makeText(DaftarResellerLayout.this, "Error found : " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelStokMakanan> call, Throwable t) {
                Toast.makeText(DaftarResellerLayout.this, "Just Error onFailure method : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

//    private void bersihkan(){
//
//        nama.setText("");
//        alamat.setText("");
//        nomorhp.setText("");
//        email.setText("");
//        username.setText("");
//        password.setText("");
//
//    }

    private void bindData() {
//        nama = (BootstrapEditText)findViewById(R.id.namareseller);
        alamat = (BootstrapEditText) findViewById(R.id.alamatreseller);
//        nomorhp = (BootstrapEditText)findViewById(R.id.nomorhpreseller);
        email = (BootstrapEditText) findViewById(R.id.emailreseller);
//        username = (BootstrapEditText)findViewById(R.id.usernamereseller);
//        password = (BootstrapEditText)findViewById(R.id.passwordreseller);
        submit = (BootstrapButton) findViewById(R.id.daftarreseller);
    }

//    public String getNama() {
//        return nama.getText().toString();
//    }
//
//    public String getAlamat() {
//        return alamat.getText().toString();
//    }
//
//    public String getNomorhp() {
//        return nomorhp.getText().toString();
//    }
//
//    public String getEmail() {
//        return email.getText().toString();
//    }
//
//    public String getUsername() {
//        return username.getText().toString();
//    }
//
//    public String getPassword() {
//        return password.getText().toString();
//    }

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
