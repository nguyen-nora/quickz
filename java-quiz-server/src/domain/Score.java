package domain;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Score {
	private String username;
	private String quizId;
	private long score;

	public Score(String username, String quizId, long score) {
		setUsername(username);
		setQuizId(quizId);
		setScore(score);
	}
	public Score(JSONObject json) {
		setUsername((String)json.get("username"));
		setQuizId((String)json.get("quizId"));
		setScore((long)json.get("score"));
	}
	public Score(String jsonStr) {
		JSONObject json = (JSONObject)JSONValue.parse(jsonStr);
		setUsername((String)json.get("username"));
		setQuizId((String)json.get("quizId"));
		setScore((long)json.get("score"));
	}

	public String getUsername() {
		return username;
	}
	public String getQuizId() {
		return quizId;
	}
	public long getScore() {
		return score;
	}
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		json.put("username", getUsername());
		json.put("quizId", getQuizId());
		json.put("score", getScore());
		return json;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
	public void setScore(long score) {
		this.score = score;
	}

	@Override public String toString() {
		return getJson().toString();
	}
}
