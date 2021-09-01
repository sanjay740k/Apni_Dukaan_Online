package com.example.onlineshopping.DataType;

public class User {
    private final String id;
    private final String userName, userMobileNumber, cityName, address;

    public User(String id, String userName, String userMobileNumber, String cityName, String address) {
        this.id = id;
        this.userName = userName;
        this.userMobileNumber = userMobileNumber;
        this.cityName = cityName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAddress() {
        return address;
    }
}
