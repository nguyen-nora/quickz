package vn.doitsolutions.quickz.network

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import vn.doitsolutions.quickz.pages.games.TcpClient
import kotlin.math.log

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConnectTcp () {
    /*val client = AsynchronousSocketChannel(),
    client.connect("10.0.247.18",4555)
    val json = "{\"request\": \"fetch\", \"content\": {\"qty\": 2, \"id\": \"1\"}}"
    client.outputStream.write("Hello from the client!".toByteArray())
    client.outputStream.write(json.toByteArray())
    val read = client.inputStream.read().toString()
    println(read)
    client.close()*/
//    val client = TcpClient("10.0.242.18",4555)
//    val json = "{\"request\": \"fetch\", \"content\": {\"qty\": 2, \"id\": \"1\"}}"
//    client.sendMessage(json)
//    log.("Waiting...")
//    val msg = client.readMessage().get()
//    println("Done")
}

