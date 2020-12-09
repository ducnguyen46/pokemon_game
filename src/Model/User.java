/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String username;
    private String password;
    private int score;
    private int state;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, int score, int state) {
        this.username = username;
        this.score = score;
        this.state = state;
    }
    
    public User(int id, String name, String username, String password, int score, int state) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.score = score;
        this.state = state;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object[] toObject(){
        return new Object[]{username, score, (state == 1? "Đang rảnh": "Đang bận")};
    }
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password 
                + ", score=" + score + ", state=" + state + '}';
    }
    
}
