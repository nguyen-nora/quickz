public class Program {
	public static void main(String[] args) {
		domain.QuizItem quizItem = new domain.QuizItem("{\"id\": \"1\", \"quizId\": \"1\", \"quizItem\": {\"question\": \"What is the capital of France?\", \"incorrect_answers\": [\"London\", \"Berlin\"], \"correct_answer\": \"Paris\"}}");
		System.out.println(quizItem);
		System.out.println(dataaccess.User.fetch("admin"));
		System.out.println(dataaccess.Score.fetch("admin", "1"));
		System.out.println(dataaccess.QuizItem.fetch("1"));
		System.out.println(dataaccess.QuizItem.fetchAll("1"));
		businesslogic.TcpServer a = new businesslogic.TcpServer(4555);
		a.run();
		String json = "{\"request\": \"fetch\", \"content\": {\"qty\": 2, \"id\": \"1\"}}";
		System.out.println(businesslogic.RequestHandler.process(json));
	}
}
