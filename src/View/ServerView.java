/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ServerControl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 503
 */
public class ServerView implements Runnable {

    private final int portNumber = 9876;
    private ServerSocket myServer;
    private Socket clientSocket;

    public ServerView() {

    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void run() {
        while (true) {
            try {
                myServer = new ServerSocket(portNumber);
                showMessage("TCP server is running...");
                while (!Thread.currentThread().isInterrupted()) {
                    clientSocket = myServer.accept();
                    System.out.println(clientSocket.getInetAddress());

                    Thread serverThread = new Thread(new ServerControl(clientSocket));
                    serverThread.start();
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServerView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
