package com.send.notification.kakao.provider;

import com.joshua.kakao.core.notificaiton.NotificationProvider;
import com.joshua.kakao.core.notificaiton.SendNotiMessage;
import com.joshua.kakao.core.notificaiton.dto.MessageMapper;
import com.joshua.kakao.core.tason.template.model.KakaoMember;
import com.joshua.kakao.core.tason.template.model.KakaoTemplate;
import com.joshua.kakao.core.tason.template.repository.KakaoMemberRepository;
import com.joshua.kakao.core.tason.template.repository.KakaoTemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@Transactional
class KakaoNotiProviderTest {

    @Autowired
    @Qualifier("KakaoNotiProvider")
    NotificationProvider sendManager;

    @Autowired
    MessageMapper mapper;

    @Autowired
    KakaoMemberRepository memberRepository;

    @Autowired
    KakaoTemplateRepository templateRepository;


    @Test
    @SuppressWarnings("serial")
    void 카카오_알림톡_보내기_테스트() {
        //given
        KakaoMember member = new KakaoMember();
        KakaoTemplate template = new KakaoTemplate();

        template.setContent("[스파이더킴] 안녕하세요. 회원님의 이용권 만료 3일 전입니다.\n" +
                "\n" +
                "▶이용권 : #{고객이름}\n" +
                "▶기간옵션 : #{고객번호}\n" +
                "▶서비스 이용기간 : #{고객이름}\n" +
                "\n" +
                "이용권 만료 시점에 베이직 요금제로 변경됩니다. 감사합니다.");
        template.setTemplateCode("C_SE_018_02_27268");
        templateRepository.save(template);

        KakaoMember findMember = memberRepository.findById(1L).orElseThrow(IllegalArgumentException::new);

        Map<String, String> criteria = new HashMap<>();
        criteria.put("고객이름", findMember.getUserName());
        criteria.put ("고객번호", findMember.getUserEmail());


        SendNotiMessage sendNotiMessage = SendNotiMessage.builder()
                .userName(findMember.getUserName())
                .userEmail(findMember.getUserEmail())
                .templateCode("C_SE_018_02_27268")
                .criteria(criteria)
                .build();

        //when
        String message = mapper.contentMapper(template.getTemplateCode(), criteria);

        System.out.println(">>>>>>>>>>" + message);

        sendManager.sendMessage(sendNotiMessage) ;

        //then
    }

}