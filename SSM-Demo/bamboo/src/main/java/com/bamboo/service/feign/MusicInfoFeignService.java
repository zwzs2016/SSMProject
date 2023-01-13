package com.bamboo.service.feign;

import com.uwan.common.dto.MusicInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("search-server")
public interface MusicInfoFeignService {
//    @PostMapping("musicinfo/query/{search}")
//    public ResponseEntity query(@PathVariable(name = "search") String search);
//
//    @PostMapping("add")
//    public ResponseEntity musicInfo(@RequestBody @Validated MusicInfoDTO musicInfoDTO);
}
