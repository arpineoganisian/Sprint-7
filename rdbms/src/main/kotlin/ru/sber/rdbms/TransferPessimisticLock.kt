package ru.sber.rdbms

import ru.sber.rdbms.exception.TransactionFailed
import java.sql.DriverManager

class TransferPessimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )

        connection.use { conn ->
            try {
                conn.autoCommit = false

                val preparedStatement1 = conn.prepareStatement(
                    "SELECT amount FROM account2 WHERE id = ?;"
                )
                preparedStatement1.use { statement ->
                    statement.setLong(1, accountId1)
                    val resultSet = statement.executeQuery()
                    resultSet.use {
                        require(it.next()) { "Не найдены данные по счету" }
                        val acc1Amount = it.getLong("amount")
                        require(acc1Amount >= amount) { "Недостаточно средств на счете" }
                    }
                }

                val preparedStatement = conn.prepareStatement(
                    "SELECT * FROM account2 WHERE id = ? FOR UPDATE;"
                )
                preparedStatement.use { statement ->
                    statement.setLong(1, accountId1)
                    statement.executeQuery()
                }

                val preparedStatement2 = conn.prepareStatement(
                    """UPDATE account2 SET amount = amount - ? WHERE id = ?;
                        UPDATE account2 SET amount = amount + ? WHERE id = ?;"""
                )
                preparedStatement2.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId1)
                    statement.setLong(3, amount)
                    statement.setLong(4, accountId2)
                    val resultSet = statement.executeUpdate()
                    require(resultSet == 1)
                }

                conn.commit()
                println("Транзакция прошла успешно")
            } catch (e: Exception) {
                throw TransactionFailed(e.message ?: "Транзакция не прошла", e)
            } finally {
                conn.autoCommit = true
            }
        }
    }
}
