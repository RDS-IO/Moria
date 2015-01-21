package security;

import controllers.routes;
import models.AuthorisedUser;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import be.objectify.deadbolt.java.AbstractDeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import be.objectify.deadbolt.core.models.Subject;

/**
 * 
 * @author C1mone
 *
 */
public class MoriaDeadBoltHandler extends AbstractDeadboltHandler  {
    private String sessionTimeOutKey = "TimeStamp";

    @Override
    public Promise<Result> beforeAuthCheck(Context ctx) {
//        If user is not in session, redirect to login page
        if(ctx.session().get("name") != null){
            return null;
        }else{
            return F.Promise.promise(new F.Function0<Result>(){
                @Override
                public Result apply() throws Throwable {
                    return redirect(routes.Application.calendar());
                }
            });
        }
    }

    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Context arg0) {
        return new MoriaDynamicResourceHandler();
    }

    @Override
    public F.Promise<Subject> getSubject(final Http.Context ctx) {

        return F.Promise.promise(new F.Function0<Subject>()
        {
            @Override
            public Subject apply() throws Throwable {
                String name = ctx.session().get("name");
                ctx.request().setUsername(name);
                AuthorisedUser user = AuthorisedUser.findByUserName(name);
                return user;
            }
        });
    }

    @Override
    public Promise<Result> onAuthFailure(final Context ctx, String arg1) {
        return F.Promise.promise(new F.Function0<Result>(){
//            If user name exist in session means not enough auth
            @Override
            public Result apply() throws Throwable {
                if(ctx.session().get("name")!=null)
                    return forbidden();
                else
                    return redirect(routes.Application.calendar());
            }
        });
    }
    /*
    private Subject verifySubjectByTimeStamp(Subject subject){
        if(isSessionTimeOut())
            return null;
        else{
            if(subject!=null)
                resetSessionTime();
            return subject;
        }

    }

    public Boolean isSessionTimeOut(){
        String sessionTimeStr = SessionUtil.getValue(sessionTimeOutKey);
        Long currentTime = new Date().getTime();
        if(sessionTimeStr!=null){
            Long sessionTime = Long.valueOf(sessionTimeStr);
            int diffInMins = (int) TimeUnit.MILLISECONDS.toMinutes(currentTime - sessionTime);
            if(diffInMins >=  AppConfig.sessionExpireTime) {
                SessionUtil.clearSession();
                System.out.println("Session time out" + diffInMins);
                return true;
            }
            System.out.println(diffInMins);
        }

        return false;
    }

    private void resetSessionTime(){
        Long currentTime = new Date().getTime();
        SessionUtil.putValue(sessionTimeOutKey,String.valueOf(currentTime));
    }*/

}
