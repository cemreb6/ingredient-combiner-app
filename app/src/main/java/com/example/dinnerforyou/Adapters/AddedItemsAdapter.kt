package com.example.dinnerforyou.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.dinnerforyou.Fragments.addedItems
import com.example.dinnerforyou.R
import kotlinx.coroutines.NonDisposableHandle.parent

class AddedItemsAdapter(val context: Context, private val list: List<String>) :
    BaseAdapter() {

    override fun getCount() = list.size
    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view=p1
        view=LayoutInflater.from(context).inflate(R.layout.added_items_list,p2,false)
        var name= view.findViewById<TextView>(R.id.item)
        name.text=list.get(p0)

        var image=view.findViewById<ImageView>(R.id.delete_icon)
        image.setOnClickListener {
            addedItems._items.removeAt(p0)
            notifyDataSetChanged()
        }
        return view
    }

}