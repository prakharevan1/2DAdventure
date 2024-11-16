package platformer.mainstuff;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        //stuff u prob want to leave default:
        final boolean resizeable = false;
        final boolean isVisible = true;
        final String title = "Evan Project";

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(resizeable);
        window.setTitle(title);
        window.setLocationRelativeTo(null); //centered to screen
        window.setVisible(isVisible);

        gamePanel.startGameThread();
    }
}
