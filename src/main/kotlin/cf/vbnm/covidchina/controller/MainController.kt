package cf.vbnm.covidchina.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class MainController {

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

}