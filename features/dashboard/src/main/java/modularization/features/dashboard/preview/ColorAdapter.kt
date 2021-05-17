package modularization.features.dashboard.preview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import modularization.features.dashboard.R
import modularization.libraries.dataSource.models.ColorValue
import modularization.libraries.uicomponents.baseClasses.RcvBaseAdapter

class ColorAdapter(
    context: Context?,
    items: MutableList<ColorValue>
) : RcvBaseAdapter<ColorAdapter.ViewHolder, ColorValue>(context!!, items) {

    companion object {
        const val PRESSED: Int = 1
        const val LONG_PRESSED: Int = 2
    }

    constructor(context: Context?) : this(context, mutableListOf())

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView as ImageView
        fun onFill(position: Int) {
            itemView.apply {
                setOnClickListener {
                    onItemClickListener.let {
                        it?.onClicked(PRESSED, position, getItem(position = position))
                    }
                }
                setOnLongClickListener {
                    onItemClickListener.let {
                        it?.onClicked(LONG_PRESSED, position, getItem(position = position))
                    }
                    return@setOnLongClickListener true
                }
            }
            img.setBackgroundColor(items[position].rgb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflater.inflate(R.layout.adapter_color_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onFill(holder.bindingAdapterPosition)
    }
}