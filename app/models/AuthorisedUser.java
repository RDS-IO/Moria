package models;
import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
/**
 * Created by C1mone on 1/21/15.
 */
@Entity
public class AuthorisedUser extends Model implements Subject {
    @Id
    public Long id;

    public String userName;

    @ManyToMany
    public List<SecurityRole> roles;

    @ManyToMany
    public List<UserPermission> permissions;

    public static final Finder<Long, AuthorisedUser> find = new Finder<Long, AuthorisedUser>(Long.class,
            AuthorisedUser.class);

    @Override
    public List<? extends Role> getRoles()
    {
        return roles;
    }

    @Override
    public List<? extends Permission> getPermissions()
    {
        return permissions;
    }

    @Override
    public String getIdentifier()
    {
        return userName;
    }

    public static AuthorisedUser findByUserName(String userName)
    {
        return find.where()
                .eq("userName",
                        userName)
                .findUnique();
    }
}
