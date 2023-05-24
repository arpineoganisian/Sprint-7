package com.example.demo.controller

import com.example.demo.persistance.Entity
import com.example.demo.persistance.EntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller{

    @Autowired
    private lateinit var entityRepository: EntityRepository

    @GetMapping("/hello/{name}")
    fun hello(@PathVariable name: String): String {
        return entityRepository.save(Entity(name = name)).toString()
    }

    @GetMapping("/all")
    fun all(): List<Entity> {
        return entityRepository.findAll()
    }
}