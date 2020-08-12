package com.team_no_yes.matzip_exe.detail

data class ModelDetailList(val monthlyVisits: List<ModelGetDetail.MonthlyVisitsItems>, val avgCost: Int, val latlng: ModelGetDetail.XY, val detailUrl: String?, val visitCount: Int, val recommendations: List<ModelGetDetail.RecommendItems>?) {
}