package ua.owox.test.searchimage.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.owox.test.searchimage.data.repository.Repository;
import ua.owox.test.searchimage.di.module.DataModule;



@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

    Repository getRepository();

}