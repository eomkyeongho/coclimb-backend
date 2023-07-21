package swm.s3.coclimb.api.adapter.in.web.gym;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swm.s3.coclimb.api.ApiResponse;
import swm.s3.coclimb.api.adapter.in.web.gym.dto.*;
import swm.s3.coclimb.api.application.port.in.gym.GymCommand;
import swm.s3.coclimb.api.application.port.in.gym.GymQuery;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymInfoResponseDto;
import swm.s3.coclimb.api.application.port.in.gym.dto.GymPageRequestDto;
import swm.s3.coclimb.api.exception.errortype.ValidationFail;
import swm.s3.coclimb.domain.Gym;

@RestController
@RequiredArgsConstructor
public class GymController {
    private final GymCommand gymCommand;
    private final GymQuery gymQuery;

    @PostMapping("/gyms")
    public ResponseEntity<ApiResponse<Void>> createGym(@RequestBody @Valid GymCreateRequest request) {
        gymCommand.createGym(request.toServiceDto());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("새로운 암장이 등록되었습니다."));
    }

    @DeleteMapping("/gyms")
    public ResponseEntity<ApiResponse<Void>> removeGymByName(@RequestBody @Valid GymRemoveRequest request) {
        gymCommand.removeGymByName(request.getName());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.noContent("암장이름을 제외한 암장 관련 정보가 제거되었습니다."));
    }
    @PatchMapping("/gyms")
    public ResponseEntity<ApiResponse<Void>> updateGym(@RequestBody @Valid GymUpdateRequest request) {
        gymCommand.updateGym(request.toServiceDto());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.noContent("암장 정보가 수정되었습니다."));
    }

    @GetMapping("/gyms/{name}")
    public ApiResponse<GymInfoResponseDto> getGymInfoByName(@PathVariable String name) {
        if(name.isBlank()){
            throw ValidationFail.onRequest()
                    .addField("name", "암장 이름은 공백일 수 없습니다.");
        }
        return ApiResponse.ok(gymQuery.getGymInfoByName(name));
    }
    @GetMapping("/gyms/locations")
    public ApiResponse<GymLocationsResponse> getGymLocations() {
        return ApiResponse.ok(GymLocationsResponse.of(gymQuery.getGymLocations()));
    }

    @GetMapping("/gyms")
    public ApiResponse<GymPageResponse> getPagedGyms(@RequestParam(required = false, defaultValue = "0") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int size) {
        if (page < 0) {
            throw ValidationFail.onRequest()
                    .addField("page", "조회할 페이지 번호는 0이상의 정수여야 합니다.");
        }
        Page<Gym> pagedGyms = gymQuery.getPagedGyms(GymPageRequestDto.builder()
                .page(page)
                .size(size)
                .build());
        return ApiResponse.ok(GymPageResponse.of(pagedGyms));
    }
}
