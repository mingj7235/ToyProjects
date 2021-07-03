package com.joshua.springweb.awsspring.web;

import com.joshua.springweb.awsspring.service.PostsService;
import com.joshua.springweb.awsspring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller

/**
 * IndexController는 GetMapping 즉, 화면 전환에 대한 컨트롤러를 달아 놓았다.
 * API와 Presentation Tier에 대한 분리를 해놓는것이 좋아 보인다.
 * Dto를 통해 Model객체에 데이터를 전달하거나,
 * Service를 통해 Model객체에 담아 정보를 Presentation Tier에 담아준다.
 */
public class IndexController {

    private final PostsService postsService;

    //머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 시, 앞에 경로와 뒤의 파일 확장자는 자동으로 지정된다.
    //앞의 경로 : src/main/resources/templates
    //뒤의 파일 확장자 : .mustache
    // 최종 반환 값 -> src/main/resources/templates/index.mustache -> View resolver가 처리한다.
    @GetMapping("/")
    public String index (Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        model.addAttribute("nums", postsService.totalNum());
        return "index";
    }

    //글 등록 페이지 전환
    @GetMapping("/posts/save")
    public String postsSave () {
        return "posts-save";
    }

    //글 상세보기 페이지 전환
    @GetMapping("/posts/detail/{id}")
    public String postsDetail (@PathVariable Long id,  Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-detail";
    }

    //글 수정 페이지 전환
    @GetMapping("/posts/update/{id}")
    public String postsUpdate (@PathVariable long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
