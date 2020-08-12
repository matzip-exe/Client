package com.team_no_yes.matzip_exe.main

import com.google.gson.annotations.SerializedName

data class ModelCheckRegion(
    @SerializedName("item")val items: List<CheckRegionItems>
) {

    inner class CheckRegionItems(
        @SerializedName("region")val region: String,
        @SerializedName("isExist")val isExist: Boolean
    )

}