package fr.pinguet62.jsfring.util.querydsl.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProfile is a Querydsl query type for Profile
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProfile extends EntityPathBase<Profile> {

    private static final long serialVersionUID = 147872737L;

    public static final QProfile profile = new QProfile("profile");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final SetPath<Right, QRight> rights = this.<Right, QRight>createSet("rights", Right.class, QRight.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final SetPath<User, QUser> users = this.<User, QUser>createSet("users", User.class, QUser.class, PathInits.DIRECT2);

    public QProfile(String variable) {
        super(Profile.class, forVariable(variable));
    }

    public QProfile(Path<? extends Profile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfile(PathMetadata<?> metadata) {
        super(Profile.class, metadata);
    }

}

