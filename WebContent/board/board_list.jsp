<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<%@ page import="java.util.*" %>
<%@ page import="dhlee.board.db.*" %>
<%@ page import="dhlee.board.serial.commitem.*" %>
<%@ page import="dhlee.lib.util.*" %>

<%!
	public String Uni2Ksc(String src) {
		String	ret = src ;
// 		try {
// 			if (src == null) return "" ;
// 			ret = new String(src.getBytes("8859_1"), "KSC5601") ;
// 		} catch (Exception ex) {
// 			ex.printStackTrace() ;
// 		}
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

    /**
    * �Խ������¿��� ����Ʈ ���ؽ� ���ڿ��� �����.. <BR>
    * @param    Properties properties which related with this method    <BR>
    *           1.LISTPAGE   ����Ʈ�������� 							<BR>
    *           2.TPAGE      ������������ �����						<BR>
    *           3.TPAGENO    ����������									<BR>
    *           4.CPAGE      ������������ �����						<BR>
    *           5.CPAGENO    ����������									<BR>
    *           6.RCNT       ��ȸ�� �� ROW ��							<BR>
    * @param    String[]   ��ȸ����										<BR>
    * @param    int        �������� ���� ��								<BR>
    * @param    int        �������ε����� ũ��							<BR>
    * @return   String     HTML      									<BR>
    */
    public static String makePageIndexStr(Properties param, String[] filters,int pageRow,int pageWidth)
    {
        int    totPage = 0;

        String listpage  = param.getProperty("LISTPAGE");
        String tpage     = param.getProperty("TPAGE");
        int    indextop  = Integer.parseInt(param.getProperty("TPAGENO"));
        String cpage     = param.getProperty("CPAGE");
    	int    pageindex = Integer.parseInt(param.getProperty("CPAGENO"));
    	int rCnt 		 = Integer.parseInt(param.getProperty("RCNT"));

		String added = "";

		for(int l=0; l<filters.length; ) {
			if(filters[l].length() > 0) {
				added = added +"&" + filters[l] + "=" + filters[l+1];
			}
			l+=2;
		}

		String strPages  = "";

		int    indexCount = 0;

		int i=0;

		if (rCnt < pageRow) {
			strPages = "<font color=silver>[1]</font>";
			totPage = 1;
		}
		else {
			totPage = (rCnt / pageRow);
			totPage +=((rCnt % pageRow)>0)?1:0;

			//strPages = "��ü " + totPage + "������ �� " + pageindex + "������ ";

			// ���� �ε���������
			if( (indextop - pageWidth) > 0) {
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop - pageWidth) +
			                "&"+ cpage +"=" + (indextop - pageWidth)+"&pk=" + (indextop - 1 -pageWidth)*pageRow;
			    if (added.length() > 0) {
			    	strPages+= added;
			    }
			    strPages += "\" onMouseOver=\"window.status=('���� �ε�����������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"../images/prev2.gif\" width=\"30\" height=\"14\" border=\"0\" align=\"absmiddle\"></a> ";
			}

			/* ���� ������
			if((pageindex - 1) < indextop) {
				if( (indextop - pageWidth) > 0) {
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop - pageWidth) +
				                "&"+ cpage +"=" + (pageindex - 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }
				    strPages += "\" onMouseOver=\"window.status=('������������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_01.gif\" border=\"0\"></a> ";
				}
			}
			else {
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop) +
			                "&"+ cpage +"=" + (pageindex - 1);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }
			    strPages += "\" onMouseOver=\"window.status=('������������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_01.gif\" border=\"0\"></a> ";
			}
			*/

			// ������ ��ȣ
			for(int pg=indextop; (pg<=totPage) && (indexCount<pageWidth); pg++) {
				indexCount++;
				if(pg==pageindex) {
					strPages += "<font color=silver>[" + pg + "] </font>";
				}
				else {
					strPages += "[<a href=\""+listpage+"?"+tpage+"=" + indextop +
											"&"+cpage+"=" + pg +"&pk=" + (pg - 1)*pageRow;
					if (added.length() > 0) {
			    	strPages+= added;
			    	}
			    	strPages += "\">" + pg + "</a>] ";
				}
			}

			/* ���� ������
			if( (pageindex + 1) >= (indextop+pageWidth)) {
				if( (pageindex + 1) <= totPage) {
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop + pageWidth) +
				                "&"+ cpage +"=" + (pageindex + 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }
				    strPages += "\" onMouseOver=\"window.status=('������������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_01.gif\" border=\"0\"></a> ";
				}
			}
			else {
				if( (pageindex + 1) <= totPage) {
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop) +
				                "&"+ cpage +"=" + (pageindex + 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }
				    strPages += "\" onMouseOver=\"window.status=('������������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_01.gif\" border=\"0\"></a> ";
				}
			}
			*/
			// ���� �ε���������
			if( (indextop + pageWidth) <= totPage) {
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop + pageWidth) +
			                                   "&"+cpage+"=" + (indextop + pageWidth)+"&pk=" + (indextop + pageWidth -1)*pageRow;
			    if (added.length() > 0) {
			    	strPages+= added;
			    }
			    strPages += "\" onMouseOver=\"window.status=('���� �ε�����������'); return true;\" onMouseOut=\"window.status=(''); return true;\" ><img src=\"../images/next2.gif\" width=\"30\" height=\"14\" border=\"0\" align=\"absmiddle\"></a> ";
			}
		}
    	return strPages;
    }
