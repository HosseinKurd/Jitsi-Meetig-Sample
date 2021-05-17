package modularization.libraries.dataSource.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import modularization.libraries.dataSource.repository.local.dao.DaoProfile;
import modularization.libraries.dataSource.models.ProfileModel;

@Database(entities = {ProfileModel.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {

    public abstract DaoProfile daoProfile();

    // todo: other DAO object added here as lawone data access object layer

}
