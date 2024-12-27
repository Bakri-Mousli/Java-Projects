package chess;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application{
	static String absPath = "C:\\Projects-and-Folders\\Informatics-Engineering\\Aleppo-University\\Third-Year\\Second-Season\\Advanced-Algorithms\\Java-Projects\\Chess-App-1\\desktop-chess\\resources\\";
	final String white = "white";
	final String black = "black";
	int clicked = -1;
	Label[][] squares = new Label[8][8];
	Board gameBoard;
	BorderPane pane = new BorderPane();
	String topSide, bottomSide, endSpec = "";
	Button queen;
	Label moveLabel = new Label();
	Vector lastClicked = new Vector();
	String leftImagePath = "C:\\Projects-and-Folders\\Informatics-Engineering\\Aleppo-University\\Third-Year\\Second-Season\\Advanced-Algorithms\\Java-Projects\\Chess-App-1\\desktop-chess\\resources\\left_arrow.png";
	String rightImagePath = "C:\\Projects-and-Folders\\Informatics-Engineering\\Aleppo-University\\Third-Year\\Second-Season\\Advanced-Algorithms\\Java-Projects\\Chess-App-1\\desktop-chess\\resources\\right_arrow.png";
	Image leftArrow = new Image(new FileInputStream(leftImagePath));
	Image rightArrow = new Image(new FileInputStream(rightImagePath));
	ArrayList<Board> storedBoards = new ArrayList<Board>();
	ArrayList<String> storedMoves = new ArrayList<String>();
	int moveKey = 0, maxMoveKey = 0, side = 1;
	boolean end = false;

    public Game() throws FileNotFoundException {
    }

    public void start(Stage primaryStage) throws Exception{
		gameBoard = new Board(black, white);
		storedBoards.add(gameBoard.clone(gameBoard));

		pane.setStyle("-fx-background-color: #e1c699;");

		this.setBoard(black, white);
		this.updateImageBoard(gameBoard);
		this.addEventsToBoard();
		
		this.setLeft();
		this.setRight();
		this.setTop();
		this.setBottom();
		Scene scene = new Scene(pane, 900, 690, Color.GREEN);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chess");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	 public static void main(String args[]) {
		 launch(args);
		 
		 }
	 
	 public void setBoard(String top, String bottom) {
		 topSide = top;
		 bottomSide = bottom; 
		 GridPane board = new GridPane();
		 int c = 1;
		
		 for(int a=0; a<8; a++) { c*= -1; 
			 for(int b=0; b<8; b++) { 
				 Label square = new Label();
				 squares[a][b] = square;
				 squares[a][b].setMinSize(80, 80);
				 
				 
				 if(c == -1) squares[a][b].setStyle("-fx-background-color: #f5f5dc;");
				 if(c == 1)  squares[a][b].setStyle("-fx-background-color: #add8e6;");
				 board.add(squares[a][b], a, b);
				 c*= -1; 
			 }
		 }
		 pane.setCenter(board);
		 
	 }
	 public void setLeft() {
		VBox left = new VBox();
		 if(topSide == white) {
			 for(int a=1; a<9; a++) {
				 Label num = new Label("  " + Integer.toString(a) + " ");
				 num.setMinSize(15, 80);
				 left.getChildren().add(num); 
			 }
		 }else{
			 for(int a=8; a>0; a--) {
				 Label num = new Label("  " + Integer.toString(a) + " ");
				 num.setMinSize(15, 80);
				 left.getChildren().add(num); 
			 }
		 } pane.setLeft(left);
	 }
	 public void setRight() {
		VBox right = new VBox();
		ScrollPane moveScroller = new ScrollPane();
		moveScroller.setMinSize(200, 500);
		
		moveLabel.setMinSize(100,20000);
		moveLabel.setAlignment(Pos.TOP_LEFT);
		moveScroller.setContent(moveLabel);
		right.getChildren().add(moveScroller);
		right.setPadding(new Insets(17,17,17,17));
		
		HBox forback = new HBox();
		Label undo = new Label(), redo = new Label();

		ImageView ivLarr = new ImageView(leftArrow);
		ivLarr.setFitHeight(100);
		ivLarr.setFitWidth(100);
		undo.setGraphic(ivLarr);
		
		undo.setOnMouseClicked(e ->{
			if(moveKey == 0) return;
			gameBoard = storedBoards.get(moveKey-1).clone(storedBoards.get(moveKey-1));
			this.printMoves(storedMoves.subList(0,moveKey-1), endSpec);
			moveKey--;
            try {
                this.updateImageBoard(gameBoard);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
		
		
		ImageView ivRarr = new ImageView(rightArrow);
		ivRarr.setFitHeight(100);
		ivRarr.setFitWidth(100);
		redo.setGraphic(ivRarr);
	
		redo.setOnMouseClicked(e ->{
			if(moveKey == maxMoveKey) return;
			moveKey++;
			this.printMoves(storedMoves.subList(0,moveKey), endSpec);
			gameBoard = storedBoards.get(moveKey).clone(storedBoards.get(moveKey));
            try {
                this.updateImageBoard(gameBoard);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
		
		
		forback.getChildren().addAll(undo,redo);	
		
		
		right.getChildren().add(forback);
		pane.setRight(right);  
	 }
	 public void setTop() {
			HBox top = new HBox();
			Button changeSide = new Button("Change Side");
			Button exit = new Button("Exit");
			changeSide.setOnAction(e ->{ 
				side *= -1;
				storedBoards.clear();
				storedMoves.clear();
				pane.setLeft(null);
				pane.setBottom(null);
				pane.setCenter(null);
				pane.setRight(null);
				moveLabel.setText(" ");
				if(side == -1) { gameBoard = new Board(white,black);
				this.setBoard(white, black); }
				if(side == 1) { gameBoard = new Board(black, white); 
				this.setBoard(black, white);}
				this.addEventsToBoard();
                try {
                    this.updateImageBoard(gameBoard);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                this.setRight();
				this.setLeft();
				this.setBottom();	
				storedBoards.add(gameBoard.clone(gameBoard));
				moveKey = 0; maxMoveKey = 0;
				end = false; endSpec = "";
			});
		Button draw = new Button("Claim Draw");
		draw.setOnAction(e ->{
			draws:{ 
					if(moveKey<6) break draws;
					if(this.isThreeFoldRepitition() || gameBoard.fiftyMoveCounter > 99) {
					gameBoard.staleMate = true;
					end = true;
					if(gameBoard.staleMate) endSpec = "0.5-0.5";
					this.printMoves(storedMoves, endSpec);
					}
			}
	   });
	   queen = new Button("Auto Queen");
	   queen.setOnAction(e ->{
		   gameBoard.autoQueen = gameBoard.autoQueen ? false : true;   
	   });
			top.getChildren().addAll(changeSide, draw, queen, exit);
			exit.setOnAction(e -> System.exit(0));
			pane.setTop(top);
	 }
	 
	 public void setBottom() {
		 HBox bottom = new HBox();	
		 Label blfill = new Label();
		 blfill.setMinSize(50, 9);
		 bottom.getChildren().add(blfill);
		 if(topSide.equals(black)) {
		 for(char a = 'a'; a<'i'; a++) {
			 Label let = new Label(Character.toString(a));
			 let.setMinSize(80, 15);
			 bottom.getChildren().add(let); 
		 }}else {
		for(char b = 'h'; b>='a'; b--) {
			 Label let = new Label(Character.toString(b));
			 let.setMinSize(80, 15);
			 bottom.getChildren().add(let); 
			 } 
		 }
		 
		 
		 
		 pane.setBottom(bottom);
}
	 
	 public void updateImageBoard(Board board) throws FileNotFoundException {
		 for(int j=0; j<8; j++) {
			 for(int h=0; h<8; h++) {
				 squares[j][h].setGraphic(null);
			 }
		 }
		 for(int a=0; a<8; a++) {
			 for(int d=0; d<8; d++) { 
				 if(board.isOccupied(a,d)) {
					Piece piece = board.getSquare(a,d);
					ImageView pieces;
					if(piece.side.equals(white)) {
					if(piece instanceof Pawn) {
						Image pawnWhite = new Image(new FileInputStream(absPath+"\\pawn_white.png"));

						pieces = new ImageView(pawnWhite);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);	
					}
					if(piece instanceof King) {
						Image rookWhite = new Image(new FileInputStream(absPath+"\\king_white.png"));
						pieces = new ImageView(rookWhite);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Rook) {
						Image rookWhite = new Image(new FileInputStream(absPath+"\\rook_white.png"));
					pieces = new ImageView(rookWhite);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);						
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Knight) {
						Image knight_white = new Image(new FileInputStream(absPath+"\\knight_white.png"));
						pieces = new ImageView(knight_white);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Bishop) {
						Image bishop_white = new Image(new FileInputStream(absPath+"\\bishop_white.png"));
						pieces = new ImageView(bishop_white);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Queen) {
						Image queen_white = new Image(new FileInputStream(absPath+"\\queen_white.png"));
						pieces = new ImageView(queen_white);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);	
					}}
					if(piece.side.equals(black)) { 
					if(piece instanceof Pawn) {
						Image pawnBlack = new Image(new FileInputStream("C:\\Projects-and-Folders\\Informatics-Engineering\\Aleppo-University\\Third-Year\\Second-Season\\Advanced-Algorithms\\Java-Projects\\Chess-App-1\\desktop-chess\\resources\\pawn_black.png"));

						pieces = new ImageView(pawnBlack);
						pieces.setFitHeight(80);
						pieces.setFitWidth(80);
						squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof King) {
						Image king_black = new Image(new FileInputStream(absPath+"\\king_black.png"));
						pieces = new ImageView(king_black);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Rook) {
						Image rookBlack = new Image(new FileInputStream(absPath+"\\rook_black.png"));
					pieces = new ImageView(rookBlack);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Knight) {
						Image knight_black = new Image(new FileInputStream(absPath+"\\knight_black.png"));
						pieces = new ImageView(knight_black);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Bishop) {
						Image bishop_black = new Image(new FileInputStream(absPath+"\\bishop_black.png"));
						pieces = new ImageView(bishop_black);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}
					if(piece instanceof Queen) {
						Image queen_black = new Image(new FileInputStream(absPath+"\\queen_black.png"));
						pieces = new ImageView(queen_black);
					pieces.setFitHeight(80);
					pieces.setFitWidth(80);
					squares[a][d].setGraphic(pieces);
					}}}}}
	 			}
	 public void addEventsToBoard() {
		 
		 for(int w=0; w<8; w++) {
			 for(int z=0; z<8; z++) { 
				 final int x = w, y = z;
				 squares[x][y].setOnMouseClicked(e ->{
						if(gameBoard.checkMate || end) return;

						if(clicked == -1) {
							if(!gameBoard.isOccupied(x,y)) return;
					 		if(gameBoard.whoseTurn == -1 && gameBoard.getSquare(x,y).side.equals(white)) return;
					 		if(gameBoard.whoseTurn == 1 && gameBoard.getSquare(x,y).side.equals(black)) return;
					 		lastClicked.setVector(x,y);
						}
						if(clicked == 1) { 
							if(gameBoard.isOccupied(x,y)) {
							if(gameBoard.getSquare(lastClicked).side.equals(gameBoard.getSquare(x,y).side)) {
								lastClicked.setVector(x, y);
								return;
								}}
						 
							Board moveBoard = new Board();
							moveBoard = gameBoard.clone(gameBoard);
							queen.setDisable(true);
							gameBoard.getSquare(lastClicked).move(new Vector(x,y), gameBoard);
					
							
								if(!gameBoard.wasMoveInvalid) {
									if(moveKey != maxMoveKey) {
//										if(maxMoveKey == 1 ) { storedBoards.remove(1); break key;}
										for(int j=maxMoveKey; j>=moveKey+1; j--) {
											storedBoards.remove(j);
											storedMoves.remove(j-1);
										}
									}
                                    try {
                                        this.updateImageBoard(gameBoard);
                                    } catch (FileNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    storedBoards.add(gameBoard.clone(gameBoard));
								
								moveKey = gameBoard.getMoveCount();
								maxMoveKey = gameBoard.getMoveCount();
								moveNotator mover = new moveNotator(moveBoard.getSquare(lastClicked), gameBoard.wasEP || moveBoard.isOccupied(x,y), gameBoard.inCheck, 
										gameBoard.checkMate, gameBoard.blackIsTop, moveBoard, new Vector(x,y), gameBoard.wasQC, gameBoard.wasKC);
								storedMoves.add(mover.getMoveNotation());
								if(gameBoard.staleMate) endSpec = "0.5-0.5";
								if(gameBoard.checkMate) {
									if(gameBoard.whoseTurn == -1) endSpec = "1-0";
									if(gameBoard.whoseTurn == 1) endSpec = "0-1";
								}
								if(gameBoard.checkMate || gameBoard.staleMate) { end = true; }
								this.printMoves(storedMoves, endSpec);
								
								}			
						}
						clicked *= -1;
					});	
			 }
		 }
	 }
	 public void printMoves(List<String> list, String theEnd) {
		 String formattedMoves = " ";
		 if(end && gameBoard.whoseTurn == 1) theEnd = "      " + theEnd;
		 for(int i=0; i<list.size(); i++) {
			 if(i % 2 == 0) { formattedMoves += "\n" + Integer.toString(i/2 + 1);
			 if(i<18) formattedMoves += ".          ";
			 if(i>=18 && i<198) formattedMoves += ".        ";
			 if(i>199) formattedMoves += ".         ";
			 }
			 formattedMoves += list.get(i) + "\t      ";
			 if(i % 2 == 0 && i == maxMoveKey-1 && end) formattedMoves += theEnd;
			 if(i % 2 !=0 && i == maxMoveKey-1 && end) formattedMoves += "\n      " + theEnd;
		 }
		 moveLabel.setText(formattedMoves);
	 }
	 
	 public boolean isThreeFoldRepitition(){

		 Board currBoard = storedBoards.get(moveKey);
		 int instances = 0;

		 for(int i = moveKey; i>=0; i -=2) {
			 if(this.areTheSame(currBoard, storedBoards.get(i))) instances++;		
			 if(instances == 4) return true;
		 }
		 
		 return false;
	 }
	 
	 public boolean areTheSame(Board a, Board b) {
		 
		 for(int c=0; c<8; c++){
			for(int d=0; d<8; d++){
				Piece aPiece = a.getSquare(c,d);
				Piece bPiece = b.getSquare(c,d);
				if(!this.areTheSame(aPiece, bPiece)) return false;
			}
		 }
		 
		 return true;
	 }
	 
	 public boolean areTheSame(Piece a, Piece b){
		 if(a == null && b == null) return true;
		 else if(a == null || b == null) return false;
		 return (a.side == b.side) && (a.getClass().equals(b.getClass()));
	 }
	 
	 
 } 

