package edu.edo.torabentoapps.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.zip.Inflater;

import edu.edo.torabentoapps.Model.ResellerModel;
import edu.edo.torabentoapps.R;
import edu.edo.torabentoapps.daftarreseller;
import edu.edo.torabentoapps.loginreseller;
import edu.edo.torabentoapps.utilitize.SampleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static edu.edo.torabentoapps.R.id.daftarreseller;

/**
 * Created by anggy on 17/08/2017.
 */

public class AkunFragment extends Fragment {

    BootstrapEditText username,password;
    BootstrapButton login,daftar;
    ProgressDialog pd;

    public AkunFragment() {

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

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), edu.edo.torabentoapps.daftarreseller.class));
            }
        });

        return view;
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
        SampleAPI.Factory.getIstance(getActivity()).validasii(getUsername(),getPassword()).enqueue(new Callback<ResellerModel>() {
            @Override
            public void onResponse(Call<ResellerModel> call, Response<ResellerModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getNilai().equals(1)){
                        pd.dismiss();
                        pd.dismiss();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Nama Reseller",MODE_PRIVATE).edit();
                        editor.putString("namareseller", response.body().getDataArray().get(0).getIdReseller());
                        editor.apply();
                        //Toast.makeText(getActivity(), "Welcome "+getUsername(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), loginreseller.class));
                        getActivity().finish();
                    }else {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Your username or password are wrong.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Unsuccessfully : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResellerModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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
