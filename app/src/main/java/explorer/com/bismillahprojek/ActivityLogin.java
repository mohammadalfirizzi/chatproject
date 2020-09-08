package explorer.com.bismillahprojek;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogin extends AppCompatActivity {
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mloginBtn, mDaftar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField = (EditText) findViewById(R.id.password);
        mloginBtn = (Button) findViewById(R.id.login);
        mDaftar = (Button) findViewById(R.id.daftar1);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(ActivityLogin.this, HomeActivity.class));

                }

            }

        };
        mDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, daftar.class));
            }
        });


        mloginBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                startSignIn();

            }

        });

    }

    @Override

    protected void onStart() {

        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

    }

    private void startSignIn() {

        String email = mEmailField.getText().toString();

        String password = mPasswordField.getText().toString();


        if (TextUtils.isEmpty(email) | TextUtils.isEmpty(password)) {

            Toast.makeText(ActivityLogin.this, "Fields are Empty", Toast.LENGTH_SHORT).show();

        } else {


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (!task.isSuccessful()) {

                        Toast.makeText(ActivityLogin.this, "Login Problem", Toast.LENGTH_SHORT).show();

                    }
                }

            });

        }

    }
}


