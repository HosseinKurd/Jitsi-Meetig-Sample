package modularization.features.dashboard.meet

import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.ViewDataBinding

class MeetViewDataBinding(
    bindingComponent: DataBindingComponent?,
    root: View?,
    localFieldCount: Int
) : ViewDataBinding(bindingComponent, root, localFieldCount) {

    override fun onFieldChange(localFieldId: Int, `object`: Any?, fieldId: Int): Boolean {
        return false
    }

    override fun setVariable(variableId: Int, value: Any?): Boolean {
        return false
    }

    override fun executeBindings() {

    }

    override fun invalidateAll() {

    }

    override fun hasPendingBindings(): Boolean {
        return false
    }
}