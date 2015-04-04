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

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

/**
 * This class keeps Global Resources which can be accessed from the app
 * anywhere.
 * @author Pankaj Jakhar
 *
 */
public class SharedResources {

	private static SharedPreferences mSharedPreferences = null;
	private static final String AD_UNIT_ID = "4356788765GFGFHJJ";
	
	/**
	 * Returns the {@link SharedPreferences}.
	 * @param context
	 * @return {@link SharedPreferences} instance.
	 */
	public static SharedPreferences getSharedPreferences(Context context){
		if(mSharedPreferences == null){
			mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return mSharedPreferences;
	}
	
	private static Typeface mCSTypeface = null;
	/**
	 * Returns the CS font to the caller.
	 * @param mContext
	 * @return
	 */
	public static Typeface getCounterStrikeFont(Context mContext){
		if(mCSTypeface == null)
			mCSTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/counter_strike.ttf");
		return mCSTypeface;
	}
	
	/**
	 * Returns the Ad Unit Id.
	 * @return AD_UNIT_ID
	 */
	public static String getAdUnitId(){
		return AD_UNIT_ID;
	}
}
