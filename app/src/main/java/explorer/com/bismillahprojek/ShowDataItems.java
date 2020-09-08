package explorer.com.bismillahprojek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 21/06/18.
 */

public class ShowDataItems extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar mProgressCircle;


    private DatabaseReference databaseReference;
    private List<upload> muploads;
    @Override
    protected void onCreate(Bundle savedInstance){
       super.onCreate(savedInstance);
       setContentView(R.layout.tampil_data);
       mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
       mRecyclerView.setHasFixedSize(true);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

       mProgressCircle = (ProgressBar)findViewById(R.id.progress_circle);

       muploads = new ArrayList<>();

       databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot postsnapshot : dataSnapshot.getChildren()){
                   upload Upload = postsnapshot.getValue(upload.class);
                   muploads.add(Upload);
               }
               mAdapter = new ImageAdapter(ShowDataItems.this, muploads);
               mRecyclerView.setAdapter(mAdapter);
               mProgressCircle.setVisibility(View.INVISIBLE);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Toast.makeText(ShowDataItems.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
               mProgressCircle.setVisibility(View.INVISIBLE);
           }
       });

   }
}

