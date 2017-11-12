package fr.pinguet62.jsfring.util.querydsl.model;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QRight is a Querydsl query type for Right
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRight extends EntityPathBase<Right> {

    private static final long serialVersionUID = -199528908L;

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

    public QRight(PathMetadata metadata) {
        super(Right.class, metadata);
    }

}