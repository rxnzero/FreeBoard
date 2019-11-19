<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*" %>
<%@ page import="dhlee.board.db.*" %>
<%@ page import="dhlee.board.serial.commitem.*" %>
<%@ page import="dhlee.lib.util.*" %>
<%@ page import="dhlee.board.io.FileUpLoad"%>
<%@ include file="/jsp/CheckMember.jsp" %>
<html>
<head>
<title>게시물 저장</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>
</head>

<script language=javaScript>
<!--
	function windowClose() {
		opener.search();
		setTimeout("close_action()",1000 * 1);
	}

	function close_action() {
		self.close();
		opener.focus();
	}
//-->
</script>

<%
	
	int			retval = 0 ;
	BoardItem	item = new BoardItem() ;
	String		tmp = null ;
	String findMode		= "";
	String findStr 		= "";
	FileUpLoad	upfile = new FileUpLoad() ;
	int			pk = 0 ;
	
	String fileSaveDir = "C:/temp/upload";
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

//	item.ItemKsc2Uni() ;                          
	try {
		BoardBean client = new BoardBean();
		client.writeItem(item) ;
	} catch(Exception e) {
		e.printStackTrace();
	}
%>

<body onload="javaScript:windowClose();">
<CENTER>

	<table align="CENTER" border="0" cellpadding="0" cellspacing="0" width="70%">
	<tr>
		<td height="200" align="center"><p><font face="굴림" size="2">작성하신 데이터가 올바르게 저장되었습니다.</font></p>
		<p><font face="굴림" size="2">게시판 리스트로 복귀합니다.</font></td>
	</tr>
	</table>
</CENTER>
</body>
</html>
