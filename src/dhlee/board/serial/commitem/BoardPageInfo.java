package dhlee.board.serial.commitem;

public class BoardPageInfo implements java.io.Serializable
{
	private static final int defaultNumPerPage = 10;

	private String	table_name;			// ���̺��
	private int		page;				// ������
	private int		numPerPage;			// �� �������� �� ��
	private int		searchColumnIndex;	// �˻���� �˻��ϰ��� �ϴ� �÷� ���� (0: �ۼ���, 1: ��¥, 2: ����)
	private String	searchWord;			// �˻���

	
	/**
	 *	������.
	 */
	public BoardPageInfo()
	{
		this.table_name = null;
		this.page = 1;
		this.numPerPage = defaultNumPerPage;
		this.searchColumnIndex = 0;
		this.searchWord = "";
	}

	public BoardPageInfo(String pTableName, int pPage, int pNumPerPage, String pSearchColumnIndex, String pSearchWord)
	{
		setTableName(pTableName);
		setPage(pPage);
		setNumPerPage(pNumPerPage);
		setSearchColumnIndex(pSearchColumnIndex);
		setSearchWord(pSearchWord);
	}
	
	
	/**
	 * ���̺�� ����.
	 *
	 * @param	pTableName	���̺��.
	 */
	public void setTableName(String pTableName)
	{
		this.table_name = pTableName;
	}

	/**
	 * ���̺�� ���.
	 *
	 * @param	���̺��.
	 */
	public String getTableName()
	{
		return this.table_name;
	}


	/**
	 * ������ ����.
	 *
	 * @param	pPage	������.
	 */
	public void setPage(int pPage)
	{
	 	if (pPage > 0)
	 		this.page = pPage;
	 	else
	 		this.page = 1;
	}


	/**
	 * �� �������� �� �� ����.
	 *
	 * @param	pNumPerPage	�� �������� �� ��.
	 */
	public void setNumPerPage(int pNumPerPage)
	{
	 	if (pNumPerPage > 0)
		 	this.numPerPage = pNumPerPage;
		else
			this.numPerPage = defaultNumPerPage;
	}


	/**
	 * �� �������� �� �� ���.
	 *
	 * @return	�� �������� �� ��.
	 */
	public int getNumPerPage()
	{
		return this.numPerPage;
	}


	/**
	 * �˻���� �˻��ϰ��� �ϴ� �÷� ����. 
	 *
	 * @param	pSearchColumnIndex	�˻���� �˻��ϰ��� �ϴ� �÷� ����("0": �ۼ���, "1": ��¥, "2": ����).
	 */
	public void setSearchColumnIndex(String pSearchColumnIndex)
	{
		int iSearchColumnIndex;
		try
		{
		 	if ((iSearchColumnIndex = Integer.parseInt(pSearchColumnIndex)) > 0 && iSearchColumnIndex <= 3)
		 		this.searchColumnIndex = iSearchColumnIndex;
		 	else
		 		this.searchColumnIndex = 0;
		}
		catch (NumberFormatException nfe)
		{
			this.searchColumnIndex = 0;
		}
	}


	/**
	 * �˻���������� Ȯ��. 
	 *
	 * @return	�˻���������� ����.
	 */
	public boolean isSetSearch()
	{
		if (this.searchColumnIndex > 0 && this.searchWord.length() > 0)
			return true;
		else
			return false;
	}


	/**
	 * �˻���� �˻��ϰ��� �ϴ� �÷� ���� ���. 
	 *
	 * @return	�˻��ϰ��� �ϴ� �÷� ����.
	 */
	public int getSearchColumnIndex()
	{
		return this.searchColumnIndex;
	}


	/**
	 * �˻��� ����. 
	 *
	 * @param	pSearchWord	�˻���.
	 */
	public void setSearchWord(String pSearchWord)
	{
		if (pSearchWord != null)
			this.searchWord = pSearchWord.trim();
		else
			this.searchWord = "";
	}


	/**
	 * �˻��� ���. 
	 *
	 * @return	�˻���.
	 */
	public String getSearchWord()
	{
		if (searchColumnIndex > 0)
			return this.searchWord;
		else
			return "";
	}

	/**
	 * ����Ÿ���̽� �˻��� ������ ù ROWNUM �� ���.
	 *
	 * @return	����Ÿ���̽� �˻��� ������ ù ROWNUM ��.
	 */
	public int getFirstRowNum()
	{
		return (this.page - 1) * this.numPerPage + 1;
	}
	
	
	/**
	 * ����Ÿ���̽� �˻��� ������ ������ ROWNUM �� ���.
	 *
	 * @return	����Ÿ���̽� �˻��� ������ ������ ROWNUM ��.
	 */
	public int getLastRowNum()
	{
		return this.page * this.numPerPage;
	}
}