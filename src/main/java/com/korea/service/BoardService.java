package com.korea.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.korea.dao.BoardDAO;
import com.korea.dto.BoardDTO;
import com.korea.dto.ReplyDTO;
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
   
   public List<BoardDTO> getBoardList(int start,int end)
	{
		return dao.Select(start, end);
	}
	
	public int getTotalCnt() {
		return dao.getTotalCount();
	}
	
	public boolean PostBoard(BoardDTO dto)
	{
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
      
      
      //게시물 번호
      String no = String.valueOf(dao.getLastNo()+1);
      
      String subPath = email + "/" + date + "/"+no;
      
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
      
    //5) Parts의 각 Part 를 write()
    		System.out.println("Parts : "+ parts.size());
    		for(Part part : parts)
    		{
    			if(part.getName().equals("files"))
    			{
    				String FileName=getFileName(part); //파일이름 가져오기

    	
    				
    				//파일명 ,확장자명 구분하기
    				
    				 
    				int start=FileName.lastIndexOf(".");		//확장자구하기 위한 시작idx
    				int end=FileName.length();					//확장자구하기 위한 끝idx
    				String ext=FileName.substring(start,end);	//파일명잘라내기(확장자만)
    				FileName = FileName.substring(0,start);	//파일명잘라내기(확장자제외)
    				
    				//파일명 + _UUID + 확장자
    				FileName=FileName+"_"+UUID.randomUUID().toString()+ext;
    				
    				//DTO저장위한 파일명 buffer에추가
    				totalFilename.append(FileName+";");
    				//DTO저장위한 파일사이즈 buffer에추가				 
    				totalFilesize.append(String.valueOf(part.getSize())+";");
    				
    				
    				
    				 try {
    					part.write(RealPath+"/"+FileName); //파일업로드
    				} catch (IOException e) {	 
    					e.printStackTrace();
    				}
    			}
    		}
    		
    		//Dto에 총파일명과 총파일사이즈를 저장
    		dto.setFilename(totalFilename.toString());
    		dto.setFilesize(totalFilesize.toString());
    		
    		//Dao 파일명전달 
    		return dao.Insert(dto);
    	}
   
		 //파일명추출(PostBoard(dto,parts)가 사용)
		 	private String getFileName(Part part)
		 	{
		 		String contentDisp=part.getHeader("content-disposition");
		 		System.out.println(contentDisp);
		 		String[] arr = contentDisp.split(";"); // 배열화 		
		 		String Filename=arr[2].substring(11,arr[2].length()-1);	
		 		return Filename;
		 	}
 	
		   //게시물 하나 가져오기
		   public BoardDTO getBoardDTO(int no) {
			   return dao.Select(no);
		   }
		   
		   //단일파일 다운로드
		
		   public boolean download(String filename, HttpServletRequest req, HttpServletResponse resp) {
			   						//매개변수 지정
       //파일명

       //파일명, 등록날짜

       //이메일계정
       HttpSession session = req.getSession(); 	//읽고있는 게시물을 session 에서 꺼내기(BoardreadController에서 저장한)
       BoardDTO dto = (BoardDTO) session.getAttribute("dto");

       String email = dto.getWriter();			//dto에서 이메일과 날짜정보 받기
       String regdate = dto.getRegdate();
       regdate = regdate.substring(0, 10);
       String no = String.valueOf(dto.getNo());


       //1 경로설정
       String downdir = "c://upload";
       String filepath = downdir + "/" + email + "/" + regdate + "/" +no+"/"+ filename; //filename 외부에서 전달받음 

       //2 헤더설정 	//다운로드용으로 만들기 위한 작업
       resp.setContentType("application/octet-stream");

       //3 문자셋 설정
       try {
           //filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20"); //파일이름 인코딩

           resp.setHeader("Content-Disposition", "attachment; fileName=" + filename); //헤더에 파일이름 전달

           //04스트림형성(다운로드 처리)
           FileInputStream fin = new FileInputStream(filepath);	
           ServletOutputStream bout = resp.getOutputStream();	//getOutputStream() 브라우저 방향으로 전달할 내용을 bout 에 저장

           int read = 0;
           byte buff[] = new byte[4096];
           while(true){
               read = fin.read(buff,0,buff.length);	//buffer공간에 0번째부터 buffer길이까지 받기 
               if(read == -1)
                   break;
               bout.write(buff,0,read);			//브라우저 방향으로 buffer에 저장된 내용 전달

           }
           bout.flush();
           bout.close();
           fin.close();
           
           return true;

       } catch (Exception e) {
           e.printStackTrace();
       }

       //등록날짜

       //c:\\upload\\이메일명\\등록날짜\\파일들.

       return false;
   }
   
   
 //다중파일 다운로드
 	public boolean download
 	(		 
 			HttpServletRequest req,
 			HttpServletResponse resp
 	)
 	{	
 		//파일명,	//등록날짜 //이메일계정 가져오기
 		HttpSession session = req.getSession();
 		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
 		
 		String email = dto.getWriter();
 		String regdate = dto.getRegdate();
 		String no = String.valueOf(dto.getNo());
 		
 		regdate = regdate.substring(0,10);
 		
 		 
 		//1 경로설정
 		String downdir="c://upload";	
 		String filepath= downdir+"/"+email+"/"+regdate+"/"+no;
 		 
 		//2 헤더설정
 		resp.setContentType("application/octet-stream");

 		//3 파일검색
 		File dir = new File(filepath);
 		File[] flist = dir.listFiles(); // 파일들 꺼내오는 작업(절대경로)
 		
 			
 		//4 문자셋 설정
 		try {
 			
 			for(int i=0;i<flist.length;i++)
 			{
 				

 				String filename=flist[i].getName();
 				System.out.println("! : " + filename);
 				filename=URLEncoder.encode(filename,"utf-8").replaceAll("\\+", "%20");
 				resp.setHeader("Content-Disposition", "attachment; fileName="+filename);
 			
 				
 				//04스트림형성(다운로드 처리)
 				FileInputStream fin = new FileInputStream(flist[i]);
 				ServletOutputStream bout=resp.getOutputStream();
 				
 				int read=0;
 				byte[] buff = new byte[4096];
 				while(true)
 				{
 					 
 					read=fin.read(buff,0,buff.length);		 
 					if(read==-1)	 
 						break;		 		
 					bout.write(buff,0,read);	 
 				}
 			 
 				bout.flush();	
 				bout.close();	
 				fin.close();
 			
 			}
 			
 			return true;
 		
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	
 		return false;
 	}
 	
 	//ZIP 으로 압축 다운로드
	public boolean downloadZIP(BoardDTO dto, HttpServletResponse resp) {
		
		String id = UUID.randomUUID().toString();
		//압축파일경로
		String zipFileName="C://Users/dbsgp/Downloads/ALL_"+id+".zip";
		
		//파일명,	//등록날짜 //이메일계정 가져오기
 		String email = dto.getWriter();
 		String regdate = dto.getRegdate();
 		String no = String.valueOf(dto.getNo());
 		regdate = regdate.substring(0,10);
 		
 		 
 		//1 경로설정
 		String downdir="c://upload";	
 		String subpath= downdir+"/"+email+"/"+regdate+"/"+no+"/";
 		
 		 
 		//2 파일이름 리스트
 		String filelist[]=dto.getFilename().split(";"); 

 		
 		
 		//3 헤더 설정
 		resp.setContentType("application/zip");
		resp.setHeader("Content-Disposition", "attachment; fileName=ALL_"+id+".zip");
		
 		try {
 			
 			//프로그램 -> 파일 방향 ZIPStream 생성
 			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFileName)); //기본스트림(FileOutputStream) 방향으로 ZIP압축 처리하는 보조스트림(ZipOutputStream) 생성
 			
 			
 			for(int i=0;i<filelist.length;i++)
 			{
 				//파일 -> 프로그램 instream 생성
 				FileInputStream fin = new FileInputStream(subpath+filelist[i]);
 				
 				//ZipEntry 생성, zout에 전달
 				ZipEntry ent = new ZipEntry(filelist[i]);	//엔트리명은 파일이름으로 설정
 				zout.putNextEntry(ent);						//ZIP방향에 entry 추가
 				
 				int read=0;
 				byte buff[]=new byte[4096];
 				while(true)
 				{
 					read=fin.read(buff,0,buff.length-1); //만들어진 데이터 공간에 buffer크기 만큼 read 받기
 					if(read==-1) {
 						break;
 					}
 					zout.write(buff,0,read); //0번째 부터 read(읽어들인) 만큼
 				}
 				zout.closeEntry();	//엔트리 단위 종료
 				fin.close();		//파일 input 스트림 종료

 				
 			}
 			zout.close();			//zipoutput 스트림 종료
 			
 			
		return true;
	}catch(Exception e) {
		e.printStackTrace();
	}
 		return false;
	}
	
	public void CountUp(int no) {
		dao.CountUp(no);
	}
	
	public boolean UpdateBoard(BoardDTO dto) {
		return dao.Update(dto);
	}
	
	public boolean BoardRemove(BoardDTO dto) {
		
		//첨부파일 경로 확인 
		if(dto.getFilename()!=null)
		{
			String email = dto.getWriter();
			String regdate = dto.getRegdate();
			regdate = regdate.substring(0,10);
			String no = String.valueOf(dto.getNo());
			
			String dirpath = UploadPath+email+"/"+regdate+"/"+no;
			//첨부파일 폴더 경로
			File dir = new File(dirpath);
			//폴더 경로로 부터 파일리스트 가져오기
			File [] filelist = dir.listFiles();
			//첨부파일 모두 삭제
			for(File filename : filelist)
			{
				filename.delete();
			}
			//첨부파일 폴더 삭제 
			dir.delete();
		}
		
		return dao.Delete(dto);
	}
	
	
	//댓글 달기
	public boolean replypost(ReplyDTO rdto) {
		
		return dao.replypost(rdto);
	}
	public ArrayList<ReplyDTO> getReplaylist(int bno) {
		return dao.getReplylist(bno);
	}
	public int getTotalReplyCount(int bno) {
		return dao.getTotalReplyCount(bno);
	}
}

