package com.socklaundry;


import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Authors {

	private long id;
	private String username;
	private String about;
	private Long submitted;
	private Long submissionCount;
	private Long commentCount;
	private Long createdAt;



	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public Long getSubmitted() {
		return submitted;
	}


	public void setSubmitted(Long submitted) {
		this.submitted = submitted;
	}


	public Long getSubmissionCount() {
		return submissionCount;
	}


	public void setSubmissionCount(Long submissionCount) {
		this.submissionCount = submissionCount;
	}


	public Long getCommentCount() {
		return commentCount;
	}


	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}


	public Long getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	private static final String GET_URL = "https://jsonmock.hackerrank.com/api/article_users/search?page=";

	public static List<Authors> listAuthors = new ArrayList<Authors>();

	public static void main(String[] args) {

		resource();

		//get username with highest comment count
		String output = getUsernameWithHighestCommentCount();
		//System.out.println(output);

		//get list of usernames by submission count greater than the inserted int parameter
		List<String> userName = getUsernames(1000);
		for(String j: userName) {
		//	System.out.println(j);
		}
		//get list of usernames by record date greater than the inserted int parameter
		List<String> records =	getUsernamesSortedByRecordDate(1224455310);
		for(String record: records)	{
		//	System.out.println(record);
		}

	}

	public static List<String> getUsernames(int threshold) {
		List<String> allAuthorResult = new ArrayList<String>();

		for(Authors currentAuthor : listAuthors) {

			if(currentAuthor.getSubmissionCount().intValue() >  threshold) {
				allAuthorResult.add(currentAuthor.getUsername());
			}
		}
		return allAuthorResult;		    
	}

	public static String getUsernameWithHighestCommentCount() {
		
		Authors starterAuthor = listAuthors.get(0);

		for(int x = 0; x < listAuthors.size();x++ ) {
			Authors currentAuthor = listAuthors.get(x);

			if(starterAuthor.getCommentCount() < currentAuthor.getCommentCount()) {
				starterAuthor = currentAuthor;
			}
		}

		return starterAuthor.getUsername();		 
	}

	public static List<String> getUsernamesSortedByRecordDate(int threshold) {
		
		List<String> allAuthorResult = new ArrayList<String>();
		LocalDateTime convertedThresholdTime = convertTime(threshold);

		for(Authors list: listAuthors) {
			LocalDateTime currentAuthor = convertTime(list.getCreatedAt());
			
			if(currentAuthor.isAfter(convertedThresholdTime)) {
				allAuthorResult.add(list.getUsername());
			}

		}

		return allAuthorResult;		    
	}

	public static LocalDateTime convertTime(long time){

		LocalDateTime dates =
				LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
		return dates ;
	}

	public static void resource() {
		String result = new String();
		String result2 = new String();

		JSONArray json = new JSONArray();
		JSONArray json2 = new JSONArray();

		try {

			result = sendGET(1);
			result2 = sendGET(2);

			JSONParser parse = new JSONParser();

			JSONObject obj = (JSONObject)parse.parse(result);
			JSONObject obj2 = (JSONObject)parse.parse(result2);

			json = (JSONArray) obj.get("data"); 
			json2 = (JSONArray) obj2.get("data"); 


		}catch(Exception ex) {

		}


		for(int i =0; i < json.size() ; i++) {
			Authors newGuy = new Authors();
			JSONObject jsonObj = (JSONObject)json.get(i);
			newGuy.setSubmitted((Long)jsonObj.get("submitted"));
			newGuy.setAbout((String)jsonObj.get("about"));
			newGuy.setCommentCount((Long)jsonObj.get("comment_count"));
			newGuy.setCreatedAt((Long)jsonObj.get("created_at"));
			newGuy.setId((Long)jsonObj.get("id"));
			newGuy.setSubmissionCount((Long)jsonObj.get("submission_count"));
			newGuy.setUsername((String)jsonObj.get("username"));

			listAuthors.add(newGuy);
		}

		for(int i =0; i < json2.size() ; i++) {
			Authors newGuy = new Authors();
			JSONObject jsonObj = (JSONObject)json2.get(i);
			newGuy.setSubmitted((Long)jsonObj.get("submitted"));
			newGuy.setAbout((String)jsonObj.get("about"));
			newGuy.setCommentCount((Long)jsonObj.get("comment_count"));
			newGuy.setCreatedAt((Long)jsonObj.get("created_at"));
			newGuy.setId((Long)jsonObj.get("id"));
			newGuy.setSubmissionCount((Long)jsonObj.get("submission_count"));
			newGuy.setUsername((String)jsonObj.get("username"));

			listAuthors.add(newGuy);
		}
	}
	
	private static String sendGET(int row) throws IOException {

		URL obj = new URL(GET_URL+row);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.connect(); 
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) { // success

			String inline = new String();
			Scanner sc = new Scanner(obj.openStream());

			while(sc.hasNext())
			{
				inline += sc.nextLine();
			}

			sc.close();
			return inline;
		} else {

			System.out.println("GET request not worked");
		}
		return null;
	}


}
