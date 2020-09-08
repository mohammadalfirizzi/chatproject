package explorer.com.bismillahprojek;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import explorer.com.bismillahprojek.utils.navigasibawahbantu;

/**
 * Created by root on 19/06/18.
 */

public class ShareActivity extends AppCompatActivity {
    private Button buttonpilih,buttonupload;
    private TextView mTextShowUploads;
    private EditText mEditTextFileName;
    private ImageView imgview;
    private ProgressBar mProgressBar;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST=1;
    FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private static final String TAG = "ShareActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = ShareActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        buttonpilih = (Button) findViewById(R.id.Buttonpilih);
        buttonupload = (Button) findViewById(R.id.button_upload);
        imgview = (ImageView) findViewById(R.id.image_view);
        mEditTextFileName = (EditText) findViewById(R.id.edit_textfile);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mTextShowUploads = (TextView) findViewById(R.id.showupload);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        buttonpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pilihgambar();
            }
        });
        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    uploadFile();
            }
        });
        mTextShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });
        Log.d(TAG, "onCreate: started");
        setupNavigasiBawah();
    }


    private void pilihgambar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            filepath = data.getData();
            Picasso.with(this).load(filepath).into(imgview);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if (filepath != null){
            StorageReference filereference = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(filepath));

            filereference.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },5000);
                                Toast.makeText(ShareActivity.this,"Upload sukses", Toast.LENGTH_LONG).show();
                                upload Upload = new upload(mEditTextFileName.getText().toString().trim(),
                                        taskSnapshot.getDownloadUrl().toString());
                                String UploadID = databaseReference.push().getKey();
                                databaseReference.child(UploadID).setValue(Upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ShareActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else {
            Toast.makeText(this,"Tidak ada file yang dipilih ",Toast.LENGTH_SHORT).show();
        }
    }
private void openImagesActivity(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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
