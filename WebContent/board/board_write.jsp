<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*" %>
<%@ page import="dhlee.board.db.*" %>
<%@ page import="dhlee.board.serial.commitem.*" %>
<%@ page import="dhlee.lib.util.*" %>
<%@ include file="/jsp/CheckMember.jsp" %>
<html>
<head>
<title>�Խù� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>
</head>

<%
	int			retval = 0 ;
	BoardItem	item = new BoardItem() ;
	String		tmp = null ;
	int			pk = 0 ;

	item.table_name = request.getParameter("bbs_name") ;
	item.title 		= request.getParameter("title") ;
	item.author 	= request.getParameter("author") ;
	item.passwd 	= request.getParameter("passwd") ;
	item.email 		= request.getParameter("email") ;
	item.afile 		= request.getParameter("afile") ;
	tmp = request.getParameter("pk") ;
	pk = Integer.parseInt(tmp == null ? "0": tmp) ;
	item.ctnt		= request.getParameter("ctnt") ;
	item.answer 	= request.getParameter("answer") ;
	item.mode 		= request.getParameter("mode") ;
	
	try {
		BoardBean client = new BoardBean();
		client.writeItem(item) ;
	} catch(Exception e) {
		e.printStackTrace();
	}
%>

<body>
<CENTER>
	<table align="CENTER" border="0" cellpadding="0" cellspacing="0" width="70%">
	<tr>
		<td height="200" align="center"><p><font face="����" size="2">�ۼ��Ͻ� �����Ͱ� �ùٸ��� ����Ǿ����ϴ�.</font></p>
		<p><font face="����" size="2">�Խ��� ����Ʈ�� �����մϴ�.</font></td>
	</tr>
	</table>
	
<meta http-equiv=refresh content="1; URL=./board/boardList2.jsp?bbs_name=<%=item.table_name%>&pk=<%=pk%>">
</CENTER>
</body>
</html>

