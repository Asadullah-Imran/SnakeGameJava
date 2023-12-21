import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        add(new GamePanel());
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
