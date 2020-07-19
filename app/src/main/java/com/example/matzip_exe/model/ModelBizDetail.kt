package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelBizDetail(
    @SerializedName("item")val items: BizDetailItems
) {
    inner class BizDetailItems(
        @SerializedName("telNum")val telNum: String,
        @SerializedName("address")val address: String,
        @SerializedName("roadAddress")val roadAddress: String,
        @SerializedName("monthlyVisits")val monthlyVisits: List<MonthlyVisitsItems>,
        @SerializedName("bizHour")val bizHour: String
    )
    inner class MonthlyVisitsItems(
        @SerializedName("date")val date: String,
        @SerializedName("count")val count: Int
    )
}