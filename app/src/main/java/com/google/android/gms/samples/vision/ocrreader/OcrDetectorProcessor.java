/*
 * Copyright (C) The Android Open Source Project
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
package com.google.android.gms.samples.vision.ocrreader;

import android.util.SparseArray;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private static final int MAX_LENGTH = 15;
    private IOnGetResultListener mIOnGetResultListener;
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, int left, int right, int top, int bottom, IOnGetResultListener iOnGetResultListener) {
        mGraphicOverlay = ocrGraphicOverlay;
        mIOnGetResultListener = iOnGetResultListener;
        mLeft = left;
        mRight = right;
        mTop = top;
        mBottom = bottom;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            String value = trim(item.getValue());
            if (value.length() == MAX_LENGTH && isNumeric(value) && item.getBoundingBox().top >= mTop
                    && item.getBoundingBox().bottom <= mBottom) {
                OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                mGraphicOverlay.add(graphic);
                mIOnGetResultListener.getResult();
            }
        }
    }

    private boolean isNumeric(String item) {
        return item.matches("^[0-9]*$");
    }

    private String trim(String item) {
        return item.replace(" ", "");
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    public interface IOnGetResultListener {
        void getResult();
    }
}
