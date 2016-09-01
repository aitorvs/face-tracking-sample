package com.aitorvs.android.eyetoggle.util;

/*
 * Copyright (C) 07/06/16 aitorvs
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

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class PlayServicesUtil {

    private static final String TAG = PlayServicesUtil.class.getSimpleName();

    public static boolean isPlayServicesAvailable(@NonNull Activity activity, final int requestCode) {
        //noinspection ConstantConditions
        if (activity == null) {
            return false;
        }

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Log.w(TAG, "GooglePlayServices resolvable error occurred: " + apiAvailability.getErrorString(resultCode));
                apiAvailability.getErrorDialog(activity, resultCode, requestCode).show();
            } else {
                Log.e(TAG, "GooglePlayServices not supported");

                // finish activity
                activity.finish();
            }

            return false;
        }

        // All good
        return true;
    }
}
