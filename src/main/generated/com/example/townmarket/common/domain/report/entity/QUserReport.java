package com.example.townmarket.common.domain.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserReport is a Querydsl query type for UserReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserReport extends EntityPathBase<UserReport> {

    private static final long serialVersionUID = 1451667078L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserReport userReport = new QUserReport("userReport");

    public final com.example.townmarket.common.QTimeStamped _super = new com.example.townmarket.common.QTimeStamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath reason = createString("reason");

    public final com.example.townmarket.common.domain.user.entity.QUser reportedUser;

    public final EnumPath<UserReport.ReportEnum> reportEnum = createEnum("reportEnum", UserReport.ReportEnum.class);

    public final NumberPath<Long> reporterId = createNumber("reporterId", Long.class);

    public QUserReport(String variable) {
        this(UserReport.class, forVariable(variable), INITS);
    }

    public QUserReport(Path<? extends UserReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserReport(PathMetadata metadata, PathInits inits) {
        this(UserReport.class, metadata, inits);
    }

    public QUserReport(Class<? extends UserReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reportedUser = inits.isInitialized("reportedUser") ? new com.example.townmarket.common.domain.user.entity.QUser(forProperty("reportedUser"), inits.get("reportedUser")) : null;
    }

}

