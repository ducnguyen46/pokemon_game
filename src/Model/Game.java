/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.sql.Time;

/**
 *
 * @author ACER
 */
public class Game implements Serializable{
    private int id;
    private Time timeFinish;

    public Game() {
    }

    public Game(int id, Time timeFinish) {
        this.id = id;
        this.timeFinish = timeFinish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(Time timeFinish) {
        this.timeFinish = timeFinish;
    }
    
}
