package explorer.com.bismillahprojek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import explorer.com.bismillahprojek.utils.navigasibawahbantu;

/**
 * Created by root on 19/06/18.
 */

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = HomeActivity.this;
    private TextView pengguna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        pengguna = (TextView) findViewById(R.id.pengguna);
        pengguna.setText("Nama Pengguna : "+ FirebaseAuth.getInstance().getCurrentUser().getEmail());
        RelativeLayout relayout2 = (RelativeLayout) findViewById(R.id.rellayout2);
        Snackbar.make(relayout2,"Welcome "+ FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
        Log.d(TAG,"onCreate: starting");
        setupNavigasiBawah();

    }
    private void setupNavigasiBawah(){
        Log.d(TAG,"setupNavigasiBawah: Setting Navigasi Bawah");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomm);
        navigasibawahbantu.setupNavigasiBawahView(bottomNavigationViewEx);
        navigasibawahbantu.enableNavigasi(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
