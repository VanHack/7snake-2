package br.lucio.snake7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import br.lucio.snake7.exception.SnakeException;

public class Main {

	private Map<String, Cell> mapCells;

	public Main() {
		mapCells = new HashMap<String, Cell>();
	}

	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.out.println("Inform the file and number of solutions to print.\n");
			System.out.println("Example: java Main /test/grid.csv");
			System.out.println("Or: java Main /test/grid.csv 2");
			System.out.println("\nThe second parameter is optional. If none was informed, only 1 result will be printed.");
			
			return;
		}

		int totalResultsToBePrinted = 1;
		if (args.length == 2) {
			totalResultsToBePrinted = Integer.parseInt(args[1]);
		}
		
		Main main = new Main();
		String csv = main.readFileCsv(args[0]);

		int[][] grid = main.loadCells(csv);
		System.out.println("7-Snake Grid of the csv file: " + grid.length + " lines and " + grid[0].length + " columns.\n");

		main.printGrid(grid);

		List<Snake7> listSnakes = main.searchSnakes(grid);

		main.findResultedPairs(listSnakes, grid.length, grid[0].length, totalResultsToBePrinted);
	}

	/**
	 * Reads the csv file and return the values as a string
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFileCsv(String fileName) {
		String csv = null;
		try {
			FileInputStream file = new FileInputStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));

			StringBuilder strBuilder = new StringBuilder();

			String linha = null;
			while ((linha = reader.readLine()) != null) {
				strBuilder.append(linha).append("\n");
			}
			reader.close();

			csv = strBuilder.toString();

		} catch (FileNotFoundException e) {
			throw new SnakeException("File not found.", e);
		} catch (IOException e) {
			throw new SnakeException("File with errors.", e);
		}

		return csv;
	}

	/**
	 * Loads the values of csv file and returns the corresponding grid
	 * 
	 * @param csv
	 * @return
	 */
	public int[][] loadCells(String csv) {
		List<Integer> listInt = new ArrayList<Integer>();

		int lines = 0;
		int columns = 0;

		// Read the values and save in a temporary list
		StringTokenizer token = new StringTokenizer(csv, "\n");
		lines = token.countTokens();

		while (token.hasMoreTokens()) {
			String line = token.nextToken();

			StringTokenizer token2 = new StringTokenizer(line, ",");
			columns = token2.countTokens();
			while (token2.hasMoreTokens()) {
				String cell = token2.nextToken();
				listInt.add(Integer.parseInt(cell));
			}

		}

		// Create the grid with values loaded from the file
		int[][] grid = new int[lines][columns];

		int i = 0, j = 0;
		for (Integer valueCell : listInt) {
			grid[i][j] = valueCell;

			j++;

			if (j >= columns) {
				j = 0;
				i++;
			}
		}

		// load the cells in a Map to be re-used when create the 7-snakes
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[i].length; j++) {
				Cell cell = new Cell();
				cell.setLine(i);
				cell.setColumn(j);
				cell.setValue(grid[i][j]);

				mapCells.put(i + ";" + j, cell);
			}
		}

		return grid;
	}

	/**
	 * Finds the possible 7-snakes in the grid
	 * 
	 * @param grid
	 * @return
	 */
	public List<Snake7> searchSnakes(int[][] grid) {
		System.out.println("\nSearching Snakes......");
		
		List<Snake7> listSnakes = new ArrayList<Snake7>();
		listSnakes.add(new Snake7());

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				searchCells(grid, i, j, listSnakes);
			}
		}

		if (!listSnakes.isEmpty()) {
			Snake7 snake = listSnakes.get(listSnakes.size() - 1);
			if (!snake.isCompleted()) {
				listSnakes.remove(snake);
			}
		}

		return listSnakes;
	}

	/**
	 * Searches the cells recursively to create a 7-Snake
	 * 
	 * @param grid
	 * @param line
	 * @param column
	 * @param listSnakes
	 */
	private void searchCells(int[][] grid, int line, int column, List<Snake7> listSnakes) {

		try {
			Snake7 snake = listSnakes.get(listSnakes.size() - 1);

			if (snake.isCompleted()) {
				snake = (Snake7) snake.clone();
				listSnakes.add(snake);
				snake.removeLastCell();

				return;
			}

			shiftRight(grid, line, column, listSnakes);
			shiftBottom(grid, line, column, listSnakes);
			shiftLeft(grid, line, column, listSnakes);
			shiftTop(grid, line, column, listSnakes);

			snake = listSnakes.get(listSnakes.size() - 1);
			snake.removeLastCell();

		} catch (CloneNotSupportedException e) {
			throw new SnakeException(e);
		}
	}

	private void shiftRight(int[][] grid, int line, int column, List<Snake7> listSnakes) {
		column++;

		Snake7 snake = listSnakes.get(listSnakes.size() - 1);
		if ((column < grid[line].length) && (!snake.isValidPosition(line, column))) {
			String id = line + ";" + column;
			Cell cell = mapCells.get(id);
			snake.addCell(cell);

			searchCells(grid, line, column, listSnakes);
		}
	}

	private void shiftLeft(int[][] grid, int line, int column, List<Snake7> listSnakes) {
		column--;

		Snake7 snake = listSnakes.get(listSnakes.size() - 1);
		if ((column >= 0) && (!snake.isValidPosition(line, column))) {
			String id = line + ";" + column;
			Cell cell = mapCells.get(id);
			snake.addCell(cell);

			searchCells(grid, line, column, listSnakes);
		}
	}

	private void shiftTop(int[][] grid, int line, int column, List<Snake7> listSnakes) {
		line--;

		Snake7 snake = listSnakes.get(listSnakes.size() - 1);
		if ((line >= 0) && (!snake.isValidPosition(line, column))) {
			String id = line + ";" + column;
			Cell cell = mapCells.get(id);
			snake.addCell(cell);

			searchCells(grid, line, column, listSnakes);
		}
	}

	private void shiftBottom(int[][] grid, int line, int column, List<Snake7> listSnakes) {
		line++;

		Snake7 snake = listSnakes.get(listSnakes.size() - 1);
		if ((line < grid.length) && (!snake.isValidPosition(line, column))) {
			String id = line + ";" + column;
			Cell cell = mapCells.get(id);
			snake.addCell(cell);

			searchCells(grid, line, column, listSnakes);
		}
	}

	/**
	 * Find the pairs of 7-Snakes
	 * 
	 * @param listSnakes
	 * @param sizeLineGrid
	 * @param sizeColumnGrid
	 */
	public void findResultedPairs(List<Snake7> listSnakes, int sizeLineGrid, int sizeColumnGrid, int resultsToPrint) {
		Map<Integer, List<Snake7>> mapSumToSnakes = new HashMap<Integer, List<Snake7>>();

		// creates a Map to group the snakes by the sum of their cells
		for (Snake7 snake : listSnakes) {
			int sum = snake.sumValues();

			List<Snake7> listSnakesFromMap = mapSumToSnakes.get(sum);
			if (listSnakesFromMap == null) {
				listSnakesFromMap = new ArrayList<Snake7>();
				mapSumToSnakes.put(sum, listSnakesFromMap);

			}
			
			boolean isDuplicated = false;
			for (Snake7 snakeToVerifyDuplicity : listSnakesFromMap) {
				if (snakeToVerifyDuplicity.equals(snake)) {
					isDuplicated = true;
					break;
				}
			}
			
			if (!isDuplicated) {
				listSnakesFromMap.add(snake);
			}
		}

		// print the results found
		System.out.println("\nResults found:");
		for (Integer keySum : mapSumToSnakes.keySet()) {
			List<Snake7> listSnakesFromMap = mapSumToSnakes.get(keySum);
			
			if (listSnakesFromMap.size() > 1) {
				System.out.println("Value (sum of cells): " + keySum + ", Total of 7-snakes: " + listSnakesFromMap.size());
			}
		}

		// finding the first result
		System.out.println("\n\nPrinting firsts 7-Snakes found (" + resultsToPrint + " solution(s)):\n");
		int totalPrinted = 0;
		
		breakFor:
		for (Integer keySum : mapSumToSnakes.keySet()) {
			List<Snake7> listSnakesFromMap = mapSumToSnakes.get(keySum);
			int size = listSnakesFromMap.size();

			for (int i = 0; i < size - 1; i++) {
				Snake7 snakeOne = listSnakesFromMap.get(i);

				for (int j = i + 1; j < size; j++) {
					Snake7 snakeTwo = listSnakesFromMap.get(j);

					if (!snakeOne.conflict(snakeTwo) && (totalPrinted < resultsToPrint)) {
						totalPrinted++;
						System.out.println("\nResult " + totalPrinted + ": (" + i + "," + j +")\n");

						printSnake(snakeOne, snakeTwo, sizeLineGrid, sizeColumnGrid);
					}
					
					
				}
				
				if (totalPrinted >= resultsToPrint) {
					break breakFor;
				}
			}

		}
	}

	/**
	 * Prints on console the 7-Snake in a grid format
	 * 
	 * @param snake
	 * @param sizeLineGrid
	 * @param sizeColumnGrid
	 * @param marcador
	 */
	private void printSnake(Snake7 snake1, Snake7 snake2, int sizeLineGrid, int sizeColumnGrid) {
		int[][] newGridSnakes = new int[sizeLineGrid][sizeColumnGrid];

		for (int k = 0; k < sizeLineGrid; k++) {
			for (int l = 0; l < sizeColumnGrid; l++) {
				newGridSnakes[k][l] = 0;
			}
		}

		for (Cell cell : snake1.getListCells()) {
			newGridSnakes[cell.getLine()][cell.getColumn()] = 1;
		}

		for (Cell cell : snake2.getListCells()) {
			newGridSnakes[cell.getLine()][cell.getColumn()] = 2;
		}

		printGrid(newGridSnakes);
		System.out.println("\n\n");
	}

	/**
	 * Prints the grid on console
	 * @param grid
	 */
	public void printGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
	}

}
