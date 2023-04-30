package com.example.turdstepper;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class TurdController {

    @FXML
    private AnchorPane AP1 = new AnchorPane();
    public Board gameBoard = new Board(600,600,30,30);
    private File turdImageFile = new File("src/main/resources/icons/turd16x16.png");
    private File flagImageFile = new File("src/main/resources/icons/flag16x16.png");
    private boolean winCondition = false;
    private boolean loseCondition = false;

    public TurdController() {

    }

    @FXML
    public void initialize() {
        drawInitialBoardTiles();

        // Mouse click eventhandler
        AP1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            int xCoord = (int) mouseEvent.getSceneX();
            int yCoord = (int) mouseEvent.getSceneY();
            int cellColumn = (xCoord / gameBoard.getCellSideLength());
            System.out.println(cellColumn);
            int cellRow = (yCoord / gameBoard.getCellSideLength());
            System.out.println(cellRow);
            Cell[][] cellArray = gameBoard.getBoardCells();

            // Left mouse button click
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                reveal(cellArray[cellColumn][cellRow]);
            // Right mouse button click
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                cellArray[cellColumn][cellRow].setHasFlag(true);
            }

            updateBoardTiles();
            mouseEvent.consume();
            gameConditionCheck();

        });


    }

    public void gameConditionCheck() {
        if (winCondition || loseCondition) {
            endGame();
        }
    }

    public void endGame() {
        Text endGameText = new Text();
        endGameText.setTextAlignment(TextAlignment.CENTER);
        endGameText.setWrappingWidth(gameBoard.getBoardWidth());
        endGameText.setY(gameBoard.getBoardHeight()*0.2);
        endGameText.setText("GAME FINISHED!");
        endGameText.setFont(Font.font("Impact",60));
        endGameText.setStroke(Color.WHITE);
        endGameText.setStrokeWidth(2);
        endGameText.setStrokeType(StrokeType.OUTSIDE);
        endGameText.setFill(Color.BLACK);
        AP1.getChildren().removeAll();
        AP1.getChildren().add(endGameText);

        TranslateTransition tt = new TranslateTransition(Duration.minutes(2),endGameText);
        int translationYTarget = gameBoard.getBoardHeight();
        tt.toYProperty().setValue(translationYTarget);
        tt.setAutoReverse(true);
        tt.play();
        RotateTransition rt = new RotateTransition(Duration.minutes(2));
        rt.setByAngle(360);
        rt.setNode(endGameText);
        rt.play();

    }

    public void updateBoardTiles() {
        ArrayList<Text> cellTextArrayList = new ArrayList<>();
        ArrayList<ImageView> flagImageArrayList = new ArrayList<>();
        ImageView turdImg = null;

        // Store existing image views and cell texts to be removed
        ArrayList<ImageView> existingImageViews = new ArrayList<>();
        ArrayList<Text> existingCellTexts = new ArrayList<>();

        for (Node node : AP1.getChildren()) {
            if (node.getClass().equals(Text.class)) {
                existingCellTexts.add((Text) node);
                continue;
            }
            if (node.getClass().equals(ImageView.class)) {
                existingImageViews.add((ImageView) node);
                continue;
            }
            Cell cell = (Cell) node;
            if (cell.isHasFlag()) {
                flagImageArrayList.add(plantFlag(cell));
            }
            if (cell.isRevealed()) {
                if (cell.isTurd()) {
                    turdImg = revealTurd(cell);
                } else {
                    Text cellText = revealBlank(cell);
                    cellTextArrayList.add(cellText);
                }
            }
        }

        // Remove existing image views and cell texts
        AP1.getChildren().removeAll(existingImageViews);
        AP1.getChildren().removeAll(existingCellTexts);

        // Add new image views
        AP1.getChildren().addAll(cellTextArrayList);
        AP1.getChildren().addAll(flagImageArrayList);
        if (turdImg != null) {
            AP1.getChildren().add(turdImg);
            loseCondition = true;
        }
    }


    //    public void updateBoardTiles() {
//        ArrayList<Text> cellTextArrayList = new ArrayList<>();
//        ArrayList<ImageView> flagImageArrayList = new ArrayList<>();
//        ImageView turdImg = null;
//        for (Node node : AP1.getChildren()) {
//            if (node.getClass().equals(Text.class)) {
//                continue;
//            }
//            if (node.getClass().equals(ImageView.class)) {
//                continue;
//            }
//            Cell cell = (Cell)node;
//            if (cell.isHasFlag()) {
//                plantFlag(cell);
//                flagImageArrayList.add(plantFlag(cell));
//            }
//            if (cell.isRevealed()) {
//                if (cell.isTurd()) {
//                    revealTurd(cell);
//                    turdImg = revealTurd(cell);
//                } else {
//                    Text cellText = revealBlank(cell);
//                    cellTextArrayList.add(cellText);
//                }
//            }
//        }
//        AP1.getChildren().addAll(cellTextArrayList);
//        AP1.getChildren().addAll(flagImageArrayList);
//        if (turdImg != null) {
//            AP1.getChildren().add(turdImg);
//            loseCondition = true;
//        }
//    }
    public ImageView revealTurd(Cell cell) {
        cell.setFill(Color.BEIGE);

        ImageView turdImgView = new ImageView();
        turdImgView.setImage(new Image(turdImageFile.toURI().toString()));
        turdImgView.setPreserveRatio(true);
        turdImgView.setFitHeight(cell.getSideLength());
        turdImgView.setFitWidth(cell.getSideLength());
        turdImgView.setX(cell.getxPosition());
        turdImgView.setY(cell.getyPosition());
        return turdImgView;
    }
    public ImageView plantFlag(Cell cell) {
        cell.setHasFlag(true);
        ImageView flagImgView = new ImageView();
        flagImgView.setImage(new Image(flagImageFile.toURI().toString()));
        flagImgView.setPreserveRatio(true);
        flagImgView.setFitHeight(cell.getSideLength());
        flagImgView.setFitWidth(cell.getSideLength());
        flagImgView.setX(cell.getxPosition());
        flagImgView.setY(cell.getyPosition());
        return flagImgView;
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