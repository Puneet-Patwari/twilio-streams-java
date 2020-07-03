package com.freshcaller.twiliostreamsjava;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TwilioStreamsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testDD(){
		String s = "{ \n" +
				" \"event\": \"media\",\n" +
				" \"sequenceNumber\": \"4\",\n" +
				" \"media\": { \n" +
				"   \"track\": \"inbound\", \n" +
				"   \"chunk\": \"1\", \n" +
				"   \"timestamp\": \"5\",\n" +
				"   \"payload\": \"no+JhoaJjpzSHxAKBgYJDhtEopGKh4aIjZm7JhILBwYIDRg1qZSLh4aIjJevLBUMBwYHDBUsr5eMiIaHi5SpNRgNCAYHCxImu5mNiIaHipGiRBsOCQYGChAf0pyOiYaGiY+e/x4PCQYGCQ4cUp+QioaGiY6bxCIRCgcGCA0ZO6aSi4eGiI2YtSkUCwcGCAwXL6yVjIeGh4yVrC8XDAgGBwsUKbWYjYiGh4uSpjsZDQgGBwoRIsSbjomGhoqQn1IcDgkGBgkPHv+ej4mGhomOnNIfEAoGBgkOG0SikYqHhoiNmbsmEgsHBggNGDWplIuHhoiMl68sFQwHBgcMFSyvl4yIhoeLlKk1GA0IBgcLEia7mY2IhoeKkaJEGw4JBgYKEB/SnI6JhoaJj57/Hg8JBgYJDhxSn5CKhoaJjpvEIhEKBwYIDRk7ppKLh4aIjZi1KRQLBwYIDBcvrJWMh4aHjJWsLxcMCAYHCxQptZiNiIaHi5KmOxkNCAYHChEixJuOiYaGipCfUhwOCQYGCQ8e/56PiYaGiY6c0h8QCgYGCQ4bRKKRioeGiI2ZuyYSCwcGCA0YNamUi4eGiIyXrywVDAcGBwwVLK+XjIiGh4uUqTUYDQgGBwsSJruZjYiGh4qRokQbDgkGBgoQH9KcjomGhomPnv8eDwkGBgkOHFKfkIqGhomOm8QiEQoHBggNGTumkouHhoiNmLUpFAsHBggMFy+slYyHhoeMlawvFwwIBgcLFCm1mI2IhoeLkqY7GQ0IBgcKESLEm46JhoaKkJ9SHA4JBgYJDx7/no+JhoaJjpzSHxAKBgYJDhtEopGKh4aIjZm7JhILBwYIDRg1qZSLh4aIjJevLBUMBwYHDBUsr5eMiIaHi5SpNRgNCAYHCxImu5mNiIaHipGiRBsOCQYGChAf0pyOiYaGiY+e/x4PCQYGCQ4cUp+QioaGiY6bxCIRCgcGCA0ZO6aSi4eGiI2YtSkUCwcGCAwXL6yVjIeGh4yVrC8XDAgGBwsUKbWYjYiGh4uSpjsZDQgGBwoRIsSbjomGhoqQn1IcDgkGBgkPHv+ej4mGhomOnNIfEAoGBgkOG0SikYqHhoiNmbsmEgsHBggNGDWplIuHhoiMl68sFQwHBgcMFSyvl4yIhoeLlKk1GA0IBgcLEia7mY2IhoeKkaJEGw4JBgYKEB/SnI6JhoaJj57/Hg8JBgYJDhxSn5CKhoaJjpvEIhEKBwYIDRk7ppKLh4aIjZi1KRQLBwYIDBcvrJWMh4aHjJWsLxcMCAYHCxQptZiNiIaHi5KmOxkNCAYHChEixJuOiYaGipCfUhwOCQYGCQ8e/56PiYaGiY6c0h8QCgYGCQ4bRKKRioeGiA==\"                        \n" +
				" } \n" +
				"}";
		JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
		String payload = jsonObject.get("media").getAsJsonObject().get("payload").getAsString();
		System.out.println(payload);
	}

}
