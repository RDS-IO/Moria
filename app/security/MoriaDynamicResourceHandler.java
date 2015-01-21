package security;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import be.objectify.deadbolt.core.DeadboltAnalyzer;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;

public class MoriaDynamicResourceHandler implements DynamicResourceHandler {

    private static final Map<String, DynamicResourceHandler> HANDLERS = new HashMap<String, DynamicResourceHandler>();

    static {
        HANDLERS.put("pureLuck", new AbstractDynamicResourceHandler() {
            public boolean isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context context) {
                return System.currentTimeMillis() % 2 == 0;
            }
        });
        HANDLERS.put("edit", new AbstractDynamicResourceHandler() {
            public boolean isAllowed(final String name, final String meta, final DeadboltHandler deadboltHandler, final Http.Context ctx) {
                return deadboltHandler.getSubject(ctx).map(
                        new F.Function<Subject, Boolean>() {
                            @Override
                            public Boolean apply(Subject subject) throws Throwable{
                                boolean allowed;
                                if (DeadboltAnalyzer.hasRole(subject, "SystemAdmin")) {
                                    allowed = true;
                                } else {
                                    Map<String, String[]> queryStrings = ctx.request().queryString();
                                    String[] requestedNames = queryStrings.get("role");
                                    allowed = requestedNames != null && requestedNames.length == 1
                                            && requestedNames[0].equals(subject.getIdentifier());

                                }
                                return allowed;
                            }
                        }
                ).get(1, TimeUnit.SECONDS);
            }
        });
    }

    public boolean isAllowed(String name, String meta, DeadboltHandler deadboltHandler, Http.Context context) {
        DynamicResourceHandler handler = HANDLERS.get(name);
        boolean result = false;
        if (handler == null) {
            Logger.error("No handler available for " + name);
        } else {
            result = handler.isAllowed(name, meta, deadboltHandler, context);
        }
        return result;
    }

    public boolean checkPermission(final String permissionValue, final DeadboltHandler deadboltHandler, final Http.Context ctx) {
        return deadboltHandler.getSubject(ctx).map(
                new F.Function<Subject, Boolean>() {
                    @Override
                    public Boolean apply(Subject subject) throws Throwable{
                        boolean permissionOk = false;
                        if (subject != null) {
                            List<? extends Permission> permissions = subject.getPermissions();
                            for (Iterator<? extends Permission> iterator = permissions.iterator(); !permissionOk && iterator.hasNext();) {
                                Permission permission = iterator.next();
                                permissionOk = permission.getValue().contains(permissionValue);
                            }
                        }
                        return permissionOk;
                    }
                }
        ).get(1, TimeUnit.SECONDS);
    }

}
