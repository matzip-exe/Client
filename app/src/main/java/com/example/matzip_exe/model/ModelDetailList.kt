package com.example.matzip_exe.model

data class ModelDetailList(val monthlyVisits: List<ModelBizDetail.MonthlyVisitsItems>, val detailUrl: String?, val recommend: List<ModelBizDetail.RecommendItems>) {
}