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
 * Wraps the information regarding the status of the Game.
 * @author Pankaj Jakhar
 *
 */
public class GameStatusInfo {

	/**Game is tie now with no empty grid cells left and no Player won.*/
	private boolean isGameTie;
	
	/**If game is won by any one of the players */
	private boolean isGameWon;

	/**Holds the information about the current move stats by Player.*/
	private PlayerMoveStats playerMoveStats;

	public boolean isGameTie() {
		return isGameTie;
	}

	public void setGameTie(boolean isGameTie) {
		this.isGameTie = isGameTie;
	}

	public boolean isGameWon() {
		return isGameWon;
	}

	public void setGameWon(boolean isGameWon) {
		this.isGameWon = isGameWon;
	}

	public void setPlayerMoveStats(PlayerMoveStats playerMoveStats) {
		this.playerMoveStats = playerMoveStats;
	}
	
	public PlayerMoveStats getPlayerMoveStats() {
		return playerMoveStats;
	}
}
