package sprint4.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sprint4.product.SOSGame;

/**
 * Unit tests for the SOSGame and related components.
 * Covers functionality based on acceptance criteria from user stories.
 */
public class TestSOSGame {

  private SOSGame simpleGame;
  private SOSGame generalGame;

  /**
   * Sets up test games before each test.
   */
  @BeforeEach
  public void setUp() {
    simpleGame = new SOSGame(5, SOSGame.GameMode.SIMPLE, null);
    generalGame = new SOSGame(7, SOSGame.GameMode.GENERAL, null);
  }

  /**
   * Tests that the board size can be selected and is set correctly.
   */
  @Test
  public void testChooseBoardSize() {
    assertEquals(5, simpleGame.getBoardSize(), "Board size should be 5");
    assertEquals(7, generalGame.getBoardSize(), "Board size should be 7");
  }

  /**
   * Tests that the correct game mode is selected when creating a game.
   */
  @Test
  public void testChooseGameMode() {
    assertEquals(SOSGame.GameMode.SIMPLE, simpleGame.getGameMode(), "Game mode should be SIMPLE");
    assertEquals(SOSGame.GameMode.GENERAL, generalGame.getGameMode(), "Game mode should be GENERAL");
  }

  /**
   * Tests that a new game starts with an empty board and blue player.
   */
  @Test
  public void testStartNewGameState() {
    char[][] board = simpleGame.getBoard();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        assertEquals(' ', board[i][j], "All cells should be initially empty");
      }
    }
    assertEquals(SOSGame.Player.BLUE, simpleGame.getCurrentPlayer(), "Game should start with BLUE player");
  }

  /**
   * Tests making a move in a simple game.
   */
  @Test
  public void testMakeMoveInSimpleGame() {
    assertTrue(simpleGame.isCellEmpty(1, 1), "Cell (1,1) should be empty initially");
    simpleGame.makeMove(1, 1, 'S');
    assertEquals('S', simpleGame.getBoard()[1][1], "Cell (1,1) should contain 'S'");
    assertEquals(SOSGame.Player.RED, simpleGame.getCurrentPlayer(), "Player should switch to RED");
  }

  /**
   * Tests making a move in a general game.
   */
  @Test
  public void testMakeMoveInGeneralGame() {
    assertTrue(generalGame.isCellEmpty(2, 2), "Cell (2,2) should be empty initially");
    generalGame.makeMove(2, 2, 'O');
    assertEquals('O', generalGame.getBoard()[2][2], "Cell (2,2) should contain 'O'");
    assertEquals(SOSGame.Player.RED, generalGame.getCurrentPlayer(), "Player should switch to RED");
  }
  
  /**
   * Tests if a game is over in a simple game.
   */
  @Test
  public void testSimpleGameIsOver() {
      char[][] board = simpleGame.getBoard();
      for (int i = 0; i < board.length; i++) {
          for (int j = 0; j < board.length; j++) {
              board[i][j] = 'O';
          }
      }
      assertTrue(simpleGame.isGameOver(), "Simple game should be over when board is full");
  }

  /**
   * Tests if a game is over in a general game.
   */
  @Test
  public void testGeneralGameIsOver() {
      char[][] board = generalGame.getBoard();
      for (int i = 0; i < board.length; i++) {
          for (int j = 0; j < board.length; j++) {
              board[i][j] = 'O';
          }
      }
      
      assertTrue(generalGame.isGameOver(), "General game should be over when board is full");
  }

}
