/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPRun;

import Control.ClientControl;
import View.LoginView;

/**
 *
 * @author DELL
 */
public class ClientRun {

    public static void main(String[] args) {
        ClientControl clientControl = new ClientControl();
        clientControl.openConnection();
        LoginView view = new LoginView(clientControl);
        view.setVisible(true);
    }
}