%>
<%@ include file="/jsp/CheckMember.jsp" %>
<html>
<head>
<title>�Խ��� ���</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../css/board.css" rel=stylesheet type=text/css>

<script language="javascript">
<!--//
function search() {
	var f = document.flist;
	f.action="board_list.jsp";
	f.submit();
}

function insert() {
	var xpos = (screen.width - 750) / 2;
	var ypos = (screen.height - 460) / 2;
	var loc = 'board_write_form.jsp?' + getParamList();
	openWin("popWin01", loc, xpos, ypos, 750, 460, "no", "no", "no");
}

function showDetail(seq_no, lvl) {
	var f = document.forms[0];
	f.seq_no.value=seq_no;
	f.lvl.value=lvl;

	var xpos = (screen.width - 750) / 2;
	var ypos = (screen.height - 460) / 2;
	var loc = 'board_display.jsp?' + getParamList();
	openWin("popWin01", loc, xpos, ypos, 750, 460, "no", "no", "no");
}

function getParamList() {
	var f = document.forms[0];
	var paramList = null;
	paramList =
		'bbs_name='+f.bbs_name.value+
		'&seq_no='+f.seq_no.value+
		'&lvl='+f.lvl.value+
		'&mode='+f.mode.value+
		'&pk='+f.pk.value+
		'&seq_no='+f.seq_no.value+
		'&lvl='+f.lvl.value;
	return paramList;
}

function openWin(windowName, loc, left, top, width, height, status, scrollbars, resizable) {
	var toolbar = 'no';
	var menubar = 'no';
	var propertiesStr =  'left='		 + left
						+',top='		 + top
						+',width='		 + width
						+',height='	 	 + height
						+',toolbar='	 + toolbar
						+',menubar='	 + menubar
						+',status='	     + status
						+',scrollbars='  + scrollbars
						+',resizable='	 + resizable;

	openwin = window.open(loc, windowName, propertiesStr);
	openwin.focus();
}
//-->
</script>
</head>

<%
    String      table_name = null ;
    String      findMode = null ;
    String      findStr = null ;
    int         pk = 0 ;
    int         max_row = 10, total_row = 0, pagecnt = 0;
	int			maxViewPageList = 5;
	int			curPageList = 0;
    Vector      ret = new Vector() ;

    table_name = request.getParameter("bbs_name") ;
    pk = request.getParameter("pk") == null ? 0 :
    	Integer.parseInt(request.getParameter("pk")) ;

	findMode = request.getParameter("findMode");
	findStr = request.getParameter("findStr");

    if(findMode == null) {
    	findMode = "0";
    }

    if(findStr == null) {
    	findStr = "";
    }
    else {
    	findStr = findStr.trim();
    }

	try {
		curPageList = Integer.parseInt(request.getParameter("cur_page_list"));

	} catch(Exception e) {
		curPageList = 1;
	}

	BoardBean client = new BoardBean();
    ret = client.getBoardList(table_name, pk, max_row, findMode, findStr) ;
%>


