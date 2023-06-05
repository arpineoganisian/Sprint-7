package org.example.entities

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val title: String,

    @ManyToOne(cascade = [CascadeType.ALL])
    val author: Author,

    @Enumerated(value = EnumType.STRING)
    val genre: Genre,

    @ManyToMany(mappedBy = "books", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val shops: MutableList<Shop>
) {
    override fun toString(): String {
        return "Book(id=$id, title='$title', author=$author, genre=$genre, shops=$shops)"
    }
}
