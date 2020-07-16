package com.example.matzip_exe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.activities.Detail
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.model.ModelMatZipList
import com.example.matzip_exe.receivers.NetworkReceiver

class MatZipListAdapter(private val itemList: ArrayList<ModelMatZipList>,
                        private var area: String, private var region: String
):RecyclerView.Adapter<MatZipListAdapter.ViewHolder>() {
    inner class ViewHolder(private val v:View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        val text_matziplist_seq = v.findViewById<TextView>(R.id.text_matziplist_seq)
        val img_matziplist_type = v.findViewById<ImageView>(R.id.img_matziplist_type)
        val text_matziplist_name = v.findViewById<TextView>(R.id.text_matziplist_name)
        val text_matziplist_type = v.findViewById<TextView>(R.id.text_matziplist_type)
        val text_matziplist_distance = v.findViewById<TextView>(R.id.text_matziplist_distance)
        val text_matziplist_visitcount = v.findViewById<TextView>(R.id.text_matziplist_visitcount)
        val img_matziplist_grade = v.findViewById<ImageView>(R.id.img_matziplist_grade)
        val matziplist_wrapper = v.findViewById<LinearLayout>(R.id.matziplist_wrapper)

        init {
            matziplist_wrapper.setOnClickListener {
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("visitcount", text_matziplist_visitcount.text)
            intent.putExtra("name", text_matziplist_name.text)
            intent.putExtra("type", text_matziplist_type.text)
            intent.putExtra("locatex", itemList[adapterPosition].latlng?.x)
            intent.putExtra("locatey", itemList[adapterPosition].latlng?.y)
            intent.putExtra("area", area)
            intent.putExtra("region", region)
            intent.putExtra("avgCost", itemList[adapterPosition].avgcost)

            v.context.startActivity(intent)

            v.context.unregisterReceiver(networkReceiver)
        }

        override fun isNotConnected() {
            Toast.makeText(v.context, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            v.context.unregisterReceiver(networkReceiver)
        }
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
        //타입 이미지
        when(CheckType(itemList[position].type!!)){
            0->{
                //한식
            }
            1->{
                //일식
            }
            2->{
                //중식
            }
            3->{
                //양식
            }
            4->{
                //카페,디저트
            }
            5->{
                //분식
            }
            6->{
                //기타
            }
        }
        holder.text_matziplist_name.text = itemList[position].name
        //거리도 gps 값이 있을 때 없을 때 나눠야할 듯
        if (itemList[position].distance != null){
            holder.text_matziplist_distance.text = itemList[position].distance.toString() + "km"
        }
        else{
            holder.text_matziplist_distance.text = holder.itemView.context.getString(R.string.no_distacne)
        }

        holder.text_matziplist_type.text = itemList[position].type
        holder.text_matziplist_visitcount.text = itemList[position].visitcount.toString()
        //등수는 seq 따라간다
        when(itemList[position].seq.toInt()){
            1->{
                holder.img_matziplist_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
            }
            2->{
                holder.img_matziplist_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
            }
            3->{
                holder.img_matziplist_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
            }
            else->{
                holder.img_matziplist_grade.setImageDrawable(null)
            }
        }
    }

    private fun CheckType(type: String): Int{
        val CheckList = arrayOf("한식", "일식", "중식", "양식", "카페,디저트", "분식")
        var result = 6

        for (i in CheckList.indices){
            if (type.contains(CheckList[i], true)){
                result = i
                break
            }
        }

        return result
    }
}