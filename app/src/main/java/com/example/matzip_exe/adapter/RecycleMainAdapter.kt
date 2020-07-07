package com.example.matzip_exe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.activities.MatzipList
import com.example.matzip_exe.model.ModelRecycleMain

class RecycleMainAdapter(private val itemList: ArrayList<ModelRecycleMain>):RecyclerView.Adapter<RecycleMainAdapter.ViewHolder>() {

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val btn_recyclebutton: Button = v.findViewById<Button>(R.id.btn_recyclebutton)

        init {
            btn_recyclebutton.setOnClickListener {
                val intent = Intent(v.context, MatzipList::class.java)
                intent.putExtra("region", itemList[adapterPosition].SendKey)
                intent.putExtra("area", btn_recyclebutton.text.toString())
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_main_view, parent, false)
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