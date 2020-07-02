package com.example.matzip_exe.model

import com.google.gson.annotations.SerializedName

data class ModelPolygon(
    @SerializedName("Item")val items: List<PolygonItems>
) {

    inner class PolygonItems(
        @SerializedName("Division")val Division: String,
        @SerializedName("Position")val Position: List<List<Double>>
    ){

    }
}