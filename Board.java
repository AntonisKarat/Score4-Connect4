package game.objects;

import java.util.Scanner;

public class Board {
	private static final int MIN_DIM = 4, MAX_DIM = 15;
	private static final int OFFSET = 3;							//The offset of the slots to the outline of the board.
	private int rows, columns;
	private Chip[][] board = new Chip[MAX_DIM + 1][MAX_DIM + 1];
	private Scanner sc = new Scanner(System.in);
	
	public Board() { }
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setRowsMember() {
		this.rows = sc.nextInt();
	}
	
	public int getColumns() {
		return columns;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setColumnsMember() {
		this.columns = sc.nextInt();
	}
	
	//Return true if the dimension of the board that the player gave is between MIN_DIM and MAX_DIM, else false!
	public boolean isInsideDims(int dimension) {
		return (dimension >= MIN_DIM && dimension <= MAX_DIM);
	}
	
	//Return the chip at x, y coordinates of the board!
	public Chip getChipAt(int x, int y){
		return board[x][y];
	}
	
	//Returns true if the content of the board at x, y coordinates is '-', else false!
	public boolean isEmptyAt(int x, int y) {
		return (getChipAt(x, y).getSymbol() == '-');
	}
	
	//Sets the player's chip to the x and y coordinates of the board!
	public void setPlayerChipAt(Player player, int x, int y) {
		getChipAt(x, y).setSymbol(player.getChip().getSymbol());
	}	
	
	//Prints the board!
	public void printBoard() {
		System.out.println();
		
		int boardLength = this.columns * 2 + OFFSET;
		
		int row = 0;
		for (int i = 0; i < this.rows; i++) {
			int column = 0;
			for (int j = 0; j < boardLength; j++) {
				if (j == 0) {
					System.out.print("|");
				}
				else if (j == boardLength - 1) {
					System.out.println("|");;
				}
				else if (j % 2 == 1) {
					System.out.print(" ");;
				}
				else {
					System.out.print(getChipAt(row, column).getSymbol());
					column++;
				}
			}
			row++;
		}
		
		for (int j = 0; j < boardLength; j++) {
			System.out.print("-");
		}
		System.out.println();
		
		int number = 1;
		for (int j = 0; j < boardLength; j++) {
			if (j == 0 || (j % 2 == 1) || j > (boardLength - OFFSET)){
				System.out.print(" ");
			}
			else {
				System.out.print(number);
				number++;
			}
		}
	}
	
	//Initializes an empty chip board with the dimensions given by the player!
	public void initBoard() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				board[i][j] = new Chip(i, j, '-', null);
			}
		}
	}
}
