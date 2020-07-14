package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelAboutUsJson(
    @SerializedName("Item") val items:List<AboutUsItems>
) {
    inner class AboutUsItems(
        @SerializedName("Position") val Position: String,
        @SerializedName("Name") val Name:String,
        @SerializedName("Email") val Email: String)
}