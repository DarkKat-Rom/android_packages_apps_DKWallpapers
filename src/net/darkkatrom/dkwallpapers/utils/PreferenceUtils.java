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

package net.darkkatrom.dkwallpapers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.preference.PreferenceManager;

import net.darkkatrom.dkwallpapers.R;

public final class PreferenceUtils {

    public static final String USE_GRADIENT         = "use_gradient";
    public static final String GRADIENT_ORIENTATION = "gradient_orientation";
    public static final String USE_CENTER_COLOR     = "use_center_color";
    public static final String START_COLOR          = "start_color";
    public static final String CENTER_COLOR         = "center_color";
    public static final String END_COLOR            = "end_color";

    public static final int DEFAULT_COLOR  = 0xff000000;
    public static final int NO_COLOR       = 0x00000000;

    private static PreferenceUtils sInstance;
    private final Context mContext;

    private final SharedPreferences mPreferences;

    public PreferenceUtils(final Context context) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final PreferenceUtils getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceUtils(context.getApplicationContext());
        }
        return sInstance;
    }

    public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener){
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public boolean getUseGradient() {
        return mPreferences.getBoolean(USE_GRADIENT, false);
    }

    public int getGradientOrientation() {
        String value = mPreferences.getString(GRADIENT_ORIENTATION, "2");
        return Integer.valueOf(value);
    }

    public Orientation getGradientDrawableOrientation() {
        final int orientation = getGradientOrientation();
        Orientation drawableOrientation = Orientation.TOP_BOTTOM;
        switch (orientation) {
            case 0:
                drawableOrientation = Orientation.LEFT_RIGHT;
                break;
            case 1:
                drawableOrientation = Orientation.TL_BR;
                break;
            case 3:
                drawableOrientation = Orientation.TR_BL;
                break;
        }
        return drawableOrientation;
    }

    public boolean getUseCenterColor() {
        return mPreferences.getBoolean(USE_CENTER_COLOR, false);
    }

    public int getBackgroundColor() {
        return mPreferences.getInt(START_COLOR, DEFAULT_COLOR);
    }

    public int[] getBackgroundColors() {
        int[] colors = new int[getUseCenterColor() ? 3 : 2];
        colors[0] = getStartColor();
        colors[1] = getUseCenterColor() ? getCenterColor() : getEndColor();
        if (getUseCenterColor()) {
           colors[2] = getEndColor();
        }
        return colors;
    }

    private int getStartColor() {
        return mPreferences.getInt(START_COLOR, DEFAULT_COLOR);
    }

    private int getCenterColor() {
        if (getUseGradient() && getUseCenterColor()) {
            return mPreferences.getInt(CENTER_COLOR, NO_COLOR);
        } else {
            return NO_COLOR;
        }
    }

    private int getEndColor() {
        if (getUseGradient()) {
            return mPreferences.getInt(END_COLOR, NO_COLOR);
        } else {
            return NO_COLOR;
        }
    }
}
