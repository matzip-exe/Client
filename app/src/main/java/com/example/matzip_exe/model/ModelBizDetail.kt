package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelBizDetail(
    @SerializedName("item")val items: BizDetailItems
) {
    inner class BizDetailItems(
        @SerializedName("monthlyVisits")val monthlyVisits: List<MonthlyVisitsItems>,
        @SerializedName("avgCost")val avgCost: Int,
        @SerializedName("latlng")val latlng: XY?,
        @SerializedName("detailUrl")val detailUrl: String,
        @SerializedName("recommendations")val recommendations: List<RecommendItems>
    )
    inner class MonthlyVisitsItems(
        @SerializedName("date")val date: String,
        @SerializedName("count")val count: Int
    )
    inner class RecommendItems(
        @SerializedName("bizType")val bizType: String,
        @SerializedName("bizName")val bizName: String
    )
    inner class XY(
        @SerializedName("x")val x: Double,
        @SerializedName("y")val y: Double
    )
}