package sprint4.product;

/**
 * Abstract base class for SOS Computer Opponent
 */
public abstract class ComputerOpponent {
	
	protected SOSGame game;
	
	/**
	 * Creates a new computer opponent logic instance.
	 * 
	 * @param game the SOS game instance
	 */
	public ComputerOpponent(SOSGame game) {
		this.game = game;
	}
	
	/**
	 * Computer opponent move decision logic.
	 * 
	 * @param boardSize size of the board
	 * @param letter	
	 * @param player
	 */
	public abstract int[] computerChooseStrategy(int boardSize, SOSGame.Player player); 
	
}