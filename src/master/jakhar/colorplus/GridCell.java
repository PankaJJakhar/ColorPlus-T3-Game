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
package master.jakhar.colorplus;

import master.jakhar.colorplus.logic.SymbolType;
import android.graphics.Rect;

/**
 * Keeps information related to a GridBox.
 * @author Pankaj Jakhar
 *
 */
public class GridCell {
	
	private int mPlayerNumber;
	private Position mStartPosition;
	private Position mEndPosition;
	private SymbolType mSymbolType = SymbolType.NONE;
	private Rect mRect = null;
	
	private int rowIndex = -1;
	private int columnIndex = -1;
	
	
	public GridCell(Rect rect, SymbolType symbolType) {
		mRect = rect;
		mSymbolType = symbolType;
	}

	
	
	public int getPlayerNumber() {
		return mPlayerNumber;
	}

	public void setPlayerNumber(int mPlayerNumber) {
		this.mPlayerNumber = mPlayerNumber;
	}

	public Position getStartPosition() {
		return mStartPosition;
	}

	public void setStartPosition(Position mStartPosition) {
		this.mStartPosition = mStartPosition;
	}

	public Position getEndPosition() {
		return mEndPosition;
	}

	public void setEndPosition(Position mEndPosition) {
		this.mEndPosition = mEndPosition;
	}

	public SymbolType getSymbolType() {
		return mSymbolType;
	}

	public void setSymbolType(SymbolType mSymbolType) {
		this.mSymbolType = mSymbolType;
	}

	public Rect getRect() {
		return mRect;
	}

	public void setRect(Rect mRect) {
		this.mRect = mRect;
	}

	public int getRowIndex() {
		return rowIndex;
	}



	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}



	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	//Wrapper of Co-ordinates on screen.
	public static class Position{
		int x, y;
		public Position(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

}
