package com.plocky.domain.pet.service;

import com.plocky.domain.member.entity.Member;
import com.plocky.domain.pet.entity.Pet;
import com.plocky.domain.pet.entity.PetKind;
import com.plocky.domain.pet.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    public Pet createPet(Member member) {
        Pet pet = Pet.builder()
                .petName(member.getNickname())
                .petKind(PetKind.PLOCKY)
                .level(0) //
                .member(member)
                .build();
        return petRepository.save(pet);
    }
}
