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

import master.jakhar.colorplus.R;
import master.jakhar.colorplus.GridCell.Position;
import master.jakhar.colorplus.logic.GameStatusInfo;
import master.jakhar.colorplus.logic.Player;
import master.jakhar.colorplus.logic.PlayerType;
import master.jakhar.colorplus.logic.SymbolType;
import master.jakhar.colorplus.logic.TicTacToeUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

public class GameBoardView extends SurfaceView implements
		SurfaceHolder.Callback, OnTouchListener {

	private final String TAG = "GameBoardView";
	private GameActivity mGameActivity = null;
	private Context mContext = null;
	private SurfaceHolder mHolder;
	private Canvas mCanvas = null;

	private int mCanvasHeight;
	private int mCanvasWidth;
	private int mCanvasCenterX;
	private int mCanvasCenterY;

	private Paint mInnerCirclePaint = null;
	private Paint mOuterCirclePaint = null;
	private Paint mGridPaint = null;
	private Paint mGridCellPaint = null;
	private Paint mTextPaint = null;

	private float mInnerCircleRadius = 350;
	private float mOuterCircleRadius = 400;
	private int mInfoTextPadding;
	private int mGridSizeNxN = 5;

	// Key to fetch game's grid size from SharedPreferences.
	// Preference Keys.
	private String GRID_SIZE_KEY = "Grid Size(NxN)";
	private String CUSTOM_SETTINGS_ENABLED = "custom_settings";
	private String COMPUTER_MODE = "computer_mode";

	private static final String PLAYER = "Player";

	/** GridCell matrix for creating Grid */
	private GridCell[][] mGridCellMatrix = null;
	private boolean[][] mCellBooleanMatrix = new boolean[mGridSizeNxN][mGridSizeNxN];
	/** Number of Players for Game. */
	private int mNumberOfPlayers = 2;

	/** Players for Game */
	private Player[] mPlayers = new Player[2];

	/**
	 * Current Player whose turn is to Play. By default Player Number 1 with
	 * index 0 will play first.
	 */
	private int mCurrentPlayer = 0;

	/**
	 * Previous Player is the one who just clicked and moved turn to next
	 * player. By default its value is set to -1 which is invalid index for a
	 * player logically.
	 */
	private int mPreviousPlayer = -1;

	/** Grid End-Points. */
	private int mGridLeft;
	private int mGridTop;
	private int mGridBottom;
	private int mGridRight;

	// Grid specs.
	private int mGridHeight;
	private int mGridWidth;

	// Grid Cell Specs.
	private int mCellHeight;
	private int mCellWidth;

	private boolean isTouch;
	private boolean mPlayAgainstComputer;

	/** Tracks if Game is Over or still running. */
	private boolean isGameOver = false;
	private int stopX;
	private int stopY;
	private int mYouTextPadding;
	private int mComputerInfoTextPadding;
	private int mYouInfoTextPadding;
	private int mComputerTextPadding;
	private int mTextPaddingLeft;
	boolean isCustomSettingEnabled = false;
	boolean isComputerPlayModeOn = true;

	public GameBoardView(Context context) {
		super(context);

		// Sets the user defined grid size.
		try {
			isCustomSettingEnabled = SharedResources.getSharedPreferences(
					mContext).getBoolean(CUSTOM_SETTINGS_ENABLED, false);

			if (!isCustomSettingEnabled) {
				mGridSizeNxN = 5;
				isComputerPlayModeOn = true;
			} else {
				mGridSizeNxN = Integer.parseInt(SharedResources
						.getSharedPreferences(mContext).getString(
								GRID_SIZE_KEY, "5"));

				isComputerPlayModeOn = SharedResources.getSharedPreferences(
						mContext).getBoolean(COMPUTER_MODE, false);
			}

		} catch (Exception e) {
			mGridSizeNxN = 7;
		}

		mGameActivity = (GameActivity) context;
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		setWillNotDraw(false);
		createPlayers();
		init(context);
	}

	public GameBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Sets the user defined grid size.
		try {
			isCustomSettingEnabled = SharedResources.getSharedPreferences(
					mContext).getBoolean(CUSTOM_SETTINGS_ENABLED, false);

			if (!isCustomSettingEnabled) {
				mGridSizeNxN = 5;
				isComputerPlayModeOn = true;
			} else {
				mGridSizeNxN = Integer.parseInt(SharedResources
						.getSharedPreferences(mContext).getString(
								GRID_SIZE_KEY, "5"));

				isComputerPlayModeOn = SharedResources.getSharedPreferences(
						mContext).getBoolean(COMPUTER_MODE, false);
			}

		} catch (Exception e) {
			mGridSizeNxN = 7;
		}

		mGameActivity = (GameActivity) context;
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		setWillNotDraw(false);
		createPlayers();
		init(context);

	}

	/**
	 * Creates Players for the Game.
	 */
	private void createPlayers() {

		// for(int playerNumber = 0; playerNumber < mNumberOfPlayers;
		// playerNumber++){
		//
		// }
		/** Create Player #1. */
		Player playerOne = null;
		playerOne = new Player(0, PlayerType.HUMAN, SymbolType.CIRCLE);

		/** Create Player #2. */
		Player playerTwo = null;

		/** Second Player can be Human or Computer. */
		if (mPlayAgainstComputer) {
			playerTwo = new Player(1, PlayerType.COMPUTER, SymbolType.CROSS);
		} else {
			playerTwo = new Player(1, PlayerType.HUMAN, SymbolType.CROSS);
		}

		mPlayers[0] = playerOne;
		mPlayers[1] = playerTwo;
	}

	/**
	 * Initializes all the Canvas related objects.
	 * 
	 * @param context
	 */
	private void init(Context context) {

		mYouTextPadding = mContext.getResources().getDimensionPixelSize(
				R.dimen.you_text_padding);
		mComputerTextPadding = mContext.getResources().getDimensionPixelSize(
				R.dimen.computer_text_padding);
		mYouInfoTextPadding = mContext.getResources().getDimensionPixelSize(
				R.dimen.you_text_info_padding);
		mComputerInfoTextPadding = mContext.getResources()
				.getDimensionPixelSize(R.dimen.computer_text_info_padding);
		mTextPaddingLeft = mContext.getResources().getDimensionPixelSize(
				R.dimen.text_padding_left);

		mInfoTextPadding = mContext.getResources().getDimensionPixelSize(
				R.dimen.info_text_padding);
		mInnerCirclePaint = new Paint();
		// mOuterCirclePaint.setTypeface(miniSystem);
		mInnerCirclePaint.setColor(android.graphics.Color.WHITE);
		mInnerCirclePaint.setStyle(Paint.Style.FILL);
		mInnerCirclePaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.inner_circle_stroke_width));
		mInnerCirclePaint.setDither(true);
		mInnerCirclePaint.setStrokeJoin(Paint.Join.ROUND);
		mInnerCirclePaint.setStrokeCap(Paint.Cap.ROUND);
		mInnerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		/** Initialize Outer Circle paint. */
		mOuterCirclePaint = new Paint();
		// mOuterCirclePaint.setTypeface(miniSystem);
		mOuterCirclePaint.setColor(Color.parseColor("#C0C0C0"));
		mOuterCirclePaint.setStyle(Paint.Style.FILL);
		mOuterCirclePaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.outer_circle_stroke_width));
		mOuterCirclePaint.setDither(true);
		mOuterCirclePaint.setStrokeJoin(Paint.Join.ROUND);
		mOuterCirclePaint.setStrokeCap(Paint.Cap.ROUND);
		mOuterCirclePaint.setMaskFilter(new BlurMaskFilter(10,
				BlurMaskFilter.Blur.NORMAL));
		mOuterCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		/** Initialize Grid Cell paint. */
		mGridPaint = new Paint();
		// mGridPaint.setTypeface(miniSystem);
		mGridPaint.setColor(Color.parseColor("#C0C0C0"));
		mGridPaint.setStyle(Paint.Style.STROKE);
		mGridPaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.grid_paint_stroke_width));
		mGridPaint.setDither(true);
		mGridPaint.setStrokeJoin(Paint.Join.ROUND);
		mGridPaint.setStrokeCap(Paint.Cap.ROUND);
		mGridPaint.setMaskFilter(new BlurMaskFilter(30,
				BlurMaskFilter.Blur.SOLID));
		mGridPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

		/** Initialize Cell Symbol paint. */
		mGridCellPaint = new Paint();
		// mSymbolPaint.setTypeface(miniSystem);
		mGridCellPaint.setColor(Color.parseColor("#A52A2A"));
		mGridCellPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mGridCellPaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.grid_cell_stroke_width));
		mGridCellPaint.setTextSize(mContext.getResources()
				.getDimensionPixelSize(R.dimen.grid_cell_text_size));
		mGridCellPaint.setDither(true);
		mGridCellPaint.setStrokeJoin(Paint.Join.ROUND);
		mGridCellPaint.setStrokeCap(Paint.Cap.ROUND);
		mGridCellPaint.setMaskFilter(new BlurMaskFilter(30,
				BlurMaskFilter.Blur.NORMAL));
		mGridCellPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// mGridCellPaint.setShadowLayer(7, 0, 0, Color.RED);

		mTextPaint = new Paint();
		// mSymbolPaint.setTypeface(miniSystem);
		// mTextPaint.setColor(Color.parseColor("#A52A2A"));
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mTextPaint.setTypeface(SharedResources.getCounterStrikeFont(mContext));
		// mTextPaint.setFakeBoldText(true);
		mTextPaint.setStrokeWidth(mContext.getResources()
				.getDimensionPixelSize(R.dimen.text_paint_stroke_width));
		mTextPaint.setTextSize(mContext.getResources().getDimensionPixelSize(
				R.dimen.text_paint_text_size));
		mTextPaint.setDither(true);
		mTextPaint.setStrokeJoin(Paint.Join.ROUND);
		mTextPaint.setStrokeCap(Paint.Cap.ROUND);
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}

	/**
	 * MyPaint is custom Paint instance which will wrap similar properties of
	 * multiple Paint instance in itself.
	 * 
	 * @author JAKHAR
	 *
	 */
	public static class MyPaint {

	}

	/**
	 * Wrapper of Grid's co-ordinates.
	 * 
	 * @author Pankaj Jakhar
	 *
	 */
	public static class GridPosition {
		int x, y;

		GridPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		mCanvasWidth = w;
		mCanvasHeight = h;

		/** Center Points of Canvas. */
		mCanvasCenterX = mCanvasWidth / 2;
		mCanvasCenterY = mCanvasHeight / 2;

		// Calculate Grid's start and end points.
		mGridWidth = mGridHeight = mCanvasWidth;

		int unusedScreen = mCanvasHeight - mCanvasWidth;

		// Shifts the Grid down on the canvas.
		mGridTop = (int) mCanvasCenterY - mGridHeight / 2 + unusedScreen / 2;
		mGridLeft = 0;
		mGridBottom = (int) mGridTop + mGridHeight;
		mGridRight = mCanvasWidth;

		mCellHeight = mCellWidth = (int) mCanvasWidth / mGridSizeNxN;

		stopX = mGridLeft;
		stopY = mGridTop;

		createGridCellMatrix();

		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Creates Rect matrix to create Grid of Rectangles.
	 */
	private void createGridCellMatrix() {
		/** Nullify all references. */
		mGridCellMatrix = null;
		mCellBooleanMatrix = null;

		/** Create new references. */
		mGridCellMatrix = new GridCell[mGridSizeNxN][mGridSizeNxN];
		mCellBooleanMatrix = new boolean[mGridSizeNxN][mGridSizeNxN];

		for (int i = 0; i < mGridSizeNxN; i++) {

			for (int j = 0; j < mGridSizeNxN; j++) {
				// Start Position of GridCell.
				int startX = mGridLeft + mCellWidth * j;
				int startY = mGridTop + mCellHeight * i;

				// End Position of GridCell.
				int endX = mGridLeft + mCellHeight + mCellWidth * j;
				int endY = mGridTop + mCellHeight + mCellHeight * i;

				Rect rect = new Rect(startX, startY, endX, endY);

				GridCell gridCell = new GridCell(rect, SymbolType.NONE);
				gridCell.setRowIndex(i);
				gridCell.setColumnIndex(j);
				gridCell.setStartPosition(new Position(startX, startY));
				gridCell.setEndPosition(new Position(endX, endY));
				mGridCellMatrix[i][j] = gridCell;
			}
		}
	}

	/**
	 * Wraps information about clicked GridCell.
	 * 
	 * @author Pankaj Jakhar
	 *
	 */
	public static class GridCellInfo {
		boolean isValidGridCell;
		int rowIndex = -1;
		int columnIndex = -1;
		Rect rect;

		public boolean isValidGridCell() {
			return isValidGridCell;
		}

		public void setValidGridCell(boolean isValidGridCell) {
			this.isValidGridCell = isValidGridCell;
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

		public Rect getRect() {
			return rect;
		}

		public void setRect(Rect rect) {
			this.rect = rect;
		}
	}

	/**
	 * Returns the info of {@link GridCell} which is clicked if clicked on the
	 * valid cell on Canvas. If and only if a valid cell is clicked then only an
	 * action is taken in order to proceed with game playing. If already
	 * occupied {@link GridCell} is clicked then no action is taken.
	 * 
	 * @param x
	 *            position of x co-ordinate clicked.
	 * @param y
	 *            position of y co-ordinate clicked.
	 * @return GridCellInfo wrapper of {@link GridCell} info and other details.
	 */
	private GridCellInfo getClickedGridCellInfo(int x, int y) {

		GridCellInfo gridCellInfo = new GridCellInfo();

		boolean isRectFound = false;
		for (int i = 0; i < mGridSizeNxN; i++) {

			for (int j = 0; j < mGridSizeNxN; j++) {

				GridCell gridCell = mGridCellMatrix[i][j];
				if (gridCell.getSymbolType() != SymbolType.NONE) {
					continue;
				}

				Rect rect = gridCell.getRect();
				if (rect.left < x && x < rect.right && rect.top < y
						&& y < rect.bottom) {

					/** Set current player's symbol in the cell. */
					gridCell.setSymbolType(getSymbolTypeCurrentPlayer());

					int rowIndex = i + 1;
					int columnIndex = j + 1;
					// Toast.makeText(mContext, "Clicked inside - Row " +
					// rowIndex + " Column " + columnIndex,
					// Toast.LENGTH_SHORT).show();
					isRectFound = true;

					/**
					 * Increment currentPlayer index and reset current player if
					 * the index of current player has reached to the Number of
					 * Players playing game.
					 */
					mPreviousPlayer = mCurrentPlayer;
					mCurrentPlayer++;
					if (mCurrentPlayer == mNumberOfPlayers) {
						mCurrentPlayer = 0;
					}

					// Wraps information.
					gridCellInfo.rect = rect;
					gridCellInfo.rowIndex = i;
					gridCellInfo.columnIndex = j;

					/** Also mark this Grid Cell filled. */
					mCellBooleanMatrix[i][j] = true;
					break;
				}
			}

			if (isRectFound)
				break;
		}
		gridCellInfo.isValidGridCell = isRectFound;

		Log.d(TAG, "ClickedGridCell ColumnIndex: " + gridCellInfo.columnIndex);
		Log.d(TAG, "ClickedGridCell RowIndex: " + gridCellInfo.rowIndex);
		Log.d(TAG, "ClickedGridCell Valid Cell Info: "
				+ gridCellInfo.isValidGridCell);

		return gridCellInfo;
	}

	/** Returns the {@link SymbolType} for current Player. */
	private SymbolType getSymbolTypeCurrentPlayer() {

		Log.d(TAG, "Current Player Played: " + mCurrentPlayer);
		Player player = mPlayers[mCurrentPlayer];
		if (player.getPlayerNumber() == 0) {
			Log.d(TAG, "Curren Player(1) Symbol: " + "CIRCLE");
			return SymbolType.CIRCLE;
		} else if (player.getPlayerNumber() == 1) {
			Log.d(TAG, "Curren Player(2) Symbol: " + "CROSS");
			return SymbolType.CROSS;
		}

		return null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDraw() called.");
		mCanvas = canvas;
		// canvas.drawColor(Color.parseColor("#0099cc"));
		canvas.drawColor(Color.parseColor("#FFFFFF"));

		stopX = stopX + 5;
		stopY = stopY;
		if (stopX >= mCanvasWidth)
			stopX = mGridLeft;
		if (stopY >= mCanvasHeight)
			stopY = mGridTop;

		// Draw Line on Canvas.
		// canvas.drawLine(mGridTop, mGridLeft, stopX, stopY, mTextPaint);

		/** Winning Counts display for Players. */
		SharedPreferences prefs = SharedResources
				.getSharedPreferences(mContext);
		int playerOneWinningCount = prefs.getInt(PLAYER + 0, 0);
		int playerTwoWinningCount = prefs.getInt(PLAYER + 1, 0);

		/** Who is next Player information display. */
		int playerNumberToDisplay = mCurrentPlayer + 1;
		// mTextPaint.setStrokeWidth(3);
		// canvas.drawText("Next", mCellWidth/3, mGridTop - mCellHeight,
		// mTextPaint);
		canvas.drawText("You: Won " + String.valueOf(playerOneWinningCount)
				+ " matches!", mTextPaddingLeft, mGridTop - mYouTextPadding,
				mTextPaint);
		canvas.drawText(
				"Computer: Won " + String.valueOf(playerTwoWinningCount)
						+ " matches!", mTextPaddingLeft, mGridTop
						- mComputerTextPadding, mTextPaint);
		mTextPaint.setStrokeWidth(1);
		// canvas.drawText("Won " + playerOneWinningCount, mTextPaddingLeft,
		// mGridTop - mCellHeight + mInfoTextPadding, mTextPaint);
		// canvas.drawText("Won " + playerTwoWinningCount, mTextPaddingLeft,
		// mGridTop - 2 * mCellHeight + mInfoTextPadding, mTextPaint);
		// canvas.drawText("Won " + playerOneWinningCount, mCellWidth/3,
		// mGridTop - 3 * mCellHeight + 50, mTextPaint);

		/** Create Game's Grid. */
		for (int i = 0; i < mGridSizeNxN; i++) {

			for (int j = 0; j < mGridSizeNxN; j++) {
				GridCell gridCell = mGridCellMatrix[i][j];
				Rect rect = gridCell.getRect();
				canvas.drawRect(rect, mGridPaint);

				/** Write Symbols in Grid Boxes.. */
				if (gridCell.getSymbolType() == SymbolType.CIRCLE) {
					// canvas.drawText("O", x, y, mSymbolPaint);
					mGridCellPaint.setColor(Color.parseColor("#0099cc"));
					canvas.drawRect(rect, mGridCellPaint);
				} else if (gridCell.getSymbolType() == SymbolType.CROSS) {
					// canvas.drawText("X", x, y, mSymbolPaint);
					mGridCellPaint.setColor(Color.CYAN);
					canvas.drawRect(rect, mGridCellPaint);
				}

			}
		}
		// invalidate();
		
		super.onDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		return super.performClick();
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();

		int eventaction = event.getAction();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN:
			GameStatusInfo gameStatusInfo = null;

			if (!isGameOver) {
				Log.d(TAG, "Game is not yet Over!");
				GridCellInfo gridCellInfo = getClickedGridCellInfo(x, y);
				// printGrid();
				/**
				 * If clicked cell is valid and Game is still not over then
				 * check if the player who clicked gridcell wins or not.
				 */
				if (gridCellInfo.isValidGridCell) {

					this.invalidate();
					Log.d(TAG, "Clicked Cell is valid");
					gameStatusInfo = TicTacToeUtil.getGameStatusInfo(
							gridCellInfo, mCellBooleanMatrix,
							mPlayers[mPreviousPlayer], mGridCellMatrix);
				} else {
					Log.d(TAG, "Clicked Cell is not valid, breaking execution!");
					break;
				}

				if (gameStatusInfo.isGameTie()) {
					isGameOver = true;
					Log.d(TAG, "Game Tie");
					Toast.makeText(mContext, "Game Tie!", Toast.LENGTH_SHORT)
							.show();
					// showPopupWindow();
				} else if (gameStatusInfo.isGameWon()) {
					/** Store winning count in SharedPreferences. */
					SharedPreferences prefs = SharedResources
							.getSharedPreferences(mContext);
					Editor editor = prefs.edit();
					String winningCountKey = PLAYER
							+ mPlayers[mPreviousPlayer].getPlayerNumber();
					int winningCount = prefs.getInt(winningCountKey, 0) + 1;
					editor.putInt(winningCountKey, winningCount);
					editor.commit();

					/** Update Game status. */
					isGameOver = true;
					int playerNum = mPreviousPlayer + 1;
					Log.d(TAG, "Game Won by Player " + playerNum);
					Toast.makeText(mContext, "You Won!", Toast.LENGTH_SHORT)
							.show();
					// showPopupWindow();
				}
				/** This is the turn of Computer to play its turn. */
				else {
					// Only if computer mode is on then computer will play.
					if (isComputerPlayModeOn) {
						GridCell gridCell = TicTacToeUtil.executeComputerTurn(
								mPlayers[mCurrentPlayer], gridCellInfo,
								gameStatusInfo.getPlayerMoveStats(),
								mGridCellMatrix, mCellBooleanMatrix);

						if (gridCell == null)
							return true;

						gridCell.setSymbolType(SymbolType.CROSS);
						gridCell.setPlayerNumber(1);
						GridCellInfo gridCellInfoComp = new GridCellInfo();
						gridCellInfoComp.rowIndex = gridCell.getRowIndex();
						gridCellInfoComp.columnIndex = gridCell
								.getColumnIndex();
						gridCellInfoComp.setValidGridCell(true);

						Position startPosition = gridCell.getStartPosition();
						Position endPosition = gridCell.getEndPosition();

						gridCellInfoComp.setRect(new Rect(startPosition.x,
								startPosition.y, endPosition.x, endPosition.y));

						// Toast.makeText(mContext, "Computer made move",
						// Toast.LENGTH_SHORT).show();

						mCellBooleanMatrix[gridCell.getRowIndex()][gridCell
								.getColumnIndex()] = true;
						mGridCellMatrix[gridCell.getRowIndex()][gridCell
								.getColumnIndex()] = gridCell;

						gameStatusInfo = TicTacToeUtil.getGameStatusInfo(
								gridCellInfoComp, mCellBooleanMatrix,
								mPlayers[mCurrentPlayer], mGridCellMatrix);

						if (gameStatusInfo.isGameTie()) {
							isGameOver = true;
							Log.d(TAG, "Game Tie");
							Toast.makeText(mContext, "Game Tie!",
									Toast.LENGTH_SHORT).show();
						} else if (gameStatusInfo.isGameWon()) {
							/** Store winning count in SharedPreferences. */
							SharedPreferences prefs = SharedResources
									.getSharedPreferences(mContext);
							Editor editor = prefs.edit();
							String winningCountKey = PLAYER
									+ mPlayers[mCurrentPlayer]
											.getPlayerNumber();
							int winningCount = prefs.getInt(winningCountKey, 0) + 1;
							editor.putInt(winningCountKey, winningCount);
							editor.commit();

							/** Update Game status. */
							isGameOver = true;
							Toast.makeText(mContext, "Computer Won!",
									Toast.LENGTH_SHORT).show();
						}

						mCurrentPlayer++;
						if (mCurrentPlayer == 2)
							mCurrentPlayer = 0;
					}
				}

				isTouch = true;
			} else {
				Toast.makeText(mContext, "Game is Over!", Toast.LENGTH_SHORT)
						.show();
			}

			break;

		case MotionEvent.ACTION_MOVE:

			// Toast.makeText(mContext, "MOVE "+"X: "+X+" Y: "+Y,
			// Toast.LENGTH_SHORT).show();

			break;

		case MotionEvent.ACTION_UP:

			// Toast.makeText(mContext, "ACTION_UP "+"X: "+X+" Y: "+Y,
			// Toast.LENGTH_SHORT).show();

			break;

		}

		return true;

	}

	/**
	 * Resets the game to its initial state.
	 */
	public void resetGame() {
		createGridCellMatrix();
		mCurrentPlayer = 0;
		mPreviousPlayer = -1;
		isGameOver = false;

		/** Refresh canvas. */
		this.invalidate();
	}

	/**
	 * Prints the current state of Grid.
	 */
	private void printGrid() {

		for (int rowIndex = 0; rowIndex < mGridSizeNxN; rowIndex++) {
			for (int columnIndex = 0; columnIndex < mGridSizeNxN; columnIndex++) {
				int rowNumber = rowIndex + 1;
				int columnNumber = columnIndex + 1;
				int playerNumber = mGridCellMatrix[rowIndex][columnIndex]
						.getPlayerNumber() + 1;
				Log.d(TAG, "Row Num. " + rowNumber + " -- Column Num. "
						+ columnNumber + " Player Num. " + playerNumber);
				Log.d(TAG,
						"Row Num. "
								+ rowNumber
								+ " -- Column Num. "
								+ columnNumber
								+ " SymbolType"
								+ mGridCellMatrix[rowIndex][columnIndex]
										.getSymbolType());
			}

		}
	}

	private void animateOuterCircle() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}
}
