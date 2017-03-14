/*
 * Copyright (C) 2016 DarkKat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.darkkatrom.dkwallpapers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

import net.darkkatrom.dkcolorpicker.fragment.ColorPickerFragment;
import net.darkkatrom.dkcolorpicker.preference.ColorPickerPreference;
import net.darkkatrom.dkwallpapers.R;
import net.darkkatrom.dkwallpapers.utils.PreferenceUtils;

public class CustomizationsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.customize);

        PreferenceUtils.getInstance(getActivity()).setOnSharedPreferenceChangeListener(this);

        if (!PreferenceUtils.getInstance(getActivity()).getUseGradient()) {
            findPreference(PreferenceUtils.START_COLOR).setTitle(R.string.color_title);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ColorPickerPreference.RESULT_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            String extraNewColor = ColorPickerFragment.KEY_NEW_COLOR;
            if (extras != null && extras.getInt(extraNewColor) != 0) {
                String extraPrefKey = ColorPickerPreference.PREFERENCE_KEY;
                ((ColorPickerPreference) findPreference(extras.getString(extraPrefKey)))
                        .setNewColor(extras.getInt(extraNewColor));
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
             String key) {
        if (key.equals(PreferenceUtils.USE_GRADIENT)) {
            boolean useGradient = PreferenceUtils.getInstance(getActivity()).getUseGradient();
            int titleResid = useGradient ? R.string.start_color_title : R.string.color_title;
            findPreference(PreferenceUtils.START_COLOR).setTitle(titleResid);
        }
    }
}
