package common;

import play.mvc.Http.Context;
import play.mvc.Http.Session;

public class SessionUtil {

    public static String getValue(String key){
        Session session = Context.current().session();
        return session.get(key);
    }
    
    public static void putValue(String key, String value){
        Session session = Context.current().session();
        session.put(key, value);
    }

    public static void clearSession(){
        Session session = Context.current().session();
        session.clear();
    }
}
