package com.example.onlineshopping.DataType;

public class Item {
    private final String idi;
    private final String itemName, itemPrice, count, totalPrice;

    public Item(String idi, String itemName, String itemPrice, String count, String totalPrice) {
        this.idi = idi;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public String getIdi() {
        return idi;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getCount() {
        return count;
    }

    public String getTotalPrice() {
        return totalPrice;
    }
}
