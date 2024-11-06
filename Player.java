package game.objects;

import java.util.Scanner;

public class Player {
	private String name;
	private Chip chip;
	private Scanner sc = new Scanner(System.in);
	
	public Player() { }
	
	public String getPlayerName() {
		return name;
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	public void setPlayerNameMember() {
		this.name = sc.nextLine();
	}
	
	public Chip getChip() {
		return chip;
	}
	
	public void setChip(Chip chip) {
		this.chip = chip;
	}
	
	public void setChipMember() {
		char symbol = sc.nextLine().charAt(0);
		this.chip = new Chip(symbol);
		this.chip.setSymbol(this.chip.getSymbol());
	}
	
	public void setChipToPlayer(Chip chip) {
		setChip(chip);	
		chip.setPlayer(this);
	}
	
	public int selectColumn() {
		System.out.println();
		System.out.println();
		
		System.out.print(this.name + ", your turn. Select column: ");
		int col = sc.nextInt();
		
		return col;
	}
	
	//Player selects a column:
	public boolean makeMove(Board board, int column) {
		int col = column - 1;
		if(!(col >= 0 && col <= (board.getColumns() - 1))) {				//Chips inside the board
			System.out.println();
			System.out.println("Can not place the chip there!"); 		
			
			return true;
		}
		else {
			int row = -1;
			while(row < board.getRows() && board.getChipAt((row + 1), col) != null && board.isEmptyAt((row + 1), col)) {
				row++;
			}
			if(row != -1) {
				board.setPlayerChipAt(this, row, col);						//Change the symbol of the board to the player's symbol
				setChipToPlayer(board.getChipAt(row, col));					//Link chip to the player
				
				return false;
			}
			else {
				System.out.println();
				System.out.println("The chip fell out of the board! There is no space left inside the column!");
				
				return true;
			}
		}
	}
}
