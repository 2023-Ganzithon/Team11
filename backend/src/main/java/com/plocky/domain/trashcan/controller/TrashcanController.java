package com.plocky.domain.trashcan.controller;

import com.plocky.domain.trashcan.dto.TrashcanDto;
import com.plocky.domain.trashcan.dto.CreateTrashcanDto;
import com.plocky.domain.trashcan.service.TrashcanService;
import com.plocky.global.jwt.service.JwtService;
import com.plocky.global.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class TrashcanController {
    private final TrashcanService trashcanService;
    private final JwtService jwtService;

    @GetMapping("/trashcan")
    public List<TrashcanDto> getList(HttpServletRequest request, HttpServletResponse response){
        List<TrashcanDto> trashcanList = trashcanService.getList();
        response.setStatus(200);
        return trashcanList;
    }

    @PostMapping("/trashcan")
    public TrashcanDto createTrashcan(HttpServletRequest request, HttpServletResponse response,
                                      @RequestBody CreateTrashcanDto form) {
        TrashcanDto trashcanDto = trashcanService.create(form);
        response.setStatus(201);
        return trashcanDto;
    }

    @DeleteMapping("/trashcan")
    public void deleteTrashcan(@RequestParam(name="trashcanId") Long id, HttpServletRequest request, HttpServletResponse response) {
        trashcanService.delete(id);
        response.setStatus(204);
    }
}
