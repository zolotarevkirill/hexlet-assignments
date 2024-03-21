package exercise.controller;

import exercise.mapper.GuestMapper;
import exercise.model.Guest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.GuestRepository;
import exercise.dto.GuestDTO;
import exercise.dto.GuestCreateDTO;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/guests")
public class GuestsController {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    @GetMapping(path = "")
    public List<GuestDTO> index() {
        var products = guestRepository.findAll();
        return products.stream()
                .map(p -> guestMapper.map(p))
                .toList();
    }

    @GetMapping(path = "/{id}")
    public GuestDTO show(@PathVariable long id) {

        var guest =  guestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Guest with id " + id + " not found"));
        var guestDto = guestMapper.map(guest);
        return guestDto;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDTO create(@RequestBody GuestCreateDTO guestData) {
        var guest = toEntity(guestData);
        guestRepository.save(guest);
        var guestDTO = toDTO(guest);
        return guestDTO;
    }


    private Guest toEntity(GuestCreateDTO guestDTO){
        var guest = new Guest();
        guest.setName(guestDTO.getName());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());
        guest.setClubCard(guestDTO.getClubCard());
        guest.setCardValidUntil(guestDTO.getCardValidUntil());
        return guest;
    }


    private GuestDTO toDTO(Guest guest) {
        var dto = new GuestDTO();
        dto.setId(guest.getId());
        dto.setName(guest.getName());
        dto.setEmail(guest.getEmail());
        dto.setPhoneNumber(guest.getPhoneNumber());
        dto.setClubCard(guest.getClubCard());
        dto.setCardValidUntil(guest.getCardValidUntil());
        dto.setCreatedAt(guest.getCreatedAt());
        return dto;
    }
}
