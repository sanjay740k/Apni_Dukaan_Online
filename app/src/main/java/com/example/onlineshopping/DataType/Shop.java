package com.example.onlineshopping.DataType;

public class Shop {
    private final String id;
    private final String shopName, shopkeeperName, shopkeeperMobileNumber, cityName, address;

    public Shop(String id, String shopName, String shopkeeperName, String shopkeeperMobileNumber, String cityName, String address) {
        this.id = id;
        this.shopName = shopName;
        this.shopkeeperName = shopkeeperName;
        this.shopkeeperMobileNumber = shopkeeperMobileNumber;
        this.cityName = cityName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
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
