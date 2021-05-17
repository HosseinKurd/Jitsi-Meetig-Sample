package modularization.libraries.uicomponents.baseClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import modularization.libraries.utils.helpers.LocaleHelper;

public abstract class BaseActivityBinding<B extends ViewDataBinding> extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName()+"_TAG";

    protected B binding;

    protected ProgressDialog progress;

    protected abstract int getLayoutResourceId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // activity enter animation

        try {

            LocaleHelper.setApplicationLanguage(this);

            binding = DataBindingUtil.setContentView(this, getLayoutResourceId());

            progress = new ProgressDialog(this);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation

            initLoading();

        } catch (Exception ex) {
            Log.i(TAG, "onCreate: ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LocaleHelper.onAttach(newBase);
    }

    public B getBinding() {
        return binding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initLoading() {
        // progress.setTitle("در حال بارگزاری");
        progress.setMessage("لطفا کمی صبر کنید...");
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
    }

    protected void showLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progress != null)
                    progress.show();
            }
        }, 100);
    }

    protected void hideLoading() {
        if (progress != null)
            progress.dismiss();
    }

}
