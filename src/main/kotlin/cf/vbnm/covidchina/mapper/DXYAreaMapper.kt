package cf.vbnm.covidchina.mapper

import cf.vbnm.covidchina.model.*
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.springframework.stereotype.Repository

@Repository
interface DXYAreaMapper : BaseMapper<DXYAreaData> {

    fun getRecentProvinceData(): List<ProvinceConfirmedData>

    fun provinceTopN(n: Int): List<ProvinceTopNData>

    fun provinceCuredTopNAndDead(n: Int): List<ProvinceCuredAndDeadTopNData>

    fun provinceSuspectedTopN(n: Int): List<ProvinceSuspectedTopNData>

    fun provinceCompareTopN(n: Int): List<ProvinceCompareTopNData>

    fun countryOverall(): List<CountryOverallData>

    fun getRecentCityData(adcode: Int): List<CityConfirmedData>


}