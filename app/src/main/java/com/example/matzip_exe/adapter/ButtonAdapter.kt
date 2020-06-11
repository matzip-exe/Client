package com.example.matzip_exe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.model.ModelButton

class ButtonAdapter(private val itemList: ArrayList<ModelButton>):RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val btn_recyclebutton = v.findViewById<Button>(R.id.btn_recyclebutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_button_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btn_recyclebutton.text = itemList[position].Name
        holder.btn_recyclebutton.isEnabled = itemList[position].Enable
    }
}