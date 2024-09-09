package com.yedam.app.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // Bean 등록, Web과 관련된 부분 담당
public class URLController {
	// @RequestMapping(path="/test", method=RequestMethod.GET) - 아래 GetMapping과 같음.
	@GetMapping("/test")
	@ResponseBody // => AJAX(페이지 지정용이 아니라 순수한 데이터 전달용)
	public String urlGetTest(String keyword) {
		return "Server Response : Get Method\n Select - " 
												+ keyword;
	}


	// @RequestMapping(path="/test", method=RequestMethod.POST)
	@PostMapping("/test")
	@ResponseBody // => AJAX(페이지 지정용이 아니라 순수한 데이터 전달용)
	public String urlPostTest(String keyword) {
		return "Server Response : Post Method\n Select - " 
												+ keyword;
	}
}