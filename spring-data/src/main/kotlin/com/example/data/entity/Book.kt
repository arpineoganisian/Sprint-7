package com.example.data.entity

import com.example.springdata.entity.Genre
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    @ManyToOne(cascade = [CascadeType.MERGE])
    val author: Author,

    @Enumerated(value = EnumType.STRING)
    val genre: Genre,
) {
    override fun toString(): String {
        return "Book(id=$id, title='$title', author=$author, genre=$genre)"
    }
}
