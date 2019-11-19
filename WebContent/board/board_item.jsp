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

<%
	String	table_name = null ;
	String findMode = null;
	String findStr = null;
	String lvl = null;
	String mode = null;
	int		pk ;
	int		seq_no = 0 ;

	table_name = request.getParameter("bbs_name") ;
	findMode = request.getParameter("findMode") ;
	findStr = Uni2Ksc(request.getParameter("findStr")) ;
	seq_no = request.getParameter("seq_no") == null ? 0 :
				Integer.parseInt(request.getParameter("seq_no")) ;
	pk = request.getParameter("pk") == null ? 0 :
				Integer.parseInt(request.getParameter("pk")) ;
	lvl = request.getParameter("lvl") == null ? "1" :
				request.getParameter("lvl") ;

	mode = lvl.equals("1")? "":"reply";

	BoardBean client = new BoardBean();
	BoardItem item = client.getBoardItem(table_name, seq_no) ;
//	item.ItemUni2Ksc() ;
%>
<html>
<head>
<script language=javaScript>
<!--
    function input(){
        if(document.form.author.value=='') {
            alert('이름을 써주세요');
            document.form.author.focus();
            return false;
        }
        if(document.form.passwd.value=='') {
           alert('패스워드를 입력해주세요');
            document.form.passwd.focus();
            return false;
        }
        if(document.form.title.value=='') {
            alert('제목을 써주세요');
            document.form.title.focus();
            return false;
        }
		document.form.act_mode.value = "update" ;
        return true;
    }

    function update_(){
        if(input()){
			// document.form.act_mode.value = "update" ;
            document.form.submit();
        }
    }

	function _chkpasswd() {
        if(document.form.passwd.value=='') {
            alert('패스워드를 입력해주세요');
            document.form.passwd.focus();
            return false;
        }
		return true ;
	}

	function delete_() {

		if (_chkpasswd()) {
			document.form.act_mode.value = "delete" ;
			document.form.submit();
		}
	}

	function golist() {
		self.close();
		opener.focus();
	}

	function resetForm() {
		document.form.reset();
	}
//-->
</script>

<title>게시판 상세보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>
</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0"  text="#000000" link="#704981">
<form name=form method="post" action="./board_update.jsp" onsubmit="input()">
<input type=hidden name="bbs_name" value="<%=table_name%>">
<input type=hidden name="findMode" value="<%=findMode%>">
<input type=hidden name="findStr" value="<%=findStr%>">
<input type=hidden name="seq_no" value="<%=seq_no%>">
<input type=hidden name="author" value="<%=item.author%>">
<input type=hidden name="regdate" value="<%=item.regdate%>">
<input type=hidden name="readcnt" value="<%=item.readcnt%>">
<input type=hidden name="sort_seq" value="<%=item.sort_seq%>">
<input type=hidden name="lvl_no" value="<%=item.lvl_no.trim()%>">
<input type=hidden name="email" value="<%=item.email.trim()%>">
<input type=hidden name="mode" value="<%=mode%>">
<input type=hidden name="act_mode" value="">
<input type=hidden name="answer" value="<%=item.answer%>">
<input type=hidden name="pk" value="<%=pk%>">
<table width="707" border="0" cellpadding="0" cellspacing="0" height="100%" align='center'>
  <tr>
      <td width="707" align="center" bgcolor="#FFFFFF" valign="middle">
  <div align="center">
  <table width="700" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" align='center'>
    <tr valign="middle">
      <td height="64"><img src="../images/<%=table_name%>.gif" ></td>
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
                <tr bgcolor="#FFFFFF">
                  <td bgcolor="#EEEEEE" width="80" height="22" align="center">작성자</td>
<%	if (item.email.trim().length() == 0) {	%>
                  <td height="22" width="160"> <%=item.author%> </td>

<%	} else {	%>
                  <td height="22" width="160"><a href="mailto:<%=item.email%>?subject=Re:<%=item.title%>" onMouseOver="window.status=('Send mail to <%=item.author%> (<%=item.email%>)'); return true;" onMouseOut="window.status='';return true;"><%=item.author%></a>
				 </td>

<%	}			%>
                  <td width="70" height="22" bgcolor="#EEEEEE" align="center">작성일</td>
                  <td height="22" width="160"> <%=item.regdate%></td>
                  <td width="70" height="22" bgcolor="#EEEEEE" align="center">조회수</td>
                  <td height="22" width="160"> <%=item.readcnt%></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                  <td bgcolor="#EEEEEE" width="80" align="center">제목</td>
                  <td colspan="3">
                    <input type="text" name="title" size="50" value="<%=item.title%>">
                  </td>
                  <td width="70" height="22" bgcolor="#EEEEEE" align="center">비밀번호</td>
                  <td height="22" width="160">
                  <input type="password" name="passwd" size="8" value="">
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr bgcolor="#F9F9F9" align="center">
            <td height="250">
              <textarea name="ctnt" cols="105" rows="15"><%=item.ctnt%></textarea>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
          <tr>
            <td height="1" bgcolor="#CCCCCC"></td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="15" valign="bottom">
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr>
<%-- if(!table_name.equals("TB_NEWS") && lvl.equals("1") && item.answer.equals("0") ) { %>
            <td width="411" align="left"><img src="../images/left_d.gif" width="405" height="38"></td>
<% }
   else { %>
            <td width="476" align="left"><img src="../images/left_d.gif" width="470" height="38"></td>
<% } %>
            <td width="65" align="center" valign="bottom"><a href="javascript:update_()"><img src="../images/save.gif" width="60" height="23" border="0"></td>
            <td width="65" align="center" valign="bottom"><a href="javascript:delete_()"><img src="../images/delete.gif" width="60" height="23" border="0"></td>
<% if(!table_name.equals("TB_NEWS") && lvl.equals("1") && item.answer.equals("0") ) { %>
            <td width="65" align="center" valign="bottom"><a href="./board_write_form.jsp?bbs_name=<%=table_name%>&lvl_no=<%=item.lvl_no.trim()%>&lvl=<%=item.lvl%>&sort_seq=<%=item.sort_seq%>&pk=<%=pk%>&mode=reply&title=<%=item.title%>"><img src="../images/reply.gif" width="60" height="23" border="0"></td>
<% } --%>
            <td width="453" align="left"><img src="../images/left_w.gif" width="447" height="38"></td>
            <td align="center" width="65" valign="bottom"><a href="javascript: update_()"><img src="../images/save.gif" width="60" height="23" border="0"></td>
            <td width="88" align="center" valign="bottom"><a href="javascript: resetForm()"><img src="../images/afresh.gif" width="83" height="23" border="0"></td>
            <td width="65" align="center" valign="bottom"><a href="javascript:golist();"><img src="../images/list.gif" width="60" height="23" border="0"></td>
            <td width="29" align="right"><img src="../images/right01.gif" width="23" height="38"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </td>
      <td rowspan="1" width="1" height="100%" bgcolor="#E2E2E2"></td>
        </tr>
        <tr height="5"><td bgcolor="#FFFFFF">&nbsp;</td></tr>
	</table>
</form>
</body>
</html>
