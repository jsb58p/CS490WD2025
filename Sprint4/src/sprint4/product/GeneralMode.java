package sprint4.product;

/**
 * General game mode implementation for SOS.
 * In general mode, the game continues until the board is full and the player with the most SOS patterns wins.
 */
public class GeneralMode extends GameModeLogic {
	
	/**
	 * Creates a new general mode game logic handler.
	 * 
	 * @param game the SOS game instance
	 */
	public GeneralMode(SOSGame game) {
		super(game);
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * @return true if the board is full, false otherwise
	 */
	@Override
	public boolean isGameOver() {
		char[][] board = game.getBoard();
		for (int i = 0; i < game.getBoardSize(); i++) {
			for (int j = 0; j < game.getBoardSize(); j++){
				if(board[i][j] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Determines the winner of the game.
	 * 
	 * @return the winning player, or NONE if the game is a draw
	 */
	@Override
	public SOSGame.Player getWinner(){
		if (blueScore > redScore) {
			System.out.println("Blue wins");
			return SOSGame.Player.BLUE;
		}
		if (redScore > blueScore) {
			System.out.println("Red wins");
			return SOSGame.Player.RED;
		}
		System.out.println("Draw");
		return SOSGame.Player.NONE;
	}
	
	/**
	 * Determines if the current player should continue their turn.
	 * 
	 * @param sosFormed the number of SOS patterns formed in the last move
	 * @return if the player formed at least one SOS, false otherwise
	 */
	@Override
	public boolean shouldPlayerContinue(int sosFormed) {
		return sosFormed > 0;
	}
}