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
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_VISIT = 0
    private val TYPE_DISTANCE = 1
    private val TYPE_AVGCOST = 2

    inner class VisitViewHolder(private val v:View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        val text_matziplist_visit_seq = v.findViewById<TextView>(R.id.text_matziplist_visit_seq)
        val img_matziplist_visit_type = v.findViewById<ImageView>(R.id.img_matziplist_visit_type)
        val text_matziplist_visit_name = v.findViewById<TextView>(R.id.text_matziplist_visit_name)
        val text_matziplist_visit_type = v.findViewById<TextView>(R.id.text_matziplist_visit_type)
        val text_matziplist_visit_distance = v.findViewById<TextView>(R.id.text_matziplist_visit_distance)
        val text_matziplist_visit_visitcount = v.findViewById<TextView>(R.id.text_matziplist_visit_visitcount)
        val img_matziplist_visit_grade = v.findViewById<ImageView>(R.id.img_matziplist_visit_grade)
        val matziplist_visit_wrapper = v.findViewById<LinearLayout>(R.id.matziplist_visit_wrapper)
        val text_matziplist_visit_avgcost = v.findViewById<TextView>(R.id.text_matziplist_visit_avgcost)

        init {
            matziplist_visit_wrapper.setOnClickListener {
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("visitcount", text_matziplist_visit_visitcount.text)
            intent.putExtra("name", text_matziplist_visit_name.text)
            intent.putExtra("type", text_matziplist_visit_type.text)
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

    inner class DistanceViewHolder(private val v: View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        val matziplist_distance_wrapper = v.findViewById<LinearLayout>(R.id.matziplist_distance_wrapper)
        val text_matziplist_distance_seq = v.findViewById<TextView>(R.id.text_matziplist_distance_seq)
        val img_matziplist_distance_type = v.findViewById<ImageView>(R.id.img_matziplist_distance_type)
        val text_matziplist_distance_name = v.findViewById<TextView>(R.id.text_matziplist_distance_name)
        val text_matziplist_distance_type = v.findViewById<TextView>(R.id.text_matziplist_distance_type)
        val text_matziplist_distance_distance = v.findViewById<TextView>(R.id.text_matziplist_distance_distance)
        val text_matziplist_distance_visitcount = v.findViewById<TextView>(R.id.text_matziplist_distance_visitcount)
        val img_matziplist_distance_grade = v.findViewById<ImageView>(R.id.img_matziplist_distance_grade)
        val text_matziplist_distance_avgcost = v.findViewById<TextView>(R.id.text_matziplist_distance_avgcost)

        init {
            matziplist_distance_wrapper.setOnClickListener {
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("visitcount", text_matziplist_distance_visitcount.text)
            intent.putExtra("name", text_matziplist_distance_name.text)
            intent.putExtra("type", text_matziplist_distance_type.text)
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

    inner class AvgcostViewHolder(private val v:View):RecyclerView.ViewHolder(v), NetworkConnectedListener{
        private val networkReceiver = NetworkReceiver()

        val matziplist_avgcost_wrapper = v.findViewById<LinearLayout>(R.id.matziplist_avgcost_wrapper)
        val text_matziplist_avgcost_seq = v.findViewById<TextView>(R.id.text_matziplist_avgcost_seq)
        val img_matziplist_avgcost_type = v.findViewById<ImageView>(R.id.img_matziplist_avgcost_type)
        val text_matziplist_avgcost_name = v.findViewById<TextView>(R.id.text_matziplist_avgcost_name)
        val text_matziplist_avgcost_type = v.findViewById<TextView>(R.id.text_matziplist_avgcost_type)
        val text_matziplist_avgcost_distance = v.findViewById<TextView>(R.id.text_matziplist_avgcost_distance)
        val text_matziplist_avgcost_visitcount = v.findViewById<TextView>(R.id.text_matziplist_avgcost_visitcount)
        val img_matziplist_avgcost_grade = v.findViewById<ImageView>(R.id.img_matziplist_avgcost_grade)
        val text_matziplist_avgcost_avgcost = v.findViewById<TextView>(R.id.text_matziplist_avgcost_avgcost)

        init {
            matziplist_avgcost_wrapper.setOnClickListener {
                networkReceiver.setOnNetworkListener(this)
                v.context.registerReceiver(networkReceiver, networkReceiver.getFilter())
            }
        }

        override fun isConnected() {
            val intent = Intent(v.context, Detail::class.java)
            intent.putExtra("visitcount", text_matziplist_avgcost_visitcount.text)
            intent.putExtra("name", text_matziplist_avgcost_name.text)
            intent.putExtra("type", text_matziplist_avgcost_type.text)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_VISIT->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_matziplist_visit_view, parent, false)
                VisitViewHolder(view)
            }
            TYPE_DISTANCE->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_matziplist_distance_view, parent, false)
                DistanceViewHolder(view)
            }
            TYPE_AVGCOST->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_matziplist_avgcost_view, parent, false)
                AvgcostViewHolder(view)
            }
            else->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_matziplist_visit_view, parent, false)
                VisitViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is VisitViewHolder->{
                holder.text_matziplist_visit_seq.text = itemList[position].seq
                holder.text_matziplist_visit_avgcost.text = "평균 : "+itemList[position].avgcost.toString()+"원"
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
                holder.text_matziplist_visit_name.text = itemList[position].name
                //거리도 gps 값이 있을 때 없을 때 나눠야할 듯
                if (itemList[position].distance != null){
                    holder.text_matziplist_visit_distance.text = itemList[position].distance.toString() + "km"
                }
                else{
                    holder.text_matziplist_visit_distance.text = holder.itemView.context.getString(R.string.no_distacne)
                }

                holder.text_matziplist_visit_type.text = itemList[position].type
                holder.text_matziplist_visit_visitcount.text = itemList[position].visitcount.toString()
                //등수는 seq 따라간다
                when(itemList[position].seq.toInt()){
                    1->{
                        holder.img_matziplist_visit_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    2->{
                        holder.img_matziplist_visit_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    3->{
                        holder.img_matziplist_visit_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    else->{
                        holder.img_matziplist_visit_grade.setImageDrawable(null)
                    }
                }
            }
            is DistanceViewHolder->{
                holder.text_matziplist_distance_seq.text = itemList[position].seq
                holder.text_matziplist_distance_avgcost.text = "평균 : "+itemList[position].avgcost.toString()+"원"
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
                holder.text_matziplist_distance_name.text = itemList[position].name
                //거리도 gps 값이 있을 때 없을 때 나눠야할 듯
                if (itemList[position].distance != null){
                    holder.text_matziplist_distance_distance.text = itemList[position].distance.toString() + "km"
                }
                else{
                    holder.text_matziplist_distance_distance.text = holder.itemView.context.getString(R.string.no_distacne)
                }

                holder.text_matziplist_distance_type.text = itemList[position].type
                holder.text_matziplist_distance_visitcount.text = "방문 수: "+itemList[position].visitcount.toString()
                //등수는 seq 따라간다
                when(itemList[position].seq.toInt()){
                    1->{
                        holder.img_matziplist_distance_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    2->{
                        holder.img_matziplist_distance_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    3->{
                        holder.img_matziplist_distance_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    else->{
                        holder.img_matziplist_distance_grade.setImageDrawable(null)
                    }
                }
            }
            is AvgcostViewHolder->{
                holder.text_matziplist_avgcost_seq.text = itemList[position].seq
                holder.text_matziplist_avgcost_avgcost.text = ChangeCost(itemList[position].avgcost)
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
                holder.text_matziplist_avgcost_name.text = itemList[position].name
                //거리도 gps 값이 있을 때 없을 때 나눠야할 듯
                if (itemList[position].distance != null){
                    holder.text_matziplist_avgcost_distance.text = itemList[position].distance.toString() + "km"
                }
                else{
                    holder.text_matziplist_avgcost_distance.text = holder.itemView.context.getString(R.string.no_distacne)
                }

                holder.text_matziplist_avgcost_type.text = itemList[position].type
                holder.text_matziplist_avgcost_visitcount.text = "방문 수: "+itemList[position].visitcount.toString()
                //등수는 seq 따라간다
                when(itemList[position].seq.toInt()){
                    1->{
                        holder.img_matziplist_avgcost_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    2->{
                        holder.img_matziplist_avgcost_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    3->{
                        holder.img_matziplist_avgcost_grade.setImageResource(R.drawable.ic_horizontal_rule_24px)
                    }
                    else->{
                        holder.img_matziplist_avgcost_grade.setImageDrawable(null)
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position].viewType){
            TYPE_VISIT->{
                TYPE_VISIT
            }
            TYPE_DISTANCE->{
                TYPE_DISTANCE
            }
            TYPE_AVGCOST->{
                TYPE_AVGCOST
            }
            else->{
                TYPE_VISIT
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

    private fun ChangeCost(cost: Int): String{
        val result: Double = cost / 10000.0
        return String.format("%.1f", result) + "만 원"
    }
}