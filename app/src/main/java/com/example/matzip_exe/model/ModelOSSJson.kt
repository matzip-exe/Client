package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelOSSJson(
    @SerializedName("Item")val items: List<OSSItems>) {

    inner class OSSItems(
        @SerializedName("Name")val Name: String,
        @SerializedName("Link") val Link: String,
        @SerializedName("Copyright") val Copyright: String,
        @SerializedName("License")val License:String)
}