package ru.sber.rdbms

import ru.sber.rdbms.exception.TransactionFailed
import java.sql.Connection
import java.sql.DriverManager

class TransferOptimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/db",
            "postgres",
            "postgres"
        )

        connection.use { conn ->
            try {
                conn.autoCommit = false

                val acc1Amount = getData(conn, accountId1, "amount")
                require(acc1Amount >= amount) { "Недостаточно средств на счете" }

                val acc1Version = getData(conn, accountId1, "version")
                val acc2Version = getData(conn, accountId2, "version")

                val preparedStatement = conn.prepareStatement(
                    """UPDATE account2 SET amount = amount - ?, version = version + 1
                        WHERE id = ? AND version = ?;
                        
                        UPDATE account2 SET amount = amount + ?, version = version + 1 
                        WHERE id = ? AND version = ?;"""
                )
                preparedStatement.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId1)
                    statement.setInt(3, acc1Version)
                    statement.setLong(4, amount)
                    statement.setLong(5, accountId2)
                    statement.setInt(6, acc2Version)
                    val result = statement.executeUpdate()
                    require(result == 1)
                    conn.commit()
                }
            } catch (e: Exception) {
                conn.rollback()
                throw TransactionFailed("Транзакция не прошла", e)
            } finally {
                conn.autoCommit = true
            }
            println("Транзакция прошла успешно")
        }
    }

    private fun getData(connection: Connection, id: Long, column: String): Int {
        val preparedStatement = connection.prepareStatement("SELECT $column FROM account2 WHERE id = ?")
        preparedStatement.use { statement ->
            statement.setLong(1, id)
            val resultSet = statement.executeQuery()
            resultSet.next()
            return resultSet.getInt(column)
        }
    }
}
