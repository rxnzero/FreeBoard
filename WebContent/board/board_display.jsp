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
/*
	public int getContentsLastLineNo(String contents) {
	     AtsStringTokenizer st = new AtsStringTokenizer(contents, "\r\n");

	     while (st.hasMoreTokens()) {
	         System.out.println(st.nextToken());
	     }

	     return st.countTokens();
     }
*/
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
	findStr = request.getParameter("findStr") ;
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

        return true;
    }

    function update_(){
        if(input()){
			document.form.act_mode.value = "update" ;
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
			if (confirm('정말로 삭제하시겠습니까?')) {
				document.form.act_mode.value = "delete" ;
				document.form.submit() ;
			}
		}
	}

	function golist() {
		self.close();
		opener.focus();
	}

	function showDetail(seq_no, lvl) {
		var f = document.forms[0];
		f.seq_no.value=seq_no;
		f.lvl.value=lvl;
		f.action="board_item.jsp";
		f.submit();
	}
	
	function replay_() {
		var f = document.forms[0];
		f.mode.value='reply';
		f.action="board_write_form.jsp";
		f.submit();
	}
	
//-->
</script>

<title>게시판 상세보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>
</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0"  text="#000000" link="#704981">
<form name=form method="POST" action="./board_update.jsp">
<input type=hidden name="bbs_name" value="<%=table_name%>">
<input type=hidden name="findMode" value="<%=findMode%>">
<input type=hidden name="findStr" value="<%=findStr%>">
<input type=hidden name="seq_no" value="<%=seq_no%>">
<input type=hidden name="author" value="<%=item.author%>">
<input type=hidden name="regdate" value="<%=item.regdate%>">
<input type=hidden name="readcnt" value="<%=item.readcnt%>">
<input type=hidden name="sort_seq" value="<%=item.sort_seq%>">
<input type=hidden name="lvl_no" value="<%=item.lvl_no.trim()%>">
<input type=hidden name="lvl" value="<%=item.lvl%>">
<input type=hidden name="email" value="<%=item.email.trim()%>">
<input type=hidden name="mode" value="<%=mode%>">
<input type=hidden name="act_mode" value="">
<input type=hidden name="answer" value="<%=item.answer%>">
<input type=hidden name="pk" value="<%=pk%>">
<input type=hidden name="title" value="<%=item.title%>">
<table width="707" border="0" cellpadding="0" cellspacing="0" height="100%" align='center'>
  <tr>
      <td width="707" align="center" bgcolor="#FFFFFF" valign="middle">
  <div align="center">
  <table width="700" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0">
    <tr valign="middle">
      <td height="64"><img src="../images/<%=table_name%>.gif" ></td>
    </tr>
    <tr>
      <td><img src="../images/topimg.gif" width="700" height="5"></td>
    </tr>
    <tr>
      <td>
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
                  <td colspan="3"><%=HFormat2.toTagQuote(item.title)%></td>
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
          <%
          /* System.out.println("aaaaaaaaaaaaa => " + item.ctnt.length() );
             int lastLineNo = 15;
             if(item.ctnt.trim().length() > 3) {
                 lastLineNo = getContentsLastLineNo(item.ctnt); //250
             }
            */
           %>
             <!--<td height="250">
              <textarea name="ctnt" cols="105" rows="1" readonly ></textarea>
            </td>-->
           <!-- td height="250" align='left' valign='top'><%= HFormat2.addBRTagToNewLine(item.ctnt) %></td -->
            <td height="250" style="padding: 15" valign="top">
              <table width="100%" height="220" border="0" cellspacing="1" cellpadding="10" bgcolor="#EAE2CA">
                <tr>
                  <td bgcolor="#FFFFF4" valign="top"><%= HFormat2.addBRTagToNewLine(item.ctnt) %></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
<%
	// 첨부화일이 있는 경우
	String saveFileName = null;
	if(item.location == null) {
		saveFileName="";
	}
	else {
		int pointIndex = item.location.lastIndexOf("/");
		saveFileName = item.location.substring(pointIndex+1, item.location.length());
	}

	if (saveFileName.trim().length() != 0) {
%>
          <tr> 
            <td> 
              <table width="700" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
                <tr bgcolor="#FFFFFF"> 
                  <td bgcolor="#EEEEEE" width="80" height="22" align="center">첨부</td>
                  <td>&nbsp;
				    <a href="<%= request.getContextPath() %>/download?filename=<%=saveFileName%>&tofilename=<%=item.file_name%>"><%=item.file_name%></a>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
<%
	}
	else
	{
%>
          <tr bgcolor="#CCCCCC"><td height="1"></td></tr>
<%
	}
%>
        </table>
      </td>
    </tr>
    <tr>
      <td height="15" valign="bottom">
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr>
<% if(!table_name.equals("TB_NEWS") && lvl.equals("1") && item.answer.equals("0") ) { %>
            <td width="411" align="left" colspan='1'><img src="../images/left_d.gif" width="405" height="38"></td>
<% }
   else { %>
            <td width="476" align="left" colspan='2'><img src="../images/left_d.gif" width="470" height="38"></td>
<% } %>
<%
	if (true) //if (emp_no.equals(item.passwd))
	{
%>
            <td width="65" align="center" valign="bottom"><a href="javascript:showDetail('<%=item.seq_no%>', '<%=item.lvl%>');"><img src="../images/change.gif" width="60" height="23" border="0"></td>
            <td width="65" align="center" valign="bottom"><a href="javascript:delete_();"><img src="../images/delete.gif" width="60" height="23" border="0"></td>
<%
	}
	else
	{
%>
            <td width="541" align="left"><img src="../images/left_d.gif" width="535" height="38"></td>
<%
	}
%>

<% 
	//if(!table_name.equals("TB_NEWS") && lvl.equals("1") && item.answer.equals("0") ) { 
	if(!table_name.equals("TB_NEWS") && lvl.equals("1") ) { 

%>
            <td width="65" align="center" valign="bottom">
            <a href="javascript:replay_();"><img src="../images/reply.gif" width="60" height="23" border="0"></td>
<% } %>
            <td width="65" align="center" valign="bottom"><a href="javascript:golist();self.close();"><img src="../images/list.gif" width="60" height="23" border="0"></td>
            <td width="29" align="right"><img src="../images/right01.gif" width="23" height="38"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  </td>
      <td></td>
        </tr>
        <tr height="5"><td bgcolor="#FFFFFF">&nbsp;</td></tr>
	</table>
</form>
</body>
</html>
