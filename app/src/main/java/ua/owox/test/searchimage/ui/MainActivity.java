package ua.owox.test.searchimage.ui;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import ru.alexbykov.nopermission.PermissionHelper;
import ua.owox.test.searchimage.R;
import ua.owox.test.searchimage.databinding.ActivityMainBinding;
import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.ui.base.BaseActivity;
import ua.owox.test.searchimage.ui.detail.DetailFragment;
import ua.owox.test.searchimage.ui.search.SearchFragment;
import ua.owox.test.searchimage.util.DetailsTransition;

public class MainActivity extends BaseActivity implements Router {

    ActivityMainBinding binding;
    PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        permissionHelper = new PermissionHelper(this);

        permissionHelper.check(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .onSuccess(() -> {
                })
                .onDenied(this::finish)
                .onNeverAskAgain(() -> permissionHelper.startApplicationSettingsActivity())
                .run();

        initFragments();

    }

    private void initFragments() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(binding.fragment.getId(), SearchFragment.newInstance())
                .commit();
    }

    @Override
    public void goToDetail(ImageView imageView, Image image) {

        DetailFragment details = DetailFragment.newInstance(image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            details.setSharedElementEnterTransition(new DetailsTransition());
            details.setSharedElementReturnTransition(new DetailsTransition());
        }

        ViewCompat.setTransitionName(imageView, "sharedImage");

        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(imageView, "sharedImage")
                .replace(binding.fragment.getId(), details)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void back() {
        onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();

        }
    }
}
