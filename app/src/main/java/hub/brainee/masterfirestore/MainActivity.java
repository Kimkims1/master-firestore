package hub.brainee.masterfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {

    private RecyclerView firestore_list;
    private FirebaseFirestore firestore;
    private FirestoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore_list = findViewById(R.id.firestore_list);
        firestore = FirebaseFirestore.getInstance();

        //Query
        Query query = firestore.collection("Products");

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        //Firestore Recycler Options
        FirestorePagingOptions<ProductsModel> options = new FirestorePagingOptions.Builder<ProductsModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, ProductsModel.class)
                .build();

        adapter = new FirestoreAdapter(options, this);


        firestore_list.setHasFixedSize(true);
        firestore_list.setLayoutManager(new LinearLayoutManager(this));
        firestore_list.setAdapter(adapter);
    }

    @Override
    public void OnItemClick(DocumentSnapshot snapshot, int position) {
        Toast.makeText(this, "Item clicked: " + position + " and the ID is: " + snapshot.getId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, SomeActivity.class);
        startActivity(intent);
    }

}