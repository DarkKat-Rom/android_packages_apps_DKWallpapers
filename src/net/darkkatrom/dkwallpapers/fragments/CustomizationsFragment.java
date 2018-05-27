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

import android.preference.PreferenceScreen;
import android.content.SharedPreferences;
import android.os.Bundle;

import net.darkkatrom.dkcolorpicker.fragment.SettingsColorPickerFragment;
import net.darkkatrom.dkcolorpicker.preference.ColorPickerPreference;
import net.darkkatrom.dkwallpapers.R;
import net.darkkatrom.dkwallpapers.utils.PreferenceUtils;

public class CustomizationsFragment extends SettingsColorPickerFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updatePreferenceScreen();
    }

    public void updatePreferenceScreen() {
        PreferenceScreen prefs = getPreferenceScreen();
        if (prefs != null) {
            prefs.removeAll();
        }
        addPreferencesFromResource(R.xml.customize);

        if (!PreferenceUtils.getInstance(getActivity()).getUseGradient()) {
            findPreference(PreferenceUtils.COLOR_SOLID_GRADIENT_START)
                    .setTitle(R.string.color_solid_title);
            ((ColorPickerPreference) findPreference(PreferenceUtils.COLOR_SOLID_GRADIENT_START))
                    .setPickerSubtitle(null);
            removePreference(PreferenceUtils.GRADIENT_ORIENTATION);
            removePreference(PreferenceUtils.GRADIENT_USE_CENTER);
            removePreference(PreferenceUtils.GRADIENT_CENTER);
            removePreference(PreferenceUtils.GRADIENT_END);
        } else if (!PreferenceUtils.getInstance(getActivity()).getUseGradientCenter()) {
            removePreference(PreferenceUtils.GRADIENT_CENTER);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
             String key) {
        if (key.equals(PreferenceUtils.USE_GRADIENT)
                || key.equals(PreferenceUtils.GRADIENT_USE_CENTER)) {
            updatePreferenceScreen();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
