package domain;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class User {
	private String username = null;
	private String pwd = null;

	public User() {
	}
	public User(String username, String pwd) {
		setUsername(username);
		setPwd(pwd);
	}
	public User(JSONObject json) {
		setUsername((String)json.get("username"));
		setPwd((String)json.get("pwd"));
	}
	public User(String jsonStr) {
		JSONObject json = (JSONObject)JSONValue.parse(jsonStr);
		setUsername((String)json.get("username"));
		setPwd((String)json.get("pwd"));
	}
	
	public String getUsername() {
		return username;
	}
	public String getPwd() {
		return pwd;
	}
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		json.put("username", getUsername());
		json.put("pwd", getPwd());
		return json;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override public String toString() {
		return getJson().toString();
	}
}
