package com.example.onlineshopping;

public class Shop {
    private final String id;
    private final String name, shopkeeperName, shopkeeperMobileNumber, cityName, address;

    public Shop(String id, String name, String shopkeeperName, String shopkeeperMobileNumber, String cityName, String address) {
        this.id = id;
        this.name = name;
        this.shopkeeperName = shopkeeperName;
        this.shopkeeperMobileNumber = shopkeeperMobileNumber;
        this.cityName = cityName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShopkeeperName() {
        return shopkeeperName;
    }

    public String getShopkeeperMobileNumber() {
        return shopkeeperMobileNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAddress() {
        return address;
    }
}
