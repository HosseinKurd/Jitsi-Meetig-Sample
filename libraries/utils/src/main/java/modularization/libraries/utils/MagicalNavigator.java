package modularization.libraries.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MagicalNavigator {

    private static MagicalNavigator instance = null;

    public static MagicalNavigator getInstance() {
        if (instance == null)
            instance = new MagicalNavigator();
        return instance;
    }

    private void launchActivity(Context context, String classPath) {
        ComponentName componentName = new ComponentName(context, classPath);
        Intent intent = new Intent().setComponent(componentName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void launchActivity(Context context, String classPath, Bundle bundle) {
        ComponentName componentName = new ComponentName(context, classPath);
        Intent intent = new Intent().setComponent(componentName);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void launchActivity(Activity activity, String classPath, int requestCode, Bundle bundle) {
        ComponentName componentName = new ComponentName(activity, classPath);
        Intent intent = new Intent().setComponent(componentName);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public void navigateToSplashActivity(Context context) {
        launchActivity(context, "modularization.features.splash.SplashActivity");
    }

    public void navigateToIntroActivity(Context context) {
        launchActivity(context, "modularization.features.intro.IntroActivity");
    }

    public void navigateToLoginActivity(Context context) {
        launchActivity(context, "modularization.features.login.LoginActivity");
    }

    public void navigateToDashboardActivity(Context context) {
        launchActivity(context, "modularization.features.dashboard.DashboardActivity");
    }


}
