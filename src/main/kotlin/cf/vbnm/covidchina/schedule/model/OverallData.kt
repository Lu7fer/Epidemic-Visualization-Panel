package cf.vbnm.covidchina.schedule.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OverallData(

    @field:JsonProperty("success")
    val success: Boolean? = null,

    @field:JsonProperty("results")
    val results: List<ResultsItemOverall?>? = null
)

data class GlobalStatistics(

    @field:JsonProperty("currentConfirmedCount")
    val currentConfirmedCount: Long? = null,

    @field:JsonProperty("confirmedCount")
    val confirmedCount: Long? = null,

    @field:JsonProperty("curedCount")
    val curedCount: Long? = null,

    @field:JsonProperty("currentConfirmedIncr")
    val currentConfirmedIncr: Long? = null,

    @field:JsonProperty("confirmedIncr")
    val confirmedIncr: Long? = null,

    @field:JsonProperty("curedIncr")
    val curedIncr: Long? = null,

    @field:JsonProperty("deadCount")
    val deadCount: Long? = null,

    @field:JsonProperty("deadIncr")
    val deadIncr: Long? = null,

    @field:JsonProperty("yesterdayConfirmedCountIncr")
    val yesterdayConfirmedCountIncr: Int? = null
)

data class ResultsItemOverall(

    @field:JsonProperty("generalRemark")
    val generalRemark: String? = null,

    @field:JsonProperty("curedCount")
    val curedCount: Long? = 0,

    @field:JsonProperty("seriousCount")
    val seriousCount: Long? = 0,

    @field:JsonProperty("note2")
    val note2: String? = null,

    @field:JsonProperty("currentConfirmedIncr")
    val currentConfirmedIncr: Long? = 0,

    @field:JsonProperty("note3")
    val note3: String? = null,

    @field:JsonProperty("suspectedIncr")
    val suspectedIncr: Long? = 0,

    @field:JsonProperty("seriousIncr")
    val seriousIncr: Long? = 0,

    @field:JsonProperty("confirmedIncr")
    val confirmedIncr: Long? = 0,

    @field:JsonProperty("updateTime")
    val updateTime: Long? = 0,

    @field:JsonProperty("globalStatistics")
    val globalStatistics: GlobalStatistics? = null,

    @field:JsonProperty("deadIncr")
    val deadIncr: Long? = 0,

    @field:JsonProperty("suspectedCount")
    val suspectedCount: Long? = 0,

    @field:JsonProperty("currentConfirmedCount")
    val currentConfirmedCount: Long? = 0,

    @field:JsonProperty("confirmedCount")
    val confirmedCount: Long? = 0,

    @field:JsonProperty("curedIncr")
    val curedIncr: Long? = 0,

    @field:JsonProperty("remark5")
    val remark5: String? = null,

    @field:JsonProperty("remark4")
    val remark4: String? = null,

    @field:JsonProperty("deadCount")
    val deadCount: Long? = 0,

    @field:JsonProperty("remark1")
    val remark1: String? = null,

    @field:JsonProperty("note1")
    val note1: String? = null,

    @field:JsonProperty("remark3")
    val remark3: String? = null,

    @field:JsonProperty("remark2")
    val remark2: String? = null
)
