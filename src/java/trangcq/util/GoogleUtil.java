/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.fluent.Form;
import trangcq.registration.RegistrationDTO;

/**
 *
 * @author USER
 */
public class GoogleUtil {

//    private static final String url = "http://localhost:8084/Lab03_ResourceSharing/googleLogin";
    private static final String url = "https://managing-resources-sharing.herokuapp.com/googleLogin";
    private static final String clientID = "466618921968-f3jf6dg7ov4aupnml43mkpndskeekckb.apps.googleusercontent.com";
    private static final String clientSecret = "ERu48lSuX5yskb1lbxUfSNKm";
    
    public String getRedirect() {

        String redirectLink = "https://accounts.google.com/o/oauth2/auth"
                + "?scope=email profile"
                + "&redirect_uri=" + url
                + "&response_type=code"
                + "&client_id=" + clientID;

        return redirectLink;
    }
    
    public static String getToken( String code) throws IOException {
        String linkGetToken = "https://accounts.google.com/o/oauth2/token";
        Form form = Form.form()
                .add("client_id", clientID)
                .add("client_secret", clientSecret)
                .add("redirect_uri", url)
                .add("code", code)
                .add("grant_type", "authorization_code");
        String response = NetUtil.post(linkGetToken, form);
       
        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        String accessToken = json.get("access_token").toString().replace("\"", "");
        return accessToken;
    }
    
    public static RegistrationDTO getUserInfor(String accessToken) throws IOException {
        String linkGetUserInfo = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
        
        String response = NetUtil.get(linkGetUserInfo);
        
        RegistrationDTO userDTO = new Gson().fromJson(response, RegistrationDTO.class);
        
        return userDTO;
    }
}
