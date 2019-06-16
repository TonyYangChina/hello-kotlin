package cn.yami.hellokotlin.db

import javax.persistence.*

@Entity
@Table(name = "song")
data class Song(
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        var id: Int? = null,
        var title: String? = null
) {
    @ManyToOne
    // @JoinColumn(name = "author_id")
    @Suppress("unused")
    var author: Author? = null
}