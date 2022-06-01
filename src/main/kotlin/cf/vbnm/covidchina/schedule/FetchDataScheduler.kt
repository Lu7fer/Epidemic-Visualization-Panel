package cf.vbnm.covidchina.schedule

import cf.vbnm.covidchina.mapper.DXYAreaMapper
import cf.vbnm.covidchina.model.DXYAreaData
import cf.vbnm.covidchina.schedule.mapper.FetchDataMapper
import cf.vbnm.covidchina.schedule.model.AreaData
import cf.vbnm.covidchina.schedule.model.OverallData
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import javax.net.ssl.HttpsURLConnection

@Component
class FetchDataScheduler(
    @field:Autowired
    var fetchDataMapper: FetchDataMapper,
    @field:Autowired
    var dxyAreaMapper: DXYAreaMapper,
    @field:Autowired
    var objectMapper: ObjectMapper
) {


    lateinit var provinceNames: ArrayList<String>

    @Scheduled(cron = "0 0 1 * * ?")
    fun fetchData() {
        /**
         * get country data
         * */
        val connection = URL("https://lab.isaaclin.cn/nCoV/api/overall").openConnection()
        if (connection is HttpsURLConnection) {
            connection.requestMethod = "GET"
            connection.setConnectTimeout(15000)
            connection.setReadTimeout(60000)
            connection.connect()
            if (connection.responseCode == 200) {
                val stream = connection.inputStream
                val overallData = objectMapper.readValue(stream.reader(), OverallData::class.java)
                if (overallData.success == true) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = overallData.results?.get(0)?.updateTime!!
                    val year = calendar.get(Calendar.YEAR).toString()
                    val month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1))
                    val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

                    val applied = DXYAreaData.cnData.copy(
                        province_confirmedCount = overallData.results[0]?.confirmedCount!!,
                        province_curedCount = overallData.results[0]?.curedCount!!,
                        province_suspectedCount = overallData.results[0]?.suspectedCount!!,
                        province_deadCount = overallData.results[0]?.deadCount!!,
                        updateTime = "$year-$month-$day"
                    )
                    dxyAreaMapper.insert(applied);
                }
            }
        }
        provinceNames = fetchDataMapper.provinceNames()
        for (i in provinceNames.indices) {
            val provinceName = when (provinceNames[i]) {
                "香港特别行政区" -> "香港"
                "澳门特别行政区" -> "澳门"
                "台湾省" -> "台湾"
                else -> provinceNames[i]
            }
            val urlEncoder = URLEncoder.encode(provinceName, StandardCharsets.UTF_8)
            val urlConnection = URL(
                "https://lab.isaaclin.cn/nCoV/api/area?latest=1&province=$urlEncoder"
            ).openConnection()
            if (urlConnection is HttpsURLConnection) {
                urlConnection.requestMethod = "GET"
                urlConnection.setConnectTimeout(15000)
                urlConnection.setReadTimeout(60000)
                urlConnection.connect()
                if (urlConnection.responseCode == 200) {
                    val stream = urlConnection.inputStream
                    val areaData = objectMapper.readValue(stream.reader(), AreaData::class.java)
                    val result = areaData.results?.get(0)

                    if (areaData.success == true) {
                        if (result != null) {
                            dxyAreaMapper.insert(
                                DXYAreaData(
                                    provinceNames[i],
                                    result.provinceEnglishName,
                                    result.locationId ?: 0,
                                    result.confirmedCount ?: 0,
                                    result.suspectedCount ?: 0,
                                    result.curedCount ?: 0,
                                    result.deadCount ?: 0,
                                    "", "",
                                    0,
                                    0, 0, 0, 0,
                                    result.updateTime?.let { format(it) }
                                )
                            )
                        }
                        val cities = result?.cities
                        if (cities != null) {
                            for (city in cities) {
                                if (city != null) {
                                    dxyAreaMapper.insert(
                                        DXYAreaData(
                                            provinceNames[i],
                                            result.provinceEnglishName,
                                            result.locationId ?: 0,
                                            result.confirmedCount ?: 0,
                                            result.suspectedCount ?: 0,
                                            result.curedCount ?: 0,
                                            result.deadCount ?: 0,
                                            city.cityName, city.cityEnglishName,
                                            city.locationId ?: 0,
                                            city.confirmedCount ?: 0,
                                            city.suspectedCount ?: 0,
                                            city.curedCount ?: 0,
                                            city.deadCount ?: 0,
                                            result.updateTime?.let { format(it) }
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun format(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val year = calendar.get(Calendar.YEAR).toString()
        val month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1))
        val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
        return "$year-$month-$day";
    }
}