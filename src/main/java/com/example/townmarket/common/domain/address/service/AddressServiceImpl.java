package com.example.townmarket.common.domain.address.service;

import com.example.townmarket.common.domain.address.BaseUtility;
import com.example.townmarket.common.domain.address.dto.AddressResponseDto;
import com.example.townmarket.common.domain.address.dto.KaKaoMapResponse;
import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.address.repository.AddressRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  @Value("${kakaoAk.key}")
  private String authorization_key;

  private final RestTemplate restTemplate;

  private final BaseUtility utility;

  private final AddressRepository addressRepository;

  Gson gson = new Gson();

  @Override
  @Transactional
  public AddressResponseDto getAddress(double x, double y, User user) {

    String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json";

    UriComponents uri = UriComponentsBuilder.newInstance()
        .fromHttpUrl(url)
        .queryParam("x", x)
        .queryParam("y", y)
        .build();

    HttpHeaders httpHeaders = utility.getDefaultHeader();
    httpHeaders.add("Authorization", String.format("KakaoAK %s", authorization_key));

    HttpEntity requestMessage = new HttpEntity(httpHeaders);
    ResponseEntity<String> response = restTemplate.exchange(
        uri.toUriString(),
        HttpMethod.GET,
        requestMessage,
        String.class);

    KaKaoMapResponse mapped_data = gson.fromJson(response.getBody(), KaKaoMapResponse.class);
    String target = mapped_data.documents.get(0).region_1depth_name;
    String target2 = mapped_data.documents.get(0).region_2depth_name;
    String target3 = mapped_data.documents.get(0).region_3depth_name;

    Address address = addressRepository.findByUser(user);

    address.updateAddress(target, target2, target3);
    return new AddressResponseDto(address);
  }

  @Override
  public AddressResponseDto getAddressSingUp(double x, double y) {

    String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json";

    UriComponents uri = UriComponentsBuilder.newInstance()
        .fromHttpUrl(url)
        .queryParam("x", x)
        .queryParam("y", y)
        .build();

    HttpHeaders httpHeaders = utility.getDefaultHeader();
    httpHeaders.add("Authorization", String.format("KakaoAK %s", authorization_key));

    HttpEntity requestMessage = new HttpEntity(httpHeaders);
    ResponseEntity<String> response = restTemplate.exchange(
        uri.toUriString(),
        HttpMethod.GET,
        requestMessage,
        String.class);

    KaKaoMapResponse mapped_data = gson.fromJson(response.getBody(), KaKaoMapResponse.class);
    String target = mapped_data.documents.get(0).region_1depth_name;
    String target2 = mapped_data.documents.get(0).region_2depth_name;
    String target3 = mapped_data.documents.get(0).region_3depth_name;

    return AddressResponseDto.builder().address(target).address2(target2).address3(target3).build();
  }

  @Override
  public void addressSave(Address address) {
    addressRepository.save(address);
  }
}

