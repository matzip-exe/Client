package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelBizDetail(
    @SerializedName("item")val items: List<BizDetailItems>
) {
    inner class BizDetailItems(
        @SerializedName("telNum")val telNum: String,
        @SerializedName("address")val address: String,
        @SerializedName("roadAddress")val roadAddress: String,
        @SerializedName("MonthlyVisits")val monthlyVisits: List<List<String>>
    )
}