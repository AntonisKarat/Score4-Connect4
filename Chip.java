package game.objects;

public class Chip {
	private int x, y;
	private char symbol;
	private Player player;
	
	public Chip() {	}
	public Chip(char symbol) {
		this.symbol = symbol;
	}
	public Chip(int x, int y, char symbol, Player player) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.player = player;
	}
	
	//Just Getters and Setters methods
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public void setSymbol(char symbol) {
		if(symbol == 'x' || symbol == 'o') {
			this.symbol = symbol;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
