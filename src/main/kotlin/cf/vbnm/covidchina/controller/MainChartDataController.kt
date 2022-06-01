package cf.vbnm.covidchina.controller

import cf.vbnm.covidchina.mapper.DXYAreaMapper
import cf.vbnm.covidchina.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/chart")
class MainChartDataController @Autowired constructor(private var dxyAreaMapper: DXYAreaMapper) {

    @RequestMapping("/totalConfirmed")
    @ResponseBody
    fun totalConfirmed(): List<ProvinceConfirmedData> {
        return try {
            dxyAreaMapper.getRecentProvinceData()
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.getRecentProvinceData()
        }
    }

    @RequestMapping("/cityConfirmed")
    @ResponseBody
    fun cityConfirmed(adcode: Int): List<CityConfirmedData> {
        return try {
            dxyAreaMapper.getRecentCityData(adcode)
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.getRecentCityData(adcode)
        }
    }

    @RequestMapping("/provinceTopN")
    @ResponseBody
    fun provinceTopN(n: Int?): List<ProvinceTopNData> {
        val n1 = when (n) {
            null, 0 -> 5
            else -> {
                n
            }
        }
        return try {
            dxyAreaMapper.provinceTopN(n1)
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.provinceTopN(n1)
        }
    }

    @RequestMapping("/provinceCuredTopNAndDead")
    @ResponseBody
    fun provinceCuredTopNAndDead(n: Int?): List<ProvinceCuredAndDeadTopNData> {
        val n1 = when (n) {
            null, 0 -> 5
            else -> {
                n
            }
        }
        return try {
            dxyAreaMapper.provinceCuredTopNAndDead(n1)
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.provinceCuredTopNAndDead(n1)
        }
    }

    @RequestMapping("/provinceSuspectedTopN")
    @ResponseBody
    fun provinceSuspectedTopN(n: Int?): List<ProvinceSuspectedTopNData> {
        val n1 = when (n) {
            null, 0 -> 5
            else -> {
                n
            }
        }
        return try {
            dxyAreaMapper.provinceSuspectedTopN(n1)
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.provinceSuspectedTopN(n1)
        }
    }

    @RequestMapping("/provinceCompareTopN")
    @ResponseBody
    fun provinceCompareTopN(n: Int?): List<ProvinceCompareTopNData> {
        val n1 = when (n) {
            null, 0 -> 5
            else -> {
                n
            }
        }
        return try {
            dxyAreaMapper.provinceCompareTopN(n1)
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.provinceCompareTopN(n1)
        }
    }

    @RequestMapping("/countryOverall")
    @ResponseBody
    fun countryOverall(n: Int?): List<CountryOverallData> {
        val n1 = when (n) {
            null, 0 -> 5
            else -> {
                n
            }
        }
        return try {
            dxyAreaMapper.countryOverall()
        } catch (e: Exception) {
            e.printStackTrace()
            dxyAreaMapper.countryOverall()
        }
    }
}

