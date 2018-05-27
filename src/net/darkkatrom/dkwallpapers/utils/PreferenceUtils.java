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

    public static final String USE_GRADIENT               = "use_gradient";
    public static final String GRADIENT_ORIENTATION       = "gradient_orientation";
    public static final String GRADIENT_USE_CENTER        = "gradient_use_center";
    public static final String COLOR_SOLID_GRADIENT_START = "color_solid_gradient_start";
    public static final String GRADIENT_CENTER            = "gradient_center";
    public static final String GRADIENT_END               = "gradient_end";

    public static final String DEFAULT_GRADIENT_ORIENTATION = "2"; // top to bottom

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

    public boolean getUseGradient() {
        return mPreferences.getBoolean(USE_GRADIENT, true);
    }

    public int getGradientOrientation() {
        String value = mPreferences.getString(GRADIENT_ORIENTATION, DEFAULT_GRADIENT_ORIENTATION);
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

    public boolean getUseGradientCenter() {
        return mPreferences.getBoolean(GRADIENT_USE_CENTER, true);
    }

    public int getBackgroundColor() {
        int defaultColor = mContext.getColor(R.color.wallpaper_default);
        return mPreferences.getInt(COLOR_SOLID_GRADIENT_START, defaultColor);
    }

    public int[] getBackgroundColors() {
        int[] colors = new int[getUseGradientCenter() ? 3 : 2];
        colors[0] = getGradientStart();
        colors[1] = getUseGradientCenter() ? getGradientCenter() : getGradientEnd();
        if (getUseGradientCenter()) {
           colors[2] = getGradientEnd();
        }
        return colors;
    }

    private int getGradientStart() {
        return getBackgroundColor();
    }

    private int getGradientCenter() {
        int defaultColor = mContext.getColor(R.color.wallpaper_default_center);
        return mPreferences.getInt(GRADIENT_CENTER, defaultColor);
    }

    private int getGradientEnd() {
        int defaultColor = mContext.getColor(R.color.wallpaper_default_end);
        return mPreferences.getInt(GRADIENT_END, defaultColor);
    }
}
