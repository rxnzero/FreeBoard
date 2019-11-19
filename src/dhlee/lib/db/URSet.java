package dhlee.lib.db;

import java.util.*;
import java.sql.*;

/**
 * <PRE>
 * Filename	: URSet.java 														<BR>
 * Class	: URSet 															<BR>
 * Function	: 																	<BR>
 * Comment	: ResultSet을 Vector형태로 바꾸어준다. 								<BR>
 * History	: 10/25/1999, 김현찬, v1.0, 최초작성 								<BR>
 *            12/20/1999, 김현찬, v1.1, sort된것에 한해서 group 시키는거 추가 	<BR>
 *            03/31/2000, 김정우, v1.2, move() method 추가 						<BR>
 *            10/06/2000, 김기원, v1.3, null을 zero space로 return				<BR>
 * </PRE>
 * @version	1.3
 * @author 	Kim Hyun Chan
 * @company	Copyright (c) 2000 by At Soft Corp. All Rights Reserved.
 */
public class URSet {
	/**
	 * DB의 조회결과를 저장하는 Vector 자료형
	 */
	protected Vector 	Result;				
	protected Vector	group;
	protected String[] 	ColumnName;			
	/**
	 * DB에서 조회된 Column의 수
	 */
	protected int		ColumnCount;		
	/**
	 * Vector내의 현재 참조하는 위치
	 */
	protected int		row = 0;			
	/**
	 * 조회된 데이터 Record 수
	 */
	protected int		RowCount = 0;		
	
	private Hashtable ht = new Hashtable();
	
	/**
	 * boolean형의 에러구분
	 */
	private boolean   error 	= true;
	/**
	 * String형의 에러메시지
	 */
	private String	  errMsg	= "";

	
	/**
	 * 생성자로써 default값을 지정한다.  	<BR><BR>
	 * (사용예) URSet uRs  =  new URSet();		<BR><BR>
	 *
	 * @param	N/A
	 * @return 	URSet	URSet
	 */
	public URSet() {
		
		this.Result 		= null;
		this.group			= null;
		this.ColumnCount	= 0;
		this.row			= 0;
	}

	
	/**
	 * 생성자로써 ResultSet을 받아 URSet을 생성한다.  	<BR><BR>
	 * (사용예) URSet uRs  =  new URSet(rs);				<BR><BR>
	 *
	 * @param 	rs	ResultSet
	 * @return 	URSet	URSet
	 * @exception	SQLException	에러발생시 SQLException 발생
	 */
	public URSet(ResultSet rs) throws SQLException{
		
		try {
			if (rs != null) {
				this.Result 	= makeVector(rs);
				this.RowCount	= this.Result.size();
				this.error 		= false;
			}
		}
		catch (SQLException e){
			this.error 	= true;
			this.errMsg = " ResultSet 조합중 Error : " + e.toString();
			System.out.println(e.getMessage());
		
		}

	}

	/**
	 * 생성자로써 Vector와 label을 받아 URSet을 생성한다.
	 *
	 * @param 	Value 	Vector
	 * @param	labels  String[]
	 * @return 	N/A
	 */
	public URSet(Vector Value,String[] labels) {
		
			makeVector(labels);
			this.Result		= Value;
			this.RowCount	= this.Result.size();
			this.error 		= false;

	}


	
	/**
	 * 생성자로써 Vector를 만들어내며, Group정보를 기록한다. Sort된 것만 사용바람. <BR>
	 *
	 * @param 	rs	ResultSet			<BR>
	 * @param 	Flag int				<BR>
	 * @return 	N/A
	 * @exception	SQLException	에러발생시 SQLException 발생
	 */
	public URSet(ResultSet rs,int Flag) throws SQLException{
		
		try {
			this.Result 	= makeVector(rs);
			this.RowCount	= this.Result.size();
			makeGroup();			// group화 한다.
			this.error 		= false;
		}
		catch (SQLException e){
			this.error 	= true;
			this.errMsg = " ResultSet 조합중 Error : " + e.toString();
			System.out.println(e.getMessage());
		
		}

	}
	
	
	
