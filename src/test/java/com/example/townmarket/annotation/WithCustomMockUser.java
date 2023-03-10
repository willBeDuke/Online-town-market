package com.example.townmarket.annotation;


import com.example.townmarket.common.enums.RoleEnum;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithCustomMockUser {

  String username() default "username1";

  String password() default "Password1!";

  String nickname() default "nickname";

  RoleEnum role() default RoleEnum.MEMBER;

}
