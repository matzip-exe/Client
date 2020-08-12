package com.team_no_yes.matzip_exe.main.aboutus

import com.google.gson.annotations.SerializedName

data class ModelGetAboutUs(
    @SerializedName("Item") val items:List<AboutUsItems>
) {
    inner class AboutUsItems(
        @SerializedName("Position") val Position: String,
        @SerializedName("Name") val Name:String,
        @SerializedName("Email") val Email: String)
}