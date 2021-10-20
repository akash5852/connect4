/*
 Team Naughty Alligators
 Connect 4 Game
 DrawingSurface Class
 */
package graphicstesting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DrawingSurface extends JPanel implements KeyListener {
    //pictures 
    private final Image arrows = new ImageIcon("src\\arrows.png").getImage();
    private final Image logo = new ImageIcon("src\\connect4.PNG").getImage();
    private final Image htpBtn = new ImageIcon("src\\button_how-to-play.png").getImage();
    private final Image localBtn = new ImageIcon("src\\button_local.png").getImage();
    private final Image onlineBtn = new ImageIcon("src\\button_online.png").getImage();
    private final Image quitBtn = new ImageIcon("src\\button_quit.png").getImage();
    private final Image space = new ImageIcon("src\\space.png").getImage();
    //creates a label for the one gif we have
    static JLabel gif = new JLabel();
    //sets the game state to main menu
    String gameState = "Main Menu";
    //uses monitor resolution to find the sizes and creates variables for it
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int width = (int) screenSize.getWidth();
    static int height = (int) screenSize.getHeight();
    //creates variables for the center of the screen along with circle x and y values.
    static int backgroundCircleX = -1500;
    static int backgroundCircleY = -1600;
    static int centerX = width / 2;
    static int centerY = height / 2;
    //creates the board array and startingg positions
    static int buttonPosition = 0, columnPosition = 0, rowPosition = 0, chipXPosition = centerX - 450, chipYPosition = centerY - 500;
    static int board[][] = new int[7][6];
    //starts with the red player and creates the two players
    static String playerMove = "red";
    static Player playerRed = new Player("red");
    static Player playerBlack = new Player("black");
    //sets the win variable to false
    static boolean win = false;
    //variable used so the first spacepress used to select a different menu in not processesed in that menu as a spacebar key press
    boolean firstSpace = false;
  
    public DrawingSurface() {
        this.add(gif);
        gif.setText("");
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //code runs if the game state is "Main Menu"
        if (gameState.equals("Main Menu")) {
            //code runs if the user hits the left arrow key
            if (code == KeyEvent.VK_LEFT) {
                //subtracts the button position by one if the user isint on the far left position
                if (buttonPosition != 0) {
                    buttonPosition--;
                }
            }
            //code runs if the user hits the right arrow key
            if (code == KeyEvent.VK_RIGHT) {
                //adds the button position by one if the user isint on the far right position
                if (buttonPosition != 3) {
                    buttonPosition++;
                }
            }
            //code runs if the user hits the spacebar
            if (code == KeyEvent.VK_SPACE) {
                if (buttonPosition == 0) {
                    //asks users to enter their names
                    playerRed = new Player(JOptionPane.showInputDialog("Please enter the name of Player 1"));
                    playerBlack = new Player(JOptionPane.showInputDialog("Please enter the name of Player 2"));
                    //opens the game menu
                    gameState = "Game Menu";
                    //runs game reset
                    gameReset();
                }  else if (buttonPosition == 2) {
                    //opens the instructions game menu
                    gameState = "Instructions Menu";
                } else if (buttonPosition == 3) {
                    //closes the game
                    System.exit(0);
                }
            }
        }
        //code runs if the game state is "Game Menu"
        if (gameState.equals("Game Menu")) {
            //code runs if the user hits the left arrow key
            if (code == KeyEvent.VK_LEFT) {
                //subtracts the column position by one is the current position isint on the far left
                if (columnPosition != 0) {
                    columnPosition--;
                }
                //sets the chip position to a new x value to match the column selected 
                chipXPosition = centerX - 450 + columnPosition * 130;
            }
            //code runs if the user hits the right arrow key
            if (code == KeyEvent.VK_RIGHT) {
                //adds the column position by one is the current position isint on the far right
                if (columnPosition != 6) {
                    columnPosition++;
                }
                //sets the chip position to a new x value to match the column selected 
                chipXPosition = centerX - 450 + columnPosition * 130;
            }
            //code runs if the user hits the spacebar
            if (code == KeyEvent.VK_SPACE) {
                if (firstSpace) {
                    placeChip();
                } else {
                    //resets firstSpace
                    firstSpace = true;
                }
            }
            //code runs if the user hits the backspace key
            if (code == KeyEvent.VK_BACK_SPACE) {
                //opens the main menu
                gameState = "Main Menu";
                //resets firstSpace
                firstSpace = false;
            }
        }
        //code runs if the game state is "Instructions Menu"
        if (gameState.equals("Instructions Menu")) {
            //code runs if the user hits the backspace key
            if (code == KeyEvent.VK_BACK_SPACE) {
                //opens the main menu
                gameState = "Main Menu";
                //resets firstSpace
                firstSpace = false;
            }
        }
    }

    //used to implement keylistener
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    private void gameUpdate(Chip newC) {
        if (playerMove.equals("red")) {
            playerRed.addChip(newC);
        } else {
            playerBlack.addChip(newC);
        }
        //column win check (vertical)
        for (int c = 0; c < 7; c++) {
            int counter = 0;
            for (int r = 0; r < 5; r++) {
                //if counter is 3
                if (board[c][r] == board[c][r + 1] && board[c][r] != 0) {
                    counter++;
                    if (counter == 3) {
                        //a player has won
                        win = true;
                        r = 5;
                        c = 7;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        //row win check (horizontal)
        for (int r = 0; r < 6; r++) {
            int counter = 0;
            for (int c = 0; c < 6; c++) {
                if (board[c][r] == board[c + 1][r] && board[c][r] != 0) {
                    counter++;
                    if (counter == 3) {
                        //a player has won
                        win = true;
                        r = 6;
                        c = 6;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[c][r] == board[c + 1][r + 1] && board[c + 1][r + 1] == board[c + 2][r + 2] && board[c + 2][r + 2] == board[c + 3][r + 3] && board[c][r] != 0) {
                    win = true;
                }
            }
        }

        for (int r = 0; r < 3; r++) {
            for (int c = 3; c < 7; c++) {
                if (board[c][r] == board[c - 1][r + 1] && board[c - 1][r + 1] == board[c - 2][r + 2] && board[c - 2][r + 2] == board[c - 3][r + 3] && board[c][r] != 0) {
                    win = true;
                }
            }
        }

        if (Chip.numChips == 42) {
            tie();
            win = false;
        }

        if (win == true) {
            if (playerMove.equals("red")) {
                won(playerRed);
            } else {
                won(playerBlack);
            }
            columnPosition = 0;
        }

        if (playerMove.equals("red")) {
            playerMove = "black";
        } else {
            playerMove = "red";
        }
    }

    private void placeChip() {
        int row = 0, column = columnPosition;
        while (board[column][row] != 0) {
            row++;
        }
        if (row < 6) {
            if (playerMove.equals("red")) {
                board[column][row] = 1;
            } else {
                board[column][row] = 2;
            }
            Chip temp = new Chip(column, row);
            rowPosition = row;
            gameUpdate(temp);
        }
    }

    private void won(Player p) {
        String input = "";
        boolean validInput = false;
        //outputs which player won that round
        JOptionPane.showMessageDialog(null, "player " + p.getName() + " wins");
        //updates the highscores
        updateHighScores();
        //only runs while validInput = false to to force playres to enter a valid input
        while (!validInput) {
            //asks user if they want to play again
            input = JOptionPane.showInputDialog("Do you want to play again? (y/n)");
            //if the user inputs "y" or "n" it sets validInput to true
            if (input.equals("y") || input.equals("n")) {
                validInput = true;
            }
        }
        //if the input is "n" it takes the user back to the main menu
        if (input.equals("n")) {
            firstSpace = false;
            gameState = "Main Menu";
        }
        //runs game resetcode
        gameReset();
    }

    private void tie() {
        String input = "";
        boolean validInput = false;
        //outputs that the game was a tie
        JOptionPane.showMessageDialog(null, "The game was a tie");
        //only runs while validInput = false to to force playres to enter a valid input
        while (!validInput) {
            //asks user if they want to play again
            input = JOptionPane.showInputDialog("Do you want to play again? (y/n)");
            //if the user inputs "y" or "n" it sets validInput to true
            if (input.equals("y") || input.equals("n")) {
                validInput = true;
            }
        }
        //if the input is "n" it takes the user back to the main menu
        if (input.equals("n")) {
            firstSpace = false;
            gameState = "Main Menu";
        }
    }

    private void gameReset() {
        //creates new players for the new game
        playerRed = new Player(playerRed.getName());
        playerBlack = new Player(playerBlack.getName());
        //sets the player move to red
        playerMove = "red";
        //resets the board
        board = new int[7][6];
        //sets win to false
        win = false;
        //resets the number of chips
        Chip.setNumChips(0);
    }

    private void updateHighScores() {
        try {
            File f;
            f = new File("src/graphicstesting/Highscores");
            Scanner s = new Scanner(f);
            FileWriter fw;
            PrintWriter pw;
            int numScores = Integer.parseInt(s.nextLine());
            int scores[][] = new int[numScores + 1][2];
            String names[] = new String[numScores + 1];
            String newHighscore;
            for (int i = 0; i < numScores; i++) {
                scores[i][1] = i;
                names[i] = s.nextLine();
                scores[i][0] = Integer.parseInt(s.nextLine());
            }

            s.close();

            scores[numScores][1] = numScores;
            if (playerMove.equals("red")) {
                scores[numScores][0] = playerRed.getNumChips();
                names[numScores] = playerRed.getName();
            } else {
                scores[numScores][0] = playerBlack.getNumChips();
                names[numScores] = playerBlack.getName();
            }

            quikSort(scores, 0, scores.length - 1);

            newHighscore = "" + (numScores + 1);
            for (int i = 0; i < numScores + 1; i++) {
                newHighscore += "\n" + names[scores[i][1]] + "\n"
                        + scores[i][0];
            }
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);
            pw.println(newHighscore);
            pw.close();
        } catch (IOException e) {
            System.out.println("error: " + e);
        }
    }

    public static int quikSortPart(int nums[][], int low, int high) {
        //a temporary int value holder
        int temp;
        //the pivot is set to the value of nums at high
        int pivot = nums[high][0];
        //i is set to low subtract 1
        int i = low - 1;
        //for loop with x from low to high
        for (int x = low; x < high; x++) {
            //if the value of nums at x is less than the pivot
            if (nums[x][0] < pivot) {
                //add one to i
                i++;
                //temp is set to the value of nums at i
                temp = nums[i][0];
                //nums at i is set to nums at x
                nums[i] = nums[x];
                //nums at x is set to temp
                nums[x][0] = temp;
            }
        }
        //temp is set to the value of nums at i add 1
        temp = nums[i + 1][0];
        //nums at i add 1 is set to nums at high
        nums[i + 1][0] = nums[high][0];
        //nums at high is set to temp
        nums[high][0] = temp;

        //temp is set to the value of nums at i add 1
        temp = nums[i + 1][1];
        nums[i + 1][1] = nums[high][1];
        nums[high][1] = temp;

        return i++;
    }

    public static void quikSort(int nums[][], int low, int high) {
        //increases the loop counter by 1
        //if the low value is less than the high vakue
        if (low < high) {
            //p is set to partAsc nums, low, high
            int p = quikSortPart(nums, low, high);
            //calls itself with nums, low, p - 1
            quikSortPart(nums, low, p - 1);
            //calls itself with nums, p + 1, high
            quikSortPart(nums, p + 1, high);
        }
    }

    private void gameMenu(Graphics g) {
        int gridX = centerX - 450, gridY, columnSelectedX = -455;
        Graphics2D g2d = (Graphics2D) g;
        //sets text to a new font
        g.setFont(new Font("Arial", Font.BOLD, 50));
        //paints new colors pink
        g2d.setPaint(Color.pink);
        //draws a rectangle which covers the whole background
        g2d.fillRect(0, 0, width, height);
        //sets the position of circle above the column selected
        columnSelectedX = columnSelectedX + columnPosition * 130;
        //paints new shapes cyan
        g2d.setColor(Color.cyan);
        //creates a round rectangle that highlights the selected column
        g2d.fillRoundRect(centerX + columnSelectedX, centerY - 360, 110, 740, 40, 40);
        //paints new shapes white
        g2d.setPaint(Color.white);
        //loops for each column on the game grid
        for (int i = 0; i < 7; i++) {
            //sets gridY to the left most column
            gridY = centerY - 340;
            //loops for each row on the game grid
            for (int j = 0; j < 6; j++) {
                //draws a circles to show the game grid
                g2d.fillOval(gridX, gridY, 100, 100);
                //adds 120 for the different rows
                gridY += 120;
            }
            //adds 130 for the different columns
            gridX += 130;
        }
        //plaints new shapes black
        g2d.setColor(Color.BLACK);
        //draws bar at the bottom of the game 
        g2d.fillRoundRect(centerX - 453, centerY + 370, 887, 20, 20, 20);
        //draws strings with the player names 
        g2d.drawString(playerRed.getName(), centerX - 450, centerY + 445);
        g2d.drawString(playerBlack.getName(), centerX + 250, centerY + 445);
        //loop that runs for the number of chips the red player has
        for (int i = 0; i < playerRed.getNumChips(); i++) {
            //paints new shapes red 
            g2d.setColor(Color.red);
            //draws all the chips the red player has placed
            g2d.fillOval(centerX - 450 + playerRed.getChip(i).getColumn() * 130, centerY + 260 - playerRed.getChip(i).getRow() * 120, 100, 100);
        }
        //loop that runs for the number of chips the black player has
        for (int i = 0; i < playerBlack.getNumChips(); i++) {
            //paint new shapes black
            g2d.setColor(Color.black);
            //draws all the chips the black player has placed
            g2d.fillOval(centerX - 450 + playerBlack.getChip(i).getColumn() * 130, centerY + 260 - playerBlack.getChip(i).getRow() * 120, 100, 100);
        }

        if (playerMove.equals("red")) {
            //paints new shapes red to show which players turn it is
            g2d.setColor(Color.red);
            //draws a circle above the selected column 
            g2d.fillOval(centerX + columnPosition * 130 - 450, centerY - 480, 100, 100);
        } else {
            //paint new shapes black to show which players turn it is
            g2d.setColor(Color.black);
            //draws a circle above the selected column 
            g2d.fillOval(centerX + columnPosition * 130 - 450, centerY - 480, 100, 100);
        }
        //repaints the window
        repaint();
        requestFocusInWindow();
    }

    private void mainMenu(Graphics g) {
        //the instructional gif closes
        gif.setVisible(false);
        //creates 2d graphics
        Graphics2D g2d = (Graphics2D) g;
        int selectionBallX = -700;
        //creates a new color for the main menu background
        Color backColor = new Color(204, 255, 230);
        //creates a new color for the circles in the main menu background
        Color backgroundCircles = new Color(26, 255, 144);
        //paints new circles as backColor
        g2d.setPaint(backColor);
        //draws a rectangle which covers the whole screen
        g2d.fillRect(0, 0, width, height);
        //paints new circles as backColorCircles
        g2d.setPaint(backgroundCircles);
        //jumps the group of circles to the left when they reach too raf right
        if (backgroundCircleX > centerX - width - 100) {
            backgroundCircleX += -1000;
        } else {
            //moves background circles to the right
            backgroundCircleX++;
        }
        //jumps the group of circles back up when they reach too low
        if (backgroundCircleY > centerY - height - 100) {
            backgroundCircleY -= 1000;
        } else {
            //moves background circles down
            backgroundCircleY++;
        }
        //loop to draw in all background circles
        for (int i = 0; i < 5000; i += 50) {
            for (int j = 0; j < 5000; j += 100) {
                //draws in two circles to cover the main menu background
                g2d.fillOval(centerX + backgroundCircleX + i, centerY + backgroundCircleY + j, 20, 20);
                g2d.fillOval(centerX + backgroundCircleX + (i + 25), centerY + backgroundCircleY + (j + 50), 20, 20);
            }
        }

        //changes the main menu selection balls x position based on user inputs
        selectionBallX = selectionBallX + buttonPosition * 455;
        //paints newly drawn shapes black
        g2d.setColor(Color.BLACK);
        //draws a small circle to indicate which option the user selected in the main menu
        g2d.fillOval(centerX + selectionBallX, centerY + 450, 25, 25);
        //repaints the main menu drawing
        repaint();
        //draws in images for the buttons
        g.drawImage(htpBtn, centerX - 25, centerY + 300, null);
        g.drawImage(localBtn, centerX - 865, centerY + 300, null);
        g.drawImage(onlineBtn, centerX - 410, centerY + 300, null);
        g.drawImage(quitBtn, centerX + 525, centerY + 300, null);
        g.drawImage(logo, centerX - 325, centerY / 4, null);
        requestFocusInWindow();
    }

    private void instructionsMenu(Graphics g) {
        //shows instructional gif
        gif.setVisible(true);
        Graphics2D g2d = (Graphics2D) g;
        //sets a new font
        g.setFont(new Font("Arial", Font.BOLD, 50));
        //makes a new color for the background
        Color backColor = new Color(204, 255, 230);
        //sets paint to the new background color
        g2d.setPaint(backColor);
        //draws a rectangle to cover the background
        g2d.fillRect(0, 0, width, height);
        //sets paint to light gray
        g2d.setColor(Color.lightGray);
        //draws 2 lines to seperate sections of the how to play menu
        g2d.drawLine(0, centerY, width, centerY);
        g2d.drawLine(centerX, 0, centerX, height);
        //sets paint to orange
        g2d.setColor(Color.ORANGE);
        //draws lines to seperate sections of the instructions menu
        g2d.drawLine(0, centerY - 5, width, centerY - 5);
        g2d.drawLine(0, centerY + 5, width, centerY + 5);
        g2d.drawLine(centerX - 5, 0, centerX - 5, height);
        g2d.drawLine(centerX + 5, 0, centerX + 5, height);
        //sets paint to black
        g2d.setColor(Color.black);
        //makes a new font for labels
        Font lbls = new Font("ARIAL", Font.TRUETYPE_FONT, 30);
        //sets the new font lbls
        g2d.setFont(lbls);
        //draws string to title to different secitons of the instructions menu
        g2d.drawString(" Controls ", centerX / 50, centerY / 10);
        g2d.drawString(" How to Win ", centerX + 20, centerY / 10);
        g2d.drawString(" Play Locally  ", centerX / 50, centerY + 65);
        g2d.drawString(" Play Online ", centerX + 20, centerY + 65);
        //draws images for the instructions menu
        g2d.drawImage(arrows, 0, centerY / 150, null);
        g2d.drawImage(space, 130, centerY - 130, null);
        //draws in instructions for the instruction menu
        g2d.drawString("Left and right arrows to move column", centerX / 2 - 50, centerY / 4);
        g2d.drawString("Spacebar to drop chip", centerX / 2 - 50, centerY - 100);
        g2d.drawString("To win the game all you need to do is get 4 chips of the same", centerX + 10, centerY - 100);
        g2d.drawString("color in a diagonal, horizontal or vertical row.", centerX + 10, centerY - 70);
        //draws in the instructional gif 
        gif.setIcon(new ImageIcon("src\\connect4.gif"));
        gif.setLocation(centerX + 25, centerY - 400);
        //draws in instructions for the instruction menu
        g2d.drawString("When playing locally, the person who enters there name first starts,", centerX / 50, centerY + 140);
        g2d.drawString("and the players take turns until one player wins or they tie.", centerX / 50, centerY + 170);
        g2d.drawString("When playing online, the user is given the option to host a game or join", centerX + 10, centerY + 140);
        g2d.drawString("a game. If host is chosen the game will ask for a port number that can", centerX + 10, centerY + 170);
        g2d.drawString("be any arbitrary number. If client is selected they will have to enter", centerX + 10, centerY + 200);
        g2d.drawString("the host's port in order to connect. This feature is no longer functioning", centerX + 10, centerY + 230);
        //repaints the menu
        repaint();

    }

    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draws whichever menu the game state is equal to
        if (gameState.equals("Main Menu")) {
            mainMenu(g);
        } else if (gameState.equals("Online Menu")) {
            mainMenu(g);
        } else if (gameState.equals("Game Menu")) {
            gameMenu(g);
        } else if (gameState.equals("Instructions Menu")) {
            instructionsMenu(g);
        }
    }
}
