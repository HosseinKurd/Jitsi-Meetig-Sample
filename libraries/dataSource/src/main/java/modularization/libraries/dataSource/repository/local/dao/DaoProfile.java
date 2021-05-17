package modularization.libraries.dataSource.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import modularization.libraries.dataSource.models.ProfileModel;

@Dao
public interface DaoProfile {

    @Query("SELECT COUNT(*) FROM profile_table")
    int getCountTable();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(ProfileModel profileModel);

    @Query("UPDATE profile_table SET first_name = :firstName," +
            "gender=:gender," +
            "phone=:phone," +
            "email=:email," +
            "picture_url=:pictureUrl," +
            "birth_date=:birthday," +
            "national_id=:nationalId," +
            "economic_id=:economicId" +
            " WHERE id =:rowId")
    void update(long rowId, String firstName, String gender, String phone, String email, String pictureUrl, String birthday, String nationalId, String economicId);

    @Delete
    void deleteProfile(ProfileModel profileModel);

    @Query("SELECT * FROM profile_table LIMIT 1")
    LiveData<ProfileModel> fetchProfile();
}
