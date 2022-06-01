package cf.vbnm.covidchina.schedule.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AreaData(

    @field:JsonProperty("success")
    val success: Boolean? = null,

    @field:JsonProperty("results")
    val results: List<ResultsItem?>? = null
)

data class CitiesItem(

    @field:JsonProperty("currentConfirmedCount")
    val currentConfirmedCount: Long? = 0,

    @field:JsonProperty("confirmedCount")
    val confirmedCount: Long? = 0,

    @field:JsonProperty("curedCount")
    val curedCount: Long? = 0,

    @field:JsonProperty("midDangerCount")
    val midDangerCount: Int? = 0,

    @field:JsonProperty("cityName")
    val cityName: String? = null,

    @field:JsonProperty("locationId")
    val locationId: Int? = 0,

    @field:JsonProperty("currentConfirmedCountStr")
    val currentConfirmedCountStr: String? = null,

    @field:JsonProperty("highDangerCount")
    val highDangerCount: Long? = 0,

    @field:JsonProperty("deadCount")
    val deadCount: Long? = 0,

    @field:JsonProperty("cityEnglishName")
    val cityEnglishName: String? = null,

    @field:JsonProperty("suspectedCount")
    val suspectedCount: Long? = 0
)

data class ResultsItem(

    @field:JsonProperty("curedCount")
    val curedCount: Long? = 0,

    @field:JsonProperty("cities")
    val cities: List<CitiesItem?>? = null,

    @field:JsonProperty("countryFullName")
    val countryFullName: String? = null,

    @field:JsonProperty("updateTime")
    val updateTime: Long? = null,

    @field:JsonProperty("continentEnglishName")
    val continentEnglishName: String? = null,

    @field:JsonProperty("suspectedCount")
    val suspectedCount: Long? = 0,

    @field:JsonProperty("currentConfirmedCount")
    val currentConfirmedCount: Long? = 0,

    @field:JsonProperty("confirmedCount")
    val confirmedCount: Long? = 0,

    @field:JsonProperty("countryEnglishName")
    val countryEnglishName: String? = null,

    @field:JsonProperty("locationId")
    val locationId: Int? = 0,

    @field:JsonProperty("provinceShortName")
    val provinceShortName: String? = null,

    @field:JsonProperty("comment")
    val comment: String? = null,

    @field:JsonProperty("countryName")
    val countryName: String? = null,

    @field:JsonProperty("provinceName")
    val provinceName: String? = null,

    @field:JsonProperty("continentName")
    val continentName: String? = null,

    @field:JsonProperty("deadCount")
    val deadCount: Long? = 0,

    @field:JsonProperty("provinceEnglishName")
    val provinceEnglishName: String? = null
)
