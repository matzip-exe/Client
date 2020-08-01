package com.example.matzip_exe.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.receivers.NetworkReceiver

class DetailRecommendationAdapter(private val itemList: ArrayList<ModelBizDetail>):RecyclerView.Adapter<DetailRecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(private val v: View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        private val img_detail_recommendation = v.findViewById<ImageView>(R.id.img_detail_recommendation)
        private val text_detail_recommendation = v.findViewById<TextView>(R.id.text_detail_recommendation)

        init {
            v.setOnClickListener{
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            v.context.unregisterReceiver(networkReceiver)
        }

        override fun isNotConnected() {
            Toast.makeText(v.context, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            v.context.unregisterReceiver(networkReceiver)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}