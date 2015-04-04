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

import com.google.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import master.jakhar.colorplus.R;

public class MainActivity extends ActionBarActivity {

	private String TAG = "MainActivity";

	Animation mAnimationScale = null;
	Animation mAnimationClockwise = null;

	private InterstitialAd mInterstitialAd;
	/** Parent layout containing all other layouts. */
	private FrameLayout mParentLayout;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//mParentLayout = (FrameLayout) findViewById(R.id.frame_home_screen_content);
		/** Add Ads View. */
		//addAdView();
		/** Add action bar. */
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.color.custom_action_bar_color));

		//Initialize SharedPreferences.
		SharedResources.getSharedPreferences(this);
		
		final TextView mTextView = (TextView) findViewById(R.id.fullscreen_content);
		mTextView.setTypeface(SharedResources
				.getCounterStrikeFont(getApplicationContext()));
		mTextView.setVisibility(View.VISIBLE);

		final ScaleAnimation animation = new ScaleAnimation(100, 100, 100, 100,
				Animation.RELATIVE_TO_SELF, (float) 0.5,
				Animation.RELATIVE_TO_SELF, (float) 0.5);
		mTextView.startAnimation(animation);

		mAnimationScale = AnimationUtils.loadAnimation(this,
				R.anim.scale_start_game);
		mAnimationClockwise = AnimationUtils.loadAnimation(this,
				R.anim.clockwise);
		mAnimationClockwise.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// mTextView.startAnimation(mAnimationScale);
				Intent intent = new Intent(MainActivity.this,
						GameActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_transition_fade,
						R.anim.activity_transition_hold);

			}
		});
		mAnimationScale.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(MainActivity.this,
						GameActivity.class);
				MainActivity.this.startActivity(intent);

			}
		});
		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// v.startAnimation(mAnimationScale);
				animation.cancel();
				v.startAnimation(mAnimationClockwise);
			}
		});

		createInterstitialAd();
	}

	/**
	 * Adds AdView to the Activity screen.
	 */
	private void addAdView() {

		com.google.android.gms.ads.AdView adView = // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
	            // values/strings.xml.
				adView = (com.google.android.gms.ads.AdView) findViewById(R.id.adView);
		LinearLayout adViewLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.adview, null);

		adViewLayout.addView(adView);
	            // Create an ad request. Check logcat output for the hashed device ID to
	            // get test ads on a physical device. e.g.
	            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
	            AdRequest adRequest = new AdRequest.Builder()
	                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	                    .build();

	            // Start loading the ad in the background.
	            adView.loadAd(adRequest);
	            
	            /** Add adView */
	    		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
	    				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	    		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    		adViewLayout.setLayoutParams(layoutParams);
	}
	
	/**
	 * Creates Interstitial Ad to show to the user.
	 */
	private void createInterstitialAd() {
		
		/** Loading InterstitialAd */
		// Create an interstitial ad. When a natural transition in the app
		// occurs (such as a
		// level ending in a game), show the interstitial. In this simple
		// example, the press of a
		// button is used instead.
		//
		// If the button is clicked before the interstitial is loaded, the user
		// should proceed to
		// the next part of the app (in this case, the next level).
		//
		// If the interstitial is finished loading, the user will view the
		// interstitial before
		// proceeding.
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(SharedResources.getAdUnitId());

		// Create an ad request.
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

		// Optionally populate the ad request builder.
		adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

		// Set an AdListener.
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {

			}

			@Override
			public void onAdClosed() {
				// Proceed to the next level.
				MainActivity.this.finish();
			}
		});

		// Start loading the ad now so that it is ready by the time the user is
		// ready to go to
		// the next level.
		mInterstitialAd.loadAd(adRequestBuilder.build());
		mInterstitialAd.show();
		
	}

	public void onSettingsButtonClick(View v) {

		openSettingsScreen();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_screen_activity_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {

		case R.id.action_settings:
			openSettingsScreen();
			return true;
			// case R.id.action_background:
			// openGallery();

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Opens settings screen.
	 */
	private void openSettingsScreen() {

		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_transition_fade,
				R.anim.activity_transition_hold);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		}
		this.finish();
		return super.onKeyDown(keyCode, event);
	}
}
