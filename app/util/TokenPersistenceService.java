package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import play.Logger;
import models.User;

public class TokenPersistenceService {
	
	private static final String TOKEN_PATH = "C:/ximeko/userKeys/%EMAIL%Token.ser";	
	
	/**
	 * looking for existing access token in file system
	 * @param user
	 * @return Token of user or null if no token exists
	 */
	public Token getTokenForUser(User user) throws IOException {

		String path = TOKEN_PATH.replace("%EMAIL%", user.email);
		try ( FileInputStream  fis = new FileInputStream(path); ObjectInputStream ois = new ObjectInputStream(fis) ) {
			
			Object obj = ois.readObject();
			if (obj instanceof Token) {
				return (Token) obj;
			}
			
			throw new IOException("Invalid content for token: "+obj);
		}
		catch (FileNotFoundException e) {
			Logger.debug("Token file not found", path);
			return null;
		}
		catch (ClassNotFoundException e) {
			Logger.debug("Error:", e);
			throw new IOException(e);
		}
	}
	
	
	/**
	 * Saves token of given user in file system
	 * @throws IOException
	 */
	public void saveTokenForUser(User user, Token token) throws IOException {
		String path = TOKEN_PATH.replace("%EMAIL%", user.email);
		try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(token);
		}
	}

}
