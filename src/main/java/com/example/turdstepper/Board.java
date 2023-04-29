package com.example.turdstepper;

import java.util.ArrayList;

public class Board {

    private int boardHeight = 600;
    private int boardWidth = 600;
    private int cellSideLength = 20;
    private Cell[][] boardCells;

    public Board(int boardHeight, int boardWidth, int cellSideLength, int turdFactor) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.cellSideLength = cellSideLength;
        this.boardCells = new Cell[boardHeight / cellSideLength][boardWidth / cellSideLength];
        populateBoard(this.boardCells, turdFactor);
    }

    public void populateBoard(Cell[][] boardCells, int turdFactor) {
        int adjustedTurdFactor = turdFactor%100;
        for (int col = 0; col < boardCells.length; col++) {
            for (int row = 0; row < boardCells[col].length; row++) {
                boardCells[col][row] = new Cell(col, row, this.cellSideLength);
                if (Math.random()*100 < adjustedTurdFactor) {
                    boardCells[col][row].setTurd(true);
                }
            }
        }
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getCellSideLength() {
        return cellSideLength;
    }

    public Cell[][] getBoardCells() {
        return boardCells;
    }
}
