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

import android.content.SharedPreferences;
import android.os.Bundle;

import net.darkkatrom.dkcolorpicker.fragment.SettingsColorPickerFragment;
import net.darkkatrom.dkwallpapers.R;
import net.darkkatrom.dkwallpapers.utils.PreferenceUtils;

public class CustomizationsFragment extends SettingsColorPickerFragment implements
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
             String key) {
        if (key.equals(PreferenceUtils.USE_GRADIENT)) {
            boolean useGradient = PreferenceUtils.getInstance(getActivity()).getUseGradient();
            int titleResid = useGradient ? R.string.start_color_title : R.string.color_title;
            findPreference(PreferenceUtils.START_COLOR).setTitle(titleResid);
        }
    }
}
