package com.example.demo.persistance

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Entity

@Entity
data class Entity (
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var name: String = ""
)