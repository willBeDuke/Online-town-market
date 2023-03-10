package com.example.townmarket.common.enums;


public enum RoleEnum {
  TOP_MANAGER(Authority.TOP_MANAGER),
  MIDDLE_MANAGER(Authority.MIDDLE_MANAGER),
  MEMBER(Authority.MEMBER);

  private final String authority;

  RoleEnum(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }

  public static class Authority {

    public static final String TOP_MANAGER = "ROLE_TOP_MANAGER";
    public static final String MIDDLE_MANAGER = "ROLE_MIDDLE_MANAGER";
    public static final String MEMBER = "ROLE_MEMBER";
  }
}
