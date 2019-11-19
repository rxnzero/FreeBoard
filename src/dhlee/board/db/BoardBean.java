/**
 * 
 */
package dhlee.board.db;

import java.lang.reflect.*;
import java.io.Writer;
import java.io.Reader;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oracle.sql.*;
import oracle.jdbc.driver.* ;

import dhlee.lib.util.*;

// User Defined Class Import
import dhlee.board.serial.commitem.BoardItem;

/**
 * @author rxnzero
 *
 */
public class BoardBean {
	protected final Log logger = LogFactory.getLog(getClass());
	public static final boolean VERBOSE = true;	// 디버깅하는데 사용하도록 함...
	
	/**
	 * 
	 */
	public BoardBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 private Connection getConnection() throws Exception {
		try {
			/*
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/boardDS");
			
			return ds.getConnection();
			*/
			Properties props = new Properties();
		    props.put("user",     "eai");
		    props.put("password", "eai");
		    			
			Driver driver = null;
			String	jdbcClass = "oracle.jdbc.driver.OracleDriver" ;
			String	jdbcURL   = "jdbc:oracle:thin:@localhost:1521:XE" ;
			driver = (Driver)Class.forName(jdbcClass).newInstance();
			
			return driver.connect(jdbcURL, props);
		} catch(Exception ex) {
			throw ex ;
		} finally {
		}
		
	}


	private int setSeqNo(String table_name) throws Exception {
		Connection	con = null ;
		Statement	stmt = null ;
		ResultSet	rslt = null ;
		int			seq_no ;

		try {
			con = getConnection();
			stmt = con.createStatement() ;
			rslt = stmt.executeQuery(
				"SELECT	MAX(SEQ_NO) FROM " + table_name) ;

			if (rslt.next()) {
				seq_no = rslt.getInt(1)+1 ;
			} else {
				seq_no = 1 ;
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage()) ;
		} finally {
			if (rslt != null) rslt.close() ;
			if (stmt != null) stmt.close() ;
			if (con != null) con.close() ;
		}

		return seq_no ;
	}

	private String getSortSeq(BoardItem item) throws Exception {
		Connection			con = null ;
		Statement			pstmt = null ;
		ResultSet			rslt = null ;
		String				lvl_no = null;
		String				sql_stmt = null ;
		int					lvl = 0 ;

		lvl = item.lvl + 1 ;
		sql_stmt = 	" SELECT	SUBSTR(RTRIM(KLVL_NO), 1, " + 
					"LENGTH(RTRIM(KLVL_NO))-3)||LTRIM(TO_CHAR(SUBSTR(RTRIM" + 
					"(KLVL_NO), LENGTH(RTRIM(KLVL_NO))-2, " +
					"LENGTH(RTRIM(KLVL_NO)))+1, '000')) LLVL_NO" +
					" FROM ( SELECT MAX(LVL_NO) KLVL_NO " +
					"		 FROM 	" + item.table_name +  
					"	     WHERE	LVL_NO like '" + item.lvl_no + "%'" +
					"		 AND	SORT_SEQ = " + item.sort_seq + 
					"		 AND	LVL =" + lvl + ")" ;

		try {
			con = getConnection();
			pstmt = con.createStatement() ;
			rslt = pstmt.executeQuery(sql_stmt) ;

			if (rslt.next()) { 
				lvl_no = rslt.getString("llvl_no") ;
				if (lvl_no == null)
					lvl_no = item.lvl_no + "-001" ;
			} else {
				lvl_no = item.lvl_no + "-001" ;
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage()) ;
		} finally {
			if (rslt != null) rslt.close() ;
			if (pstmt != null) pstmt.close() ;
			if (con != null) con.close() ;
		}

		return lvl_no.trim() ;
	}

