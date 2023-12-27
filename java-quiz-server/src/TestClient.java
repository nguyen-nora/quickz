public class TestClient {
	public static void main(String[] args) throws Exception {
		businesslogic.TcpClient client = new businesslogic.TcpClient();
		client.connect("localhost", 4555);
		// Remember to connect before sending. Connects automatically if including endpoint in constructor
		//businesslogic.TcpClient client = new businesslogic.TcpClient("127.0.0.1", 4555);

		// Get quiz by quizId and qty
		String json = "{\"request\": \"fetch\", \"content\": {\"qty\": 2, \"id\": \"1\"}}";
		client.sendMessage(json);
		System.out.println("Received: "+client.readMessage().get());


		// Login with username and pwd
		client.sendMessage("{\"request\": \"login\", \"content\": {\"username\": \"admin\", \"pwd\": \"admin\"}}");
		System.out.println("Received: "+client.readMessage().get());

		// Update score with username, quizId and score
		client.sendMessage("{\"request\": \"updateScore\", \"content\": {\"username\": \"admin\", \"quizId\": \"1\", \"score\": 100}}");
		System.out.println("Received: "+client.readMessage().get());

		try {
			System.in.read();
		} catch (Exception e) {

		}
		client.stop();
	}
}
