package team.youngnrich.game.aiChat.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import team.youngnrich.game.aiChat.dto.request.AiChatRequestDto;

@Service
@RequiredArgsConstructor
public class AiChatService {
    private static String API_KEY;
    private String modelId = "gpt-3.5-turbo";
    private String url = "https://api.openai.com/v1/chat/completions";
    private RestTemplate restTemplate = new RestTemplate();

    public String ask(Authentication authentication, AiChatRequestDto requestDto) throws ParseException {
        String question =
                "너는 저명한 경제학자의 서재에 걸린 초상화 속에 갇힌 사람이야. 경제와 금융에 대해 해박한 지식을 가지고 있어. 언제나 만성피로에 시달리고, 알이 두꺼운 안경과 학사모를 쓰고 있으며, 곱슬거리는 짧은 머리카락을 가졌고, 진한 다크써클을 가지고 있어. 오늘 한 청년이 서재에 방문했어. 그래서 너는 서재에 방문한 한 청년의 질문에 답해주는 일을 하게 되었어. 너는 매우 피곤한 상태이지만, 최대한 친절하고 친근한 말투로 답변해주려고 노력해. 이제부터 청년이 너에게 경제 용어의 정의에 대해 질문할 거야. 청년은 다음과 같은 6개의 용어들 중 하나를 골라 질문할 거야: MMF, CMA, 채권형 펀드, 적립식 펀드, 주식형 펀드, ELS. 답변에는 해당 상품의 위험도가 꼭 포함되어야 해. 답변은 300자 이내여야 하고, 존댓말이어야 해. 실제 사람과 대화하는 느낌이 들도록, 감정을 실어 연기를 하며 답해줘. 이제 질문을 들려줄 테니 즉시 그 질문에 답해: \"" +
                        requestDto.getKeyword() + "는 무슨 뜻인가요?\"";
        return getAnswer(question);
    }

    public String getAnswer(String input) throws ParseException {

        System.out.println("######### 질문: " + input);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // Chat GPT API에 보낼 요청 Body 작성
        String requestBody = "{\"model\": \"" + modelId
                + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + input + "\"}]}";

        // Body를 JSON으로 바꾸기
        JSONParser jsonParser = new JSONParser();

        // 요청 생성
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Chat GPT API의 응답 내용을 문자열로 바꾸어 리턴
        String result =  restTemplate.postForObject(url, request, String.class);

        JSONObject resultJson = (JSONObject) jsonParser.parse(result);

        JSONArray choices = (JSONArray) resultJson.get("choices");

        JSONObject choice = (JSONObject) choices.get(0);

        JSONObject message =  (JSONObject) choice.get("message");

        return message.get("content").toString();

    }

    @Component
    public static class AiChatServicePropertyInitializer {
        @Autowired
        public AiChatServicePropertyInitializer(Environment environment) {
            API_KEY = environment.getProperty("gpt.api-key");
        }
    }


}
