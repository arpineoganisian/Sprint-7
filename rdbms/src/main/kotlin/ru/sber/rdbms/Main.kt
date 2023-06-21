package ru.sber.rdbms

fun main() {

    // 1
    TransferConstraint().run {
        this.transfer(9, 2, 100)
        this.transfer(1, 2, 1000) //недостаточно средств
        this.transfer(100, 200, 100) //несуществующий счет
    }
    println()

    // 2
    TransferOptimisticLock().run {
        this.transfer(9, 1, 100)
        try { //недостаточно средств
            this.transfer(1, 2,1000)
        } catch (e: Exception) { println(e.message) }
        try { //несуществующий счет
            this.transfer(100, 200, 100)
        } catch (e: Exception) { println(e.message) }
    }
    println()

    // 3
    TransferPessimisticLock().run {
        this.transfer(9, 1, 100)
        try { // недостаточно средств
            this.transfer(1, 2, 1000)
        } catch (e: Exception) { println(e.message) }
        try { //несуществующий счет
            this.transfer(100, 200, 100)
        } catch (e: Exception) { println(e.message) }
    }
    // дедлок
    val transfer = TransferPessimisticLock()
    val thread1 = Thread {
        try {
            transfer.transfer(9, 8, 100)
        } catch (e: Exception) { println(e.message) }
    }
    val thread2 = Thread {
        try {
            Thread.sleep(1)
            transfer.transfer(8, 9, 100)
        } catch (e: Exception) { println(e.message) }
    }
    thread1.start()
    thread2.start()
    thread1.join()
    thread2.join()
    println()

    println("Главная функция завершена")
}