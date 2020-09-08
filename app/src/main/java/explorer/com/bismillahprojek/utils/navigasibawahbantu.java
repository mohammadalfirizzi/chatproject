package explorer.com.bismillahprojek.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import explorer.com.bismillahprojek.AlertActivity;
import explorer.com.bismillahprojek.HomeActivity;
import explorer.com.bismillahprojek.LikesActivity;
import explorer.com.bismillahprojek.R;
import explorer.com.bismillahprojek.SearchActivity;
import explorer.com.bismillahprojek.ShareActivity;

/**
 * Created by root on 19/06/18.
 */

public class navigasibawahbantu {
    private static final String TAG = "navigasibawahbatu";
            public static void setupNavigasiBawahView(BottomNavigationViewEx bottomNavigationViewEx){
                Log.d(TAG, "setupNavigasiBawahView: Setting Up Navigasi Bawah View");
                bottomNavigationViewEx.enableAnimation(false);
                bottomNavigationViewEx.enableItemShiftingMode(false);
                bottomNavigationViewEx.enableShiftingMode(false);
                bottomNavigationViewEx.setTextVisibility(false);

            }

            public static void enableNavigasi(final Context context, BottomNavigationViewEx view){
                view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.home:
                                Intent intent1 = new Intent(context, HomeActivity.class);
                                context.startActivity(intent1);
                                break;
                            case R.id.search:
                                Intent intent2 = new Intent(context, SearchActivity.class);
                                context.startActivity(intent2);
                                break;
                            case R.id.alert:
                                Intent intent3 = new Intent(context, AlertActivity.class);
                                context.startActivity(intent3);
                                break;
                            case R.id.pesan:
                                Intent intent4 = new Intent(context, LikesActivity.class);
                                context.startActivity(intent4);
                                break;
                            case R.id.tambah:
                                Intent intent5 = new Intent(context, ShareActivity.class);
                                context.startActivity(intent5);
                                break;
                        }
                        return false;
                    }
                });
            }
}
