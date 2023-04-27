package com.example.turdstepper;

import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private int sideLength = 20;
    private int rowNumber;
    private int columnNumber;
    private boolean turd = false;
    private boolean revealed = false;

    public Cell(int columnNumber, int rowNumber, int sideLength) {
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.sideLength = sideLength;
    }

    public boolean isTurd() {
        return turd;
    }

    public void setTurd(boolean turd) {
        this.turd = turd;
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
