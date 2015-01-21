package models;

import be.objectify.deadbolt.core.models.Permission;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by C1mone on 1/21/15.
 */
@Entity
public class UserPermission extends Model implements Permission {
    @Id
    public Long id;

    @Column(name = "permission_value")
    public String value;

    public static final Model.Finder<Long, UserPermission> find = new Model.Finder<Long, UserPermission>(Long.class,
            UserPermission.class);

    public String getValue()
    {
        return value;
    }

    public static UserPermission findByValue(String value)
    {
        return find.where()
                .eq("value",
                        value)
                .findUnique();
    }
}
