package sprint4.product;

/**
 * Simple game mode implementation for SOS game.
 * In simple mode, the first player to form an SOS pattern wins.
 */
public class SimpleMode extends GameModeLogic {
	
	/**
	 * Creates a new simple mode game logic handler.
	 * 
	 * @param game the SOS game instance.
	 */
	public SimpleMode(SOSGame game) {
		super(game);
	}
	
	/**
	 * Checks if the game is over.
	 * 
	 * @return  true if any player has scored or the board is full, false otherwise
	 */
	@Override
	public boolean isGameOver() {
	    if (blueScore > 0 || redScore > 0) {
	        return true;
	    }
	    
	    char[][] board = game.getBoard();
	    for (int i = 0; i < game.getBoardSize(); i++) {
	        for (int j = 0; j < game.getBoardSize(); j++) {
	            if (board[i][j] == ' ') {
	                return false;
	            }
	        }
	    }
	    return true;
	}
	
	/**
	 * Determine which player is the winner.
	 * 
	 * @return the winning player, or NONE if no one has scored yet
	 */
	@Override
	public SOSGame.Player getWinner() {
		if (blueScore > 0) {
			 System.out.println("Blue wins");
			return SOSGame.Player.BLUE;
		}
		if (redScore > 0) {
			 System.out.println("Red wins");
			return SOSGame.Player.RED;
		}
		System.out.println("Draw");
		return SOSGame.Player.NONE;
	}
	
	/**
	 * Determines if the current player should continue their turn.
	 * 
	 * @param sosFormed the number of SOS patterns formed
	 * @return always false in simple mode
	 */
	@Override
	public boolean shouldPlayerContinue(int sosFormed) {
		return false;
	}
}