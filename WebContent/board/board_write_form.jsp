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
	
	public String Ksc2Uni(String src) {
		String	ret = null ;
		try {
			if (src == null) return "" ;
			ret = new String(src.getBytes("KSC5601"), "8859_1") ;
		} catch (Exception ex) {
			ex.printStackTrace() ;
		}
		return ret ;
	}
%>
<%@ include file="/jsp/CheckMember.jsp" %>
<%
	String findMode = request.getParameter("findMode") ;
	String findStr = request.getParameter("findStr") ;

	String	bbs_name = request.getParameter("bbs_name") ;
	String	mode = request.getParameter("mode") ;
	String	act_mode = request.getParameter("act_mode") ;
	String	lvl_no = request.getParameter("lvl_no") ;
	String	title = request.getParameter("title") ;
	System.out.println(Uni2Ksc(title));
	String	lvl = request.getParameter("lvl").equals("") ? "0" :
				request.getParameter("lvl");
	int		sort_seq = request.getParameter("sort_seq") == null ? 0 :
			Integer.parseInt(request.getParameter("sort_seq")) ;
	int	pk = request.getParameter("pk") == null ? 0 :
			Integer.parseInt(request.getParameter("pk")) ;
	String	name =  (String)session.getAttribute("name") ;
	String	email = (String)session.getAttribute("mail_addr") ;
	String	passwd = (String)session.getAttribute("passwd") ;
	
	if(name == null) name = "";
	if(email == null) email = "";
	if(passwd == null) passwd = "";
	
	if (mode.equals("new")) pk = 0 ;
%>
<html>
<head>
<script language=javaScript>
<!--
	function input(){
		if(document.form.author.value=='')
		{
			alert('이름을 써주세요');
			document.form.author.focus();
			return false;
		}
		if(document.form.passwd.value=='')
		{
			alert('패스워드를 입력해주세요');
			document.form.passwd.focus();
			return false;
		}
		if(document.form.title.value=='')
		{
			alert('제목을 써주세요');
			document.form.title.focus();
			return false;
		}

		return true;
	}

	function checkinput(){
		if(input()){
			document.form.submit();
		}
	}

	function golist() {
		self.close();
		opener.focus();
	}


	function resetform() {
		document.form.reset();
	}

//-->
</script>

<title>게시판 쓰기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>

</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" text="#000000" link="#704981">
<form name=form method="post" action="<%= request.getContextPath() %>/BoardSave" enctype="multipart/form-data">
<input type=hidden name="bbs_name" value="<%=bbs_name%>">
<input type=hidden name="findMode" value="<%=findMode%>">
<input type=hidden name="findStr" value="<%=findStr%>">
<input type=hidden name="lvl" value="<%=lvl%>">
<input type=hidden name="lvl_no" value="<%=lvl_no%>">
<input type=hidden name="sort_seq" value="<%=sort_seq%>">
<input type=hidden name="pk" value="<%=pk%>">
<input type=hidden name="mode" value="<%=mode%>">
<table width="707" border="0" cellpadding="0" cellspacing="0" height="100%" align='center'>
  <tr>
      <td width="707" align="center" bgcolor="#FFFFFF" valign="middle">
  <div align="center">
  <table width="700" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0">
    <tr valign="top">
      <td height="64"><img src="../images/<%=bbs_name%>.gif" ></td>
    </tr>
    <tr>
      <td><img src="../images/topimg.gif" width="700" height="5"></td>
    </tr>
    <tr>
      <td height="10">
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table width="700" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
                <tr>
                  <td width="70" bgcolor="#EEEEEE" align="center">작성자</td>
                  <td width="160" bgcolor="#FFFFFF">
                    <input type="text" name="author" value=<%=name%>>
                  </td>

									<td width="70" bgcolor="#EEEEEE" align="center">패스워드</td>
                  <td width="160" bgcolor="#FFFFFF">
                    <input type="password" name="passwd" value=<%=passwd%>>
                  </td>

                  <td width="70" bgcolor="#EEEEEE" align="center">e-mail</td>
                  <td width="180" bgcolor="#FFFFFF">
                    <input type="text" name="email" size="30" value=<%=email%>>
                  </td>
                  
                </tr>
                <tr>
                  <td width="40" bgcolor="#EEEEEE" align="center">제목</td>
                  <td colspan="5" bgcolor="#FFFFFF">
<%	if (mode.equals("reply")) {	%>
                    <input type="text" name="title" size="50" maxlength="100" value="Re : <%= title %>">
<%	} else {					%>
                    <input type="text" name="title" size="50" maxlength="100" >
<%	}							%>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr align="center">
            <td height="250" bgcolor="#F9F9F9">
              <textarea name="ctnt" cols="105" rows="15"></textarea>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr>
            <td height="10">
              <table width="700" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
                <tr>
                  <td width="80" bgcolor="#EEEEEE" align="center">첨부</td>
                  <td width="620" bgcolor="#FFFFFF">
                    <input type="file" name="afile" size="50">
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="15" valign="bottom">
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="453" align="left"><img src="../images/left_w.gif" width="447" height="38"></td>
            <td align="center" width="65" valign="bottom"><a href="javascript:checkinput()"><img src="../images/save.gif" width="60" height="23" border="0"></td>
            <td width="88" align="center" valign="bottom"><a href="javascript:resetform();"><img src="../images/afresh.gif" width="83" height="23" border="0"></td>
            <td width="65" align="center" valign="bottom"><a href="javascript:golist();"><img src="../images/list.gif" width="60" height="23" border="0"></td>
            <td width="29" align="right"><img src="../images/right01.gif" width="23" height="38"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </div>
  </td>
      <td></td>
        </tr>
	</table>
</form>
</body>
</html>
