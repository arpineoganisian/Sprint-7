package com.example.data.repository

import com.example.data.entity.Author
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface AuthorRepository : CrudRepository<Author, Long> {
}