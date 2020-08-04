package com.example.matzip_exe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.activities.Detail
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.model.ModelRecommendation
import com.example.matzip_exe.receivers.NetworkReceiver

class DetailRecommendationAdapter(private val itemList: ArrayList<ModelRecommendation>,
                                  private var area: String, private var region: String):RecyclerView.Adapter<DetailRecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(private val v: View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        val img_detail_recommendation = v.findViewById<ImageView>(R.id.img_detail_recommendation)
        val text_detail_recommendation = v.findViewById<TextView>(R.id.text_detail_recommendation)

        init {
            v.setOnClickListener{
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("area", area)
            intent.putExtra("region", region)
            intent.putExtra("name", itemList[adapterPosition].bizName)
            intent.putExtra("type", splitType(itemList[adapterPosition].bizType))

            v.context.startActivity(intent)
            v.context.unregisterReceiver(networkReceiver)
        }

        override fun isNotConnected() {
            Toast.makeText(v.context, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            v.context.unregisterReceiver(networkReceiver)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recylce_detail_recommendation_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(CheckType(itemList[position].bizType)){
            0->{
                //한식
                holder.img_detail_recommendation.setImageResource(R.drawable.han)
            }
            1->{
                //일식
                holder.img_detail_recommendation.setImageResource(R.drawable.jpn)
            }
            2->{
                //중식
                holder.img_detail_recommendation.setImageResource(R.drawable.chi)
            }
            3->{
                //양식
                holder.img_detail_recommendation.setImageResource(R.drawable.wes)
            }
            4->{
                //카페,디저트
                holder.img_detail_recommendation.setImageResource(R.drawable.caf)
            }
            5->{
                //분식
                holder.img_detail_recommendation.setImageResource(R.drawable.bun)
            }
            6->{
                //기타
                holder.img_detail_recommendation.setImageResource(R.drawable.etc)
            }
        }
        holder.text_detail_recommendation.text = itemList[position].bizName
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

    private fun splitType(type: String?): String? {
        val splitTypes = type?.split(">")
        var result = ""
        if (splitTypes != null) {
            result = when (splitTypes.size) {
                1 -> {
                    splitTypes[0]
                }
                2 -> {
                    splitTypes[1]
                }
                else -> {
                    splitTypes[1]
                }
            }
        }

        return result
    }
}