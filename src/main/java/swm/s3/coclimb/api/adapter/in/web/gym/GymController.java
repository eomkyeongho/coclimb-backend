package swm.s3.coclimb.api.adapter.in.web.gym;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swm.s3.coclimb.api.application.port.in.gym.GymCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymInfoResponseDto;
import swm.s3.coclimb.api.application.port.in.gym.GymQuery;

@RestController
@RequiredArgsConstructor
@Validated
public class GymController {
    private final GymCommand gymCommand;
    private final GymQuery gymQuery;

    @PostMapping("/gyms")
    public ResponseEntity<Void> createGym(@RequestBody @Valid GymCreateRequest request) {
        gymCommand.createGym(request.toServiceDto());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/gyms")
    public ResponseEntity<Void> removeGymByName(@RequestBody @Valid GymRemoveRequest request) {
        gymCommand.removeGymByName(request.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PatchMapping("/gyms")
    public ResponseEntity<Void> updateGym(@RequestBody @Valid GymUpdateRequest request) {
        gymCommand.updateGym(request.toServiceDto());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/gyms/{name}")
    public GymInfoResponseDto getGymInfoByName(@PathVariable @NotBlank(message = "조회할 암장 이름을 입력해주세요.") String name) {
        return gymQuery.getGymInfoByName(name);
    }
    @GetMapping("/gyms/locations")
    public GymLocationsResponse getGymLocations() {
        return GymLocationsResponse.of(gymQuery.getGymLocations());
    }
}
