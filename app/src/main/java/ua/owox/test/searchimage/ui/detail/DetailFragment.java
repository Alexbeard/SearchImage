package ua.owox.test.searchimage.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ua.owox.test.searchimage.R;
import ua.owox.test.searchimage.databinding.FragmentDetailBinding;
import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.ui.MainActivity;
import ua.owox.test.searchimage.ui.Router;
import ua.owox.test.searchimage.ui.base.BaseFragment;
import ua.owox.test.searchimage.util.BitmapUtil;


public class DetailFragment extends BaseFragment<FragmentDetailBinding, DetailPresenter<DetailFragment>> implements DetailContract.View {

    private static final String RESPONSE = "image";
    private Image image;
    private Router router;

    public static DetailFragment newInstance(Image image) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(RESPONSE, image);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void setUpViews() {
        if (getArguments() != null)
            image = getArguments().getParcelable(RESPONSE);

        router = (MainActivity) getActivity();
        initToolbar();
        loadImage();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    private void loadImage() {
        Picasso.get()
                .load(image.getUrls().getRegular())
                .into(binding.image);
    }

    private void shareImage() {

        Picasso.get().load(image.getUrls().getRegular()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, BitmapUtil.getLocalBitmapUri(getContext(), bitmap));
                    startActivity(Intent.createChooser(i, "Share Image"));
                } catch (ActivityNotFoundException ex) {
                    Logger.d("Not found Activity for share: " + ex.getMessage());
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });

    }

    private void downloadImage() {
        BitmapUtil.downloadImage(getContext(), getString(R.string.app_name), image.getUser().getUsername(), image.getUrls().getFull());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                router.back();
                break;
            case R.id.action_download:
                downloadImage();
                break;
            case R.id.action_share:
                shareImage();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }
}
