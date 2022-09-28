package com.example.buupass2

//shorturl.at/ey124

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(var context: Context, var data:ArrayList<Students>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtEmail:TextView
        var mTxtAdmNo:TextView
        init {
            this.mTxtName = row?.findViewById(R.id.mTvName1) as TextView
            this.mTxtEmail = row?.findViewById(R.id.mTvName2) as TextView
            this.mTxtAdmNo = row?.findViewById(R.id.mTvAdmNo) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.item_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:Students = getItem(position) as Students
        viewHolder.mTxtName.text = item.name1
        viewHolder.mTxtEmail.text = item.name2
        viewHolder.mTxtAdmNo.text = item.admNo
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}