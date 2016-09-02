package com.aitorvs.android.eyetoggle.tracker;

/*
 * Copyright (C) 01/09/16 aitorvs
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

import com.aitorvs.android.eyetoggle.event.LeftEyeClosedEvent;
import com.aitorvs.android.eyetoggle.event.NeutralFaceEvent;
import com.aitorvs.android.eyetoggle.event.RightEyeClosedEvent;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import org.greenrobot.eventbus.EventBus;

public class FaceTracker extends Tracker<Face> {

    private static final float PROB_THRESHOLD = 0.7f;
    private static final String TAG = FaceTracker.class.getSimpleName();
    private boolean leftClosed;
    private boolean rightClosed;

    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        if (leftClosed && face.getIsLeftEyeOpenProbability() > PROB_THRESHOLD) {
            leftClosed = false;
        } else if (!leftClosed &&  face.getIsLeftEyeOpenProbability() < PROB_THRESHOLD){
            leftClosed = true;
        }
        if (rightClosed && face.getIsRightEyeOpenProbability() > PROB_THRESHOLD) {
            rightClosed = false;
        } else if (!rightClosed && face.getIsRightEyeOpenProbability() < PROB_THRESHOLD) {
            rightClosed = true;
        }

        if (leftClosed && !rightClosed) {
            EventBus.getDefault().post(new LeftEyeClosedEvent());
        } else if (rightClosed && !leftClosed) {
            EventBus.getDefault().post(new RightEyeClosedEvent());
        } else if (!leftClosed && !rightClosed) {
            EventBus.getDefault().post(new NeutralFaceEvent());
        }
    }
}
