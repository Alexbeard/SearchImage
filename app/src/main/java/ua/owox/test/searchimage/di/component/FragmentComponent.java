package ua.owox.test.searchimage.di.component;

import dagger.Component;
import ua.owox.test.searchimage.di.PerFragment;
import ua.owox.test.searchimage.di.module.FragmentModule;
import ua.owox.test.searchimage.ui.detail.DetailFragment;
import ua.owox.test.searchimage.ui.search.SearchFragment;


@PerFragment
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SearchFragment fragment);

    void inject(DetailFragment fragment);

}
