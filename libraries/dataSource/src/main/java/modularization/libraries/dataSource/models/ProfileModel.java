package modularization.libraries.dataSource.models;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import modularization.libraries.dataSource.R;
import modularization.libraries.uicomponents.MagicalImageView;

@Entity(tableName = "profile_table")
public class ProfileModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 1;

    @ColumnInfo(name = "profile_id")
    @SerializedName("profile_id")
    private String profileId = "";

    @ColumnInfo(name = "nick_name")
    @SerializedName("nick_name")
    private String nickName = "";

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    private String firstName = "";

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    private String lastName = "";

    @ColumnInfo(name = "picture_url")
    @SerializedName("picture_url")
    private String pictureUrl = "";

    @ColumnInfo(name = "gender")
    @SerializedName("gender")
    private String gender = "";

    @ColumnInfo(name = "birth_date")
    @SerializedName("birth_date")
    private String birthDate = "";

    // extra
    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email = "";

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    private String phoneNumber = "";

    // extra map
    @ColumnInfo(name = "national_id")
    @SerializedName("national_id")
    private String nationalId = "";

    @ColumnInfo(name = "economic_id")
    @SerializedName("economic_id")
    private String economicId = "";

    public ProfileModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getEconomicId() {
        return economicId;
    }

    public void setEconomicId(String economicId) {
        this.economicId = economicId;
    }

    public List<Map<String, Object>> getData() {

      /*  data = new ArrayList<>();

        if (phoneNumber != null) {
            Map<String, Object> phoneNumberCity = new HashMap<>();
            phoneNumberCity.put("key", EnumProfileList.PHONE_NUMBER.getValueStr());
            phoneNumberCity.put("value", phoneNumber);
            data.add(phoneNumberCity);
        } else {
            Map<String, Object> phoneNumberCity = new HashMap<>();
            phoneNumberCity.put("key", EnumProfileList.PHONE_NUMBER.getValueStr());
            phoneNumberCity.put("value", ProfileFunction.extractProfileInfoStr(profileDataList, EnumProfileList.PHONE_NUMBER));
            data.add(phoneNumberCity);
        }


        if (email != null) {
            Map<String, Object> mapEmail = new HashMap<>();
            mapEmail.put("key", EnumProfileList.EMAIL.getValueStr());
            mapEmail.put("value", email);
            data.add(mapEmail);
        } else {
            Map<String, Object> mapEmail = new HashMap<>();
            mapEmail.put("key", EnumProfileList.EMAIL.getValueStr());
            mapEmail.put("value", String.valueOf(ProfileFunction.extractProfileInfo(profileDataList, EnumProfileList.EMAIL)));
            data.add(mapEmail);
        }


        return data;*/
        return null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ProfileModel profileModel = (ProfileModel) obj;
        return profileId.equals(profileModel.getProfileId());
    }

    @BindingAdapter("avatarImage")
    public static void loadImage(MagicalImageView view, String imageUrl) {
        view.setImageUrlRound(imageUrl, R.drawable.ic_logo_lawone_svg);
    }

}
