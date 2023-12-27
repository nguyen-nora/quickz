package domain;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class QuizItem {
	private String id;
	private String quizId;
	private String question;
	private List<String> incorrectAnswers;
	private List<String> correctAnswers;

	public QuizItem(String id, String quizId, String question, List<String> incorrectAnswers, List<String> correctAnswers) {
		setId(id);
		setQuizId(quizId);
		setQuestion(question);
		setIncorrectAnswers(incorrectAnswers);
		setCorrectAnswers(correctAnswers);
	}
	public QuizItem(String id, String quizId, String jsonStr) {
		setId(id);
		setQuizId(quizId);
		JSONObject quizItem = (JSONObject)JSONValue.parse(jsonStr);
		setQuestion((String)quizItem.get("question"));
		setIncorrectAnswers((List<String>)quizItem.get("incorrect_answers"));
		setCorrectAnswers((List<String>)quizItem.get("correct_answers"));
	}
	public QuizItem(JSONObject json) {
		setId((String)json.get("id"));
		setQuizId((String)json.get("quizId"));
		JSONObject quizItem = (JSONObject)JSONValue.parse(json.get("quizItem").toString());
		setQuestion((String)quizItem.get("question"));
		setIncorrectAnswers((List<String>)quizItem.get("incorrect_answers"));
		setCorrectAnswers((List<String>)quizItem.get("correct_answers"));
	}
	public QuizItem(String jsonStr) {
		JSONObject json = (JSONObject)JSONValue.parse(jsonStr);
		setId((String)json.get("id"));
		setQuizId((String)json.get("quizId"));
		JSONObject quizItem = (JSONObject)JSONValue.parse(json.get("quizItem").toString());
		setQuestion((String)quizItem.get("question"));
		setIncorrectAnswers((List<String>)quizItem.get("incorrect_answers"));
		setCorrectAnswers((List<String>)quizItem.get("correct_answers"));
	}

	public String getId() {
		return id;
	}
	public String getQuizId() {
		return quizId;
	}
	public String getQuestion() {
		return question;
	}
	public List<String> getIncorrectAnswers() {
		return incorrectAnswers;
	}
	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}
	public JSONObject getQuizItem() {
		JSONObject json = new JSONObject();
		json.put("question", getQuestion());
		json.put("incorrect_answers", getIncorrectAnswers());
		json.put("correct_answers", getCorrectAnswers());
		return json;
	}
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("quizId", getQuizId());
		json.put("quizItem", getQuizItem());
		return json;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setIncorrectAnswers(List<String> incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	
	@Override public String toString() {
		return getJson().toString();
	}
}
