package com.example.matzip_exe.model

data class ModelMatZipList(val seq: String, val type: Int, val name: String, val latlng: ModelBizList.XY, val distance: Double?, val visitcount: Int) {
}