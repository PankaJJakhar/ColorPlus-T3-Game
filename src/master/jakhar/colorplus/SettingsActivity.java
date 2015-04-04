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

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.KeyEvent;
import master.jakhar.colorplus.R;

public class SettingsActivity extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    addPreferencesFromResource(R.xml.preferences);
	    
	    final CheckBoxPreference computerModePref = (CheckBoxPreference) getPreferenceManager().findPreference("computer_mode"); 
	    
	    final CheckBoxPreference customSettingsPref = (CheckBoxPreference) getPreferenceManager().findPreference("custom_settings");
	    
	    final ListPreference gridListPref = (ListPreference) getPreferenceManager().findPreference("Grid Size(NxN)");

	    customSettingsPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {            
	        public boolean onPreferenceChange(Preference preference, Object newValue) {
	            Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());       
	            boolean flag = Boolean.valueOf(newValue.toString());
	            if(flag){
	            	computerModePref.setEnabled(true);
	            	gridListPref.setEnabled(true);
	            } else {
	            	computerModePref.setEnabled(false);
	            	computerModePref.setDefaultValue(true);
	            	gridListPref.setEnabled(false);
	            }
	            
	            return true;
	        }
	    }); 
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
		//Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
		//startActivity(intent);
		finish();
		return super.onKeyDown(keyCode, event);
	}
}
