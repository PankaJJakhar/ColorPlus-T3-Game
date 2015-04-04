/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 PankaJJakhar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package master.jakhar.colorplus.logic;

import master.jakhar.colorplus.GridCell;
import master.jakhar.colorplus.GameBoardView.GridCellInfo;
import master.jakhar.colorplus.logic.PlayerMoveStats.MoveStats;

/**
 * Contains all the logic of Tic-Tacc-Toe.
 * 
 * @author Pankaj Jakhar
 *
 */
public class TicTacToeUtil {

	private static GridCell[][] mGridCellMatrix;
	private static boolean[][] mCellBooleanMatrix;
	private static Player mPlayer;

	/** {@link GridCellInfo} on which user clicked. */
	private static GridCellInfo mGridCellInfo;

	/** Size of the Matrix - created Grid. */
	private static int mNxNMatrixSize;

	/**
	 * Provides the information about running Game.
	 * 
	 * @param player
	 * @param mGridCellMatrix
	 * @return
	 */
	public static GameStatusInfo getGameStatusInfo(GridCellInfo gridCellInfo,
			boolean[][] cellBooleanMatrix, Player player,
			GridCell[][] gridCellMatrix) {

		mGridCellMatrix = gridCellMatrix;
		mGridCellInfo = gridCellInfo;
		mNxNMatrixSize = mGridCellMatrix.length;

		mCellBooleanMatrix = cellBooleanMatrix;

		mPlayer = player;

		GameStatusInfo gameStatusInfo = new GameStatusInfo();
		PlayerMoveStats playerMoveStats = isWon();
		gameStatusInfo.setPlayerMoveStats(playerMoveStats);

		if (playerMoveStats.getHorizontalMoveStats().isPlayerWon()
				|| playerMoveStats.getVerticalMoveStats().isPlayerWon()
				|| playerMoveStats.getDiagonalMoveStats().isPlayerWon()) {

			gameStatusInfo.setGameWon(true);
			gameStatusInfo.setGameTie(false);
		} else if (isGameTie()) {
			gameStatusInfo.setGameWon(false);
			gameStatusInfo.setGameTie(true);
		} else {
			gameStatusInfo.setGameWon(false);
			gameStatusInfo.setGameTie(false);
		}

		return gameStatusInfo;
	}

	/**
	 * Checks if Game is Over now. If all the Grid Cells are filled and no one
	 * is the winner then game is over now.
	 * */
	private static boolean isGameTie() {

		for (int rowIndex = 0; rowIndex < mNxNMatrixSize; rowIndex++) {
			for (int columnIndex = 0; columnIndex < mNxNMatrixSize; columnIndex++) {
				if (!mCellBooleanMatrix[rowIndex][columnIndex])
					return false;
			}
		}

		return true;
	}

	/**
	 * Checks if player is won Horizontally, Vertically or Diagonally.
	 * 
	 * @return
	 */
	private static PlayerMoveStats isWon() {

		PlayerMoveStats playerMoveStats = new PlayerMoveStats();

		playerMoveStats.setHorizontalMoveStats(isWonHorizontally());
		playerMoveStats.setVerticalMoveStats(isWonVertically());
		playerMoveStats.setDiagonalMoveStats(isWonDiagonally());

		return playerMoveStats;
	}

