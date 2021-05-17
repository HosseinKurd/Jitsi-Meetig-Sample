package modularization.libraries.uicomponents;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import modularization.libraries.uicomponents.callbacks.DialogCallback;
import modularization.libraries.uicomponents.databinding.MagicalDialogAlertBinding;
import modularization.libraries.utils.PublicValue;


public class MagicalDialogAlert
        extends AppCompatDialog
        implements View.OnClickListener {

    protected MagicalDialogAlertBinding binding;

    private String dialogTitle;
    private String negativeButtonText;
    private String positiveButtonText;
    private String dialogText;
    private SpannableStringBuilder spannableStringBuilder;
    protected DialogCallback dialogCallback;

    public MagicalDialogAlert(Context context) {
        super(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.magical_dialog_alert, null, false);
        setContentView(binding.getRoot());

        initDialog();
        initViews();
        initClicks();

    }

    private void initDialog() {
        int width = getWidthOfDialog();
        Objects.requireNonNull(getWindow()).setLayout(
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int getWidthOfDialog() {
        float width = PublicValue.DIALOG_DEFAULT_WIDTH_PERCENTAGE;
        return (int) (getContext().getResources().getDisplayMetrics().widthPixels * width);
    }

    private void initClicks() {
        binding.magicalButtonPositive.setOnClickListener(this);
        binding.magicalButtonNegative.setOnClickListener(this);
    }

    public void initViews() {
        if (dialogText != null) {
            binding.magicalTextViewTitle.setText(dialogText);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                textViewText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);*/
        } else if (spannableStringBuilder != null) {
            binding.magicalTextViewTitle.setText(spannableStringBuilder);
        }
        if (positiveButtonText != null) {
            binding.magicalButtonPositive.setVisibility(View.VISIBLE);
            binding.magicalButtonPositive.setText(positiveButtonText);
        } else {
            binding.magicalButtonPositive.setVisibility(View.GONE);
        }
        if (negativeButtonText != null) {
            binding.magicalButtonNegative.setVisibility(View.VISIBLE);
            binding.magicalButtonNegative.setText(negativeButtonText);
        } else {
            binding.magicalButtonNegative.setVisibility(View.GONE);
        }
    }

    public void setTitle(@StringRes int text) {
        String string = getContext().getResources().getString(text);
        setTitle(string);
    }

    public void setTitle(String text) {
        dialogTitle = text;
    }

    public void setNegativeButton(@StringRes int text, View.OnClickListener onNegativeButtonClickListener) {
        String string = getContext().getResources().getString(text);
        setNegativeButton(string, onNegativeButtonClickListener);
    }

    public void setNegativeButton(String text, View.OnClickListener onNegativeButtonClickListener) {
        negativeButtonText = text;
    }


    public void setNegativeButton(String text) {
        negativeButtonText = text;
    }

    public void setNegativeButton(@StringRes int stringID) {
        negativeButtonText = getContext().getResources().getString(stringID);
    }

    public void setPositiveButton(@StringRes int text, View.OnClickListener onPositiveButtonClickListener) {
        String string = getContext().getResources().getString(text);
        setPositiveButton(string, onPositiveButtonClickListener);
    }

    public void setPositiveButton(String text, View.OnClickListener onPositiveButtonClickListener) {
        positiveButtonText = text;
    }

    public void setPositiveButton(String text) {
        positiveButtonText = text;
    }

    public void setPositiveButton(@StringRes int stringID) {
        positiveButtonText = getContext().getResources().getString(stringID);
    }

    public void setText(@StringRes int stringID) {
        String string = getContext().getResources().getString(stringID);
        setText(string);
    }

    public void setText(SpannableStringBuilder stringBuilder) {
        this.spannableStringBuilder = stringBuilder;
    }

    public void setText(String string) {
        dialogText = string;
    }

    public void setHeaderBackground(@DrawableRes int drawable) {
        // linearHeader.setBackground(ContextCompat.getDrawable(getContext(), drawable));
    }

    public void setDialogCallback(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.magicalButton_positive) {
            dismiss();
            if (dialogCallback != null)
                dialogCallback.onPositiveButtonClicked();
        } else if (view.getId() == R.id.magicalButton_negative) {
            dismiss();
            if (dialogCallback != null)
                dialogCallback.onNegativeButtonClicked();

        }
    }
}
