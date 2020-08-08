package com.team_no_yes.matzip_exe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team_no_yes.matzip_exe.R
import com.team_no_yes.matzip_exe.model.ModelRecycleOSS

class RecycleOSSAdapter(private val itemList: ArrayList<ModelRecycleOSS>): RecyclerView.Adapter<RecycleOSSAdapter.ViewHolder>() {
    inner class ViewHolder(private val v: View): RecyclerView.ViewHolder(v){
        val oss_name = v.findViewById<TextView>(R.id.oss_name)
        val oss_link = v.findViewById<TextView>(R.id.oss_link)
        val oss_copyright = v.findViewById<TextView>(R.id.oss_copyright)
        val oss_license = v.findViewById<TextView>(R.id.oss_license)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_oss_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.oss_name.text = itemList[position].name
        holder.oss_link.text = itemList[position].link
        holder.oss_copyright.text = itemList[position].copyright
        holder.oss_license.text = itemList[position].license
    }
}