	/**
	 * Sort되어온것의 group내의 숫자를 알려준다. <BR>
	 *
	 * @param 	N/A					<BR>
	 * @return 	N/A					<BR>
	 */
	private void makeGroup() {
		
		if ( this.Result == null ) return;
		
		int tmpSize 	= this.Result.size();	
		
		int count = 1;
		
		for ( int i = 1; i < tmpSize ; i ++ ) {
			Vector vt1 = new Vector();
			Vector vt2 = new Vector();
			
			
			vt1			= (Vector)this.Result.elementAt(i-1);
			vt2			= (Vector)this.Result.elementAt(i);
			
			String tmp1 = (String)vt1.elementAt(0);
			String tmp2 = (String)vt2.elementAt(0);
			
			
			if ( tmp1.equals(tmp2) ) count ++ ;
			else {
				group.addElement(new Integer(count));
				count 	= 1;
			}
		} // end of for
		
		
	}	// end of makeGroup
	

	/**
	 * Group의 전체 사이즈를 넘긴다. 	<BR>
	 *
	 * @param	N/A						<BR>
	 * @return 	int total group Size	<BR>
	 */
	public int getGroupSize() {
		return this.group.size();
	}	// end of getGroupSize()
	
	
	
	/**
	 * Group에서 해당 index의 사이즈를 넘긴다.. <BR>
	 *
	 * @param 	index int						<BR>
	 * @return 	int  해당 index의 size   	<BR>
	 */
	public int getGroupSize(int index) {
		Integer tmp = (Integer)group.elementAt(index);
		
		return tmp.intValue();
	}	// end of getGroupSize(int index)
	
	
	/**
	 * 에러 구분과 메세지를 지정한다. 				<BR><BR>
	 * (사용예) uRs.setErrMsg(true, "에러발생"); 	<BR><BR>
	 *
	 * @param 	value boolean		<BR>
	 * @param	msg	  String		<BR>
	 * @return 	N/A					<BR>
	 */
	public void setErrMsg(boolean value, String msg) {
		this.error 	= value;
		this.errMsg = msg;
	}
	
	
	/**
	 * 에러 구분을 가져온다.		<BR><BR>
	 * (사용예) boolean lb_Error = uRs.getError(); 	<BR><BR>
	 *
	 * @param 	N/A					<BR>
	 * @return 	boolean				<BR>
	 */
	public boolean getError() {
		return this.error;
	}

	
	/**
	 * 에러 메세지를  가져온다.		<BR><BR>
	 * (사용예) boolean ls_ErrMsg = uRs.getErrorMsg(); 	<BR><BR>
	 *
	 * @param 	N/A					<BR>
	 * @return 	String				<BR>
	 */
	public String getErrorMsg() {
		return this.errMsg;
	}
	
	
	/**
	 * ResultSet의 RowCount를 알려준다. <BR><BR>
	 * (사용예) int li_Cnt = uRs.getRowCount(); 	<BR><BR>
	 *
	 * @param 	N/A
	 * @return 	int
	 */
	public int getRowCount() {
		return this.RowCount;
	}


	/**
	 * ResultSet를 Vector로 만들어낸다.<BR><BR>
	 * (사용예) Vector lv_rs = uRs.rsToVector(rs); 	<BR><BR>
	 *
	 * @param 	rs	ResultSet
	 * @return 	Vector
	 * @exception	SQLException
	 */
	public Vector rsToVector(ResultSet rs) throws SQLException {
		try {
			return makeVector(rs);
		}
		catch(SQLException e) {
			return null;
		}	
		
	}	// end of rsToVector
	
	
	/**
	 * URSet의 현위치를 한 레코드 이전으로 이동<BR><BR>
	 * (사용예) uRs.pre(); 	<BR><BR>
	 *
	 * @param	N/A 	
	 * @return 	boolean
	 */
	public boolean pre() {
		if ( this.row > 0 ) {
			this.row--;
			return true;
		}else return false;
	}
	
	/**
	 * URSet의 현위치를 index만큼 이전으로 이동<BR><BR>
	 * (사용예) boolean lb_back = uRs.back(3); 	<BR><BR>
	 *
	 * @param 	after	int
	 * @return	boolean 	
	 */
	public boolean back(int index) {
		this.row -= index;
		if ( this.row < 0 ) {
			this.row += index;
			return false;
		}
		else return true;
	}
	
