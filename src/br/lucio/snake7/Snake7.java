package br.lucio.snake7;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Snake with a sequence of 7 cells
 * 
 * @author lucio
 *
 */
public class Snake7 implements Cloneable {

	private List<Cell> listCells;

	private static final int SIZE_OF_SNAKE = 7;

	/**
	 * Constructor
	 */
	public Snake7() {
		setListCells(new ArrayList<Cell>());
	}

	/**
	 * Adds a cell in a 7-Snake structure
	 * 
	 * @param cell
	 */
	public void addCell(Cell cell) {
		getListCells().add(cell);
	}

	/**
	 * Removes the last cell added
	 */
	public void removeLastCell() {
		if (!getListCells().isEmpty()) {
			getListCells().remove(getListCells().size() - 1);
		}
	}

	/**
	 * 
	 * @return The Last cell added
	 */
	public Cell getLastCell() {
		if (!getListCells().isEmpty()) {
			return getListCells().get(getListCells().size() - 1);
		}

		return null;
	}

	/**
	 * Verifies if a 7-Snake is with the completed structure
	 * 
	 * @return
	 */
	public boolean isCompleted() {
		return getListCells().size() == SIZE_OF_SNAKE;
	}

	/**
	 * Verifies if the position (line, column) is valid in a 7-Snake structured.
	 * 
	 * @param line
	 * @param column
	 * @return
	 */
	public boolean isValidPosition(int line, int column) {
		int qtdCellsAdjacents = 0;

		// verifies if the new position is valid for the last adjacent columns
		for (Cell cell : getListCells()) {
			int resultLine = line - cell.getLine();
			int resultColumn = column - cell.getColumn();

			if (((resultLine == 0) && (resultColumn == 1))
					|| ((resultLine == 1) && (resultColumn == 0))) {
				qtdCellsAdjacents++;
			}

		}

		// the new position has only 1 adjacent cell
		if (qtdCellsAdjacents == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the sum of values of the 7-Snake's cells
	 * @return
	 */
	public int sumValues() {
		int total = 0;
		for (Cell cell : listCells) {
			total += cell.getValue();
		}

		return total;
	}

	/**
	 * Verifies if two snakes has conflict in its cells
	 * 
	 * @param otherSnake
	 * @return
	 */
	public boolean conflict(Snake7 otherSnake) {

		for (Cell cell : getListCells()) {
			for (Cell cellOtherSnake : otherSnake.getListCells()) {
				if (cell == cellOtherSnake) {
					return true;
				}
			}
		}

		return false;
	}
	
	public List<Cell> getListCells() {
		return listCells;
	}

	public void setListCells(List<Cell> listCells) {
		this.listCells = listCells;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Snake7 obj = (Snake7) super.clone();

		if (getListCells() != null) {
			List<Cell> newList = new ArrayList<Cell>();
			for (Cell cell : getListCells()) {
				newList.add(cell);
			}

			obj.setListCells(newList);
		}

		return obj;
	}
}
