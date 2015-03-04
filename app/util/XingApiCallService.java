package util;

import java.io.IOException;
import java.util.Iterator;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.XingApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import play.Logger;

import com.google.gson.Gson;

import models.JsonContactsWrapper;
import models.JsonUsers;
import models.User;
import models.XingContact;

public class XingApiCallService {

	// TODO make configurable
	private static final String GET_ID_EMAIL = "https://api.xing.com/v1/users/me.json?fields=id,active_email";
	private static final String GET_CONTACTS = "https://api.xing.com/v1/users/me/contacts.json?user_fields=id,first_name,last_name,display_name,permalink";
	private static final OAuthService service;
	private static Token requestToken = null;
	
	static {
		// TODO make configurable
		service = (OAuthService) new ServiceBuilder().
				provider(XingApi.class)
				.apiKey("891c8ed2c53864fb97c6")
				.apiSecret("e3d5b2450ab6cd84e76c19286267e2e61038337e")
				.callback("http://localhost:9000/contactsview/verifier")
				.build();
	}
	
	/**
	 * Send Api request for users active_email and xing id
	 * @param users access token
	 * @return User with active_email and xing id from response
	 * @throws IOException 
	 */
	public static User getUserWithActiveMailAndId(Token token) throws IOException {
		try {
			OAuthRequest request = new OAuthRequest(Verb.GET, GET_ID_EMAIL);
			service.signRequest(token, request);
			Response response = request.send();
			Gson gson = new Gson();
			JsonUsers helpUsers = gson.fromJson(response.getBody(), JsonUsers.class);
			return helpUsers.usersList.get(0);
		} catch (Exception e){
			Logger.debug("Error :", e);
			throw new IOException(e);
		}
	}
	/**
	 * Send Api request for users xing contacts
	 * @param user access token 
	 * @return JsonContactsWrapper
	 * @throws IOException 
	 */
	public static JsonContactsWrapper getJsonContactsWrapperForToken (Token token) throws IOException {
		try {
			OAuthRequest request = new OAuthRequest(Verb.GET, GET_CONTACTS);
			service.signRequest(token, request);

			//checken ob response NICHT null, header auswerten, wegen rate limits... 
			Response response = request.send();

			Gson gson = new Gson();
			return gson.fromJson(
					response.getBody(), JsonContactsWrapper.class);

		} catch (Exception e) {
			Logger.debug("Error :", e);
			throw new IOException(e);
		}
	}
	/**
	 * 
	 * @return the Url for the server authentication page
	 */
	public static String getAuthorizationUrl() {
		return service.getAuthorizationUrl( getRequestToken());
	}

	public static Token getRequestToken() {
		if ( this.requestToken == null ) {
			this.requestToken = service.getRequestToken();
			return this.requestToken;
		}
		else return this.requestToken;
	}

	public static Token getAccessToken( Verifier verifier ) {
		return service.getAccessToken( getRequestToken(), verifier );
	}
	
}
