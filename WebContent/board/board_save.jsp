<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*" %>
<%@ page import="dhlee.board.db.*" %>
<%@ page import="dhlee.board.serial.commitem.*" %>
<%@ page import="dhlee.lib.util.*" %>
<%@ page import="dhlee.board.io.FileUpLoad"%>
<%@ include file="/jsp/CheckMember.jsp" %>
<html>
<head>
<title>�Խù� ����</title>
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
	item.file_name	= upfile.getParameter("afile"); // �������ϸ�
	if(item.file_name.trim().length() > 0) {
	  item.location	= fileSaveDir + "/" + upfile.getRealName(); // �������ϸ�
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
		<td height="200" align="center"><p><font face="����" size="2">�ۼ��Ͻ� �����Ͱ� �ùٸ��� ����Ǿ����ϴ�.</font></p>
		<p><font face="����" size="2">�Խ��� ����Ʈ�� �����մϴ�.</font></td>
	</tr>
	</table>
</CENTER>
</body>
</html>
