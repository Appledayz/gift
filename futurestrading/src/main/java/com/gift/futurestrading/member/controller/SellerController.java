package com.gift.futurestrading.member.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gift.futurestrading.member.service.SellerService;
import com.gift.futurestrading.member.vo.ConsumerRequestVo;
import com.gift.futurestrading.member.vo.SellerFileRequest;
import com.gift.futurestrading.member.vo.SellerRequestVo;


@Controller
public class SellerController {
	@Autowired
	private SellerService sellerService;
	//test용
	@RequestMapping(value = "/joinseller", method = RequestMethod.GET)
	public String test() {
		System.out.println("출력");
		return "member/seller/addMemberSeller";
	}
	//sellerDetail 페이지 요청
	@RequestMapping("/joinsellerdetail")
	public String joinSellerDetail() {
		System.out.println("출력");
		return "member/seller/addMemberSellerDetail";
	}
	// joinsellerdetail 창에서 값을 받아서 이쪽으로 요청
	@RequestMapping("/sellerFileUpload")
	public String getSellerDetail(SellerFileRequest sellerFile , HttpSession session ) throws IOException {
		System.out.println("출력111");
		//D:\gift\maven.1544422656107\futurestrading\src\main\resources\sellerFile
		String path = session.getServletContext().getRealPath("\\upload\\");
		String realPath =path;
		System.out.println(path+"pathpathpathpath");
		SellerService sellerService = new SellerService();
		System.out.println(sellerFile.getMultipartFile()+"파일옵로드");
		sellerService.sellerFileUpload(sellerFile, realPath);
		return "index";
	}
	
	//add seller 넘겨준 값 데이터 베이스 저장
	@RequestMapping(value = "/addseller", method = RequestMethod.POST)
	public String addConsumer(SellerRequestVo sellerRequestVo) {
		System.out.println("ConsumerController.addConsumer() 호출");
		int insertResult = sellerService.insertSeller(sellerRequestVo);
		System.out.println(insertResult + " <---insertResult");
		return "index";
	}
	//ajax 관련
	@RequestMapping(value = "/checkId", method = { RequestMethod.POST })
	@ResponseBody
	public int idCheck(@RequestBody String id) {
		System.out.println("SellerController");
		System.out.println("ConsumerController.idCheck() 호출");
		System.out.println(id + " <---id");

		String pattern = "^[a-zA-Z0-9]*$";
		String pattern2 = "^[0-9]*$";

		int count = 1;
		// 문자 숫자
		boolean result = Pattern.matches(pattern, id);
		// 문자만
		boolean result2 = Pattern.matches(pattern2, id);
		// 숫자만
		/*
		 * count o 사용가능 count 1 중복됨 count 2 숫자만 count 3 특수문자가 있음
		 */

		// 특수문자 있는지 확인
		if (!result) {
			System.out.println("특수문자가 포함되어 있음");
			count = 3;
			// 숫자만 있는지 확인
		} else if (result2) {
			count = 2;
		} else {
			System.out.println("완벽함");
			/* id로 중복성 체크 메소드 호출 */
			count = sellerService.idCheck(id);
		}
		return count;
	}
}