<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0" text="#000000" link="#704981">
<form name="flist" method="get" action="board_list.jsp">
<input type="hidden" name="bbs_name" value="<%= table_name %>">
<input type="hidden" name="seq_no" value="">
<input type="hidden" name="lvl" value="">
<input type="hidden" name="mode" value="new">
<input type="hidden" name="pk" value="<%= pk %>">
<table width="707" border="0" cellpadding="0" cellspacing="0" height="100%">
<tr>
    <td width="707" align="center" bgcolor="#FFFFFF" valign="top" height="100%">
<div align="center">
  <table width="615" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0">

    <tr valign="top">
      <td height="64"><img src="../images/<%=table_name%>.gif" ></td>
    </tr>

    <tr>
      <td><img src="../images/topimg.gif" width="700" height="5"></td>
    </tr>
    <tr>
      <td height="10">
        <table width="700" border="0" cellspacing="1" cellpadding="0" bgcolor="#CCCCCC">
          <tr bgcolor="#FFFFFF">
            <td>
              <table width="698" border="0" cellspacing="1" cellpadding="1">
                <tr align="center">
                  <td height="20" width="50" bgcolor="#EEEEEE">��ȣ</td>
                  <td height="20" width="35" bgcolor="#EEEEEE">ȭ��</td>
                  <td height="20" width="290" bgcolor="#EEEEEE">����</td>
                  <td height="20" width="80" bgcolor="#EEEEEE">�ۼ���</td>
                  <td height="20" width="90" bgcolor="#EEEEEE">����</td>
                  <td height="20" width="70" bgcolor="#EEEEEE">��ȸ��</td>
                </tr>
                <tr>
                  <td height="1" bgcolor="#CCCCCC"></td>
                  <td height="1" bgcolor="#CCCCCC"></td>
                  <td height="1" bgcolor="#CCCCCC"></td>
                  <td height="1" bgcolor="#CCCCCC"></td>
                  <td height="1" bgcolor="#CCCCCC"></td>
                  <td height="1" bgcolor="#CCCCCC"></td>
                </tr>
<%
    for (int i = 0; i < max_row && i < ret.size(); i++) {
        BoardItem   item = (BoardItem)ret.elementAt(i) ;
        //item.ItemUni2Ksc() ;
//         item.ItemKsc2Uni();
		total_row = item.total_row ;
		pagecnt = (total_row / max_row) + (total_row % max_row == 0 ? 0 : 1) ;
		if (i == 0) pk = item.pk - 1 ;
%>
<%	if (i % 2 != 0) {	 %>
                <tr onMouseOver="this.style.backgroundColor='#FDF4EE'" onMouseOut="this.style.backgroundColor='white'">
<%	} else {		 %>
                <tr  bgcolor="#F9F9F9" onMouseOver="this.style.backgroundColor='#FDF4EE'" onMouseOut="this.style.backgroundColor='#F9F9F9'">
<%	}			%>

                  <td height="20" align="center"><%=item.disp_no%></td>
<%
	String saveFileName = null;
	if(item.location == null) {
		saveFileName="";
	}
	else {
		int pointIndex = item.location.lastIndexOf("/");
		saveFileName = item.location.substring(pointIndex+1, item.location.length());
	}

	if (saveFileName.trim().length() != 0) { %>
                  <td align="center"><a href="<%= request.getContextPath() %>/download?filename=<%=Uni2Ksc(saveFileName)%>&tofilename=<%=Uni2Ksc(item.file_name)%>"><img src="../images/attach.gif" width="11" height="11" border="0"></a></td>
<%	} else {		%>
                  <td align="center">&nbsp;</td>
<%	}			%>

<%	if(item.lvl == 1) {		%>
                  <td height="24"><a href="javascript:showDetail('<%=item.seq_no%>', '<%=item.lvl%>');"><%=Uni2Ksc(item.title)%></a></td>
<%	} else {			%>
		  <td height="20">
<%	for (int j = 0; j < item.lvl-1; j++) {	%>
		&nbsp;
<%	}					%>
		 <img src="../images/re.gif" width="16" height="20">
                  <a href="javascript:showDetail('<%=item.seq_no%>', '<%=item.lvl%>');"><%=Uni2Ksc(item.title)%></a></td>
<% } %>

<%	if (item.email.trim().length() == 0) { %>
                  <td align="center"><%=Uni2Ksc(item.author)%></td>
<%	} else { %>
				  <td align="center"><a href="mailto:<%=item.email%>?subject=Re:<%=Uni2Ksc(item.title)%>" onMouseOver="window.status=('Send mail to <%=Uni2Ksc(item.author)%> (<%=item.email%>)'); return true;" onMouseOut="window.status=''; return true;"><%=Uni2Ksc(item.author)%></a>
				  </td>
<%	}		 %>
                  <td align="center"><%=item.regdate%></td>
                  <td align="center"><%=item.readcnt%></td>
                </tr>
<%}%>
              </table>
            </td>
          </tr>
