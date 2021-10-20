/*
 Team Naughty Alligators
 Connect 4 Game
 Chip Class
 */
package graphicstesting;

public class Chip {

    static int numChips;
    private int num;
    private int column;
    private int row;

    /**
     *
     * @param c
     * @param r
     */
    public Chip(int c, int r) {
        row = r;
        column = c;
        num = numChips;
        numChips++;
    }

    /**
     *
     * @return
     */
    public int getNum() {
        return num;
    }

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @param num
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     *
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     *
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @param numChips
     */
    public static void setNumChips(int numChips) {
        Chip.numChips = numChips;
    }

    /**
     *
     * @param c
     * @return
     */
    public Chip clone(Chip c) {
        Chip newC = new Chip(c.column, c.row);
        newC.setNum(c.num);
        return newC;
    }

    /**
     *
     * @param c
     * @return
     */
    public boolean equals(Chip c) {
        return num == c.getNum() && column == c.getColumn() && row == c.getRow();
    }

    public String toString() {
        return "Number: " + num + "\n"
                + "Column: " + column + "\n"
                + "Row: " + row;
    }

}
