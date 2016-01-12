package fr.pinguet62.jsfring.util.querydsl.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRight is a Querydsl query type for Right
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRight extends EntityPathBase<Right> {

    private static final long serialVersionUID = -34028972L;

    public static final QRight right_ = new QRight("right_");

    public final StringPath code = createString("code");

    public final SetPath<Profile, QProfile> profiles = this.<Profile, QProfile>createSet("profiles", Profile.class, QProfile.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QRight(String variable) {
        super(Right.class, forVariable(variable));
    }

    public QRight(Path<? extends Right> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRight(PathMetadata<?> metadata) {
        super(Right.class, metadata);
    }

}

