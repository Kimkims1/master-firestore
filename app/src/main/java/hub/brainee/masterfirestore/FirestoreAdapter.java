package hub.brainee.masterfirestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public
class FirestoreAdapter extends FirestorePagingAdapter<ProductsModel, FirestoreAdapter.ProductsViewHolder> {

    private OnListItemClick OnListItemClick;

    public FirestoreAdapter(FirestorePagingOptions<ProductsModel> options,OnListItemClick OnListItemClick) {
        super(options);
        this.OnListItemClick = OnListItemClick;

    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice() + "");
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {
            case LOADING_INITIAL:
               // Toast.makeText(context, "Loading initial data...", Toast.LENGTH_SHORT).show();
                break;
            case LOADING_MORE:
               // Toast.makeText(context, "Loading more....", Toast.LENGTH_SHORT).show();
                break;
            case FINISHED:
               // Toast.makeText(context, "Finished loading all items", Toast.LENGTH_SHORT).show();
                break;
            case LOADED:
               // Toast.makeText(context, "Total items loaded: " + getItemCount(), Toast.LENGTH_SHORT).show();
                break;
            case ERROR:
               // Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView price;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnListItemClick.OnItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnListItemClick {
        void OnItemClick(DocumentSnapshot snapshot, int position);
    }
}
