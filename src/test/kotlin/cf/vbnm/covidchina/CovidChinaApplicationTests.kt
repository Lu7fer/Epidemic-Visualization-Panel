package cf.vbnm.covidchina

import cf.vbnm.covidchina.mapper.DXYAreaMapper
import cf.vbnm.covidchina.model.DXYAreaData
import cf.vbnm.covidchina.schedule.FetchDataScheduler
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.csvreader.CsvReader
import org.junit.jupiter.api.Test
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.nio.charset.StandardCharsets


@SpringBootTest
@MapperScan("cf.vbnm.covidchina")
class CovidChinaApplicationTests {


    @Autowired
    lateinit var mapper: DXYAreaMapper
    @Autowired
    lateinit var fetchDataScheduler: FetchDataScheduler

    fun scheduleTask() {
        fetchDataScheduler.fetchData()
    }

    fun contextLoads() {
        val file = File("D:\\28941\\Downloads\\DXYArea1.csv")

        val csvReader = CsvReader(file.absolutePath, ',', StandardCharsets.UTF_8)
        if (!csvReader.readHeaders()) {
            System.err.println("Wrong Header")
        }
        var i = 0

        while (csvReader.readRecord()) {
            i++
            val dxyAreaData = DXYAreaData(
                csvReader.get(csvReader.getHeader(0)),
                csvReader.get(csvReader.getHeader(1)),
                csvReader.get(csvReader.getHeader(2)).toIntOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(3)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(4)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(5)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(6)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(7)),
                csvReader.get(csvReader.getHeader(8)),
                csvReader.get(csvReader.getHeader(9)).toIntOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(10)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(11)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(12)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(13)).toLongOrNull() ?: 0,
                csvReader.get(csvReader.getHeader(14)).split(" ")[0]
            )
            val query = QueryWrapper<DXYAreaData>()
/*            query.exists(
                "select * from covid_area_data where provinceName= #{1} and cityName = #{2} and updateTime = #{3}",
                dxyAreaData.provinceName, dxyAreaData.cityName, dxyAreaData.updateTime
            )*/
            query.eq("provinceName", dxyAreaData.provinceName)
            query.eq("cityName", dxyAreaData.cityName)
            query.eq("updateTime", dxyAreaData.updateTime)
            val one = mapper.selectOne(query)
            if (one == null)
                mapper.insert(
                    dxyAreaData
                )
            else
                System.err.println("重复数据")
            println("  $i")
        }

    }

}
