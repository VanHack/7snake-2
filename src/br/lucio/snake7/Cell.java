package br.lucio.snake7;

/**
 * Represents a Cell from a Grid
 * 
 * @author lucio
 *
 */
public class Cell {

	private int line;
	private int column;
	private int value;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
