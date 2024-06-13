package team.youngnrich.game.oauth.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import team.youngnrich.game.oauth.dto.KakaoUserInfoResponseDto;

@Service
public class KakaoLoginService {
    private RestTemplate restTemplate = new RestTemplate();

    private final String REQUEST_URL_FOR_ACCESSTOKEN = "https://kauth.kakao.com/oauth/token";
    private final String REQUEST_URL_FOR_USERINFO = "https://kapi.kakao.com/v2/user/me";
    private static String API_KEY;
    private static String REDIRECT_URI;

    @Component
    public static class KakaoLoginServicePropertyInitializer {
        @Autowired
        public KakaoLoginServicePropertyInitializer (Environment environment) {
            API_KEY = environment.getProperty("kakao.api-key");
            REDIRECT_URI = environment.getProperty("kakao.redirect-uri");
        }
    }

    public KakaoUserInfoResponseDto login(String code) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        String tokenResponse = getAccessToken(code);
        JSONObject tokenJson = (JSONObject) jsonParser.parse(tokenResponse);
        String accessToken = tokenJson.get("access_token").toString();

        String infoResponse = getUserInfo(accessToken);
        JSONObject userInfoJson = (JSONObject) jsonParser.parse(infoResponse);
        JSONObject properties = (JSONObject) userInfoJson.get("properties");
        return KakaoUserInfoResponseDto.builder()
                .kakaoId(userInfoJson.get("id").toString())
                .nickname(properties.get("nickname").toString())
                .profileImageUrl(properties.get("profile_image").toString())
                .build();
    }

    private String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", API_KEY);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        String response;
        try {
            response = restTemplate.postForObject(REQUEST_URL_FOR_ACCESSTOKEN, request, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("[ERROR] 카카오 Access Token 받아오기 실패!");
        }
        if (response == null || response.contains("error")) throw new IllegalArgumentException("[ERROR] 카카오 Access Token 받아오기 실패!");
        return response;
    }

    private String getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        String response;
        try {
            response =  restTemplate.postForObject(REQUEST_URL_FOR_USERINFO, request, String.class);
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("[ERROR] 카카오 사용자 정보 받아오기 실패!");
        }
        if (response == null || response.contains("error")) throw new IllegalArgumentException("[ERROR] 카카오 사용자 정보 받아오기 실패!");
        return response;
    }
}
