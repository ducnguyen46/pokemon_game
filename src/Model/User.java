/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String username;
    private String password;
    private int score;

    public User(int id, String name, String username, String password, int score) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.score = score;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", score=" + score + '}';
    }
    
}