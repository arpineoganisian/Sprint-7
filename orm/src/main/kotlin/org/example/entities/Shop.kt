package org.example.entities

import org.hibernate.annotations.NaturalId
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Shop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @NaturalId
    val address: String,

    @ManyToMany(fetch = FetchType.EAGER)
    val books: MutableList<Book>
)
{
    override fun toString(): String {
        return "Shop(id=$id, address='$address')"
    }
}
