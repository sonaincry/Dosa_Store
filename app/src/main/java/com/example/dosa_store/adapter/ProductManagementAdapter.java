    package com.example.dosa_store.adapter;

    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.example.dosa_store.MainActivity;
    import com.example.dosa_store.ProductDetailActivity;
    import com.example.dosa_store.ProductManagement;
    import com.example.dosa_store.R;
    import com.example.dosa_store.model.Product;
    import org.w3c.dom.Text;

    import java.util.ArrayList;

    import retrofit2.http.DELETE;

    public class ProductManagementAdapter extends RecyclerView.Adapter<ProductManagementAdapter.ViewHolder> {
        private static ArrayList<Product> productList;
        private ProductManagement productManagement;
        public ProductManagementAdapter(ArrayList<Product> productList, ProductManagement productManagement) {
            this.productList = productList;
            this.productManagement = productManagement;
        }

        @NonNull
        @Override
        public ProductManagementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_management_item, parent, false);
            return new ProductManagementAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.id.setText(Integer.toString(product.getId()));
            holder.name.setText(product.getName());
            Glide.with(holder.itemView).load(product.getImgUrl()).centerCrop().into(holder.img);
            holder.description.setText(product.getDescription());
            holder.price.setText(Double.toString(product.getPrice()));
            holder.quantity.setText(Integer.toString(product.getQuantity()));
            holder.update.setOnClickListener(v -> {
                productManagement.update(position, product);
            });
            holder.delete.setOnClickListener(v -> {
                productManagement.delete(position);
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView id;
            ImageView img;

            TextView name;
            TextView description;
            TextView price;
            TextView quantity;

            ImageView delete;
            ImageView update;


            public ViewHolder(View itemView) {
                super(itemView);
                id= itemView.findViewById(R.id.item_id);
                img= itemView.findViewById(R.id.item_image);
                name=itemView.findViewById(R.id.item_title);
                description=itemView.findViewById(R.id.item_description);
                price = itemView.findViewById(R.id.item_price);
                quantity = itemView.findViewById(R.id.item_quantity);
                delete = itemView.findViewById(R.id.ivDelete);
                update = itemView.findViewById(R.id.ivUpdate);
            }
        }
    }
