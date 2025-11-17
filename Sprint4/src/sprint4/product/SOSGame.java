package sprint4.product;

/**
*Represents the SOS game logic.
*Handles game size, rules, and player turns.
*/
public class SOSGame {
	
	/**
	 * Represents the game mode options.
	 */
	public enum GameMode {
		SIMPLE,
		GENERAL
	}
	
	/**
	 * Represents the player colors.
	 */
	public enum Player {
		BLUE,
		RED,
		NONE
	}
	
	/**
	 * Represents the array boundaries to be checked.
	 */
	public enum Bound {
		OHORIZONTALBOUND,
		SLEFTBOUND,
		SRIGHTBOUND,
		OVERTICALBOUND,
		SUPBOUND,
		SDOWNBOUND,
	}
	
	private Player currentPlayer;
	private int boardSize;
	private GameMode gameMode;
	private char[][] board;
	private GameModeLogic gameModeLogic;
	private BoundaryCheck bounds;
	private SOSGameGUI gui;
	private ComputerOpponent cpu = new CpuOpponentS(this);
	
	
	/**
	 * Creates a new SOS game with the specified settings.
	 * 
	 * @param boardSize the size of the board (e.g. 3 for 3x3)
	 * @param gameMode the game mode (SIMPLE or GENERAL)
	 * @param gui the SOSGameGUI instance
	 */
	public SOSGame(int boardSize, GameMode gameMode, SOSGameGUI gui) {
		this.boardSize = boardSize;
		this.gameMode = gameMode;
		this.gui = gui;
		this.board = new char[boardSize][boardSize];
		this.currentPlayer = Player.BLUE;
		bounds = new BoundaryCheck(boardSize);

		if (gameMode == GameMode.SIMPLE) {
			gameModeLogic = new SimpleMode(this);
		} else {
			gameModeLogic = new GeneralMode(this);
		}
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	/**
	 * Updates the board size.
	 * Used when user changes the spinner value.
	 * 
	 * @param boardSize the new board size (between 3 and 10)
	 */
	public void setBoardSize(int boardSize) {
		this.boardSize  = boardSize;
	}
	
	/**
	 * Updates the game mode.
	 * Used when user selects Simple or General game.
	 * 
	 * @param gameMode the new game mode
	 */
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
		if (gameMode == GameMode.SIMPLE) {
			gameModeLogic = new SimpleMode(this);
		} else {
			gameModeLogic = new GeneralMode(this);
		}
	}
	
	/**
	 * Gets the current game mode.
	 * 
	 * @return the game mode
	 */
	public GameMode getGameMode() {
		return gameMode;
	}
	
	/**
	 * Gets the current board state.
	 * 
	 * @return the 2D char array representing the board.
	 */
	public char[][] getBoard(){
		return board;
	}
	
	/**
	 * Gets the current board size.
	 * 
	 * @return the board size
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * Checks if a cell is empty.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @return true if the cell is empty, false otherwise
	 */
	public boolean isCellEmpty(int row, int col) {
		return board[row][col] == ' ';
	}
	
	/**
	 * Gets the current player.
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Switches to the other player.
	 */
	public void switchPlayer() {
		currentPlayer = (currentPlayer == Player.BLUE) ? Player.RED : Player.BLUE;
	}
	
	/**
	 * Places a letter at the specified position and applies game logic.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @param letter the letter to place ('S' or 'O')
	 */
	public void makeMove(int row, int col, char letter) {
		board[row][col] = letter;
		gameModeLogic.handleMove(row, col, letter, currentPlayer);
		
		if (gameModeLogic.isGameOver()) {
			gui.endGameDisplay(gameModeLogic.getWinner(), gameModeLogic.blueScore, gameModeLogic.redScore);
		}
	}
	
	/**
	 * Checks to see if the current player is controlled by the computer.
	 * Then select the appropriate strategy based on the letter assigned to the computer opponent.
	 */
	public int[] cpuMoveCheck() {
		switch(currentPlayer) {
		case BLUE:
			if(gui.getBluePlayerOpponent() == "Computer") {
				switch(gui.getBluePlayerRadio()) {
				case "S":
					if(!(cpu instanceof CpuOpponentS)) {
						cpu = new CpuOpponentS(this);
						System.out.println("CPU Blue switch letter");
					}
					break;
				case "O":
					if(!(cpu instanceof CpuOpponentO)) {
						cpu = new CpuOpponentO(this);
						System.out.println("CPU Blue switch letter");
					}
					break;
				default:
					break;
				}
				return cpu.computerChooseStrategy(boardSize, currentPlayer);
			}
			break;
		case RED:
			if(gui.getRedPlayerOpponent() == "Computer") {
				switch(gui.getRedPlayerRadio()) {
				case "S":
					if(!(cpu instanceof CpuOpponentS)) {
						cpu = new CpuOpponentS(this);
						System.out.println("CPU Red switch letter");
					}
					break;
				case "O":
					if(!(cpu instanceof CpuOpponentO)) {
						cpu = new CpuOpponentO(this);
						System.out.println("CPU Red switch letter");
					}
					break;
				default: 
					break;
				}
				return(cpu.computerChooseStrategy(boardSize, currentPlayer));
			}
			break;
		default:
			break;
		}
		return null;
	}
	
