/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPRun;

import View.ServerView;

public class ServerRun {

    public static void main(String[] args){
        Thread serverViewThread = new Thread(new ServerView());
        serverViewThread.start();
    }
}
