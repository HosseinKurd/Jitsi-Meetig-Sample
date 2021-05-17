package modularization.libraries.dataSource.repository;

import modularization.libraries.dataSource.repository.remote.loginAPI.LoginAPI;
import modularization.libraries.dataSource.viewModels.LoginViewModel;

public class RepositoryManagerRemote {

    private static RepositoryManagerRemote instance;

    public RepositoryManagerRemote() {
    }

    public static RepositoryManagerRemote newInstance() {

        if (instance == null)
            instance = new RepositoryManagerRemote();
        return instance;
    }

    public void callRegister(LoginViewModel loginViewModel) {
        LoginAPI.getInstance().register(loginViewModel);
    }

    public void callActivate(LoginViewModel loginViewModel) {
        LoginAPI.getInstance().activate(loginViewModel);
    }

}
