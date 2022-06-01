package cf.vbnm.covidchina.model

import com.baomidou.mybatisplus.annotation.TableField


data class ProvinceConfirmedData(
    @TableField("provinceName")
    val name: String,
    @TableField("province_zipCode")
    val adcode: Int,
    @TableField("province_confirmedCount")
    val value: Int
)
data class CityConfirmedData(
    @TableField("cityName")
    val name: String,
    @TableField("city_zipCode")
    val adcode: Int,
    @TableField("city_confirmedCount")
    val value: Int
)

data class ProvinceTopNData(
    @TableField("provinceName")
    val name: String,
    @TableField("province_zipCode")
    val adcode: Int,
    @TableField("province_confirmedCount")
    val province_confirmedCount: Int,
    @TableField("province_suspectedCount")
    val province_suspectedCount: Int,
    @TableField("province_curedCount")
    val province_curedCount: Int,
    @TableField("province_deadCount")
    val province_deadCount: Int,
)

data class ProvinceCuredAndDeadTopNData(
    @TableField("provinceName")
    val name: String,
    @TableField("province_zipCode")
    val adcode: Int,
    @TableField("province_curedCount")
    val province_curedCount: Int,
    @TableField("province_deadCount")
    val province_deadCount: Int,
)

data class ProvinceSuspectedTopNData(
    @TableField("provinceName")
    val name: String,
    @TableField("province_zipCode")
    val adcode: Int,
    @TableField("province_suspectedCount")
    val value: Int
)

data class ProvinceCompareTopNData(
    @TableField("provinceName")
    val name: String,
    @TableField("province_zipCode")
    val adcode: Int,
    @TableField("province_confirmedCount")
    val province_confirmedCount: Int,
    @TableField("province_suspectedCount")
    val province_suspectedCount: Int,
    @TableField("province_curedCount")
    val province_curedCount: Int,
    @TableField("province_deadCount")
    val province_deadCount: Int,
)

data class CountryOverallData(
    @TableField("province_confirmedCount")
    val value: Int,
    @TableField("updateTime")
    val date: String
)
