package explorer.com.bismillahprojek;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import explorer.com.bismillahprojek.R;
import explorer.com.bismillahprojek.utils.navigasibawahbantu;

/**
 * Created by root on 19/06/18.
 */

public class LikesActivity extends AppCompatActivity {
    private static int SIGN_IN_REQUEST_CODE = 1;

    private FirebaseListAdapter<chat_message> adapter;
    RelativeLayout chatt;
    FloatingActionButton fab;


    private static final String TAG = "LikesActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext = LikesActivity.this;
    private Button mLogoutBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (requestCode == RESULT_OK) {
                Snackbar.make(chatt, "Sukses masuk Selamat Datang !!! ", Snackbar.LENGTH_SHORT).show();
                displayChatMessage();
            } else {
                Snackbar.make(chatt, "Gagal Masuk !!!", Snackbar.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatt);
        mAuth = FirebaseAuth.getInstance();

        chatt = (RelativeLayout) findViewById(R.id.chatt);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new chat_message(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }
        else {
            Snackbar.make(chatt,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }

        Log.d(TAG, "onCreate: started");
        setupNavigasiBawah();
    }

    private void displayChatMessage(){
        ListView listofMessage = (ListView) findViewById(R.id.list);
        adapter = new FirebaseListAdapter<chat_message>(this,chat_message.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, chat_message model, int position) {
                TextView messageText,messageUser,messageTime;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageTime = (TextView) v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };
        listofMessage.setAdapter(adapter);
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
