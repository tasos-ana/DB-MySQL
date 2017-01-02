package util;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Giakoumis Giwrgos
 */
public class Cookies {

    private static Random rand = new Random(); // Seeded by current date/time
    private static HashMap<Integer, String> servletCookies = new HashMap<>();
    private static HashMap<Integer, String> userType = new HashMap<>();

    public static int countCookies() {
        return servletCookies.size();
    }

    public static int addCookie(String username, String type) {
        int value = rand.nextInt();

        while (servletCookies.containsKey(value)) {
            value = rand.nextInt();
        }
        servletCookies.put(value, username);
        userType.put(value, type);

        return value;
    }

    public static void removeCookie(String cookie) {
        if (cookie == null) {
            return;
        }

        int key = Integer.parseInt(cookie);
        servletCookies.remove(key);
        userType.remove(key);
    }

    public static String getCookieValue(String cookie) {
        if (cookie == null) {
            return null;
        }

        int key = Integer.parseInt(cookie);

        return servletCookies.get(key);
    }
    
    public static String getCookieType(String cookie) {
        if (cookie == null) {
            return null;
        }

        int key = Integer.parseInt(cookie);

        return userType.get(key);
    }

    public static String getRequestCookieValue(HttpServletRequest request,
            String cookieName,
            String defaultValue) {

        Cookie[] cookies = request.getCookies();//get all the cookies from request
        if (cookies != null) {
            for (Cookie cookie : cookies) {//for each cookie we check the name
                if (cookieName.equals(cookie.getName())) {//if it's equal with the recommended
                    return (cookie.getValue());//return the value
                }
            }
        }
        return defaultValue;
    }

    public static Cookie getRequestCookie(HttpServletRequest request,
            String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
