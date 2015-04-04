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

/**
 * This class holds the information related to one move made by
 * a {@link Player} to win the game.
 * @author Pankaj Jakhar.
 *
 */
public class PlayerMoveStats { 

	private MoveStats horizontalMoveStats;
	private MoveStats verticalMoveStats;
	private MoveStats diagonalMoveStats;
	
	
	public static class MoveStats {
	
		private boolean isPlayerWon = false;
		private double winningRatio;
		
		/** This variable will hold the information if the Human can win in the future in 
		 * a Row, Column or Diagonally. 
		 * */
		private boolean isFutureWinPossible = true;
		
		//If user can win on Reverse Diagonal then this flag is true.
		private boolean isRevDiagonal = true;
		
		public double getWinningRatio() {
			return winningRatio;
		}
		public void setWinningRatio(double winningRatio) {
			this.winningRatio = winningRatio;
		}
		public boolean isPlayerWon() {
			return isPlayerWon;
		}
		public void setPlayerWon(boolean isPlayerWon) {
			this.isPlayerWon = isPlayerWon;
		}
		public boolean isFutureWinPossible() {
			return isFutureWinPossible;
		}
		public void setFutureWinPossible(boolean isFutureWinPossible) {
			this.isFutureWinPossible = isFutureWinPossible;
		}
		public boolean isRevDiagonal() {
			return isRevDiagonal;
		}
		public void setRevDiagonal(boolean isRevDiagonal) {
			this.isRevDiagonal = isRevDiagonal;
		}
	}


	public MoveStats getHorizontalMoveStats() {
		return horizontalMoveStats;
	}


	public void setHorizontalMoveStats(MoveStats horizontalMoveStats) {
		this.horizontalMoveStats = horizontalMoveStats;
	}


	public MoveStats getVerticalMoveStats() {
		return verticalMoveStats;
	}


	public void setVerticalMoveStats(MoveStats verticalMoveStats) {
		this.verticalMoveStats = verticalMoveStats;
	}


	public MoveStats getDiagonalMoveStats() {
		return diagonalMoveStats;
	}


	public void setDiagonalMoveStats(MoveStats diagonalMoveStats) {
		this.diagonalMoveStats = diagonalMoveStats;
	}
}
