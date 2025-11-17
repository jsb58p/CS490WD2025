package sprint4.product;

/**
 * Abstract base class for SOS game mode logic
 */
public abstract class GameModeLogic{
	
	protected int blueScore = 0;
	protected int redScore = 0;
	protected SOSGame game;
	
	/**
	 * Creates a new game mode logic instance.
	 * 
	 * @param game the SOS game instance
	 */
	public GameModeLogic(SOSGame game) {
		this.game = game;
	}
	
	/**
	 * Handles a move and updates scores.
	 * 
	 * @param row index the row the letter was placed
	 * @param col index the column the letter was placed
	 * @param letter the letter placed ('S' or 'O')
	 * @param player the player making the move
	 */
	public void handleMove(int row, int col, char letter, SOSGame.Player player) {
		int sosFormed = game.checkScore(row, col, letter, true);
		updateScore(player, sosFormed);
		if(!shouldPlayerContinue(sosFormed)) {
			game.switchPlayer();
			System.out.println("Switching player...");
		}
	}
	
	/**
	 * Updates the score for the specified player.
	 * 
	 * @param points the number of points to add
	 */
	private void updateScore(SOSGame.Player player, int points) {
		if (player == SOSGame.Player.BLUE) {
			blueScore += points;
		} else {
			redScore += points; 
		}
	}
	
	/**
	 * Checks if the game is over according to mode rules (Simple or General).
	 * 
	 * @return true if the game has ended, false otherwise
	 */
	public abstract boolean isGameOver();
	
	/**
	 * Determines the winner according to mode rules (Simple or General).
	 * 
	 * @return the winning player, or NONE if the game is a draw
	 */
	public abstract SOSGame.Player getWinner();
	
	/**
	 * Determines if the current player should get another turn.
	 * 
	 * @param sosFormed the number of SOS patterns formed in the last move
	 * @return true if the player gets to continue, false otherwise (according to mode rules)
	 */
	public abstract boolean shouldPlayerContinue(int sosFormed);
	
	/**
	 * Gets the current score for the blue player.
	 * 
	 * @return the blue player's score
	 */
	public int getBlueScore() {
		return blueScore;
	}
	
	/**
	 * Gets the current score for the red player.
	 * 
	 * @return the red player's score
	 */
	public int getRedScore() {
		return redScore;
	}
}