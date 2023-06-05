package org.example

import org.example.dao.BookDao
import org.example.entities.Author
import org.example.entities.Book
import org.example.entities.Genre
import org.example.entities.Shop
import org.hibernate.cfg.Configuration

fun main() {

    val sessionFactory = Configuration()
        .configure()
        .addAnnotatedClass(Book::class.java)
        .addAnnotatedClass(Author::class.java)
        .addAnnotatedClass(Shop::class.java)
        .buildSessionFactory()


    val bookDao = BookDao(sessionFactory)

    sessionFactory.use {

        val author = Author(name = "Stephen King", books = mutableListOf())
        val author2 = Author(name = "Fyodor Dostoevsky", books = mutableListOf())

        val shop = Shop(address = "Avenue Q, 123", books = mutableListOf())
        val shop2 = Shop(address = "Boulevard X, 456", books = mutableListOf())

        val book = Book(
            title = "The Shining",
            author = author,
            genre = Genre.HORROR,
            shops = mutableListOf(shop)
        )

        val book2 = Book(
            title = "Crime and Punishment",
            author = author2,
            genre = Genre.DRAMA,
            shops = mutableListOf(shop2)
        )

        val book3 = Book(
            title = "The Idiot",
            author = author2,
            genre = Genre.DRAMA,
            shops = mutableListOf(shop, shop2)
        )

        bookDao.save(book)
        bookDao.save(book2)
        bookDao.save(book3)

        println("НАЙДЕНА КНИГА: ${bookDao.find(book.id)} \n")

        println("НАЙДЕНЫ КНИГИ: ${bookDao.findAll()} \n")

        bookDao.update(book.copy(title = "The Shining 2"))
        println("ОБНОВЛЕНА КНИГА: ${bookDao.find(book.id)} \n")

        println("УДАЛЕНИЕ КНИГИ: $book \n")
        bookDao.delete(book)

        println("НАЙДЕНЫ КНИГИ: ${bookDao.findAll()} \n")
    }
}