package com.korea.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Part;
import com.korea.dao.BoardDAO;
import com.korea.dto.BoardDTO;
public class BoardService {
   BoardDAO dao = BoardDAO.getInstance();
   
   private String UploadPath = "c://upload/";//업로드 폴더 지정(멤버변수 c://upload\)
   
   
   //싱글톤 패턴
   private static BoardService instance=null;
   public static BoardService getInstance() {
      if(instance==null)
         instance=new BoardService();
      return instance;
   }
   private BoardService() {}
   
   public List<BoardDTO> getBoardList(int start, int end){
      return dao.Select(start, end);
   }
   
   public int getTotalCount() {
      return dao.getTotalCount();
   }
   
   public boolean PostBoard(BoardDTO dto) {
      return dao.Insert(dto);
   }
   
   
   //파일포함 글쓰기 서비스
   public boolean PostBoard(BoardDTO dto, ArrayList<Part> parts) {
      //업로드 처리
      //하위폴더명(Email/2022-07-14/파일쌓이기~)
      //기본업로드Path+하위폴더명 -> 하위폴더 지정(email, date 날짜정보)
      String email = dto.getWriter(); 
      Date now = new Date();
      
      //날짜포맷변경하기
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String date = simpleDateFormat.format(now);
      
      String subPath = email + "/" + date;
      
      //file클래스 경로 연결하기
      File RealPath = new File(UploadPath + subPath); //RealPath : c:/upload/email/date
      
      
      //하위폴더 생성
      if(!RealPath.exists()) {
         RealPath.mkdirs(); 
      }
      
      
      //파일명 저장을 위한 StringBuffer 추가
      StringBuffer totalFilename = new StringBuffer();
      //파일 사이즈 저장을 위한 StringBuffer 추가
      StringBuffer totalFilesize = new StringBuffer();
      
      
      //Parts의 각 Part를 write()
      System.out.println("Parts : "+parts.size());
      
      //files 파트만 확인해서 파일 이름 추출
      for(Part part : parts) {
         if(part.getName().equals("files")){ 
            
            String FileName = getFileName(part); //파일이름 가져오기
            
           
            
            //DTO 저장을 위한 파일명 buffer에 추가
            totalFilename.append(FileName+ ";");
            //DTO 저장을 위한 파일사이즈 buffer에 추가
            totalFilesize.append(String.valueOf(part.getSize()+ ";"));
            
            try {

            int start=FileName.length()-4;		//확장자 구하기 위한 시작 idx
            int end=FileName.length();			//확장자 구하기 위한 끝 idx
            String ext = FileName.substring(start,end);	//파일명 잘라내기(확장자만)
            FileName=FileName.substring(0,start-1);		//파일명 잘라내기(확장자 제외)
            
            //파일명 + _UUID + 확장자
            FileName=FileName+"_"+UUID.randomUUID().toString()+ext; //파일이름 중복방지
            
           
            //파일 업로드
               part.write(RealPath + "/" + FileName); //part.write(파일경로)
          
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
      
      //Dto에 총 파일명과 파일사이즈 저장
      
      dto.setFilename(totalFilename.toString());      
      dto.setFilesize(totalFilesize.toString());      

      
      //DAO 파일명 전달
      return dao.Insert(dto);
   }
   
   //파일명 추출(PostBoard(dto,parts)가 사용)
   private String getFileName(Part part) {
      String contentDisp = part.getHeader("content-disposition");
      //System.out.println(contentDisp);
      String[] arr = contentDisp.split(";"); //배열화
      String Filename = arr[2].substring(11, (arr[2].length()-1));
      return Filename;
   }
}

