package dhlee.board.serial.commitem;

public class BoardPageInfo implements java.io.Serializable
{
	private static final int defaultNumPerPage = 10;

	private String	table_name;			// 테이블명
	private int		page;				// 페이지
	private int		numPerPage;			// 한 페이지당 줄 수
	private int		searchColumnIndex;	// 검색어로 검색하고자 하는 컬럼 구분 (0: 작성자, 1: 날짜, 2: 제목)
	private String	searchWord;			// 검색어

	
	/**
	 *	생성자.
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
	 * 테이블명 설정.
	 *
	 * @param	pTableName	테이블명.
	 */
	public void setTableName(String pTableName)
	{
		this.table_name = pTableName;
	}

	/**
	 * 테이블명 얻기.
	 *
	 * @param	테이블명.
	 */
	public String getTableName()
	{
		return this.table_name;
	}


	/**
	 * 페이지 설정.
	 *
	 * @param	pPage	페이지.
	 */
	public void setPage(int pPage)
	{
	 	if (pPage > 0)
	 		this.page = pPage;
	 	else
	 		this.page = 1;
	}


	/**
	 * 한 페이지당 줄 수 설정.
	 *
	 * @param	pNumPerPage	한 페이지당 줄 수.
	 */
	public void setNumPerPage(int pNumPerPage)
	{
	 	if (pNumPerPage > 0)
		 	this.numPerPage = pNumPerPage;
		else
			this.numPerPage = defaultNumPerPage;
	}


	/**
	 * 한 페이지당 줄 수 얻기.
	 *
	 * @return	한 페이지당 줄 수.
	 */
	public int getNumPerPage()
	{
		return this.numPerPage;
	}


	/**
	 * 검색어로 검색하고자 하는 컬럼 지정. 
	 *
	 * @param	pSearchColumnIndex	검색어로 검색하고자 하는 컬럼 구분("0": 작성자, "1": 날짜, "2": 제목).
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
	 * 검색모드인지를 확인. 
	 *
	 * @return	검색모드인지의 여부.
	 */
	public boolean isSetSearch()
	{
		if (this.searchColumnIndex > 0 && this.searchWord.length() > 0)
			return true;
		else
			return false;
	}


	/**
	 * 검색어로 검색하고자 하는 컬럼 구분 얻기. 
	 *
	 * @return	검색하고자 하는 컬럼 구분.
	 */
	public int getSearchColumnIndex()
	{
		return this.searchColumnIndex;
	}


	/**
	 * 검색어 지정. 
	 *
	 * @param	pSearchWord	검색어.
	 */
	public void setSearchWord(String pSearchWord)
	{
		if (pSearchWord != null)
			this.searchWord = pSearchWord.trim();
		else
			this.searchWord = "";
	}


	/**
	 * 검색어 얻기. 
	 *
	 * @return	검색어.
	 */
	public String getSearchWord()
	{
		if (searchColumnIndex > 0)
			return this.searchWord;
		else
			return "";
	}

	/**
	 * 데이타베이스 검색시 가져올 첫 ROWNUM 값 얻기.
	 *
	 * @return	데이타베이스 검색시 가져올 첫 ROWNUM 값.
	 */
	public int getFirstRowNum()
	{
		return (this.page - 1) * this.numPerPage + 1;
	}
	
	
	/**
	 * 데이타베이스 검색시 가져올 마지막 ROWNUM 값 얻기.
	 *
	 * @return	데이타베이스 검색시 가져올 마지막 ROWNUM 값.
	 */
	public int getLastRowNum()
	{
		return this.page * this.numPerPage;
	}
}