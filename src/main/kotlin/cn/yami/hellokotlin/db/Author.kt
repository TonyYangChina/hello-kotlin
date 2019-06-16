package cn.yami.hellokotlin.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.*
import javax.transaction.Transactional

@Entity
@Table(name = "author")
data class Author(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        val id: Int? = null,
        val name: String? = null,
        val status: Int? = 0,
        //  val age: Int,
        @Column(name = "nick_name")
        var nickName: String? = null
) {
        @OneToMany
        @JoinTable(name = "author_song",joinColumns = [JoinColumn(name = "author_id")],
                inverseJoinColumns = [JoinColumn(name="song_id")])
        var songs: List<Song>? = ArrayList()
}

@Repository
@Suppress("unused")
interface AuthorRepository : JpaRepository<Author, Int> {
        // 查询
        //fun findByTokenAndStatus(token: String, status: Int = 1): Author?
        // 更新
        @Transactional
        @Modifying
        @Query("UPDATE Author a SET a.name =:name WHERE a.id =:id")
        fun updateAuthorName(@Param("name") name: String,
                             @Param("id") id: Int)


}
