/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ACER
 */
public class Game_Detail {
    private int idGame;
    private int idPlayer;
    private float point;

    public Game_Detail() {
    }

    public Game_Detail(int idGame, int idPlayer, float point) {
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.point = point;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
    
}
