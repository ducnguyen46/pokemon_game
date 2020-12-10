package Game;

/**
 * ----------------- @author nguyenvanquan7826 -----------------
 * ---------------nguyenvanquan7826.wordpress.com --------------
 */
public class MyMain {
	MyFrame frame;
        static Algorithm algorithm;

	public MyMain(Algorithm algorithm) {
            this.algorithm = algorithm;
		frame = new MyFrame(algorithm);
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();
	}

	public static void main(String[] args) {
		new MyMain(algorithm);
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
