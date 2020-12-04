/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ServerControl;

/**
 *
 * @author 503
 */
public class ServerView {

    public ServerView() throws Exception {
        System.out.println("run now");
        new ServerControl();
        showMessage("TCP server is running...");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
