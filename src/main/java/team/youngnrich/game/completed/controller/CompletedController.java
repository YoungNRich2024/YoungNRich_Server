package team.youngnrich.game.completed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.youngnrich.game.completed.dto.request.ClearRequestDto;
import team.youngnrich.game.completed.dto.response.RecordListResponseDto;
import team.youngnrich.game.completed.service.CompletedService;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completed")
public class CompletedController {
    private final CompletedService completedService;

    @PostMapping
    public ResponseEntity<String> clear (Authentication authentication, @RequestBody ClearRequestDto requestDto) throws AuthenticationException {
        return ResponseEntity.ok().body(completedService.clear(authentication, requestDto));
    }

    @GetMapping("/myrecord")
    public RecordListResponseDto getMyRecord (Authentication authentication) {
        return completedService.getMyRecord(authentication);
    }
}