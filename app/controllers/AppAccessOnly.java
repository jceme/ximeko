//package com.mycompany.app;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.Scanner;
//
//import org.scribe.builder.ServiceBuilder;
//import org.scribe.builder.api.XingApi;
//import org.scribe.model.OAuthRequest;
//import org.scribe.model.Response;
//import org.scribe.model.Token;
//import org.scribe.model.Verb;
//import org.scribe.model.Verifier;
//import org.scribe.oauth.OAuthService;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mycompany.app.model.*;
//
///**
// * Hello world!
// *
// */
//public class AppAccessOnly 
//{
//	private static final String PROTECTED_RESOURCE_URL = "https://api.xing.com/v1/users/me";
//	private static final String GET_ID_EMAIL = "https://api.xing.com/v1/users/me.json?fields=id,active_email";
//    
//	public static void main( String[] args )
//	{
//		OAuthService service = new ServiceBuilder().
//				provider(XingApi.class)
//				.apiKey("891c8ed2c53864fb97c6")
//				.apiSecret("e3d5b2450ab6cd84e76c19286267e2e61038337e")
//				.build();
//		Scanner in = new Scanner(System.in);
//		ObjectOutputStream oos = null;
//		FileOutputStream fos = null;
//		ObjectInputStream ois = null;
//		FileInputStream fis = null;
//		Token readAccessToken = null;
//		
//		// looking for existing access token in file system
//		try {
//			fis = new FileInputStream("D:/test.ser");
//			ois = new ObjectInputStream(fis);
//			Object obj = ois.readObject();
//			if (obj instanceof Token) {
//				readAccessToken = (Token) obj;
//			}
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		finally {
//			if (ois != null)
//				try {
//					ois.close();
//				}
//				catch(IOException e) {}
//			if (fis != null)
//				try {
//					fis.close();
//				}
//				catch (IOException e) {}
//		}
//		if (readAccessToken != null) {
//			OAuthRequest request = new OAuthRequest(Verb.GET, GET_ID_EMAIL);
//			service.signRequest(readAccessToken, request);
//			Response response = request.send();
//			
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//			try {
//				UserArray users = mapper.readValue(response.getBody(), UserArray.class);
//				System.out.println("user_id:" + users.getUserArray()[0].getId());
//				System.out.println("active_email:" + users.getUserArray()[0].getActive_email());
//			} catch (JsonParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			System.out.println();
//			System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
//		}
//		else {
//		System.out.println("=== Xing's OAuth Workflow ===");
//		System.out.println();
//
//		// Obtain the Request Token
//		System.out.println("Fetching the Request Token...");
//		Token requestToken = service.getRequestToken();
//		System.out.println("Got the Request Token!");
//		System.out.println();
//		System.out.println("Now go and authorize Scribe here:");
//		System.out.println(service.getAuthorizationUrl(requestToken));
//		System.out.println("And paste the verifier here");
//		System.out.print(">>");
//		Verifier verifier = new Verifier(in.nextLine());
//		in.close();
//		System.out.println();
//
//		// Trade the Request Token and Verfier for the Access Token
//		System.out.println("Trading the Request Token for an Access Token...");
//		Token accessToken = service.getAccessToken(requestToken, verifier);
//		
//		System.out.println("Got the Access Token!");
//		System.out.println("(if your curious it looks like this: " + accessToken + " )");
//		System.out.println();
//		
//		// save access token
//		try {
//			fos = new FileOutputStream("D:/test.ser");
//			oos = new ObjectOutputStream(fos);
//			oos.writeObject(accessToken);
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally {
//			if (oos != null)
//				try {
//					oos.close(); 
//				}
//				catch (IOException ex) {}
//			if (fos != null)
//				try {
//					fos.close();
//				}
//				catch (IOException ex2) {}
//		}
//
//		// Now let's go and ask for a protected resource!
//		System.out.println("Now we're going to access a protected resource...");
//		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//		service.signRequest(accessToken, request);
//		Response response = request.send();
//		System.out.println("Got it! Lets see what we found...");
//		System.out.println();
//		System.out.println(response.getBody());
//		System.out.println();
//		System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
//		System.out.println( "End of first Request!" );
//    }
//	}
//}
