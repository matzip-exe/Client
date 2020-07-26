package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelBizDetail(
    @SerializedName("item")val items: BizDetailItems
) {
    inner class BizDetailItems(
        @SerializedName("address")val address: String,
        @SerializedName("roadAddress")val roadAddress: String,
        @SerializedName("monthlyVisits")val monthlyVisits: List<MonthlyVisitsItems>,
        @SerializedName("detailUrl")val detailUrl: String
    )
    inner class MonthlyVisitsItems(
        @SerializedName("date")val date: String,
        @SerializedName("count")val count: Int
    )
}