	/**
	 * Checks if Player is won diagonally.
	 * 
	 * @return MoveStats
	 */
	private static MoveStats isWonDiagonally() {

		boolean isWon = true;
		boolean isWonDiagonally = true;
		boolean isWonDiagonallyRev = true;

		int rowIndex = mGridCellInfo.getRowIndex();
		int columnIndex = mGridCellInfo.getColumnIndex();

		/**
		 * If clicked cells Row and Column index are not same then it is not
		 * lying on diagonal.
		 */
		// if(rowIndex != columnIndex){
		// isWon = false;
		// MoveStats moveStats = new MoveStats();
		// moveStats.setPlayerWon(isWon);
		// moveStats.setWinningRatio(0);
		// moveStats.setFutureWinPossible(false);
		// return moveStats;
		// }

		// No optimization for now.
		// int loopSize = mNxNMatrixSize / 2;
		// Check for all the columns in this row.
		for (int index = 0; index < mNxNMatrixSize; index++) {
			GridCell gridCell = mGridCellMatrix[index][index];

			if (mPlayer.getSymbolType() != gridCell.getSymbolType()) {
				isWonDiagonally = false;
				break;
			}
		}

		for (int index = mNxNMatrixSize - 1; index >= 0; index--) {
			GridCell gridCell = mGridCellMatrix[index][mNxNMatrixSize - index
					- 1];

			if (mPlayer.getSymbolType() != gridCell.getSymbolType()) {
				isWonDiagonallyRev = false;
				break;
			}
		}

		// If user is winning diagonally either way then he wins.
		// otherwise he loses.
		if (isWonDiagonally || isWonDiagonallyRev)
			isWon = true;
		else
			isWon = false;

		MoveStats moveStats = isFutureDWinPossible();
		moveStats.setPlayerWon(isWon);
		return moveStats;
	}

	/**
	 * Check future diagonal winning possibility.
	 * 
	 * @return
	 */
	private static MoveStats isFutureDWinPossible() {

		MoveStats moveStats = new MoveStats();

		int matchCount1 = 0;

		// No optimization for now.
		// int loopSize = mNxNMatrixSize / 2;
		// Check for all the columns in this row.
		for (int index = 0; index < mNxNMatrixSize; index++) {
			GridCell gridCell = mGridCellMatrix[index][index];

			if (mPlayer.getSymbolType() == gridCell.getSymbolType()) {
				matchCount1++;
			} else if (gridCell.getSymbolType() == SymbolType.NONE) {

			} else {
				moveStats.setFutureWinPossible(false);
			}
		}

		int matchCount2 = 0;
		for (int index = mNxNMatrixSize - 1; index >= 0; index--) {
			GridCell gridCell = mGridCellMatrix[index][mNxNMatrixSize - index
					- 1];

			if (mPlayer.getSymbolType() == gridCell.getSymbolType()) {
				matchCount2++;
			} else if (gridCell.getSymbolType() == SymbolType.NONE) {

			} else {
				moveStats.setFutureWinPossible(false);
				moveStats.setRevDiagonal(false);
			}
		}

		double winningRatio = 0;
		if (matchCount1 > matchCount2) {
			winningRatio = (double) matchCount1 / mNxNMatrixSize;
			moveStats.setRevDiagonal(false);
		} else {
			winningRatio = (double) matchCount2 / mNxNMatrixSize;
			moveStats.setRevDiagonal(true);
		}

		moveStats.setWinningRatio(winningRatio);
		return moveStats;
	}

	/**
	 * Checks if Player is won Vertically.
	 * 
	 * @return
	 */
	private static MoveStats isWonVertically() {

		boolean isWon = true;

		// Check for all the columns in this row.
		int columnIndex = mGridCellInfo.getColumnIndex();
		for (int rowIndex = 0; rowIndex < mNxNMatrixSize; rowIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (mPlayer.getSymbolType() != gridCell.getSymbolType()) {
				isWon = false;
				break;
			}
		}

		MoveStats moveStats = isFutureVWinPossible();
		moveStats.setPlayerWon(isWon);
		return moveStats;
	}

	/**
	 * Stats if this Player can win here in the future(Vertically).
	 * 
	 * @return
	 */
	private static MoveStats isFutureVWinPossible() {

		MoveStats moveStats = new MoveStats();

		int matchCount = 0;

		// Check for all the columns in this row.
		int columnIndex = mGridCellInfo.getColumnIndex();
		for (int rowIndex = 0; rowIndex < mNxNMatrixSize; rowIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (mPlayer.getSymbolType() == gridCell.getSymbolType()) {
				matchCount++;
			} else if (gridCell.getSymbolType() == SymbolType.NONE) {

			} else {
				moveStats.setFutureWinPossible(false);
			}
		}

		double winningRatio = (double) matchCount / mNxNMatrixSize;
		moveStats.setWinningRatio(winningRatio);
		return moveStats;
	}

