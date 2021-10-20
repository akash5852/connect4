/*
 Team Naughty Alligators
 Connect 4 Game
 GraphicsTesting Class
 */
package graphicstesting;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GraphicsTesting extends JFrame {

    public GraphicsTesting() {
        //create the User interface

        initUI();
        setVisible(true);
    }

    private void initUI() {
        //set title of the JFrame
        setTitle("Simple Java 2D example");
        //add a custom JPanel to draw on
        add(new DrawingSurface());
        //set the size of the window
        setSize(900, 900);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setFocusable(true);
        setUndecorated(true);
        setVisible(true);
        //tell the JFrame what to do when closed
        //this is important if our application has multiple windows
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //make sure that all UI updates are concurrency safe (related to multi threading)
        //much more detailed http://www.javamex.com/tutorials/threads/invokelater.shtml
        SwingUtilities.invokeLater(() -> {
            //instantiate the main window
            GraphicsTesting windowFrame = new GraphicsTesting();
        });
    }

}
