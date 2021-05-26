package modularization.libraries.uicomponents.baseClasses

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by Kurdia.Epic on 5/12/2018.
 */
abstract class RcvBaseAdapter<VH : RecyclerView.ViewHolder?, T> : RecyclerView.Adapter<VH> {

    constructor(context: Context, items: MutableList<T>) : super() {
        inflater = LayoutInflater.from(context)
        ctxAdapter = context
        this.items = items
    }

    constructor(context: Context) : this(context, mutableListOf())

    /**
     * Get T typed item [List]
     * @return T typed item [List]
     */
    var items: MutableList<T>
    protected var ctxAdapter: Context
    protected var inflater: LayoutInflater
    var onItemClickListener: OnItemClickListener<T>? = null

    /**
     * @param item New item should add to [items] in first indeex
     */
    fun remove(position: Int) {
        val iterator = items.iterator()
        var index = -1
        while (iterator.hasNext()) {
            iterator.next()
            index++
            if (index == position) {
                iterator.remove()
            }
        }
        notifyDataSetChanged()
    }

    /**
     * @param item New item should add to [items] in first indeex
     */
    fun addItemToFirst(item: T?) {
        if (item == null) {
            return
        }
        items.add(0, item)
    }

    /**
     * @param item New item should add to [items]
     */
    fun addItem(item: T?) {
        if (item == null) {
            return
        }
        items.add(item)
    }

    /**
     * @param position Index of target item
     * @param item New item should add to [items]
     */
    fun addItem(position: Int, item: T?) {
        if (item == null) {
            return
        }
        if (items.isEmpty()) {
            items.add(item)
        }
        items.add(position, item)
    }

    /**
     * @param position Index of target item
     * @return T type
     */
    fun getItem(position: Int): T = items[position]

    /**
     * a function to getColor from colorResource
     * @return colorResInt type
     * */
    fun getColor(@ColorRes resId: Int): Int = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> ctxAdapter.resources.getColor(
            resId,
            null
        )
        else -> ctxAdapter.resources.getColor(resId)
    }

    /**
     * Get size of Items
     * @return Item size
     */
    override fun getItemCount(): Int = items.size

    /**
     * Item Click Listener callback Interface
     * @param <T> Type of item Extends [Object]
    </T> */
    interface OnItemClickListener<T> {
        fun onClicked(actionId: Int, position: Int, item: T)
    }

    /**
     * a function for setting the Image Resource using Glide
     * @param resId is the image resourceId
     * @param imageView is the image holder that image loads into
     * */
    open fun setImageResource(resId: Int, imageView: ImageView) {
        Glide.with(ctxAdapter)
            .load(resId)
            .into(imageView)
    }

}