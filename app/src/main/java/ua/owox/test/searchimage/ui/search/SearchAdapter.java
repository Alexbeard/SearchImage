package ua.owox.test.searchimage.ui.search;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ua.owox.test.searchimage.databinding.ListItemBinding;
import ua.owox.test.searchimage.model.Image;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Image> images = new ArrayList<>();
    private OnImageClicked listener;

    public SearchAdapter(OnImageClicked listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = ListItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = images.get(position);

        holder.binding.profileName.setText(image.getUser().getUsername());
        holder.binding.likes.setText(String.format("%d", image.getLikes()));

        Picasso.get()
                .load(image.getUrls().getSmall())
                .into(holder.binding.image);

        Picasso.get()
                .load(image.getUser().getProfileImage().getImage())
                .into(holder.binding.profileImage);

        holder.binding.getRoot().setOnClickListener(v -> listener.onImageClick(holder.binding.image, image));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void updateItems(List<Image> images) {
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    public void clear() {
        this.images.clear();
        notifyDataSetChanged();
    }

    interface OnImageClicked {
        void onImageClick(ImageView imageView, Image image);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
