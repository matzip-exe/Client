package com.team_no_yes.matzip_exe.main.oss

import com.google.gson.annotations.SerializedName

data class ModelGetOSS(
    @SerializedName("Item")val items: List<OSSItems>) {

    inner class OSSItems(
        @SerializedName("Name")val Name: String,
        @SerializedName("Link") val Link: String,
        @SerializedName("Copyright") val Copyright: String,
        @SerializedName("License")val License:String)
}