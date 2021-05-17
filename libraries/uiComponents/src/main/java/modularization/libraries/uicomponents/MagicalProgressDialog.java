package modularization.libraries.uicomponents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import modularization.libraries.uicomponents.callbacks.DialogCallback;
import modularization.libraries.uicomponents.databinding.LayoutMagicalProgressDialogBinding;

public class MagicalProgressDialog extends AppCompatDialog {

    private Context context;
    protected DialogCallback dialogCallback;
    private static final float DIALOG_DEFAULT_WIDTH_PERCENTAGE = 0.9f;
    private LayoutMagicalProgressDialogBinding binding;

    public MagicalProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initDialog();
    }

    private void initDialog() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_magical_progress_dialog, null, false);
        setContentView(binding.getRoot());

        int width = getWidthOfDialog();
        Objects.requireNonNull(getWindow()).setLayout(
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int getWidthOfDialog() {
        float width = DIALOG_DEFAULT_WIDTH_PERCENTAGE;
        return (int) (getContext().getResources().getDisplayMetrics().widthPixels * width);
    }

    public void setDialogCallback(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    private void showProgress() {
        if (binding.textViewProgress != null)
            binding.textViewProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        if (binding.textViewProgress != null)
            binding.textViewProgress.setVisibility(View.GONE);
    }

    public void clearProgress() {
        if (binding.textViewProgress != null) {
            binding.textViewProgress.setText(this.context.getResources().getString(R.string.dash));
            binding.textViewProgress.setVisibility(View.GONE);
        }
    }

    public void setProgress(double progress, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showProgress();
                if (binding.textViewProgress != null)
                    binding.textViewProgress.setText(String.format("%s%%", progress));
            }
        });
    }

}
