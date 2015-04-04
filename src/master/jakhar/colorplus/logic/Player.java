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

public class Player {

	//Set default PlayerType to HUMAN.
	private PlayerType mPlayerType = PlayerType.HUMAN;
	private int mPlayerNumber = -1;
	private SymbolType mSymbolType = SymbolType.NONE;
	
	public Player(int playerNumber, PlayerType playerType, SymbolType symbolType){
		mPlayerNumber = playerNumber;
		mPlayerType = playerType;
		mSymbolType = symbolType;
	}
	
	public SymbolType getSymbolType() {
		return mSymbolType;
	}

	public void setSymbolType(SymbolType mSymbolType) {
		this.mSymbolType = mSymbolType;
	}

	public PlayerType getPlayerType() {
		return mPlayerType;
	}

	public void setPlayerType(PlayerType playerType) {
		mPlayerType = playerType;
	}
	
	public int getPlayerNumber(){
		return mPlayerNumber;
	}
	
	public void setPlayerNumber(int mPlayerNumber) {
		this.mPlayerNumber = mPlayerNumber;
	}
	
}
