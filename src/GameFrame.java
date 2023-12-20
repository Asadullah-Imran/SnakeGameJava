import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        add(new GamePanel());
        setTitle("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
