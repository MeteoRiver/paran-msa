package com.paranmanzang.roomservice.service.impl;

import com.paranmanzang.roomservice.model.domain.AddressModel;
import com.paranmanzang.roomservice.model.domain.AddressUpdateModel;
import com.paranmanzang.roomservice.model.entity.Address;
import com.paranmanzang.roomservice.model.repository.AddressRepository;
import com.paranmanzang.roomservice.service.AddressService;
import com.paranmanzang.roomservice.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final RoomServiceImpl roomService;
    private final Converter converter;

    private final String Client_Id="";
    private final String Client_Secret_Key= "";

    @Override
    public String search(String query) {
        return Objects.requireNonNull(WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Naver-Client-Id", Client_Id)
                .defaultHeader("X-Naver-Client-Secret", Client_Secret_Key)
                .build().get()
                .uri(UriComponentsBuilder
                        .fromUriString("https://openapi.naver.com")
                        .path("/v1/search/local.json")
                        .queryParam("query",query)
                        .queryParam("display",5)
                        .queryParam("start",1)
                        .queryParam("sort","random")
                        .encode(StandardCharsets.UTF_8)
                        .build()
                        .toUri())
                .retrieve()
                .toEntity(String.class)
                .block()).getBody();
    }

    @Override
    public AddressModel insert(AddressModel model) {
        return converter.convertTonAddressModel(
                addressRepository.save(Address.builder()
                        .address(model.getAddress())
                        .longitude(model.getLongitude())
                        .latitude(model.getLatitude())
                        .detailAddress(model.getDetailAddress())
                        .roomId(model.getRoomId())
                        .build()));
    }

    @Override
    public AddressModel update(AddressUpdateModel model) {
        return addressRepository.findById(model.getId()).map(address -> {
                    address.setAddress(model.getAddress());
                    address.setDetailAddress(model.getDetailAddress());
                    address.setLongitude(model.getLongitude());
                    address.setLatitude(model.getLatitude());
                    return addressRepository.save(address);
                })
                .map(converter::convertTonAddressModel)
                .orElse(null);
    }

    @Override
    public Boolean delete(Long id) {
        addressRepository.delete(Address.builder().id(id).build());
        return addressRepository.findById(id).isEmpty();
    }

    @Override
    public AddressModel findById(Long id) {
        return addressRepository.findById(id)
                .map(converter::convertTonAddressModel)
                .orElse(null);
    }

    @Override
    public List<?> findAll() {
        return addressRepository.findEnabledRoom(roomService.getIdAllEnabled()).stream()
                .map(converter::convertTonAddressModel).toList();
    }

    @Override
    public List<?> findByQuery(String query) {
        return addressRepository.findQuery(query, roomService.getIdAllEnabled()).stream()
                .map(converter::convertTonAddressModel).toList();
    }
}
