package com.paranmanzang.roomservice.controller;

import com.paranmanzang.roomservice.model.domain.AddressModel;
import com.paranmanzang.roomservice.model.domain.AddressUpdateModel;
import com.paranmanzang.roomservice.service.impl.AddressServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "05. Address")
@RequestMapping("/api/rooms/addresses")
public class AddressController {

    private final AddressServiceImpl addressService;

    @GetMapping("/search")
    @Operation(summary = "검색 조회", description = "검색어에 해당하는 주소 정보를 최대 5개까지 조회합니다.", tags = {"05. Address",})
    public ResponseEntity<?> search(@RequestParam("query") String query){
        return ResponseEntity.ok(addressService.search(query));

    }

    @PostMapping("")
    @Operation(summary = "주소 등록", description = "주소를 db에 저장합니다.")
    public ResponseEntity<?> insert(@Valid @RequestBody AddressModel addressModel, BindingResult result) throws BindException {
        if (result.hasErrors()) throw new BindException(result);
        return ResponseEntity.ok(addressService.insert(addressModel));
    }

    @PutMapping("")
    @Operation(summary = "주소 수정", description = "주소를 수정합니다.")
    public ResponseEntity<?> update(@Valid @RequestBody AddressUpdateModel addressModel, BindingResult result) throws BindException{
        if(result.hasErrors()) throw new BindException(result);
        return ResponseEntity.ok(addressService.update(addressModel));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "주소 삭제", description = "id 값을 기준으로 주소정보를 삭제합니다.")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(addressService.delete(id));
    }
    @GetMapping("")
    @Operation(summary = "전체 주소 조회", description = "존재하는 모든 주소정보를 조회합니다.")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/{query}")
    @Operation(summary = "db내 검색", description = "주소 기반으로 정보를 조회합니다.")
    public ResponseEntity<?> findByQuery(@PathVariable("query") String query){
        return ResponseEntity.ok(addressService.findByQuery(query));
    }
}
