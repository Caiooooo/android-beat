package com.madao.simplebeat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppFrontBackHelper {

    private OnAppStatusListener mOnAppStatusListener;

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    public void register(Application application, OnAppStatusListener listener){
        mOnAppStatusListener = listener;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void unRegister(Application application){
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        }

        //打开的Activity数量
        private int activityStartCount = 0;



        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数量从0变到1说明是从后台切到前台
            if (activityStartCount == 1){
                //从后台切到前台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onFront();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数量从1到0说明是从前台切到后台
            if (activityStartCount == 0){
                //从前台切到后台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public interface OnAppStatusListener{
        void onFront();
        void onBack();
    }

}