	public void writeItem(BoardItem item) throws Exception {
		Connection			con = null ;
		PreparedStatement	pstmt = null ;
		Statement			stmt = null ;
		int					seq_no ;

		try {
			seq_no = setSeqNo(item.table_name) ;

			if (item.sort_seq == 0) {
				item.lvl_no = "001" ;
				item.sort_seq = seq_no * -1 ;
				item.lvl = 1 ;
			} else {
				item.lvl_no = getSortSeq(item) ;
				item.lvl  = item.lvl + 1;
			}

			con = getConnection();
			con.setAutoCommit(false) ;
			pstmt = con.prepareStatement(
					"INSERT INTO " + item.table_name + 
					" (SEQ_NO, TITLE, AUTHOR, PASSWD, " +
					"	EMAIL, LOCATION, REG_DATE, READ_CNT, CTNT, LVL_NO, " +
					"	SORT_SEQ, LVL, ANSWER, FILE_NAME ) " +
					"VALUES ( ?, ?, ?, ?, ?, " +
					"	?, TO_CHAR(SYSDATE, 'YYYYMMDD'), 0, empty_clob(), ?," +
					"	?, ?, ?, ?) " ) ;

			pstmt.setInt(1, seq_no) ;
			pstmt.setString(2, item.title) ;
			pstmt.setString(3, item.author) ;
			pstmt.setString(4, item.passwd) ;
			pstmt.setString(5, item.email) ;
			pstmt.setString(6, item.location) ;
			pstmt.setString(7, item.lvl_no) ;
			pstmt.setInt(8, item.sort_seq) ;
			pstmt.setInt(9, item.lvl) ;


logger.debug( ">> STEP01 - insert");

			pstmt.setString(10, item.answer) ;
			pstmt.setString(11, item.file_name) ;

			pstmt.executeUpdate() ;
		} catch (Exception ex) {
			throw new Exception("fail to add item to board step 1", ex) ;
		} finally {
			try {
				if (pstmt != null) pstmt.close() ;
			} catch (SQLException ex) {
				ex.printStackTrace() ;
			}
		}
logger.debug( ">> STEP02 - update reply option");
		log("item.mode", item.mode);
		if(item.mode.equals("reply")) {
		// 질문에 대한 답변은 하나만 존재하도록 하기위해
		// 더이상 질문에 대한 답변을 작성하지 못하도록 플래그를 바꾼다.
			try {
				int quest_seq_no = item.sort_seq * -1;
				stmt = con.createStatement() ;
				stmt.executeUpdate(
					" UPDATE " + item.table_name + 
					" SET ANSWER = '1' " + 
					" WHERE	SEQ_NO = " + quest_seq_no )  ;
				log("sql", 
					" UPDATE " + item.table_name + 
					" SET ANSWER = '1' " + 
					" WHERE	SEQ_NO = " + quest_seq_no );
				stmt.close() ;

logger.debug( ">> STEP03");
			} catch (Exception ex) {
				throw new Exception("fail to add item to board step 2", ex) ;
			} finally {
				try {
					if (stmt != null) stmt.close() ;
				} catch (SQLException ex) {
					ex.printStackTrace() ;
				}
			}
		}


		ResultSet	rslt = null ;

		try {
			stmt = con.createStatement() ;
logger.debug( ">> STEP04");
			rslt = stmt.executeQuery(
				" SELECT CTNT FROM " + item.table_name +
				" WHERE SEQ_NO = " + seq_no + " FOR UPDATE") ;
		} catch (Exception ex) {
			throw new Exception("fail to add item to board step 2", ex) ;
		} finally {
			try {
				if (stmt != null) stmt.close() ;
			} catch (SQLException ex) {
				ex.printStackTrace() ;
			}
		}
logger.debug( ">> STEP06");

		try {
			stmt = con.createStatement() ;
			rslt = stmt.executeQuery(
				" SELECT CTNT FROM " + item.table_name +
				" WHERE SEQ_NO = " + seq_no + " FOR UPDATE") ;
logger.debug( ">> STEP06-1");			
			if (rslt.next()) {
logger.debug( ">> STEP06-2");					
				CLOB	ctntClob = ((OracleResultSet)rslt).getCLOB(1) ;
logger.debug( ">> STEP06-3");						
				Writer	clobWriter = ctntClob.getCharacterOutputStream() ;
logger.debug( ">> STEP06-4");						
				clobWriter.write(item.ctnt.toCharArray(), 0, 
						item.ctnt.toCharArray().length);
				clobWriter.close() ;
			}
logger.debug( ">> STEP07");
			stmt = con.createStatement() ;
			stmt.executeUpdate(
				" UPDATE	TB_BBS_MAIN " +
				" SET		TOTAL_ROW = TOTAL_ROW + 1" +
				" WHERE		BBS_ENAME = '" + item.table_name +"'")  ;
			stmt.close() ;
			con.commit() ;
logger.debug( ">> STEP08");			
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback() ;
			} catch (SQLException se) {
				se.printStackTrace() ;
			}
			throw new Exception("fail to add item to board step 3", ex) ;
		} finally {
			try {
				if (rslt != null) rslt.close() ;
				if (stmt != null) stmt.close() ;
				if (con != null) con.close() ;
			} catch (SQLException ex) {
				ex.printStackTrace() ;
			}
		}
	}

	public boolean deleteItem(String table_name, int seq_no, int sort_seq, 
		String lvl_no, String passwd, String mode) throws Exception {
		Connection			con = null ;
		PreparedStatement	pstmt = null ;
		Statement			stmt = null ;
		ResultSet			rslt = null ;
		String				ipasswd = null ;
		int					del_cnt = 0 ;

		try {
			con = getConnection();
			stmt = con.createStatement() ;
			rslt = stmt.executeQuery(
					" SELECT	PASSWD " +
					" FROM		" + table_name + 
					" WHERE		SEQ_NO = " + seq_no) ;
			
			if (rslt.next()) 
				ipasswd = rslt.getString("passwd") ;
			else {
				rslt.close() ;
				stmt.close() ;
				con.close() ;
				return false ;
			}

			if (!ipasswd.equals(passwd)) {
				rslt.close() ;
				stmt.close() ;
				con.close() ;
				return false ;
			}
			con.setAutoCommit(false) ;

			try {
				stmt = con.createStatement() ;
				del_cnt = stmt.executeUpdate (
					" DELETE FROM " + table_name + 
					" WHERE SORT_SEQ = " + sort_seq +
					" AND 	LVL_NO LIKE '" + lvl_no + "%' ") ;
			} catch (Exception ex) {
				throw new Exception("fail to delete item to board step 1", ex) ;
			} finally {
				try {
					if (stmt != null) stmt.close() ;
				} catch (SQLException ex) {
					ex.printStackTrace() ;
				}
			}

			if(mode.equals("reply")) {
			// 질문에 대한 답변은 하나만 존재하도록 하기위해
			// 더이상 질문에 대한 답변을 작성하지 못하도록 플래그를 바꾼다.
				try {
					int quest_seq_no = sort_seq * -1;
					log("quest_seq_no", quest_seq_no);
					stmt = con.createStatement() ;
					stmt.executeUpdate(
						" UPDATE " + table_name + 
						" SET ANSWER = '0' " + 
						" WHERE	SEQ_NO = " + quest_seq_no )  ;
					stmt.close() ;
				} catch (Exception ex) {
					throw new Exception("fail to delete item to board step 2", ex) ;
				} finally {
					try {
						if (stmt != null) stmt.close() ;
					} catch (SQLException ex) {
						ex.printStackTrace() ;
					}
				}
			}

			stmt = con.createStatement() ;
			stmt.executeUpdate(
				" UPDATE		TB_BBS_MAIN " +
				" SET			TOTAL_ROW = TOTAL_ROW - " + del_cnt +
				" WHERE			BBS_ENAME = '" + table_name + "'") ;

			con.commit() ;
		} catch (SQLException se) {
			throw new Exception(se.getMessage()) ;
		} catch (Exception ex) {
			ex.printStackTrace() ;
		} finally {
			try {
				if (stmt != null) stmt.close() ;
				if (pstmt != null) pstmt.close() ;
				if (con != null) con.close() ;
			} catch (Exception ex) {
				ex.printStackTrace() ;
			}
		}
		return true ;
	}

	public boolean deleteItem(BoardItem item) throws Exception {
		return deleteItem(item.table_name, item.seq_no, item.sort_seq, 
			item.lvl_no, item.passwd, item.mode) ;
	}

	public boolean updateItem(BoardItem item ) throws Exception {
		Connection			con = null ;
		PreparedStatement	pstmt = null ;
		Statement			stmt = null ;
		ResultSet			rslt = null ;
		String				passwd = null ;

		try {
			con = getConnection();

			stmt = con.createStatement() ;
			rslt = stmt.executeQuery(
				" SELECT	PASSWD " +
				" FROM		" + item.table_name + 
				" WHERE		SEQ_NO = " + item.seq_no) ;

			if (rslt.next())
				passwd = rslt.getString("passwd") ;
			else {
				rslt.close() ;
				stmt.close() ;
				con.close() ;
				return false ;
			}

			if (!passwd.equals(item.passwd)) {
				rslt.close() ;
				stmt.close() ;
				con.close() ;
				return false ;
			}
			
			pstmt = con.prepareStatement(
				" UPDATE	" + item.table_name +
				" SET		TITLE = ?, " +
				"			AUTHOR = ?, PASSWD = ?, EMAIL = ?, " +
				"			CTNT = empty_clob() " +
				" WHERE		SEQ_NO = ? ") ;

			pstmt.setString(1, item.title) ;
			pstmt.setString(2, item.author) ;
			pstmt.setString(3, item.passwd) ;
			pstmt.setString(4, item.email) ;
			pstmt.setInt(5, item.seq_no) ;

			pstmt.executeUpdate() ;
			pstmt.close() ;

			con.setAutoCommit(false) ;
			pstmt = con.prepareStatement(
				" SELECT	CTNT FROM " + item.table_name + 
				" WHERE SEQ_NO = ? FOR UPDATE") ;

			pstmt.setInt(1, item.seq_no) ;

			rslt = pstmt.executeQuery() ;

			if (rslt.next()) {
				String	tmp = new String(item.ctnt) ;
				CLOB	ctntClob = ((OracleResultSet)rslt).getCLOB(1) ;
				Writer	clobWriter = ctntClob.getCharacterOutputStream() ;
				clobWriter.write(tmp.toCharArray(),0,tmp.toCharArray().length);
				clobWriter.close() ;
			}
			con.commit() ;
		} catch (SQLException se) {
			throw new Exception(se.getMessage()) ;
		} catch (Exception ex) {
			ex.printStackTrace() ;
		} finally {

			try {
				if (rslt != null) rslt.close() ;
				if (pstmt != null) pstmt.close() ;
				if (con != null) con.close() ;
			} catch (Exception ex) {
				ex.printStackTrace() ;
			}
		}
		return true ;
	}

	public Vector getBoardList(String table_name, int pk, int max_row, String findMode, String findStr) 
		throws Exception {
		Connection				conn = null ;
		PreparedStatement		pstmt = null ;
		Statement				stmt = null ;
		ResultSet				rslt = null ;
		Vector					rowset = new Vector() ;
		int						rowcnt = 0, idx = 0, total_row = 0 ;
		int						disp_no = 0 ;
		boolean					first = true ;
		
		StringBuffer selQuery = new StringBuffer();
		
		try {
			//conn = getConnection();
			conn = getConnection();

			//stmt = conn.createStatement() ;
            //
			//rslt = stmt.executeQuery(
			//	" SELECT	TOTAL_ROW " +
			//	" FROM		TB_BBS_MAIN " +
			//	" WHERE		BBS_ENAME = '" + table_name + "'") ;
            //
			//if (rslt.next()) 
			//	total_row = rslt.getInt("total_row") ;
			//else 
			//	total_row = 0 ;
            //
			//stmt.close() ;
			
			if(findStr.trim().length() == 0) {
				findMode = "";
			}
			
			String condition = " SORT_SEQ < 0 ";
			if(findMode.equals("1")) {
				condition += "	AND		AUTHOR LIKE '%"+HFormat2.toLikeQuote(findStr)+"%' ESCAPE '/' ";
			}
			// 작성일
			else if(findMode.equals("2")) {
				condition += "	AND		REG_DATE LIKE '%"+HFormat2.toLikeQuote(findStr)+"%' ESCAPE '/' ";
			}
			// 제목으로 조회할 때
			else if(findMode.equals("3")) {
				condition += "	AND		SORT_SEQ IN ( SELECT SORT_SEQ FROM " + table_name
							+ "	WHERE TITLE LIKE '%"+HFormat2.toLikeQuote(findStr)+"%' ESCAPE '/' ) ";
			}
			else {
				;
			}
			
			selQuery.append(" SELECT	A.PK, A.TITLE, A.AUTHOR, A.EMAIL, A.LOCATION, A.REGDATE, ");
			selQuery.append("			A.READ_CNT, A.SEQ_NO, A.LVL_NO, A.LVL, A.SORT_SEQ, A.FILE_NAME, B.TOTAL_ROW ");
			selQuery.append(" FROM ( SELECT	ROWNUM PK, TITLE, AUTHOR, EMAIL, LOCATION, ");
			selQuery.append(" 			 TO_CHAR(TO_DATE(REG_DATE,'YYYYMMDD'),'YYYY-MM-DD') ");
			selQuery.append("			   REGDATE, READ_CNT, SEQ_NO, LVL_NO, LVL, SORT_SEQ, FILE_NAME ");
			selQuery.append(" 	     FROM	" + table_name);
			selQuery.append("	       WHERE	" + condition);
			// 작성자
			selQuery.append("	) A,");
			selQuery.append("	(SELECT COUNT(*) TOTAL_ROW FROM "+ table_name);
			selQuery.append("	WHERE " + condition);
			selQuery.append("	) B ");
			selQuery.append(" WHERE	A.PK BETWEEN ? AND ? ");
logger.debug(" board list query = [" + selQuery.toString() + "]");
logger.debug(" board list query pk  = [" + pk+ "]");

			
			pstmt = conn.prepareStatement(selQuery.toString()) ; 

			pstmt.setInt(1, pk + 1) ;
			pstmt.setInt(2, pk + max_row) ;

			rslt = pstmt.executeQuery() ;			
			
			//if (pk == 0)
			//	disp_no =  total_row ;
			//else
			//	disp_no =  total_row - pk ;
			
			while(rslt.next()) {
				//if (++rowcnt > max_row) {
				//	 continue;
				//}
				//else {
					BoardItem item = new BoardItem() ;
					item.pk = rslt.getInt("pk") ;
					item.title = rslt.getString("title") ;
					item.author = rslt.getString("author") ;
					item.email = rslt.getString("email") ;
					item.location = rslt.getString("location") ;
					item.regdate = rslt.getString("regdate") ;
					item.readcnt = rslt.getInt("read_cnt") ;
					item.lvl = rslt.getInt("lvl") ;
					item.lvl_no = rslt.getString("lvl_no") ;
					item.seq_no = rslt.getInt("seq_no") ;
					item.sort_seq = rslt.getInt("sort_seq") ;
					item.file_name = rslt.getString("file_name") ; // 실제파일명
					item.table_name = table_name ;
					item.total_row = rslt.getInt("total_row") ;
					item.disp_no = item.total_row - item.pk + 1;
					rowset.addElement(item) ;
				//}
			}

			//for (int i = 0; i < rowset.size(); i++) {
			//	BoardItem item = (BoardItem)rowset.elementAt(i) ;
			//	item.disp_no = disp_no - i ;
			//	logger.debug(" item.disp_no = [" + item.disp_no + "]");
			//	item.total_row = rowcnt ;
			//}

		} catch (SQLException se) {
			throw new Exception("sql error occured during make list", se) ;
		} catch (Exception ex) {
			throw new Exception("general error occured during make list", ex) ;
		} finally {
			try {
				if (stmt != null) stmt.close() ;
				if (rslt != null) rslt.close() ;
				if (pstmt != null) pstmt.close() ;
				if (conn != null) conn.close() ;
			} catch (SQLException se) { 
				se.printStackTrace() ;
			}
		}
		return rowset ;
	}

	public Vector getBoardList(BoardItem item, String findMode, String findStr) throws Exception {
		return getBoardList(item.table_name, item.pk, item.max_row, findMode, findStr) ;
	}

	public BoardItem getBoardItem(String table_name, int seq_no) 
		throws Exception {
		Connection			conn = null ;
		PreparedStatement	pstmt = null ;
		Statement			stmt = null ;
		ResultSet			rslt = null ;
		BoardItem			item = new BoardItem() ;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
				" SELECT	CTNT, TITLE, AUTHOR, NVL(EMAIL, ' ') EMAIL, " +
				"			NVL(LOCATION, ' ') LOCATION, "+
				"			TO_CHAR(TO_DATE(REG_DATE,'YYYYMMDD'),'YYYY-MM-DD') REGDATE, READ_CNT, LVL_NO, LVL, SORT_SEQ, " +
				"			ANSWER, FILE_NAME, PASSWD" +
				" FROM		" + table_name + 
				" WHERE		SEQ_NO = ? ") ;

			pstmt.setInt(1, seq_no) ;

			rslt = pstmt.executeQuery() ;

			if (rslt.next()) {
				item.init() ;
				item.title = rslt.getString("title") ;
				item.author = rslt.getString("author") ;
				item.passwd = rslt.getString("passwd") ;
				item.email = rslt.getString("email") ;
				item.location = rslt.getString("location") ;
				item.regdate = rslt.getString("regdate") ;
				item.readcnt = rslt.getInt("read_cnt") ;
				item.seq_no = seq_no;
				item.lvl_no = rslt.getString("lvl_no") ;
				item.lvl = rslt.getInt("lvl") ;
				item.sort_seq = rslt.getInt("sort_seq") ;

				//이찬식 추가
				item.answer = rslt.getString("answer") ;
				item.file_name = rslt.getString("file_name") ;

				CLOB ctntClob = ((OracleResultSet)rslt).getCLOB(1) ;
				Reader clobStream = ctntClob.getCharacterStream() ;
				StringBuffer ctntBuff = new StringBuffer() ;
				int	 nchars = 0 ;
				char[] buffer = new char[10] ;

				while((nchars = clobStream.read(buffer)) != -1) 
					ctntBuff.append(buffer, 0, nchars) ;

				clobStream.close() ;
				item.ctnt = ctntBuff.toString() ;

				stmt = conn.createStatement() ;
				stmt.executeUpdate(
						" UPDATE	" + table_name + 
						" SET		READ_CNT = READ_CNT + 1 " +
						" WHERE		SEQ_NO = " + seq_no) ;
				stmt.close() ;
			}
		} catch (SQLException se) {
			throw new Exception("sql error occured during fetch item", se) ;
		} catch (Exception ex) {
			throw new Exception("general error occured during fetch item", ex) ;
		} finally {
			try {
				if (stmt != null) stmt.close() ;
				if (rslt != null) rslt.close() ;
				if (pstmt != null) pstmt.close() ;
				if (conn != null) conn.close() ;
			} catch (SQLException se) { 
				se.printStackTrace() ;
			}
		}
		return item ;
	}

	public BoardItem getBoardItem(BoardItem item) throws Exception {
		return getBoardItem(item.table_name, item.seq_no) ;
	}
	
	/**
	 * ResultSet, PreparedStatement, Connection 리소스들을 해제한다.
	 *
	 * @param		rs      	        		ResultSet
	 * @param		ps         		            PreparedStatement 
	 * @param		con         		        Connection
	 */
	protected void cleanup(ResultSet rs, PreparedStatement ps, Connection con) {
		if(rs != null)
			try{ rs.close();}	catch(Exception fe1){fe1.printStackTrace();}
		if(ps != null)
			try{ ps.close();}	catch(Exception fe2){fe2.printStackTrace();}
		if(con != null)
			try{ con.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}


	/**
	 * Connection 리소스들을 해제한다.
	 *
	 * @param		con         		        Connection
	 */

	protected void cleanup(Connection con) {
		if(con != null)
			try{ con.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}

	/**
	 * PreparedStatement, Connection 리소스들을 해제한다.
	 *
	 * @param		ps         		            PreparedStatement 
	 * @param		con         		        Connection
	 */
	protected void cleanup(PreparedStatement ps, Connection con) {
		if(ps != null)
			try{ ps.close();}	catch(Exception fe2){fe2.printStackTrace();}
		if(con != null)
			try{ con.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}

	/**
	 * ResultSet, PreparedStatement 리소스들을 해제한다.
	 *
	 * @param		rs         		            ResultSet 
	 * @param		ps	         		        PreparedStatement
	 */
	protected void cleanup(ResultSet rs, PreparedStatement ps) {
		if(rs != null)
			try{ rs.close();}	catch(Exception fe2){fe2.printStackTrace();}
		if(ps != null)
			try{ ps.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}

	/**
	 * PreparedStatement 리소스들을 해제한다.
	 *
	 * @param		ps	         		        PreparedStatement
	 */
	protected void cleanup(PreparedStatement ps) {
		if(ps != null)
			try{ ps.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}

	/**
	 * PreparedStatement 리소스들을 해제한다.
	 *
	 * @param		stmt	         		        Statement
	 */
	protected void cleanup(Statement stmt) {
		if(stmt != null)
			try{ stmt.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}

	/**
	 * ResultSet, CallableStatement, Connection 리소스들을 해제한다.
	 *
	 * @param		rs      	        		ResultSet
	 * @param		cs         		            CallableStatement 
	 * @param		con         		        Connection
	 */
	protected void cleanup(ResultSet rs, CallableStatement cs, Connection con){
		if(rs != null)
			try{ rs.close();}	catch(Exception fe1){fe1.printStackTrace();}
		if(cs != null)
			try{ cs.close();}	catch(Exception fe2){fe2.printStackTrace();}
		if(con != null)
			try{ con.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}
	
	/**
	 * ResultSet, CallableStatement리소스들을 해제한다.
	 *
	 * @param		rs      	        		ResultSet
	 * @param		cs         		            CallableStatement 
	 */
	protected void cleanup(ResultSet rs, CallableStatement cs){
		if(rs != null)
			try{ rs.close();}	catch(Exception fe1){fe1.printStackTrace();}
		if(cs != null)
			try{ cs.close();}	catch(Exception fe2){fe2.printStackTrace();}
	}

	/**
	 * CallableStatement, Connection 리소스들을 해제한다.
	 *
	 * @param		cs         		            CallableStatement 
	 * @param		con         		        Connection
	 */
	protected void cleanup(CallableStatement cs, Connection con){
		if(cs != null)
			try{ cs.close();}	catch(Exception fe2){fe2.printStackTrace();}
		if(con != null)
			try{ con.close();}	catch(Exception fe3){fe3.printStackTrace();}
	}
	
	protected void log(String arg) {
		if (VERBOSE) logger.debug(arg);
	} 

	/* You might also consider using WebLogic's log service
	 *
	 * @param arg1               			String
	 * @param arg2               			String
	 */
	protected void log(String arg1, String arg2) {
		if (VERBOSE) logger.debug(arg1 + "=[" + arg2 + "]");
	} 

	/* You might also consider using WebLogic's log service
	 *
	 * @param arg1               			String
	 * @param arg2               			int
	 */
	protected void log(String arg1, int arg2) {
		if (VERBOSE) logger.debug(arg1 + "=[" + arg2 + "]");
	} 

	/* WebLogic's log service. Object의 멤버와 멤버값을 프린트 한다
	 *
	 * @param ob                			Object
	 */
	protected void log(Object ob){
		if (!VERBOSE) return;

		try{
			Class cls = ob.getClass();
			Field[] fields = cls.getFields();
			int max_filed = fields.length;
			for(int i = 0; i < max_filed; i++){
				logger.debug(fields[i].getName() + "=[" + fields[i].get(ob)+ "]" );
			}
        }catch (Exception ce){
            ce.printStackTrace();
        }
	}
}
