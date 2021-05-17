package modularization.libraries.dataSource.repository;

import android.content.Context;

import androidx.room.Room;

import modularization.libraries.dataSource.repository.local.ApplicationDatabase;
import modularization.libraries.dataSource.utils.PublicValue;

public class RepositoryManagerLocal {

    private static RepositoryManagerLocal instance;

    private ApplicationDatabase database;

    public static RepositoryManagerLocal newInstance(Context context) {
        if (instance == null)
            instance = new RepositoryManagerLocal(context);
        return instance;
    }

    private RepositoryManagerLocal(Context context) {
        // database instance
        database = Room.databaseBuilder(context
                , ApplicationDatabase.class
                , PublicValue.DATABASE_NAME)
                .allowMainThreadQueries() //Todo : should be removed and impl thread instead of it
                .build();
    }

    public ApplicationDatabase getDatabase() {
        return database;
    }

    ////////////
    // TEMPLATE
    ////////////

    /*
    public LiveData<ProfileModel> getProfile() {
        return database.daoProfile().fetchProfile();
    }

    public long insertProfile(ProfileModel profileModel) {
        return database.daoProfile().insert(profileModel);
    }

    public boolean updateProfileToDB(ProfileModel profileModel, long rowId) {
        int count = database.daoProfile().getCountTable();

        if (count > 0) {
            database.daoProfile().update(
                    rowId,
                    profileModel.getFirstName(),
                    profileModel.getGender(),
                    profileModel.getPhoneNumber(),
                    profileModel.getEmail(),
                    profileModel.getPictureUrl(),
                    profileModel.getBirthDate(),
                    profileModel.getNationalId(),
                    profileModel.getEconomicId());

            return true;
        }
        return false;
    }
    */

}
