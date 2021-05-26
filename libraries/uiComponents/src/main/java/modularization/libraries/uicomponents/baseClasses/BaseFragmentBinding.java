package modularization.libraries.uicomponents.baseClasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import modularization.libraries.utils.helpers.LocaleHelper;

public abstract class BaseFragmentBinding<B extends ViewDataBinding> extends Fragment {

    protected final String TAG = getClass().getSimpleName() + "__TAG";

    protected B binding;

    protected abstract int getLayoutResourceId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if (getContext() != null)
                LocaleHelper.setApplicationLanguage(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void showMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }


}
