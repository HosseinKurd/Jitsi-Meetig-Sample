package modularization.libraries.dataSource.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class LocationModel
        implements Serializable {

    private String id = "";
    private String pictureUrl = "";
    private String name = "";
    private String description = "";
    private String city = "";
    private String address = "";
    private String workingTime = "";
    private String website = "";
    private ArrayList<String> phones = new ArrayList<>();
    private ArrayList<LocationServiceModel> services = new ArrayList<>();

    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public LocationModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<LocationServiceModel> getServices() {
        return services;
    }

    public void setServices(ArrayList<LocationServiceModel> services) {
        this.services = services;
    }

    public static ArrayList<LocationModel> wrapData(Object contentInfo) {
        ArrayList<LocationModel> arrayLocations = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            LocationModel locationModel = new LocationModel();
            locationModel.setName("دفاتر ثبت ازدواج و طلاق " + i);
            String description = "دفاتر ازدواج و طلاق از نهادهای تخصصی مدنی هستند که بخشی از وظایف اجرایی سازمان ثبت اسناد و املاک کشور را انجام میدهند. این دفاتر از نظر مالی مستقل هستند و به طور خصوصی اداره می شوند؛ ولی سازمان ثبت اسناد و املاک کشور سردفتران (مدیران دفاتر) را گزینش میکند قوانین این دفاتر را تنظیم و ابلاغ می کند و بر عملکرد این دفاتر نظارت می کند ";
            StringBuilder descLongText = new StringBuilder().append(description).append(description).append(description).append(description).append(description).append(description).append(description);
            locationModel.setDescription(descLongText.toString() + " " + i);
            locationModel.setAddress("تهران ، کریم خان ، ایرانشهر " + i);
            locationModel.setWebsite("https://lawone.ir");
            locationModel.setWorkingTime("۸ تا ۱۲");

            ArrayList<String> arrayPhones = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                String phone = "0213456789" + j;
                arrayPhones.add(phone);
            }
            locationModel.setPhones(arrayPhones);

            ArrayList<LocationServiceModel> arrayServices = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                LocationServiceModel locationServiceModel = new LocationServiceModel();
                locationServiceModel.setTitle("مدارک مورد نیاز برای طلاق " + j);
                locationServiceModel.setDescription(descLongText + " " + j);
                arrayServices.add(locationServiceModel);
            }
            locationModel.setServices(arrayServices);


            locationModel.setLatitude(35.6892 + i * 0.01);
            locationModel.setLongitude(51.3890 + i * 0.01);

            arrayLocations.add(locationModel);
        }

        return arrayLocations;
    }
}
