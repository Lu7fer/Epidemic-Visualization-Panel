package cf.vbnm.covidchina.model

import com.baomidou.mybatisplus.annotation.TableName

//@Component
@TableName("covid_area_data")
data class DXYAreaData(
    var provinceName: String?,
    var provinceEnglishName: String?,
    var province_zipCode: Int,
    var province_confirmedCount: Long,
    var province_suspectedCount: Long,
    var province_curedCount: Long,
    var province_deadCount: Long,
    var cityName: String?,
    var cityEnglishName: String?,
    var city_zipCode: Int,
    var city_confirmedCount: Long,
    var city_suspectedCount: Long,
    var city_curedCount: Long,
    var city_deadCount: Long,
    var updateTime: String?
) {
    companion object {
        val cnData = DXYAreaData("中国", "China", 100000, 0, 0, 0, 0, "", "", 0, 0, 0, 0, 0, "")
    }

}