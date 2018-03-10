package ua.owox.test.searchimage.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ua.owox.test.searchimage.di.component.DaggerFragmentComponent;
import ua.owox.test.searchimage.di.component.FragmentComponent;
import ua.owox.test.searchimage.di.module.FragmentModule;
import ua.owox.test.searchimage.ui.App;


public abstract class BaseFragment<B extends ViewDataBinding, P extends BasePresenter> extends Fragment implements IView {

    protected B binding;
    @Inject
    protected P presenter;
    private FragmentComponent fragmentComponent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        if (fragmentComponent == null)
            fragmentComponent = provideFragmentComponent();

        inject();
        presenter.bindView(this);
        setHasOptionsMenu(true);
        setUpViews();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    protected FragmentComponent provideFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule())
                .appComponent(App.getAppComponent())
                .build();
    }

    protected FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }

    protected abstract void inject();

    protected abstract void setUpViews();

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.unbindView();
    }

}
