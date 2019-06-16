package cn.yami.hellokotlin.web

import cn.yami.hellokotlin.db.Author
import cn.yami.hellokotlin.db.AuthorRepository
import cn.yami.hellokotlin.db.Song
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/author")
class AuthorController {

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @PostMapping("/add")
    fun add(request: HttpServletRequest,
            @RequestParam(value="name") name: String,
            @RequestParam(value="nickName",required = false) nickName: String,
            @RequestParam(value="songTitles") songTitles: String): Map<String, Any> {
        val author = Author(name = name, nickName = nickName)
        val titles: List<String> = songTitles.split(",")

        if (titles.isNotEmpty()) {
            // author.songs = ArrayList()
            for (title in titles) {
                (author.songs as ArrayList<Song>).add(Song(title = title))
            }
        }
        authorRepository.save(author)

        return mapOf("ret" to 0, "msg" to "OK")

    }

}