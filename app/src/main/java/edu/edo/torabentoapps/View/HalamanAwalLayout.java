package edu.edo.torabentoapps.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import edu.edo.torabentoapps.R;

public class HalamanAwalLayout extends AppCompatActivity {
    private BottomBar bottomBar;
    public ProgressDialog pd;
    static MaterialSearchView searchView;
    Menu menua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        SharedPreferences espe = getSharedPreferences("Nama Reseller", MODE_PRIVATE);
        String user = espe.getString("username", null);
        String pass = espe.getString("password", null);

//        Toast.makeText(this, "Username : " + user, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Password : " + pass, Toast.LENGTH_SHORT).show();

        if (user != null && pass != null) {
            startActivity(new Intent(HalamanAwalLayout.this, LoginLayout.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);

            pd = new ProgressDialog(HalamanAwalLayout.this);
            pd.setTitle("Pesan");
            pd.setMessage("Sedang mengambil data...");
            pd.setIcon(R.drawable.loading);
//            pd.setCancelable(false);
            pd.show();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            searchView = (MaterialSearchView) findViewById(R.id.search_view);
            bottomBar = (BottomBar) findViewById(R.id.bottombar);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                Fragment fragment = null;

                @Override
                public void onTabSelected(@IdRes int tabId) {
                    switch (tabId) {
                        case R.id.tab_home:
                            //Toast.makeText(HalamanAwalLayout.this, "Tab satu", Toast.LENGTH_SHORT).show();
                            searchView.setEnabled(true);
                            fragment = new HomeFragment();
                            setTitle("Tora Bento Apps");
//                        if(!menua.hasVisibleItems()){
//                            MenuItem item1 = menua.findItem(R.id.action_search);
//                            item1.setVisible(true);
//                        }
                            break;
                        case R.id.tab_akun:
                            //Toast.makeText(HalamanAwalLayout.this, "Tab dua", Toast.LENGTH_SHORT).show();
                            searchView.setVisibility(View.GONE);
                            fragment = new ResellerFragment();
                            setTitle("Login Reseller");
                            break;
                        case R.id.tab_keranjang:
                            //Toast.makeText(HalamanAwalLayout.this, "Tab tiga", Toast.LENGTH_SHORT).show();
                            //searchView.setEnabled(false);
                            searchView.setVisibility(View.GONE);
                            fragment = new KeranjangFragment();
                            setTitle("Keranjang");
                            break;
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, fragment)
                            .commit();
                }
            });


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //pd.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //pd.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.historitransaksi :
                startActivity(new Intent(HalamanAwalLayout.this,HistoryTransaksiLayout.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        menua = menu;
        return true;
    }
}
