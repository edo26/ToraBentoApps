package edu.edo.torabentoapps;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import edu.edo.torabentoapps.fragment.AkunFragment;
import edu.edo.torabentoapps.fragment.ItemFragment;

public class MainActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomBar = (BottomBar)findViewById(R.id.bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            Fragment fragment = null;
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if(tabId==R.id.tab_home){
                    fragment = new ItemFragment();
                }else if(tabId == R.id.tab_akun){
                    fragment = new AkunFragment();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();
            }
        });
    }
}
