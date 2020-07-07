package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelBizList(
    @SerializedName("item")val items: List<BizListItems>
) {
    inner class BizListItems(
        @SerializedName("bizName")val bizName: String,
        @SerializedName("bizType")val bizType: String,
        @SerializedName("avgCost")val avgCost: Int,
        @SerializedName("distance")val distace: Double,
        @SerializedName("visitCount")val visitCount: Int
    )
}