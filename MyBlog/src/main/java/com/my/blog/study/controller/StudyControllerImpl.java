package com.my.blog.study.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.my.blog.common.dto.PageDTO;
import com.my.blog.common.dto.SearchDTO;
import com.my.blog.member.dto.MemberDTO;
import com.my.blog.study.dto.ScontentDTO;
import com.my.blog.study.dto.SimgDTO;
import com.my.blog.study.dto.StudyDTO;
import com.my.blog.study.service.StudyService;

@Controller
public class StudyControllerImpl implements StudyController {
	
	@Autowired
	private StudyService studyService;
	
	@RequestMapping(value ="/study/studyList",method=RequestMethod.GET)
	@Override
	public ModelAndView studyList(@ModelAttribute(value="searchDTO")SearchDTO searchDTO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		searchDTO.setStartNum((searchDTO.getCurrentPage()-1)*searchDTO.getSeeCount()+1);
		searchDTO.setEndNum(searchDTO.getStartNum()+searchDTO.getSeeCount()-1);
		System.out.println(searchDTO.getCurrentPage());
		System.out.println(searchDTO.getStartNum());
		System.out.println(searchDTO.getEndNum());
		
		List<StudyDTO> studyList = studyService.studyList(searchDTO);
		PageDTO studyPage =studyService.getStudyPage(searchDTO); 
		ModelAndView mv = new ModelAndView("/study/studyList");
		mv.addObject("studyPage",studyPage);
		mv.addObject("studyList",studyList);
		mv.addObject("search",searchDTO);
		return mv;
		
	}

	@RequestMapping(value="/study/studyWriteForm",method=RequestMethod.GET)
	@Override
	public ModelAndView studyWriteForm(SearchDTO searchDTO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("/study/studyWriteForm");
		mv.addObject("search",searchDTO);
		return mv;
	}

	@RequestMapping(value="/study/studyWrite",method=RequestMethod.POST)
	@Override
	public ResponseEntity studyWrite(@ModelAttribute(value="StudyDTO")StudyDTO studyDTO,
														@ModelAttribute(value="ScontentDTO")ScontentDTO scontentDTO,
														@ModelAttribute(value="SimgDTO")SimgDTO simgDTO,
														MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("loginMember");
		studyDTO.setMember_id(memberDTO.getMember_id());
		
		List<ScontentDTO> scontentList = scontentDTO.getScontentList();
		List<MultipartFile> multiFileList = request.getFiles("imgfiles");
		List<SimgDTO> simgList = simgDTO.getSimgList();
		
		int result = studyService.studyWrite(studyDTO,scontentList,simgList,multiFileList); //????????? ?????? (??????)
		ResponseEntity rs = null;
		String message ="";
		HttpHeaders headers = new HttpHeaders();
		
		
		if(result>0) {
			message="<script>";
			message+="alert('????????? ?????????????????????.');";
			message+="location.href='"+request.getContextPath()+"/study/studyList?dae_no="+studyDTO.getDae_no()+"&so_no="+studyDTO.getSo_no()+"';";
			message+="</script>";
		}else {
			message="<script>";
			message+="alert('????????? ?????????????????????.');";
			message+="location.href='"+request.getContextPath()+"/study/studyList?dae_no="+studyDTO.getDae_no()+"&so_no="+studyDTO.getSo_no()+"';";
			message+="</script>";
		}
		headers.add("Content-Type","text/html; charset=utf-8");
		rs = new ResponseEntity(message, headers, HttpStatus.OK);	
		return rs;
	}

	//??? ???????????? 
	@RequestMapping(value="/study/studyDetail",method=RequestMethod.GET)
	@Override
	public ModelAndView studyDetail(@RequestParam(value="study_no")int study_no,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> details = new HashMap<String,Object>();
		details = studyService.studyDetail(study_no);
		int max_order_length = 0;
		max_order_length = studyService.studyMaxOrder(study_no); //?????? ?????? max??? ????????????
		ModelAndView mv = new ModelAndView("/study/studyDetail");
		
		mv.addObject("study",details.get("study")); //??? ??????
		mv.addObject("scontentList",details.get("scontentList")); //??? ?????? ?????????
		mv.addObject("simgList",details.get("simgList")); //??? ????????? ?????????
		mv.addObject("max_order_length",max_order_length);
		
		return mv;
	}
	
	//???????????? ??? ???????????? ??? ??????????????? ????????? ????????????
	@RequestMapping(value="/study/studyModifyForm",method=RequestMethod.GET)
	@Override
	public ModelAndView studyModifyForm(int study_no, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,Object> details = new HashMap<String,Object>();
		details = studyService.studyDetail(study_no);
		int max_order_length = 0;
		max_order_length = studyService.studyMaxOrder(study_no); //?????? ?????? max??? ????????????
		ModelAndView mv = new ModelAndView("/study/studyModifyForm");
		
		mv.addObject("study",details.get("study")); //??? ??????
		mv.addObject("scontentList",details.get("scontentList")); //??? ?????? ?????????
		mv.addObject("simgList",details.get("simgList")); //??? ????????? ?????????
		mv.addObject("max_order_length",max_order_length);
		
		return mv;
	}

	
	
	/*
	 	Requestparam??? ????????? ?????? ?????? 
	 	Ajax??? ?????? ????????? ??????	 
		@RequestParam(value="arr[]" String[] arr)
		GET ?????? POST??? ??????
		@RequestParam(value="arr" String[] arr)
	 */
	//??? ???????????? 
	@RequestMapping(value="/study/studyModify",method=RequestMethod.POST)
	@Override
	public ResponseEntity studyModify(@RequestParam(value="modifyFile",required=false) int[] modifyFile, //required=false ?????? ??????????????? ??????????????? ??????????????????.
															@RequestParam(value="reposition",required=false) int[] reposition,
															@RequestParam(value="repositionValue",required=false) int[] repositionValue,
															@RequestParam(value="deleteFile",required=false) int[] deleteFile,
															@ModelAttribute(value="StudyDTO")StudyDTO studyDTO,
															@ModelAttribute(value="ScontentDTO")ScontentDTO scontentDTO,
															@ModelAttribute(value="SimgDTO")SimgDTO simgDTO,
															MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		List<ScontentDTO> scontentList = scontentDTO.getScontentList();
		List<SimgDTO> simgList = simgDTO.getSimgList();
		List<MultipartFile> files = request.getFiles("imgfiles");
		
		int result = studyService.studyModify(modifyFile,reposition,repositionValue,deleteFile,studyDTO,scontentList,simgList,files);
		ResponseEntity rs =null;
		String message = "";
		HttpHeaders headers = new HttpHeaders();
		if(result>0) {
			message+="<script>";
			message+="alert('??????????????????????????????.');";
			message+="location.href='"+request.getContextPath()+"/study/studyDetail?study_no="+studyDTO.getStudy_no()+"';";
			message+="</script>";
		}else {
			message+="<script>";
			message+="alert('?????? ?????????????????????.');";
			message+="location.href='"+request.getContextPath()+"/study/studyDetail?study_no="+studyDTO.getStudy_no()+"';";
			message+="</script>";
		}
		headers.add("Content-Type","text/html; charset=utf-8");
		rs=new ResponseEntity(message,headers,HttpStatus.OK);
		return rs;
	}
	
	
	

}
