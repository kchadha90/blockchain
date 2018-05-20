/*
 * Copyright (c) 2018, kchadha
 */

package user;

import io.netty.util.internal.StringUtil;

import java.util.Objects;

/**
 * user.UserData class to prototype any data to be stored in the block
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

    /**
     * @param domain
     * @param username
     * @param password
     * @throws IllegalArgumentException
     */
    public UserData(String domain, String username, String password) throws IllegalArgumentException {

        if (StringUtil.isNullOrEmpty(domain) || StringUtil.isNullOrEmpty(username)
                || StringUtil.isNullOrEmpty(password)) {
            throw new IllegalArgumentException();
        }

        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return this.domain + this.username + this.password;
    }
}
