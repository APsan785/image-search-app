package com.apsan.imagesearchapp.api.models;

public class PhotoUser {
    private String name;
    private String username;
    private String portfolio_url;

    public String getPortfolio_url() {
        return portfolio_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