<%--
	���̺��� ������ ȭ�鿡 �ѷ��ְ� �������ε����� �ۼ��Ѵ�.
--%>
<%
	int pageRow = 10;
	int pageWidth = 5;
	int    indextop  = 1;
	int    pageindex = 1;
	String pageStart = null;
	String spage     = null;
	String strPages = "";
	int rCnt = 0;

	pageStart = (String)request.getParameter("indextop");
	spage     = (String)request.getParameter("pageindex");

	if(pageStart != null) indextop=Integer.parseInt(pageStart);
	if(spage != null) pageindex=Integer.parseInt(spage);

	// ��������
	String[] orders = new String[2];
	String order = "no";
	orders[0] = "order";
	orders[1] = order;

	// ��ȸ����
	String[] filters = new String[6];
	filters[0] = "bbs_name";
	filters[1] = table_name;
	filters[2] = "findMode";
	filters[3] = findMode;
	filters[4] = "findStr";
	filters[5] = findStr;

	Properties pageparam = new Properties();
	pageparam.setProperty("LISTPAGE","board_list.jsp");
	pageparam.setProperty("TPAGE","indextop");
   	pageparam.setProperty("TPAGENO",Integer.toString(indextop));
	pageparam.setProperty("CPAGE","pageindex");
	pageparam.setProperty("CPAGENO",Integer.toString(pageindex));
	pageparam.setProperty("RCNT",Integer.toString(total_row));

	strPages = makePageIndexStr(pageparam,filters,pageRow,pageWidth);
%>
          <tr bgcolor="#FFFFFF" align="center">
            <td height="25"><%= strPages %></td>
          </tr>
          <tr bgcolor="#FFFFFF" align="right">
            <td height="30">
              <table width="346" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="76" height="30"><img src="../images/serachtitle.gif" width="76" height="30"></td>
                  <td width="80" align="center">
                    <select name="findMode">
                      <option value="1" <%= findMode.equals("1")? "selected":"" %>>�ۼ���</option>
                      <option value="2" <%= findMode.equals("2")? "selected":"" %>>��¥</option>
                      <option value="3" <%= findMode.equals("3")? "selected":"" %>>����</option>
                    </select>
                  </td>
                  <td width="100">
                    <input type="text" name="findStr" size="12" maxlength="20" value="<%=findStr%>">
                  </td>
                  <td width="90"><a href="javascript:search();"><img src="../images/serach.gif" width="60" height="23" border="0"></a></td>
                </tr>
              </table>

            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="bottom">
      <td height="15">
        <table width="700" border="0" cellspacing="0" cellpadding="0">
          <tr>
<%
        String  email = (String)session.getAttribute("mail_addr") ;
	if(email == null || email.trim().length() < 1 ) {
		email = "";
	}

	if(table_name.equals("TB_NEWS") && email.trim().length() < 1 ) {
%>
        <!--    <td width="65" valign="bottom" align="center"><img src="../images/write.gif" width="60" height="23" border="0"></td>
		    <td width="29" align="right"><img src="../images/right01.gif" width="23" height="38"></td>-->

<%
	}
	else {
%>
            <td width="606" align="left"><img src="../images/left_l.gif" width="600" height="38"></td>
            <td width="65" valign="bottom" align="center"><a href="javascript:insert();"><img src="../images/write.gif" width="60" height="23" border="0"></a></td>
            <td width="29" align="right"><img src="../images/right01.gif" width="23" height="38"></td>
<%
	}
%>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</td>
    <td></td>
      </tr>
      </table>
</form>
</body>
</html>
