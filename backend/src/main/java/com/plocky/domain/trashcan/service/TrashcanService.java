package com.plocky.domain.trashcan.service;

import com.plocky.domain.member.entity.Member;
import com.plocky.domain.member.repository.MemberRepository;
import com.plocky.domain.trashcan.dto.CreateTrashcanDto;
import com.plocky.domain.trashcan.dto.TrashcanDto;
import com.plocky.domain.trashcan.entity.Trashcan;
import com.plocky.domain.trashcan.repository.TrashcanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrashcanService {
    private final TrashcanRepository trashcanRepository;
    private final MemberRepository memberRepository;

    public List<TrashcanDto> getList() {
        return trashcanRepository.findAll()
                .stream()
                .map(trashcan -> {
                    return new TrashcanDto(trashcan.getId(), trashcan.getLatitude(), trashcan.getLongitude());
                })
                .collect(Collectors.toList());
    }

    public TrashcanDto create(CreateTrashcanDto form) {
        Trashcan trashcan = Trashcan.builder()
                .latitude(form.getLatitude())
                .longitude(form.getLongitude())
                .build();
        Trashcan newTrashcan = trashcanRepository.save(trashcan);
        return new TrashcanDto(newTrashcan.getId(), newTrashcan.getLatitude(), newTrashcan.getLongitude());
    }

    public void delete(Long id) {
        Trashcan trashcan = trashcanRepository.findById(id).orElseThrow(
                () -> new NullPointerException("TrashCan not found: " + id));
        trashcanRepository.delete(trashcan);
    }
}
