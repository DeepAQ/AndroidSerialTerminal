package cn.imaq.serialterminal

import cn.wch.ch34xuartdriver.CH34xUARTDriver
import java.io.IOException

/**
 * Created by adn55 on 2017/4/16.
 */
class SerialConnection : Connection() {

    companion object {
        var driver: CH34xUARTDriver? = null
    }

    override val readThread = Thread {
        val buf = ByteArray(1024)
        while (driver != null && driver!!.isConnected) {
            val len = driver!!.ReadData(buf, buf.size)
            if (len > 0) {
                receiver?.invoke(buf, len)
            } else {
                break
            }
        }
    }

    override fun send(bytes: ByteArray, length: Int) {
        if (driver!!.WriteData(bytes, length) <= 0) {
            throw IOException()
        }
    }

    override fun close() {
        driver?.CloseDevice()
    }

}