	/**
	 * Checks if the full row contains only the Symbol assigned for this
	 * {@link Player}.
	 * 
	 * If all the Symbols in the row matches with the Player's Symbol then this
	 * player wins.
	 * 
	 * @return MoveStats Stats of this move by Player. It returns the winning
	 *         ratio also.
	 */
	private static MoveStats isWonHorizontally() {

		boolean isWon = true;

		// Check for all the columns in this row.
		int rowIndex = mGridCellInfo.getRowIndex();
		for (int columnIndex = 0; columnIndex < mNxNMatrixSize; columnIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (mPlayer.getSymbolType() != gridCell.getSymbolType()) {
				isWon = false;
				break;
			}
		}

		MoveStats moveStats = isFutureHWinPossible();
		moveStats.setPlayerWon(isWon);
		return moveStats;
	}

	/**
	 * Horizontal Future win check.
	 * 
	 * Returns the information if the future winning is possible for this Human
	 * user. In that case it will also return the current possible winning
	 * ratio.
	 *
	 * @return MoveStats
	 */
	private static MoveStats isFutureHWinPossible() {

		MoveStats moveStats = new MoveStats();

		int matchCount = 0;

		// Check for all the columns in this row.
		int rowIndex = mGridCellInfo.getRowIndex();
		for (int columnIndex = 0; columnIndex < mNxNMatrixSize; columnIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (mPlayer.getSymbolType() == gridCell.getSymbolType()) {
				matchCount++;
			} else if (gridCell.getSymbolType() == SymbolType.NONE) {

			} else {
				moveStats.setFutureWinPossible(false);
			}
		}

		double winningRatio = (double) matchCount / mNxNMatrixSize;
		moveStats.setWinningRatio(winningRatio);
		return moveStats;
	}

	// Code for Computer Turn starts here.

	/**
	 * If the winning percent of Human reaches to 80 % then Computer should make
	 * a move so that this player will not win.
	 */
	private static final double WINNING_PERCENT_TOLERENCE = 0.80;

	/**
	 * Plays for computer after Human player is done.
	 * 
	 * @param player
	 */
	public static GridCell executeComputerTurn(Player player,
			GridCellInfo humanGridCellInfo, PlayerMoveStats playerMoveStats,
			GridCell[][] gridCellMatrix, boolean[][] cellBooleanMatrix) {

		mGridCellMatrix = gridCellMatrix;
		mCellBooleanMatrix = cellBooleanMatrix;

		MoveStats hMoveStats = playerMoveStats.getHorizontalMoveStats();
		MoveStats vMoveStats = playerMoveStats.getVerticalMoveStats();
		MoveStats dMoveStats = playerMoveStats.getDiagonalMoveStats();

		boolean hFutureWin = hMoveStats.isFutureWinPossible();
		boolean vFutureWin = vMoveStats.isFutureWinPossible();
		boolean dFutureWin = dMoveStats.isFutureWinPossible();

		/** See If Computer needs to take any action to counter Human move. */
		if (hFutureWin && vFutureWin & dFutureWin) {
			if (hMoveStats.getWinningRatio() > vMoveStats.getWinningRatio()
					&& hMoveStats.getWinningRatio() > dMoveStats
							.getWinningRatio()) {

				return processForHorizonatalWin(humanGridCellInfo, player);
			} else if (vMoveStats.getWinningRatio() > dMoveStats
					.getWinningRatio()
					&& vMoveStats.getWinningRatio() > hMoveStats
							.getWinningRatio()) {
				return processForVerticalWin(humanGridCellInfo, player);
			} else {
				return processForDiagonalWin(humanGridCellInfo, player,
						dMoveStats);
			}

		} else if (hFutureWin && vFutureWin) {

			if (hMoveStats.getWinningRatio() > vMoveStats.getWinningRatio()) {
				return processForHorizonatalWin(humanGridCellInfo, player);
			} else {
				return processForVerticalWin(humanGridCellInfo, player);
			}

		} else if (vFutureWin && dFutureWin) {
			if (vMoveStats.getWinningRatio() > dMoveStats.getWinningRatio()) {
				return processForVerticalWin(humanGridCellInfo, player);
			} else {
				return processForDiagonalWin(humanGridCellInfo, player,
						dMoveStats);
			}

		} else if (hFutureWin && dFutureWin) {
			if (hMoveStats.getWinningRatio() > dMoveStats.getWinningRatio()) {
				return processForHorizonatalWin(humanGridCellInfo, player);
			} else {
				return processForDiagonalWin(humanGridCellInfo, player,
						dMoveStats);
			}
		} else if (hFutureWin) {
			return processForHorizonatalWin(humanGridCellInfo, player);
		} else if (vFutureWin) {
			return processForVerticalWin(humanGridCellInfo, player);
		} else if (dFutureWin) {
			return processForDiagonalWin(humanGridCellInfo, player, dMoveStats);
		}
		/** It means that Computer can do something to win itself. */
		else {
			return pickRandomGridCell();
		}

		// horizontalWinCheck();
	}

