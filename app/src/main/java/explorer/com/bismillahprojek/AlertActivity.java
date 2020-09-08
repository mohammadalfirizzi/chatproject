package explorer.com.bismillahprojek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import explorer.com.bismillahprojek.utils.navigasibawahbantu;

/**
 * Created by root on 19/06/18.
 */

public class AlertActivity extends AppCompatActivity {
    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
    private static final String TAG = "AlertActivity";
    private static final int ACTIVITY_NUM = 4;
    private Context mContext = AlertActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_utama);
        Log.d(TAG, "onCreate: started");
        setupNavigasiBawah();
        mLogoutBtn = (Button) findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(AlertActivity.this, ActivityLogin.class));

            }

        });
    }

    private void setupNavigasiBawah(){
        Log.d(TAG,"setupNavigasiBawah: Setting Navigasi Bawah");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomm);
        navigasibawahbantu.setupNavigasiBawahView(bottomNavigationViewEx);
        navigasibawahbantu.enableNavigasi(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
