package ru.sber.rdbms

import org.postgresql.util.PSQLException
import java.sql.DriverManager

class TransferConstraint {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )

        connection.use { conn ->
            val prepareStatement = conn.prepareStatement(
                """UPDATE account1 SET amount = amount - ? WHERE id = ?;
                UPDATE account1 SET amount = amount + ? WHERE id = ?;"""
            )
            prepareStatement.use { statement ->
                statement.setLong(1, amount)
                statement.setLong(2, accountId1)
                statement.setLong(3, amount)
                statement.setLong(4, accountId2)
                try {
                    val resultSet = statement.executeUpdate()
                    if (resultSet != 1) {
                        println("Не найдены данные по счету")
                        return
                    }
                } catch (e: PSQLException) {
                    if (e.message?.contains("amount_check") == true)
                        println("Недостаточно средств на счете")
                    else
                        println(e.message)
                    return
                }
                println("Транзакция прошла успешно")
            }
        }
    }
}
