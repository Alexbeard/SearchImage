package ua.owox.test.searchimage.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ua.owox.test.searchimage.R;
import ua.owox.test.searchimage.databinding.FragmentSearchBinding;
import ua.owox.test.searchimage.model.Image;
import ua.owox.test.searchimage.ui.MainActivity;
import ua.owox.test.searchimage.ui.Router;
import ua.owox.test.searchimage.ui.base.BaseFragment;
import ua.owox.test.searchimage.util.RxSearch;


public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchPresenter<SearchFragment>> implements SearchContract.View, SearchAdapter.OnImageClicked {

    private GridLayoutManager manager;
    private SearchAdapter adapter;
    private boolean isSearch;
    private boolean isLoading;
    private Router router;

    int page;
    int searchPage;
    String query;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected void setUpViews() {
        router = (MainActivity) getActivity();
        initToolbar();

        if (adapter == null) {
            adapter = new SearchAdapter(this);
            page = 1;
            presenter.loadImages(page);
        } else {
            binding.list.setAdapter(adapter);
        }

        initList();

    }

    private void initToolbar() {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initList() {
        manager = new GridLayoutManager(getContext(), 2);
        binding.list.setLayoutManager(manager);
        binding.list.setAdapter(adapter);
        binding.list.addOnScrollListener(recyclerViewOnScrollListener);
        binding.list.setItemAnimator(new DefaultItemAnimator());
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = manager.getChildCount();
            int totalItemCount = manager.getItemCount();
            int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

            if (!isLoading)
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    if (!isSearch)
                        presenter.loadNextPage(++page);
                    else
                        presenter.loadNextSearchPage(query, ++searchPage);

                    isLoading = !isLoading;
                }
        }
    };

    @Override
    public void onImagesLoadSuccess(List<Image> images) {
        isLoading = false;
        adapter.updateItems(images);
    }

    @Override
    public void onSearchSuccess(List<Image> images) {
        adapter.updateItems(images);
    }

    @Override
    public void onImageClick(ImageView imageView, Image image) {
        router.goToDetail(imageView, image);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            adapter.clear();
            binding.progress.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.progress.getRoot().setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty(boolean show) {
        if (show) {
            adapter.clear();
            binding.emptyView.setVisibility(View.VISIBLE);
        } else {
            binding.emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        RxSearch.fromSearchView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(item -> item.length() > 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    searchPage = 1;
                    this.query = query;
                    presenter.searchImages(query, searchPage);
                    isSearch = true;
                });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }
}
