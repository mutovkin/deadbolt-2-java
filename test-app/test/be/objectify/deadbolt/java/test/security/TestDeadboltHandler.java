package be.objectify.deadbolt.java.test.security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.test.models.User;
import play.libs.F;
import play.mvc.Http;

/**
 * Extends the project's default DeadboltHandler to get the subject based on a cookie value - good for testing ONLY!
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class TestDeadboltHandler extends MyDeadboltHandler
{
    @Override
    public F.Promise<Subject> getSubject(Http.Context context)
    {
        final Http.Cookie userCookie = context.request().cookie("user");
        return F.Promise.promise(new F.Function0<Subject>()
        {
            @Override
            public Subject apply() throws Throwable
            {
                return User.findByUserName(userCookie.value());
            }
        });
    }
}
