/*
 * Copyright (C) 2007 The Android Open Source Project
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

package android.app;

import android.content.Intent;
import android.os.Bundle;

/**
 * A screen that contains and runs multiple embedded activities.
 *
 * @deprecated Use the new {@link Fragment} and {@link FragmentManager} APIs
 * instead; these are also
 * available on older platforms through the Android compatibility package.
 */
@Deprecated
public class ActivityGroupFake extends Activity {
    private static final String STATES_KEY = "android:states";
    static final String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";

    protected LocalActivityManager mLocalActivityManager;

    public ActivityGroupFake() {
        this(true);
    }

    public ActivityGroupFake(boolean singleActivityMode) {
        mLocalActivityManager = createLocalActivityManager(singleActivityMode);
    }

    public LocalActivityManager createLocalActivityManager(boolean singleActivityMode) {
        return new LocalActivityManager(this, singleActivityMode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle states = savedInstanceState != null
                ? (Bundle) savedInstanceState.getBundle(STATES_KEY) : null;
        mLocalActivityManager.dispatchCreate(states);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle state = mLocalActivityManager.saveInstanceState();
        if (state != null) {
            outState.putBundle(STATES_KEY, state);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(isFinishing());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocalActivityManager.dispatchStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalActivityManager.dispatchDestroy(isFinishing());
    }



    public Activity getCurrentActivity() {
        return mLocalActivityManager.getCurrentActivity();
    }

    public final LocalActivityManager getLocalActivityManager() {
        return mLocalActivityManager;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Activity act = mLocalActivityManager.getCurrentActivity();
            /*
            if (false) Log.v(
                TAG, "Dispatching result: who=" + who + ", reqCode=" + requestCode
                + ", resCode=" + resultCode + ", data=" + data
                + ", rec=" + rec);
            */
        if (act != null) {
            act.onActivityResult(requestCode, resultCode, data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}


