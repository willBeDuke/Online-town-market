package com.example.townmarket.common.oauth.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthDto {
  private Map<String, Object> attributes;
  private String attributeKey;
  private String email;
  private String name;
  private String picture;


  public static OAuthDto ofGoogle(String attributeKey, Map<String, Object> attributes) {
    return OAuthDto.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String)attributes.get("picture"))
        .attributes(attributes)
        .attributeKey(attributeKey)
        .build();
  }


  public Map<String, Object> convertToMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", attributeKey);
    map.put("key", attributeKey);
    map.put("name", name);
    map.put("email", email);
    map.put("picture", picture);

    return map;
  }
}
