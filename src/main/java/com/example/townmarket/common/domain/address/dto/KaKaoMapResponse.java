package com.example.townmarket.common.domain.address.dto;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KaKaoMapResponse {
  public Meta meta;
  public ArrayList<Document> documents;

  public class Document{
    public String region_type;
    public String code;
    public String address_name;
    public String region_1depth_name;
    public String region_2depth_name;
    public String region_3depth_name;
    public String region_4depth_name;
    public double x;
    public double y;
  }

  public class Meta{
    public int total_count;
  }
}