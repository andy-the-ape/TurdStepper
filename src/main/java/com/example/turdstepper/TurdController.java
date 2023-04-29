package com.example.turdstepper;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TurdController {

    @FXML
    AnchorPane AP1 = new AnchorPane();
    Board gameBoard = new Board(600,600,30,30);

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

    public void updateBoardTiles() {
        ArrayList<Text> cellTextArrayList = new ArrayList<>();
        for (Node node : AP1.getChildren()) {
            if (node.getClass().equals(Text.class)) {
                continue;
            }
            Cell cell = (Cell)node;
            if (cell.isRevealed()) {
                if (cell.isTurd()) {
                    revealTurd(cell);
                } else {
                    Text cellText = revealBlank(cell);
                    cellTextArrayList.add(cellText);
                }
            }

        }
        AP1.getChildren().addAll(cellTextArrayList);
    }
    public void revealTurd(Cell cell) {
        cell.setFill(Color.SADDLEBROWN);
    }

    public Text revealBlank(Cell cell) {
        cell.setFill(Color.WHITESMOKE);

        Text cellText = new Text();
        cellText.setX(cell.getxPosition() + cell.getSideLength()*0.3);
        cellText.setY(cell.getyPosition() + cell.getSideLength()*0.7);
        cellText.setText(calculateNearbyTurds(cell));
        cellText.setFont(Font.font("Arial",18));
        cellText.setFill(Color.BLACK);
        return cellText;
    }

    public String calculateNearbyTurds(Cell cell) {
        int turdCounter = 0;
        Cell[][] cellArray = gameBoard.getBoardCells();

        for (int col = cell.getColumnNumber()-1; col <= cell.getColumnNumber()+1; col++) {
            for (int row = cell.getRowNumber()-1; row <= cell.getRowNumber()+1; row++) {
                if (col >= 0 && col < cellArray.length && row >= 0 && row < cellArray[0].length) {
                    if (!(col == cell.getColumnNumber() && row == cell.getRowNumber()) && cellArray[col][row].isTurd()) {
                        turdCounter++;
                        System.out.println("column: "+col + " and row: "+row);
                    }
                }
            }
        }
        return String.valueOf(turdCounter);
    }

    public void drawInitialBoardTiles() {
        Cell[][] boardCellArray = gameBoard.getBoardCells();

        for (int col = 0; col < boardCellArray.length; col++) {
            for (int row = 0; row < boardCellArray[col].length; row++) {
                Cell cell = boardCellArray[col][row];
                cell.setX(gameBoard.getCellSideLength() * col);
                cell.setxPosition(gameBoard.getCellSideLength() * col);
                cell.setY(gameBoard.getCellSideLength() * row);
                cell.setyPosition(gameBoard.getCellSideLength() * row);
                cell.setWidth(cell.getSideLength());
                cell.setHeight(cell.getSideLength());
                cell.setStroke(Color.DIMGRAY);
                cell.strokeWidthProperty().setValue(1);
                cell.setFill(Color.LIGHTGRAY);

                AP1.getChildren().add(cell);
            }
        }
    }

    public void reveal(Cell cell) {
        cell.setRevealed(true);

    }
}