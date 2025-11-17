package sprint4.product;

/** Implementation of computer opponent with "S" selected
 * 
 */
public class CpuOpponentS extends ComputerOpponent {

	/**
	 * Creates a new "S" computer opponent logic handler
	 * @param game
	 */
	public CpuOpponentS(SOSGame game) {
		super(game);
	}
	
	/**
	 * Chooses a board cell for computer opponent to place letter 'S'
	 * 
	 * @param boardSize the size of the board
	 * @param player the computer opponent, 'BLUE' or 'RED
	 */
	public int[] computerChooseStrategy(int boardSize, SOSGame.Player player){
		System.out.println("O Choosing strategy");
		int[][] options = new int[boardSize * boardSize][3];
		
		int highestIndex = -1;
		int highestScore = -1;
		int index = 0;
		
		for(int row = 0; row < boardSize; row++) {
			for(int col = 0; col < boardSize; col++) {
				if(game.getBoard()[row][col] == ' ') {
					int score = game.checkScore(row, col, 'S', false);
					options[index][0] = row;
					options[index][1] = col;
					options[index][2] = score;
					index++;
				}
			}
		}
		
		for(int i = 0; i < index; i++) {
			if(options[i][2] > highestScore) {
				highestScore = options[i][2];
				highestIndex = i;
			} else if(options[i][2] == highestScore && Math.random() < 0.01 * Math.pow(10.0 / boardSize, Math.log(5) / Math.log(2))) {
		        highestIndex = i;
			}
		}
		game.makeMove(options[highestIndex][0], options[highestIndex][1], 'S');
	    return new int[]{options[highestIndex][0], options[highestIndex][1]};
	}
	
}
