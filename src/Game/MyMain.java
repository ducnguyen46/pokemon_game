package Game;

import Control.ClientControl;

/**
 * ----------------- @author nguyenvanquan7826 -----------------
 * ---------------nguyenvanquan7826.wordpress.com --------------
 */
public class MyMain {
	MyFrame frame;
        static Algorithm algorithm;
        static ClientControl clientControl;

	public MyMain(ClientControl clientControl, Algorithm algorithm) {
            this.clientControl = clientControl;
            this.algorithm = algorithm;
		frame = new MyFrame(clientControl, algorithm);
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();
	}

	public static void main(String[] args) {
		new MyMain(clientControl, algorithm);
                
	}
        
        public void stop(){
            frame.dispose();
        }

	class MyTimeCount extends Thread {

		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				frame.setTime(frame.getTime() - 1);
				if (frame.getTime() == 0) {
                                    System.out.println("Score: " + frame.getGraphicsPanel().getScore());
					frame.showDialogNewGame(
							"Full time\nDo you want play again?", "Lose");
				}
			}
		}
	}
}
