package com.example.projectnqueen;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public int size=8;//default size
    public Stage stage1;
    public GridPane gridPane = new GridPane();

    @Override
    public void start(Stage stage) {
        VBox control;
        TextField text_size = new TextField("8");

        Label label_size,label_x,label2;
        Button btn;
        label_size=new Label(" Enter size of board ");
        label_size.setStyle("-fx-text-fill:white");
        label_size.setFont(new Font(35));
        text_size.setStyle("-fx-background-color:white");
        text_size.setMaxWidth(150);
        text_size.setAlignment(Pos.CENTER);
        text_size.setFont(new Font(30));
        btn=new Button("Start");
        btn.setPrefHeight(30);
        btn.setPrefWidth(250);
        btn.setStyle("-fx-background-color:#c0c0c0;-fx-text-fill:#2b3443");
        btn.setFont(new Font(26));
        label2=new Label("NQueen-Algorithm");
        label2.setStyle("-fx-background-color:#2b3443;-fx-text-fill:white");
        label2.setFont(new Font(60));
        label_x=new Label();
        label_x.setPrefHeight(50);
        label_x.setFont(new Font(40));
        label_x.setStyle("-fx-background-color:red;-fx-text-fill:white");
        label_x.setText("(XXX)Impossible");
        Image image = new Image("queen.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(150);
        gridPane.setAlignment(Pos.CENTER);
        control=new VBox(20,imageView,label2,text_size,btn);
        control.setAlignment(Pos.CENTER);
        control.setStyle("-fx-background-color:#2b3443");


        //Button activation event
        btn.setOnAction(actionEvent -> {
            size= Integer.parseInt(text_size.getText());
            // Create the chess board
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    Region chessBox = new Region();
                    chessBox.setMinSize((int) (800.0 / size), (int) (680.0 / size));
                    chessBox.setMaxSize(100,100);
                    if ((i + j) % 2 == 0)
                        chessBox.setStyle("-fx-background-color: #ffffff;");
                    else
                        chessBox.setStyle("-fx-background-color: #000000;");
                    gridPane.add(chessBox, j, i);
                }
            //Algorithm testing
            int[][] board = new int[size][size];
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    board[i][j] = 0;
            if (!solveNQ(board, 0))
                gridPane.add(label_x,0,0);
            stage1.show();
            stage.close();
        });


        //Create Chess Board window
        Scene scene1 = new Scene(gridPane,800,680);
        stage1 = new Stage();
        stage1.setScene(scene1);
        stage1.setTitle("NQueen-App");

        //Create start window
        Scene scene = new Scene(control,800,700);
        stage.setScene(scene);
        stage.setTitle("Start-App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //the Constraints
    public boolean isSafe(int[][] board, int row, int col) {

        //row constraint
        for (int i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;
        //Main diameter constraint
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;
        //Secondary diameter constraint
        for (int i = row, j = col; j >= 0 && i < size; i++, j--)
            if (board[i][j] == 1)
                return false;
        return true;
    }

    //NQueen
    public boolean solveNQ(int[][] board, int col) {
        if (col >= size)
            return true;

        for (int i = 0; i < size; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;
                if (solveNQ(board, col + 1)) {
                    Image image = new Image("queen.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth((int) (800.0/size));
                    imageView.setFitHeight((int) (700.0/size));
                    gridPane.add(imageView, i,col);
                    return true;
                }
                board[i][col] = 0;
            }
        }

        return false;
    }
}