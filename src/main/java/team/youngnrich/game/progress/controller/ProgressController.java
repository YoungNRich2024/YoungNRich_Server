package team.youngnrich.game.progress.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import team.youngnrich.game.progress.service.ProgressService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/progress")
public class ProgressController {
    private final ProgressService progressService;

    @GetMapping("/exist")
    public ProgressExistResponseDto checkIfExists (Authentication authentication) {
        return progressService.checkIfExists(authentication);
    }

    @GetMapping
    public ProgressResponseDto getMyProgress (Authentication authentication) {
        return progressService.getMyProgress(authentication);
    }

    @PostMapping("/puzzle")
    public ResponseEntity<String> puzzleSolved (Authentication authentication, PuzzleSovedRequestDto requestDto) {
        return progressService.puzzleSolved(authentication, requestDto);
    }

    @PostMapping("/keyget")
    public ResponseEntity<String> getKey (Authentication authentication) {
        return progressService.getKey(authentication);
    }

    @PostMapping("/keyuse")
    public ResponseEntity<String> useKey (Authentication authentication) {
        return progressService.useKey(authentication);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test (Authentication authentication, @RequestBody TestRequestDto requestDto) {
        return progressService.getKey(authentication, requestDto);
    }

    @PostMapping("/new")
    public ResponseEntity<String> newGame (Authentication authentication) {
        return progressService.newGame(authentication);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save (Authentication authentication, SaveRequestDto requestDto) {
        return progressService.save(authentication, requestDto);
    }
}
