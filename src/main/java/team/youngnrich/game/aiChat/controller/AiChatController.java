package team.youngnrich.game.aiChat.controller;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.youngnrich.game.aiChat.dto.request.AiChatRequestDto;
import team.youngnrich.game.aiChat.service.AiChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiChatController {
    private final AiChatService aiChatService;

    @PostMapping
    public ResponseEntity<String> ask(Authentication authentication, @RequestBody AiChatRequestDto requestDto) throws ParseException {
        return ResponseEntity.ok().body(aiChatService.ask(authentication, requestDto));
    }
}
