package com.example.gouwu.util;

import android.content.Context;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

public class SelectPicture {
    public static void takePic(Context context, boolean isCamera,int maxSelectNum, OnResultCallbackListener onResultCallbackListener) {
        if (isCamera) {
            PictureSelector.create(context)
                    .openCamera(SelectMimeType.ofImage())
                    .forResult(onResultCallbackListener);
        } else {
            PictureSelector.create(context)
                    .openGallery(SelectMimeType.ofImage())
                    .setMaxSelectNum(maxSelectNum)
                    .setMinSelectNum(1)
                    .setImageSpanCount(4)
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(onResultCallbackListener);
        }
    }
}
