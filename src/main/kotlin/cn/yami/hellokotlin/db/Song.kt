package cn.yami.hellokotlin.db

import javax.persistence.*

@Entity
@Table(name = "song")
data class Song(
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        var id: Int? = null,
        var title: String? = null
) {
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "author_id")
    @Suppress("unused")
    var author: Author? = null
}