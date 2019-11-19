package dhlee.board.servlet;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dhlee.board.io.*;
import dhlee.board.serial.commitem.*;
import dhlee.board.db.*;

public class BoardSave extends HttpServlet {
	private String fileSaveDir;
    
	public void init() {
        this.fileSaveDir = this.getServletConfig().getInitParameter("upload");        
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int			retval = 0 ;
		BoardItem	item = new BoardItem() ;
		String		tmp = null ;
		String findMode		= "";
		String findStr 		= "";
		FileUpLoad	upfile = new FileUpLoad() ;
		int			pk = 0 ;
		
		upfile.setSaveDir(fileSaveDir) ;
		upfile.readForm(request, response) ;

		item.table_name = upfile.getTruncParameter("bbs_name");

		findMode 		= upfile.getTruncParameter("findMode");
		findStr 		= upfile.getTruncParameter("findStr");
		item.title 		= upfile.getTruncParameter("title");
		item.author 	= upfile.getTruncParameter("author");

		item.passwd 	= upfile.getTruncParameter("passwd");

		item.email 		= upfile.getTruncParameter("email");
		item.afile 		= upfile.getParameter("afile");
		item.lvl_no 	= upfile.getTruncParameter("lvl_no");
		item.sort_seq 	= upfile.getIntParameter("sort_seq");
		item.lvl 		= upfile.getIntParameter("lvl");
		pk	 			= upfile.getIntParameter("pk");
		item.ctnt		= upfile.getParameter("ctnt");
		item.answer		= "0";
		item.mode		= upfile.getTruncParameter("mode");
		item.file_name	= upfile.getParameter("afile"); // 실제파일명
		if(item.file_name.trim().length() > 0) {
		  item.location	= fileSaveDir + "/" + upfile.getRealName(); // 저장파일명
		}

//		item.ItemKsc2Uni() ;                          
		try {
			BoardBean client = new BoardBean();
			client.writeItem(item) ;
		} catch(Exception e) {
			e.printStackTrace();
		}
        response.sendRedirect(request.getContextPath()+"/reload.html");
    }
}
