package fr.pinguet62.jsfring.util.querydsl.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 553190547L;

    public static final QUser user = new QUser("user");

    public final BooleanPath active = createBoolean("active");

    public final StringPath email = createString("email");

    public final DateTimePath<java.util.Date> lastConnection = createDateTime("lastConnection", java.util.Date.class);

    public final StringPath login = createString("login");

    public final StringPath password = createString("password");

    public final SetPath<Profile, QProfile> profiles = this.<Profile, QProfile>createSet("profiles", Profile.class, QProfile.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

