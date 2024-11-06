package game;

import java.util.Random;
import game.objects.*;

public class Game {
	private Board board = new Board();
	private Player[] players = new Player[2];
	private int nextPlayer;
	
	public Game() { }
	
	public void run() {
		setup();
		board.printBoard();
				
		Random rand = new Random();
		nextPlayer = rand.nextInt(2);
		
		int ending = -1; 
		boolean playsAgain = false;
		while(true) {
			ending = gameHasEnded();
			if(ending >= 0) {
				break;
			}
			
			int col;
			switch(nextPlayer) {
			case 0:
				do {
					col = players[0].selectColumn();
					playsAgain = players[0].makeMove(board, col);
				}while(playsAgain);
				nextPlayer++;
				break;
			case 1:
				do {
					col = players[1].selectColumn();
					playsAgain = players[1].makeMove(board, col);
				}while(playsAgain);
				nextPlayer--;
				break;
			}
			
			board.printBoard();
		}
		
		printEndMessage(ending);
	}
	
	//Sets up the game (Player names, player's chips, board dimensions)
	private void setup() {
		players[0] = new Player();
		players[1] = new Player();

		System.out.println("This is Score4");
		
		//Players choose their name!
		System.out.print("Please enter the name of the 1st player: ");
		players[0].setPlayerNameMember();
		while(players[0].getPlayerName().isEmpty() || players[0].getPlayerName().isBlank()) {
			System.out.print("Incorrect input! Please enter the name of the 1st player: ");
			players[0].setPlayerNameMember();
		}
		
		System.out.print("Please enter the name of the 2nd player: ");
		players[1].setPlayerNameMember();
		while(players[1].getPlayerName().isEmpty() || players[1].getPlayerName().isBlank()) {
			System.out.print("Incorrect input! Please enter the name of the 2nd player: ");
			players[1].setPlayerNameMember();
		}
		
		//Player chooses their chip, the other takes the other automatically!
		System.out.print(players[0].getPlayerName() + ", please select your chip: ");
		players[0].setChipMember();
		while(!(players[0].getChip().getSymbol() == 'x' || players[0].getChip().getSymbol() == 'o') && players[0].getChip().getSymbol() == ' '){
			System.out.print("Incorrect input! " + players[0].getPlayerName() + ", please select your chip (x or o): ");
			players[0].setChipMember();
		}

		//The other player gets automatically the other chip!
		char symbol = ((players[0].getChip().getSymbol() == 'x') ? 'o' : 'x');;
		Chip other = new Chip(symbol);					//I just want the symbol.
		players[1].setChip(other);
		System.out.println(players[1].getPlayerName() + ", your chip is: " + players[1].getChip().getSymbol());
		
		//A Player chooses the dimensions of the table!
		System.out.print("Please enter the number of rows: ");
		board.setRowsMember();
		while (!board.isInsideDims(board.getRows())) {
			System.out.print("Incorrect input! Please enter the number of rows [4-15]: ");
			board.setRowsMember();
		}
		
		System.out.print("Please enter the number of columns: ");
		board.setColumnsMember();
		while (!board.isInsideDims(board.getColumns())) {
			System.out.print("Incorrect input! Please enter the number of columns [4-15]: ");
			board.setColumnsMember();
		}
		
		board.initBoard();
	}

	private void printEndMessage(int code) {
		nextPlayer = ((nextPlayer == 0) ? 1 : 0); //Last player who won
		
		switch(code) {
		case 0:
		case 1:
		case 2:
			System.out.println();
			System.out.println();
			System.out.println("GAME OVER. THE WINNER IS " + players[nextPlayer].getPlayerName());
			break;
		case 3:
			System.out.println();
			System.out.println();
			System.out.println("GAME OVER. WE HAVE A DRAW");
			break;
		}
	}
	
	//Endings enum for the readability.
	private enum Endings{
		HORIZONTAL, 	
		VERTICAL,		
		DIAGONAL,		
		DRAW,			
		STILLINPROGRESS
	}
	
	//Condition to end the game.
	private int gameHasEnded() {
		Endings end = getEnding();
		
		switch(end) {
		case HORIZONTAL:
			return 0;
		case VERTICAL:
			return 1;
		case DIAGONAL:
			return 2;
		case DRAW:
			return 3;
		case STILLINPROGRESS:
		default:
			return -1;
		}
		
	}
	
	private Endings getEnding() {
		int lastPlayer = ((nextPlayer == 0) ? 1 : 0);
		int connect4;
		
		//Draw
		boolean boardIsFull = true;
		for(int col = 0; col < board.getColumns() - 1; col++) {
			if(board.isEmptyAt(0, col)) {
				boardIsFull = false;
				break;
			}
		}
		if(boardIsFull) {
			return Endings.DRAW;
		}
		
		//Horizontal
		for(int row = board.getRows() - 1; row >= 0; row--) {
			connect4 = 0;
			for(int col = 0; col < board.getColumns() - 1; col++) {
				if(	board.getChipAt(row, col).getSymbol() == players[lastPlayer].getChip().getSymbol() &&
					board.getChipAt(row, col).getSymbol() == board.getChipAt(row, col + 1).getSymbol()){
					connect4++;
					if(connect4 == 3) {
						return Endings.HORIZONTAL; 
					}
				}
				else {
					connect4 = 0;
				}
			}
		}
		
		//Vertical
		for(int col = 0; col < board.getColumns() - 1; col++) {
			connect4 = 0;
			for(int row = 0; row < board.getRows() - 1; row++) {
				if( board.getChipAt(row, col).getSymbol() == players[lastPlayer].getChip().getSymbol() && 
					board.getChipAt(row, col).getSymbol() == board.getChipAt(row + 1, col).getSymbol()) {
					connect4++;
					if(connect4 == 3) {
						return Endings.VERTICAL;
					}
				}
				else {
					connect4 = 0;
				}
			}
		}
		
		//Diagonal down-up
		for(int row = 3; row <= board.getRows() - 1; row++){
			for(int col = 0; (col + 3) <= board.getColumns() - 1; col++) {
				char symbol = board.getChipAt(row, col).getSymbol();
				if(symbol == players[lastPlayer].getChip().getSymbol()
				&& symbol == board.getChipAt(row - 1, col + 1).getSymbol() 
				&& symbol == board.getChipAt(row - 2, col + 2).getSymbol() 
				&& symbol == board.getChipAt(row - 3, col + 3).getSymbol()) 
				{
					return Endings.DIAGONAL;
				}
			}
		}
		
		//Diagonal up-down
		for(int row = 0; (row + 3) <= board.getRows() - 1; row++) {
			for(int col = 0; (col + 3) <= board.getColumns() - 1; col++) {
				char symbol = board.getChipAt(row, col).getSymbol();
				if(symbol == players[lastPlayer].getChip().getSymbol() 
				&& board.getChipAt(row, col).getSymbol() == board.getChipAt(row + 1, col + 1).getSymbol() 
				&& board.getChipAt(row, col).getSymbol() == board.getChipAt(row + 2, col + 2).getSymbol() 
				&& board.getChipAt(row, col).getSymbol() == board.getChipAt(row + 3, col + 3).getSymbol())
				{
					return Endings.DIAGONAL;
				}
			}
		}
		
		return Endings.STILLINPROGRESS;
	}
}
