package edu.edo.torabentoapps;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import edu.edo.torabentoapps.fragment.AkunFragment;
import edu.edo.torabentoapps.fragment.ItemFragment;
import edu.edo.torabentoapps.fragment.TransaksiFragment;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    public ProgressDialog pd;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        setContentView(R.layout.activity_main);

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
                        //Toast.makeText(MainActivity.this, "Tab satu", Toast.LENGTH_SHORT).show();
                        fragment = new ItemFragment();
                        break;
                    case R.id.tab_akun:
                        //Toast.makeText(MainActivity.this, "Tab dua", Toast.LENGTH_SHORT).show();
                        fragment = new AkunFragment();
                        break;
                    case R.id.tab_keranjang:
                        //Toast.makeText(MainActivity.this, "Tab tiga", Toast.LENGTH_SHORT).show();
                        fragment = new TransaksiFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                Toast.makeText(MainActivity.this, "Kamu sedang menuliskan + "+query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        pd = new ProgressDialog(MainActivity.this);
        pd.setTitle("Pesan");
        pd.setMessage("Sedang mengambil data...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pd.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pd.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
