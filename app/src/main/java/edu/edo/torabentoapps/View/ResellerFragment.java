package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import edu.edo.torabentoapps.Controller.SampleAPI;
import edu.edo.torabentoapps.Model.Reseller.ModelLogin;
import edu.edo.torabentoapps.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anggy on 17/08/2017.
 */

public class ResellerFragment extends Fragment {

    BootstrapEditText username,password;
    BootstrapButton login,daftar;
    ProgressDialog pd;

    public ResellerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_activity, container, false);

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Pesan");
        pd.setMessage("Memuat...");
        username = (BootstrapEditText)view.findViewById(R.id.username);
        password = (BootstrapEditText)view.findViewById(R.id.password);
        login = (BootstrapButton)view.findViewById(R.id.btnbirumuda);
        daftar = (BootstrapButton)view.findViewById(R.id.btnmerah);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                cekDulu();
            }
        });

        daftarReseller();

        return view;
    }

    private void daftarReseller() {
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DaftarResellerLayout.class));
            }
        });
    }

    private void cekDulu(){
        if(getUsername().equals("") || getPassword().equals("")){
            pd.dismiss();
            Toast.makeText(getActivity(), "Username atau Password belum terisi.", Toast.LENGTH_SHORT).show();
        }else {
            validation();
            //bersihKan();
        }
    }

    private void validation(){
        SampleAPI.Factory.getIstance(getActivity()).validasii(getUsername(),getPassword()).enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.isSuccessful()){
                    if (response.body().getNilai().equals(1)){
                        pd.dismiss();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Nama Reseller",MODE_PRIVATE).edit();
                        editor.putString("namareseller", response.body().getDataArray().get(0).getIdReseller());
                        editor.putString("username",username.getText().toString());
                        editor.putString("password",password.getText().toString());
                        editor.apply();
                        //Toast.makeText(getActivity(), "Welcome "+getUsername(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginLayout.class));
                        getActivity().finish();
                    }else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Email atau Password anda salah.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Unsuccessfully : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "onFailure : "+t.getMessage()+"\n Code Error : "+t.getCause(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

    private void bersihKan(){
        username.setText("");
        password.setText("");
    }

    public String getUsername() {
        return username.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }
}
