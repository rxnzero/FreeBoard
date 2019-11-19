<%@ page language="java" contentType="text/html;charset=euc-kr"%>
<%@ page import="java.util.*" %>
<%@ page import="dhlee.board.db.*" %>
<%@ page import="dhlee.board.serial.commitem.*" %>
<%@ page import="dhlee.lib.util.*" %>
<%!
	public String Uni2Ksc(String src) {
		String	ret = null ;
		try {
			if (src == null) return "" ;
			ret = new String(src.getBytes("8859_1"), "KSC5601") ;
		} catch (Exception ex) {
			ex.printStackTrace() ;
		}
		return ret ;
	}
%>
<%@ include file="/jsp/CheckMember.jsp" %>

<html>
<head>
<title>�Խù� ���� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>
</head>

<script language=javaScript>

function windowClose() {
	opener.search();
	setTimeout("close_action()",1000 * 1);
}

function close_action() {
	self.close();
	opener.focus();
}


</script>

<%
	int		retval = 0 ;
	BoardItem	item = new BoardItem() ;
	String		tmp = null ;
	
	String		act_mode = null ;
	boolean		rslt = false ;
	int		pk = 0 ;

	act_mode				= request.getParameter("act_mode") ;
	tmp 						= request.getParameter("seq_no") ;
	item.seq_no 		= Integer.parseInt(tmp == null ? "0": tmp) ;
	item.table_name = request.getParameter("bbs_name") ;
	item.title 			= request.getParameter("title") ;
	item.author 		= request.getParameter("author") ;
	
	
	item.passwd 		= request.getParameter("passwd") ;
	item.email 			= request.getParameter("email") ;
	item.afile 	= request.getParameter("afile") ;
	tmp 		= request.getParameter("sort_seq") ;
	item.sort_seq 	= Integer.parseInt(tmp == null ? "0": tmp) ;
	item.lvl_no 	= request.getParameter("lvl_no") ;
	//item.ctnt	= item.Uni2Ksc(request.getParameter("ctnt")) ;
	item.ctnt	= request.getParameter("ctnt") ;
	tmp 		= request.getParameter("pk") ;
	pk	 	= Integer.parseInt(tmp == null ? "0": tmp) ;

	String findMode = request.getParameter("findMode") ;
	//String findStr = Uni2Ksc(request.getParameter("findStr")) ;
	String findStr 	= request.getParameter("findStr") ;

	item.mode	= request.getParameter("mode") ;
	//�Ľ̵� �Ŀ� \r���ڰ� �����ϹǷ� �װ��� ����
	if(item.mode.indexOf("reply") != -1) {
		item.mode = "reply";
	}

	BoardBean client = new BoardBean();

	item.printMember() ;
   	item.ItemUni2Ksc() ;
	if (act_mode.equals("update"))
		rslt = client.updateItem(item) ;
	else if (act_mode.equals("delete"))
		rslt = client.deleteItem(item) ;

	if(!rslt) {
%>
<body onload="javaScript:windowClose();">
<CENTER>
	<table align="CENTER" border="0" cellpadding="0" cellspacing="0" width="70%">
	<tr>
		<td height="200" align="center"><p><font face="����" size="2">��� ��ȣ�� Ʋ���ϴ�.</font></p>
		<p><font face="����" size="2">�Խ��� ����Ʈ�� �����մϴ�.</font></td>
	</tr>
	</table>

<%  } else {		%>

<body onload="javaScript:windowClose();">
<CENTER>
	<table align="CENTER" border="0" cellpadding="0" cellspacing="0" width="70%">
	<tr>
<%
		if (act_mode.equals("update")) {
%>
		<td height="200" align="center"><p><font face="����" size="2">�ۼ��Ͻ� �����Ͱ� �ùٸ��� ����Ǿ����ϴ�.</font></p>
<%
		} else {
%>
		<td height="200" align="center"><p><font face="����" size="2">�Խù��� ���������� �����Ǿ����ϴ�.</font></p>
<%		}
%>
		<p><font face="����" size="2">�Խ��� ����Ʈ�� �����մϴ�.</font></td>
	</tr>
	</table>
<%	}				%>
</CENTER>
</body>
</html>

