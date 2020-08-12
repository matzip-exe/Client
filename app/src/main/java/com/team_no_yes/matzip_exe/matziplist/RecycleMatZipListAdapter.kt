package com.team_no_yes.matzip_exe.matziplist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.team_no_yes.matzip_exe.R
import com.team_no_yes.matzip_exe.detail.Detail
import com.team_no_yes.matzip_exe.network.NetworkConnectedListener
import com.team_no_yes.matzip_exe.network.NetworkReceiver

class RecycleMatZipListAdapter(private val itemList: ArrayList<ModelMatZipList>,
                               private var area: String, private var region: String
):RecyclerView.Adapter<RecycleMatZipListAdapter.ViewHolder>() {
    inner class ViewHolder(private val v:View):RecyclerView.ViewHolder(v),
        NetworkConnectedListener {
        private val networkReceiver = NetworkReceiver()

        val text_matziplist_seq = v.findViewById<TextView>(R.id.text_matziplist_seq)
        val img_matziplist_type = v.findViewById<ImageView>(R.id.img_matziplist_type)
        val text_matziplist_name = v.findViewById<TextView>(R.id.text_matziplist_name)
        val text_matziplist_type = v.findViewById<TextView>(R.id.text_matziplist_type)
        val img_matziplist_grade = v.findViewById<ImageView>(R.id.img_matziplist_grade)
        val text_matziplist_value = v.findViewById<TextView>(R.id.text_matziplist_value)

        init {
            v.setOnClickListener{
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("name", text_matziplist_name.text)
            intent.putExtra("type", text_matziplist_type.text)
            intent.putExtra("area", area)
            intent.putExtra("region", region)

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
        when(CheckType(itemList[position].type!!)){
            0-> {
                //한식
                holder.img_matziplist_type.setImageResource(R.drawable.han_fixed)
            }
            1->{
                //일식
                holder.img_matziplist_type.setImageResource(R.drawable.jpn_fixed)
            }
            2->{
                //중식
                holder.img_matziplist_type.setImageResource(R.drawable.chi_fixed)
            }
            3->{
                //양식
                holder.img_matziplist_type.setImageResource(R.drawable.wes_fixed)
            }
            4->{
                //카페,디저트
                holder.img_matziplist_type.setImageResource(R.drawable.caf_fixed)
            }
            5->{
                //분식
                holder.img_matziplist_type.setImageResource(R.drawable.bun_fixed)
            }
            6->{
                //기타
                holder.img_matziplist_type.setImageResource(R.drawable.etc_fixed)
            }
        }
        holder.text_matziplist_name.text = itemList[position].name
        holder.text_matziplist_type.text = splitType(itemList[position].type)

        when(itemList[position].seq.toInt()){
            1->{
                holder.img_matziplist_grade.setImageResource(R.drawable.first)
            }
            2->{
                holder.img_matziplist_grade.setImageResource(R.drawable.second)
            }
            3->{
                holder.img_matziplist_grade.setImageResource(R.drawable.third)
            }
            else->{
                holder.img_matziplist_grade.setImageDrawable(null)
            }
        }

        when(itemList[position].viewType){
            0->{
                holder.text_matziplist_value.text = itemList[position].visitcount.toString()+"회"
            }
            1->{
                if (itemList[position].distance != null){
                        holder.text_matziplist_value.text = itemList[position].distance.toString() + "km"
                }
                else{
                    holder.text_matziplist_value.text = holder.itemView.context.getString(R.string.no_distacne)
                }
            }
            2->{
                holder.text_matziplist_value.text = ChangeCost(itemList[position].avgcost)
            }
        }
    }

    private fun CheckType(type: String): Int{
        val CheckList = arrayOf("한식", "일식", "중식", "양식", "카페", "분식")
        var result = 6

        for (i in CheckList.indices){
            if (type.contains(CheckList[i], true)){
                result = i
                break
            }
        }

        return result
    }

    private fun ChangeCost(cost: Int): String{
        val result: Double = cost / 10000.0
        return String.format("%.1f", result) + "만 원"
    }

    private fun splitType(type: String?): String?{
        val splitTypes = type?.split(">")
        var result = ""
        if (splitTypes != null){
            result = when(splitTypes.size){
                1->{
                    splitTypes[0]
                }
                2->{
                    splitTypes[1]
                }
                else->{
                    splitTypes[1]
                }
            }
        }

        return result
    }
}