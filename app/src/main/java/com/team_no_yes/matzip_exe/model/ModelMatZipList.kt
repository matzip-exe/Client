package com.team_no_yes.matzip_exe.model

data class ModelMatZipList(val seq: String, val type: String?, val name: String, /*val latlng: ModelBizList.XY?,*/ val distance: Double?, val visitcount: Int, val avgcost: Int, val viewType: Int) {
}