package com.example.imageinsert.model;

public class User {
    private String name,password,email,phone;
    private int id;
    private byte[] img;

    public User() {
    }

    public User(byte[] img) {
        this.img = img;
    }

    public User(String name, String password, String email, String phone, byte[] img) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.img =img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
