package org.example.entities

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val name: String,

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    var books: MutableList<Book>
)
{
    override fun toString(): String {
        return "Author(id=$id, name='$name')"
    }
}
