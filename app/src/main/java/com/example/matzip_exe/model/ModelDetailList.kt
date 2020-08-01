package com.example.matzip_exe.model

data class ModelDetailList(val monthlyVisits: List<ModelBizDetail.MonthlyVisitsItems>, val avgCost: Int?, val latlng: ModelBizDetail.XY?, val detailUrl: String?, val recommendations: List<ModelBizDetail.RecommendItems>) {
}