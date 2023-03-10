package com.example.townmarket.annotation;


import com.example.townmarket.common.enums.RoleEnum;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomAdminSecurityContextFactory.class)
public @interface WithCustomMockAdmin {

  String username() default "adminName1";

  String password() default "Password1!";

  String nickname() default "nickname";

  RoleEnum role() default RoleEnum.TOP_MANAGER;

}
