package modularization.features.dashboard.meet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import modularization.features.dashboard.R
import modularization.features.dashboard.join.JoinViewDataBinding
import modularization.libraries.uicomponents.baseClasses.BaseFragmentBinding

class MeetFrg : BaseFragmentBinding<MeetViewDataBinding>() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MeetFrg? = null
            get() {
                if (field == null) {
                    field = MeetFrg()
                }
                return field
            }
    }

    /** ViewModel related to Fragment*/
    private lateinit var viewModel: MeetViewModel

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_meeting
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MeetViewModel::class.java)
        return inflater.inflate(layoutResourceId, container, false).apply {
            viewModel.liveData.observe(viewLifecycleOwner, Observer {
                val item = it ?: return@Observer
            })
        }
    }
}