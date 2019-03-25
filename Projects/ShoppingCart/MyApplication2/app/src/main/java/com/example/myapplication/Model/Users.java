package com.example.myapplication.Model;

public class Users {
    private String name, phone, password, mail, cart, orders;
    public Users() {

    }

    public Users(String name, String phone, String password, String mail, String cart, String orders) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.mail = mail;
        this.cart = cart;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
