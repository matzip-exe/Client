package com.team_no_yes.matzip_exe.main.aboutus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team_no_yes.matzip_exe.R

class RecycleAboutUsAdapter(private val itemList: ArrayList<ModelRecycleAboutUs>):RecyclerView.Adapter<RecycleAboutUsAdapter.ViewHolder>() {
    inner class ViewHolder(private val v: View): RecyclerView.ViewHolder(v){
        val text_aboutus_position = v.findViewById<TextView>(R.id.text_aboutus_position)
        val text_aboutus_name = v.findViewById<TextView>(R.id.text_aboutus_name)
        val text_aboutus_email = v.findViewById<TextView>(R.id.text_aboutus_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_aboutus_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text_aboutus_position.text = itemList[position].position
        holder.text_aboutus_name.text = itemList[position].name
        holder.text_aboutus_email.text = itemList[position].Email
    }
}