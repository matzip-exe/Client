package com.team_no_yes.matzip_exe.detail

import com.team_no_yes.matzip_exe.detail.ModelBizDetail

data class ModelDetailList(val monthlyVisits: List<ModelBizDetail.MonthlyVisitsItems>, val avgCost: Int, val latlng: ModelBizDetail.XY, val detailUrl: String?, val visitCount: Int, val recommendations: List<ModelBizDetail.RecommendItems>?) {
}