	/**
	 * Checks if the most recent move formed any "SOS" patterns and counts them.
	 * It first checks the horizontal and diagonal for a given letter, then vertical.
	 * Determines which cells to draw the line in.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @param letter the letter placed ('S' or 'O')
	 * @param drawLine determines if a line will be drawn if an 'SOS' is formed
	 * @return the number of "SOS" patterns formed by this move
	 */
	public int checkScore(int row, int col, char letter, boolean drawLine) {
		int sosCount = 0;
		if(gui != null) {
			for(int i = -1; i <= 1; i++) {
				// Horizontal and diagonal check for "SOS".
				switch (letter) {
				case 'S':
					if(bounds.boundsCheck(Bound.SRIGHTBOUND, row, col, i)) {
					    if(board[row - (2*i)][col + 2] == 'S' && board[row-i][col+1] == 'O') {
					        sosCount++;
							switch(i) {
							case -1:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row+j, col+j, getCurrentPlayer(), i);	
									}
								}
								break;
							case 0:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row, col+j, getCurrentPlayer(), i);
									}
								}
								break;
							case 1:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row-j, col+j, getCurrentPlayer(), i);
									}
								}
								break;
							default:
								break;
							}
					    }
					}
					if (bounds.boundsCheck(Bound.SLEFTBOUND, row, col, i) && board[row+i][col-1] == 'O') {
						if(board[row + (2*i)][col - 2] == 'S' && board[row+i][col-1] == 'O') {
					        sosCount++;
					        switch(i) {
							case -1:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row-j, col-j, getCurrentPlayer(), i);	
									}
								}
								break;
							case 0:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row, col-j, getCurrentPlayer(), i);
									}
								}
								break;
							case 1:
								for(int j = 0; j < 3; j++) {
									if(drawLine) {
										System.out.println("Draw Line");
										gui.drawLine(row+j, col-j, getCurrentPlayer(), i);
									}
								}
								break;
							default:
								break;
							}
					    }
					}
					break;
				case 'O':
					if(bounds.boundsCheck(Bound.OHORIZONTALBOUND, row, col, i))
						if(board[row-i][col+1] == 'S' && board[row+i][col-1] == 'S') {
						sosCount++;
						switch(i) {
						case -1:
							for(int j = -1; j <= 1; j++) {
								if(drawLine) {
									System.out.println("Draw Line");
									gui.drawLine(row-j, col-j, getCurrentPlayer(), i);	
								}
							}
							break;
						case 0:
							for(int j = -1; j <= 1; j++) {
								if(drawLine) {
									System.out.println("Draw Line");
									gui.drawLine(row, col-j, getCurrentPlayer(), i);
								}
							}
							break;
						case 1:
							for(int j = -1; j <= 1; j++) {
								if(drawLine) {
									System.out.println("Draw Line");
									gui.drawLine(row+j, col-j, getCurrentPlayer(), i);
								}
							}
							break;
						default:
							break;
						}
					} 
					break;
				}
			}
			// Vertical check for "SOS".
			switch (letter) {
			case 'S':
				if(bounds.boundsCheck(Bound.SUPBOUND, row, col)) { 
					if(board[row+2][col] == 'S' && board[row+1][col] == 'O') { 
						for(int i = 0; i < 3; i++) {
							if(drawLine) {
								gui.drawLine(row+i, col, getCurrentPlayer(), 2);
							}
						}	
					sosCount++;
					}
				}
				if(bounds.boundsCheck(Bound.SDOWNBOUND, row, col)) {
					if(board[row-2][col] == 'S' && board[row-1][col] == 'O') {
						for(int i = 0; i < 3; i++) {
							if(drawLine) {
								gui.drawLine(row-i, col, getCurrentPlayer(), 2);
							}
						}
						sosCount++;
					}
				}
				break;
			case 'O':
				if(bounds.boundsCheck(Bound.OVERTICALBOUND, row, col))
					if(board[row+1][col] == 'S' && board[row-1][col] == 'S'){
					for(int i = -1; i <= 1; i++) {
						if(drawLine) {
							System.out.println("Ready to draw...");
							gui.drawLine(row-i, col, getCurrentPlayer(), 2);
						}
					}
					sosCount++;
				}
				break;
			}
		}
		return sosCount;
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * @return true if the game has ended, false otherwise
	 */
	public boolean isGameOver() {
	    return gameModeLogic.isGameOver();
	}

	/**
	 * Gets the winner of the game.
	 * 
	 * @return the winning player
	 */
	public Player getWinner() {
	    return gameModeLogic.getWinner();
	}
	
	/**
	 * Gets the blue player score.
	 * 
	 * @return blue player score
	 */
	public int getBlueScore() {
		return gameModeLogic.blueScore;
	}
	
	/**
	 * Gets the red player score.
	 * 
	 * @return red player score
	 */
	public int getRedScore() {
		return gameModeLogic.redScore;
	}
}