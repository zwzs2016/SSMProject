package com.bamboo.service.feign;

import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.entity.MusicInfo;
import com.uwan.common.entity.out.ResponseEntityResult;
import com.uwan.common.entity.out.RestPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "search-server",path = "musicinfo")
public interface MusicInfoFeignService {
    @PostMapping("query/{search}/{page}")
    public ResponseEntity<List<MusicInfo>> query(@PathVariable(name = "search") String search, @PathVariable(name = "page") String page);

//    @PostMapping("add")
//    public ResponseEntity musicInfo(@RequestBody @Validated MusicInfoDTO musicInfoDTO);

    @GetMapping("ribbon")
    public ResponseEntity<ResponseEntityResult> ribbon();
}
