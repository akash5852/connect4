/*
 Team Naughty Alligators
 Connect 4 Game
 Player Class
 */
package graphicstesting;

/**
 *
 * @author Akash
 */
public class Player {

    private String name;
    private String colour;
    private Chip chips[];
    private int numChips;

    /**
     *
     * @param n
     */
    public Player(String n) {
        name = n;
        chips = new Chip[21];
        numChips = 0;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param i
     * @return
     */
    public Chip getChip(int i) {
        if (numChips > i) {
            return chips[i];
        }
        return null;
    }

    /**
     *
     * @param chips
     */
    public void setChips(Chip[] chips) {
        this.chips = chips;
    }

    /**
     *
     * @param c
     */
    public void addChip(Chip c) {
        if (numChips < 21) {
            chips[numChips] = c;
            numChips++;
        }
    }

    /**
     *
     * @return
     */
    public int getNumChips() {
        return numChips;
    }

    /**
     *
     * @param p
     * @return
     */
    public Player clone(Player p) {
        return new Player(name);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean equals(Player p) {
        return name.equals(p.getName());
    }

    public String toString() {
        return "Name: " + name;
    }
}
