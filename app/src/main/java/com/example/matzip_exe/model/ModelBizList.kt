package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName
import com.naver.maps.geometry.LatLng

data class ModelBizList(
    @SerializedName("item")val items: List<BizListItems>
) {
    inner class BizListItems(
        @SerializedName("bizName")val bizName: String,
        @SerializedName("bizType")val bizType: String,
        @SerializedName("avgCost")val avgCost: Int,
        @SerializedName("latlng")val latlng: XY,
        @SerializedName("distance")val distance: Double,
        @SerializedName("visitCount")val visitCount: Int
    )
    inner class XY(
        @SerializedName("x")val x: Double,
        @SerializedName("y")val y: Double
    )
}