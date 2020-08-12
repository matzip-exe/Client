package com.team_no_yes.matzip_exe.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.team_no_yes.matzip_exe.R
import com.team_no_yes.matzip_exe.matziplist.MatzipList
import com.team_no_yes.matzip_exe.network.NetworkConnectedListener
import com.team_no_yes.matzip_exe.network.NetworkReceiver

class RecycleMainAdapter(private val itemList: ArrayList<ModelRecycleMain>):RecyclerView.Adapter<RecycleMainAdapter.ViewHolder>() {

    inner class ViewHolder(private val v: View):RecyclerView.ViewHolder(v),
        NetworkConnectedListener {
        private val networkReceiver = NetworkReceiver()
        val btn_recyclebutton: Button = v.findViewById<Button>(R.id.btn_recyclebutton)

        init {
            btn_recyclebutton.setOnClickListener {
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, MatzipList::class.java)
            intent.putExtra("region", itemList[adapterPosition].SendKey)
            intent.putExtra("area", btn_recyclebutton.text.toString())

            v.context.startActivity(intent)

            v.context.unregisterReceiver(networkReceiver)
        }

        override fun isNotConnected() {
            Toast.makeText(v.context, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            v.context.unregisterReceiver(networkReceiver)
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