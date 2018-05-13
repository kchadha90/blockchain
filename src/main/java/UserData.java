/*
 * Copyright (c) 2018, kchadha
 */

/**
 * UserData class to prototype any data to be stored in the block
 */
public class UserData {
    public String getDomain() {
        return domain;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String domain;
    private String username;
    private String password;

    public UserData(String domain, String username, String password) {
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return this.domain + this.username + this.password;
    }
}