	/**
	 * URSet의 현위치를 ahead만큼 뒤로 이동<BR><BR>
	 * (사용예) boolean lb_go = uRs.go(10); 	<BR><BR>
	 *
	 * @param 	ahead	int
	 * @return	boolean 	
	 */
	public boolean go(int ahead) {
		boolean result;
		this.row += ahead;
		if ( this.row > this.RowCount ) {
			result = false;
			this.row -= ahead;
		}
		else result = true;

		return result;
	}

	/**
	 * URSet의 현위치를 한 레코드 뒤로 이동<BR><BR>
	 * (사용예) boolean lb_next = uRs.next(); 	<BR><BR>
	 *
	 * @param 	N/A
	 * @return	boolean	
	 */
	public boolean next() {
		this.row ++ ;
		if ( this.row > this.RowCount ) {
			this.row --;
			return false;
		}
		else return true;
	}


	/**
	 * URSet의 현위치를 index의 위치로 이동<BR><BR>
	 * (사용예) boolean lb_move = uRs.move(10); 	<BR><BR>
	 *
	 * @param 	index	int
	 * @return 	boolean
	 */
	public boolean move(int index) {
		int nRow = this.row;
		
		boolean result;
		this.row = index;
		if ( this.row > this.RowCount ) {
			result = false;
			this.row = nRow;
		}
		else result = true;

		return result;
	}
	
	

	/**
	 * index 컬럼의 Result의 값을 가져온다.<BR><BR>
	 * (사용예) String ls_Val = uRs.getValue(1); 	<BR><BR>
	 *
	 * @param 	index	int
	 * @return 	String
	 */
	
	public String getValue(int index) {
		Vector rowResult = new Vector();
		
		rowResult = (Vector)this.Result.elementAt(this.row-1);
		
		return (String)rowResult.elementAt(index-1);
	}

		
	/**
	 * indexName의 Result의 값을 가져온다.<BR><BR>
	 * (사용예) String ls_Val = uRs.getValue("emp_no"); 	<BR><BR>
	 *
	 * @param 	indexName	String 
	 * @return 	String
	 */
	public String getValue(String indexName) {
		Vector rowResult = new Vector();
		
		Integer index = (Integer)this.ht.get(indexName);
		
		rowResult = (Vector)this.Result.elementAt(this.row -1 );
		
		return (String)rowResult.elementAt(index.intValue()-1);
	}
	
	
	/**
	 * ResultSet을 Vector로 변환
	 *
	 * @param 	rs	ResultSet 
	 * @return 	Vector
	 * @exception	SQLException	에러발생시 SQLException 발생
	 */
	private Vector makeVector(ResultSet rs) throws SQLException {
		
		Vector rsVector = new Vector();
		
		try {
			
			ResultSetMetaData metadata = rs.getMetaData();
			int numcols	= metadata.getColumnCount();
			String[] labels 	= new String[numcols];
			this.ColumnName 	= labels;
			this.ColumnCount	= numcols;
			this.row			= 0;
			
			// getColumnLabel name for begin
			for (int i = 0 ; i < numcols ; i ++ ) {
				labels[i] = metadata.getColumnLabel(i+1);
				this.ht.put(labels[i],new Integer(i+1));
				
			} // end of getColumnLabel for
			
			String temp_rs = null;
			while(rs.next()) {
				
				Vector columnValue = new Vector();
				
				for ( int j = 0 ; j < numcols ; j ++ ) {
					temp_rs = rs.getString(j+1);
					
					if (temp_rs == null) {
						temp_rs = "";
					} else {
						temp_rs = temp_rs.trim();
					}
					
					columnValue.addElement(temp_rs);
				}
				
				rsVector.addElement(columnValue);
				
			}
			
			return rsVector;
			
		}
		catch(SQLException e) {
			return null;
			
		}
	
	}

	/**
	 * Vector의 부분을 바꾸자.
	 *
	 * @param 	labels  String[] 
	 * @return 	N/A
	 */
	private void makeVector(String[] labels)  {
		
		this.row			= 0;
		// getColumnLabel name for begin
		for (int i = 0 ; i < labels.length ; i ++ ) {
			this.ht.put(labels[i],new Integer(i+1));
			
		} // end of getColumnLabel for

	} 

}
