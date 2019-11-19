package dhlee.lib.db;

import java.util.*;
import java.sql.*;

/**
 * <PRE>
 * Filename	: URSet.java 														<BR>
 * Class	: URSet 															<BR>
 * Function	: 																	<BR>
 * Comment	: ResultSet�� Vector���·� �ٲپ��ش�. 								<BR>
 * History	: 10/25/1999, ������, v1.0, �����ۼ� 								<BR>
 *            12/20/1999, ������, v1.1, sort�ȰͿ� ���ؼ� group ��Ű�°� �߰� 	<BR>
 *            03/31/2000, ������, v1.2, move() method �߰� 						<BR>
 *            10/06/2000, ����, v1.3, null�� zero space�� return				<BR>
 * </PRE>
 * @version	1.3
 * @author 	Kim Hyun Chan
 * @company	Copyright (c) 2000 by At Soft Corp. All Rights Reserved.
 */
public class URSet {
	/**
	 * DB�� ��ȸ����� �����ϴ� Vector �ڷ���
	 */
	protected Vector 	Result;				
	protected Vector	group;
	protected String[] 	ColumnName;			
	/**
	 * DB���� ��ȸ�� Column�� ��
	 */
	protected int		ColumnCount;		
	/**
	 * Vector���� ���� �����ϴ� ��ġ
	 */
	protected int		row = 0;			
	/**
	 * ��ȸ�� ������ Record ��
	 */
	protected int		RowCount = 0;		
	
	private Hashtable ht = new Hashtable();
	
	/**
	 * boolean���� ��������
	 */
	private boolean   error 	= true;
	/**
	 * String���� �����޽���
	 */
	private String	  errMsg	= "";

	
	/**
	 * �����ڷν� default���� �����Ѵ�.  	<BR><BR>
	 * (��뿹) URSet uRs  =  new URSet();		<BR><BR>
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
	 * �����ڷν� ResultSet�� �޾� URSet�� �����Ѵ�.  	<BR><BR>
	 * (��뿹) URSet uRs  =  new URSet(rs);				<BR><BR>
	 *
	 * @param 	rs	ResultSet
	 * @return 	URSet	URSet
	 * @exception	SQLException	�����߻��� SQLException �߻�
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
			this.errMsg = " ResultSet ������ Error : " + e.toString();
			System.out.println(e.getMessage());
		
		}

	}

	/**
	 * �����ڷν� Vector�� label�� �޾� URSet�� �����Ѵ�.
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
	 * �����ڷν� Vector�� ������, Group������ ����Ѵ�. Sort�� �͸� ���ٶ�. <BR>
	 *
	 * @param 	rs	ResultSet			<BR>
	 * @param 	Flag int				<BR>
	 * @return 	N/A
	 * @exception	SQLException	�����߻��� SQLException �߻�
	 */
	public URSet(ResultSet rs,int Flag) throws SQLException{
		
		try {
			this.Result 	= makeVector(rs);
			this.RowCount	= this.Result.size();
			makeGroup();			// groupȭ �Ѵ�.
			this.error 		= false;
		}
		catch (SQLException e){
			this.error 	= true;
			this.errMsg = " ResultSet ������ Error : " + e.toString();
			System.out.println(e.getMessage());
		
		}

	}
	
	
	
	/**
	 * Sort�Ǿ�°��� group���� ���ڸ� �˷��ش�. <BR>
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
	 * Group�� ��ü ����� �ѱ��. 	<BR>
	 *
	 * @param	N/A						<BR>
	 * @return 	int total group Size	<BR>
	 */
	public int getGroupSize() {
		return this.group.size();
	}	// end of getGroupSize()
	
	
	
	/**
	 * Group���� �ش� index�� ����� �ѱ��.. <BR>
	 *
	 * @param 	index int						<BR>
	 * @return 	int  �ش� index�� size   	<BR>
	 */
	public int getGroupSize(int index) {
		Integer tmp = (Integer)group.elementAt(index);
		
		return tmp.intValue();
	}	// end of getGroupSize(int index)
	
	
	/**
	 * ���� ���а� �޼����� �����Ѵ�. 				<BR><BR>
	 * (��뿹) uRs.setErrMsg(true, "�����߻�"); 	<BR><BR>
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
	 * ���� ������ �����´�.		<BR><BR>
	 * (��뿹) boolean lb_Error = uRs.getError(); 	<BR><BR>
	 *
	 * @param 	N/A					<BR>
	 * @return 	boolean				<BR>
	 */
	public boolean getError() {
		return this.error;
	}

	
	/**
	 * ���� �޼�����  �����´�.		<BR><BR>
	 * (��뿹) boolean ls_ErrMsg = uRs.getErrorMsg(); 	<BR><BR>
	 *
	 * @param 	N/A					<BR>
	 * @return 	String				<BR>
	 */
	public String getErrorMsg() {
		return this.errMsg;
	}
	
	
	/**
	 * ResultSet�� RowCount�� �˷��ش�. <BR><BR>
	 * (��뿹) int li_Cnt = uRs.getRowCount(); 	<BR><BR>
	 *
	 * @param 	N/A
	 * @return 	int
	 */
	public int getRowCount() {
		return this.RowCount;
	}


	/**
	 * ResultSet�� Vector�� ������.<BR><BR>
	 * (��뿹) Vector lv_rs = uRs.rsToVector(rs); 	<BR><BR>
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
	 * URSet�� ����ġ�� �� ���ڵ� �������� �̵�<BR><BR>
	 * (��뿹) uRs.pre(); 	<BR><BR>
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
	 * URSet�� ����ġ�� index��ŭ �������� �̵�<BR><BR>
	 * (��뿹) boolean lb_back = uRs.back(3); 	<BR><BR>
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
	 * URSet�� ����ġ�� ahead��ŭ �ڷ� �̵�<BR><BR>
	 * (��뿹) boolean lb_go = uRs.go(10); 	<BR><BR>
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
	 * URSet�� ����ġ�� �� ���ڵ� �ڷ� �̵�<BR><BR>
	 * (��뿹) boolean lb_next = uRs.next(); 	<BR><BR>
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
	 * URSet�� ����ġ�� index�� ��ġ�� �̵�<BR><BR>
	 * (��뿹) boolean lb_move = uRs.move(10); 	<BR><BR>
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
	 * index �÷��� Result�� ���� �����´�.<BR><BR>
	 * (��뿹) String ls_Val = uRs.getValue(1); 	<BR><BR>
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
	 * indexName�� Result�� ���� �����´�.<BR><BR>
	 * (��뿹) String ls_Val = uRs.getValue("emp_no"); 	<BR><BR>
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
	 * ResultSet�� Vector�� ��ȯ
	 *
	 * @param 	rs	ResultSet 
	 * @return 	Vector
	 * @exception	SQLException	�����߻��� SQLException �߻�
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
	 * Vector�� �κ��� �ٲ���.
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
