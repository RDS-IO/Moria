package security;

import play.mvc.Http.Context;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;

public class AbstractDynamicResourceHandler implements DynamicResourceHandler {


    @Override
    public boolean isAllowed(String s, String s2, DeadboltHandler deadboltHandler, Context context) {
        return false;
    }

    @Override
    public boolean checkPermission(String s, DeadboltHandler deadboltHandler, Context context) {
        return false;
    }
}
