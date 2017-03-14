/*
 * Copyright (C) 2011 The Android Open Source Project
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

package net.darkkatrom.dkwallpapers;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.View;

import net.darkkatrom.dkwallpapers.utils.PreferenceUtils;

public class ColorWallpaperActivity extends Activity {
    private View mPreviewRoot;

    private PreferenceUtils mUtils;

    private GradientDrawable mBackground = null;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.wallpaper_preview);

        mPreviewRoot = findViewById(R.id.preview_root);

        mUtils = new PreferenceUtils(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setPreviewBackground();
    }

    private void setPreviewBackground() {
        mPreviewRoot.setBackground(null);
        if (!mUtils.getUseGradient()) {
            mPreviewRoot.setBackgroundColor(mUtils.getBackgroundColor());
        } else {
            if (mBackground == null) {
                mBackground = new GradientDrawable();
            }
            mBackground.setOrientation(mUtils.getGradientDrawableOrientation());
            mBackground.setColors(mUtils.getBackgroundColors());
            mPreviewRoot.setBackground(mBackground);
        }
    }

    public void setWallpaper(View v) {
        if (mBackground == null) {
            return;
        }
        WallpaperManager wm = WallpaperManager.getInstance(this);
        int width = mPreviewRoot.getWidth();
        int height = (int) (wm.getDesiredMinimumHeight());
        Bitmap wallpaperBitmap = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas c = new Canvas(wallpaperBitmap);
        if (!mUtils.getUseGradient()) {
            paint.setColor(mUtils.getBackgroundColor());
        } else {
            int startPositionX = Math.round(width * 0.5f);
            int startPositionY = 0;
            int endPositionX = Math.round(width * 0.5f);
            int endPositionY = height;

            int orientation = mUtils.getGradientOrientation();
            switch (orientation) {
                case 0:
                    startPositionX = 0;
                    startPositionY = Math.round(height * 0.5f);
                    endPositionX = width;
                    endPositionY = Math.round(height * 0.5f);
                    break;
                case 1:
                    startPositionX = 0;
                    startPositionY = 0;
                    endPositionX = width;
                    endPositionY = height;
                    break;
                case 3:
                    startPositionX = width;
                    startPositionY = 0;
                    endPositionX = 0;
                    endPositionY = height;
                    break;
            }

            Shader gradientShader = new LinearGradient(startPositionX, startPositionY, endPositionX,
                    endPositionY, mUtils.getBackgroundColors(), null, Shader.TileMode.CLAMP);
            paint.setShader(gradientShader);
        }
        c.drawRect(0, 0, height, height, paint);

        try {
            wm.setBitmap(wallpaperBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            wm.setBitmap(wallpaperBitmap, null, true, WallpaperManager.FLAG_LOCK);
        } catch (java.io.IOException e) {
        }

        setResult(RESULT_OK);
        wallpaperBitmap.recycle();
        finish();
    }

    public void showCustomizations(View v) {
        startActivity(new Intent(this, CustomizationsActivity.class));
    }
}
