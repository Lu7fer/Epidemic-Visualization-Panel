package cf.vbnm.covidchina.schedule.mapper

import cf.vbnm.covidchina.model.DXYAreaData
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
interface FetchDataMapper : BaseMapper<DXYAreaData> {
    @Select("SELECT DISTINCT provinceName FROM covid_area_data")
    fun provinceNames(): ArrayList<String>

}