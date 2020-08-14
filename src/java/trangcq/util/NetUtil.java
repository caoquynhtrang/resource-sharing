/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.util;

import java.io.IOException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author USER
 */
public class NetUtil {

    public static String get(String url) throws IOException {
        String response = Request.Get(url)
                .execute().returnContent().asString();

        return response;
    }

    public static String post(String url, Form form) throws IOException {
        //form la 1 Http Form, gom cac cap (name, value)
        String response = Request.Post(url)
                .bodyForm(form.build())
                .execute().returnContent().asString();

        return response;
    }
}
