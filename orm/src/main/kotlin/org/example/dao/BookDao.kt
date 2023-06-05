package org.example.dao

import org.example.entities.Book
import org.hibernate.SessionFactory

class BookDao(val sessionFactory: SessionFactory) {

    fun save(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(book)
            session.transaction.commit()
        }
    }

    fun find(id: Int): Book? {
        val result: Book?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Book::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Book> {
        val result: List<Book>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Book").list() as MutableList<Book>
            session.transaction.commit()
        }
        return result
    }

    fun update(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(book)
            session.transaction.commit()
        }
    }

    fun delete(book: Book) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.delete(book)
            session.transaction.commit()
        }
    }
}