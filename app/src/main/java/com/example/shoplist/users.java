package com.example.shoplist;

class User {
    private String username;
    private String password;

    public User() {
        // Пустой конструктор нужен для Firebase
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры для username и password
    // ...
}
