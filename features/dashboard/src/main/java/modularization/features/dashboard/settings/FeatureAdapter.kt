package modularization.features.dashboard.settings

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import modularization.features.dashboard.R
import modularization.libraries.dataSource.models.FeatureAdapterItem
import modularization.libraries.uicomponents.MagicalTextView
import modularization.libraries.uicomponents.baseClasses.RcvBaseAdapter
import modularization.libraries.utils.Logger

class FeatureAdapter(context: Context, items: MutableList<FeatureAdapterItem>) :

    RcvBaseAdapter<FeatureAdapter.ViewHolder, FeatureAdapterItem>(context, items) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtDescription: MagicalTextView =
            itemView.findViewById(R.id.txt_feature_adapter)
        private val checkBox: CheckBox = itemView.findViewById(R.id.chk_feature_adapter)

        fun onBind(position: Int) {
            Logger.w(
                "TAG_TAG",
                "Position: $position , Item: ${getItem(position)}"
            )
            txtDescription.text = getItem(position).description
            checkBox.text = getItem(position).featureFlag.key
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

            }
            checkBox.isChecked = getItem(position).featureFlag.value
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                getItem(position).featureFlag.value = isChecked
                // onItemClickListener?.onClicked(buttonView.id, position, getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflater.inflate(R.layout.adapter_feature_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }
}