package businesslogic;

import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import dataaccess.*;
import domain.*;

public class RequestHandler {
	public static String process(String str) {
		JSONObject json = (JSONObject)JSONValue.parse(str);
		Map<String,Object> map = new HashMap<>();

		map.put("request",(String)json.get("request"));
		map.put("content",((JSONObject)json.get("content")).toString());
		switch ((String)map.get("request")) {
			case "fetch":
				return fetch((String)map.get("content"));
			case "login":
				return login((String)map.get("content"));
			case "updateScore":
				return updateScore((String)map.get("content"));
			default:
				return "{}";
		}
	}
	private static String fetch(String str) {
		JSONObject json = (JSONObject)JSONValue.parse(str);
		long qty = (long)json.get("qty");
		String quizId = (String)json.get("id");
		List<domain.QuizItem> list = dataaccess.QuizItem.fetchAll(quizId);
		Collections.shuffle(list);
		list = list.stream().limit(qty).collect(Collectors.toList());
		json = new JSONObject();
		json.put("qty", qty);
		json.put("items", list);
		return json.toString();
	}
	private static String updateScore(String str) {
		domain.Score score = new domain.Score(str);
		JSONObject json = new JSONObject();
		json.put("success", dataaccess.Score.add(score));
		return json.toString();
	}
	private static String login(String str) {
		domain.User user = new domain.User(str);
		JSONObject json = new JSONObject();
		try {
			json.put("success", user.getPwd().equals(dataaccess.User.fetch(user.getUsername()).getPwd()));
		} catch (Exception e) {
			json.put("success", false);
		}
		return json.toString();
	}
}
