package vn.doitsolutions.quickz.network

import io.socket.client.Socket
import io.socket.client.IO
import java.net.URISyntaxException

object SocketHandler {

    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use the your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server
            //            mSocket = IO.socket("http://10.0.2.2:5432")
            mSocket = IO.socket("http://127.0.0.1:4555")
            println("CERA: Socket created");
        } catch (e: URISyntaxException) {
            println(e.message + "CERA: Socket not created")

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
        println("CERA: socket connected = " + mSocket.connected())
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}