package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelCheckRegion(
    @SerializedName("item")val items: List<CheckRegionItems>
) {

    inner class CheckRegionItems(
        @SerializedName("region")val region: String,
        @SerializedName("isExist")val isExist: Boolean
    )

}