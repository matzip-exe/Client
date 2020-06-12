package com.example.matzip_exe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.model.ModelMatZipList

class MatZipListAdapter(private val itemList: ArrayList<ModelMatZipList>):RecyclerView.Adapter<MatZipListAdapter.ViewHolder>() {
    inner class ViewHolder(v:View):RecyclerView.ViewHolder(v){
        val text_matziplist_seq = v.findViewById<TextView>(R.id.text_matziplist_seq)
        val img_matziplist_type = v.findViewById<ImageView>(R.id.img_matziplist_type)
        val text_matziplist_name = v.findViewById<TextView>(R.id.text_matziplist_name)
        val text_matziplist_type = v.findViewById<TextView>(R.id.text_matziplist_type)
        val text_matziplist_distance = v.findViewById<TextView>(R.id.text_matziplist_distance)
        val text_matziplist_visitcount = v.findViewById<TextView>(R.id.text_matziplist_visitcount)
        val img_matziplist_grade = v.findViewById<ImageView>(R.id.img_matziplist_grade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_matziplist_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text_matziplist_seq.text = itemList[position].seq
        //타입은 업종 종류 정해져야 정할 수 있을 듯
        holder.text_matziplist_name.text = itemList[position].name
        //거리도 gps 값이 있을 때 없을 때 나눠야할 듯
//        holder.text_matziplist_distance.text = itemList[position].distance
        holder.text_matziplist_visitcount.text = itemList[position].visitcount
        //등수는 seq 따라간다
    }
}