	/**
	 * Picks the random valid cell and returns it.
	 * 
	 * @return
	 */
	private static GridCell pickRandomGridCell() {

		GridCell gridCell = null;
		for (int rowIndex = 0; rowIndex < mCellBooleanMatrix.length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < mCellBooleanMatrix.length; columnIndex++) {

				if (!mCellBooleanMatrix[rowIndex][columnIndex]) {
					return mGridCellMatrix[rowIndex][columnIndex];
				}
			}
		}

		return gridCell;
	}

	/**
	 * Processes and takes the action on the basis of Diagonal impact of Human
	 * Move.
	 * 
	 * @param humanGridCellInfo
	 */
	private static GridCell processForDiagonalWin(
			GridCellInfo humanGridCellInfo, Player player, MoveStats dMoveStats) {

		//In case Reverse diagonal is not the one where user is winning.
		if (!dMoveStats.isRevDiagonal()) {
			// Check for all the columns in this row.
			for (int index = 0; index < mNxNMatrixSize; index++) {
				GridCell gridCell = mGridCellMatrix[index][index];

				if (gridCell.getSymbolType() == SymbolType.NONE) {
					return gridCell;
				}
			}
		} else {
			for (int index = mNxNMatrixSize - 1; index >= 0; index--) {
				GridCell gridCell = mGridCellMatrix[index][mNxNMatrixSize
						- index - 1];

				if (gridCell.getSymbolType() == SymbolType.NONE) {
					return gridCell;
				}
			}
		}
		return null;
	}

	/**
	 * Processes and takes the action on the basis of Vertical impact of Human
	 * Move.
	 * 
	 * @param humanGridCellInfo
	 */
	private static GridCell processForVerticalWin(
			GridCellInfo humanGridCellInfo, Player player) {

		// Check for all the columns in this row.
		int columnIndex = mGridCellInfo.getColumnIndex();
		for (int rowIndex = 0; rowIndex < mNxNMatrixSize; rowIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (gridCell.getSymbolType() == SymbolType.NONE) {
				return gridCell;
			}
		}
		return null;
	}

	/**
	 * Processes and takes the action on the basis of Horizontal impact of Human
	 * Move.
	 * 
	 * @param humanGridCellInfo
	 * @param player
	 */
	private static GridCell processForHorizonatalWin(
			GridCellInfo humanGridCellInfo, Player player) {

		// Check for all the columns in this row.
		int rowIndex = humanGridCellInfo.getRowIndex();
		for (int columnIndex = 0; columnIndex < mNxNMatrixSize; columnIndex++) {
			GridCell gridCell = mGridCellMatrix[rowIndex][columnIndex];

			if (gridCell.getSymbolType() == SymbolType.NONE) {
				return gridCell;
			}
		}
		return null;
	}
}
