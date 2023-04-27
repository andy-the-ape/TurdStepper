package com.example.turdstepper;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class TurdController {

    @FXML
    AnchorPane AP1 = new AnchorPane();
    Board gameBoard = new Board(600,600,30,50);

    public TurdController() {

    }

    @FXML
    public void initialize() {
        drawInitialBoardTiles();

        AP1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            int xCoord = (int) mouseEvent.getSceneX();
            int yCoord = (int) mouseEvent.getSceneY();

            int cellColumn = (xCoord / gameBoard.getCellSideLength());
            System.out.println(cellColumn);
            int cellRow = (yCoord / gameBoard.getCellSideLength());
            System.out.println(cellRow);
            Cell[][] cellArray = gameBoard.getBoardCells();
            reveal(cellArray[cellColumn][cellRow]);
            updateBoardTiles();
            mouseEvent.consume();
        });
    }

    private void updateBoardTiles() {
        for (Node node : AP1.getChildren()) {
            Cell cell = (Cell)node;
            if (cell.isRevealed()) {
                cell.setFill(Color.WHITESMOKE);
            }
            else {
                cell.setFill(Color.LIGHTGRAY);
            }
        }
    }

    public void drawInitialBoardTiles() {
        Cell[][] boardCellArray = gameBoard.getBoardCells();

        for (int col = 0; col < boardCellArray.length; col++) {
            for (int row = 0; row < boardCellArray[col].length; row++) {
                Cell cell = boardCellArray[col][row];
                cell.setX(gameBoard.getCellSideLength() * col);
                cell.setY(gameBoard.getCellSideLength() * row);
                cell.setWidth(cell.getSideLength());
                cell.setHeight(cell.getSideLength());
                cell.setStroke(Color.DIMGRAY);
                cell.strokeWidthProperty().setValue(1);
                if (cell.isRevealed()) {
                    cell.setFill(Color.WHITESMOKE);
                }
                else {
                    cell.setFill(Color.LIGHTGRAY);
                }
                AP1.getChildren().add(cell);
            }
        }
    }

    public void reveal(Cell cell) {
        cell.setRevealed(true);

    }
}