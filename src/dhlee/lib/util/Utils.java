package dhlee.lib.util;

import java.text.*;
import java.util.*;
import java.io.*;

public class Utils
{
    protected static Utils m_instance;

   /**
    * constructor... 
    * @param    N/A 
    * @return   N/A
    */
    protected Utils()
    {
    	m_instance = null;
    }


    public static Utils getInstance() throws Exception
    {
        if (m_instance == null ) {
            throw new Exception("System is not initialized properly: "
                               +"common.lib.util.Utils instance is null");
        }
        return m_instance;
    }


	/**
	 *	Utils class 초기화
	 */
    public static void init() throws Exception
    {
    	System.out.println("Utils class init start...");
    	if(m_instance == null) {
        	m_instance = new Utils();
        }
    }


    /**
    * String형의 년월일을 받아 입력된 구분자를 붙여 반환    <BR>
    * (예) toDate("20000331","/") --> "2000/03/31"   <BR>
    * @param    str		String Source
    * @param    strDel	String Delimeter
    * @return   String  변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy/mm/dd)         
    */
    public static String toDate(String str,String strDel)
   	{
        if (str == null || str.trim().length() == 0) 
	        return ""; 
        if (str.trim().length() == 8) {
        	if ( strDel.trim().length() == 1 ) {
        		return new String(str.substring(0,4) + strDel + str.substring(4,6) + strDel + str.substring(6,8));
        	} else {
        		//선택 구분자가 없을때는 "/"을 사용한다.
	        	return new String(str.substring(0,4) + "/" + str.substring(4,6) +"/" + str.substring(6,8));
	        }
        }else {
        	return str;
        }
   	}     	    	


    /**
    * 날짜형식 구분자가 "."인 경우 변환 메소드    <BR>
    * (예) toDate("20000331") --> "2000.03.31"   <BR>
    * @param    str		String Source
    * @return   String  변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy.mm.dd)         
    */
   	public static String toDate(String str)
   	{
        if(str == null || str.trim().length() == 0 || stoi(str) == 0 ) 
        	return ""; 

        if ( str.trim().length() == 8 ) {
        	return new String(str.substring(0,4) + "." + str.substring(4,6) +"." + str.substring(6,8));
        }else if ( str.trim().length() == 6 ) {
        	return new String(str.substring(0,4) + "." + str.substring(4,6));
        }else {
        	return str;
        }
	        
   	} 
    	
    	
    /**
    * 시간형식으로 변환하는 메소드    <BR>
    * (예) toTime("101010") --> "10:10:10"   <BR>
    * @param    str		String Source
    * @return   String  변경된 시간 문자열.(구분자가 첨가된 시간 형태)(hh:mm:ss)         
    */
   	public static String toTime(String str)
   	{
        if(str == null || str.trim().length() == 0 ) 
	        return ""; 
        if ( str.trim().length() == 4 ) {
        	return new String(str.substring(0,2) + ":" + str.substring(2,4));
        }else if ( str.trim().length() == 6 ) {
        	return new String(str.substring(0,2) + ":" + str.substring(2,4)+ ":" + str.substring(4,6));
        }else {
        	return str;
        }
   	} 


    /**
    * 시간형식으로 변환하는 메소드    <BR>
    * (예) toLTime("20000330101010") --> "2000년 03월 31일 10시 10분 10초"   <BR>
    * @param    str		String Source
    * @return   String  변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy년 mm월 dd일 hh시 mm분 ss초)         
    */
   	public static String toLTime(String str) throws java.io.UnsupportedEncodingException  
   	{
        if(str == null) 
	        return ""; 
        if ( str.trim().length() == 14 ) {
        	return new String(str.substring(0,4) + "년 " + str.substring(4,6)+ "월 " + str.substring(6,8) +"일 " + str.substring(8,10) +"시 " + str.substring(10,12) +"분 " + str.substring(12,14) +"초" );
        }else {
        	return str;
        }
   	} 


    /**
    * 주민번호 형식으로 변환하는 메소드    <BR>
    * (예) toJumin("7208031002415") --> "720803-1000000"   <BR>
    * @param    str		String Source
    * @return   String  변경된 주민등록 문자열.(구분자가 첨가된 주민등록번호 형태)(111111-1111111)         
    */
   	public static String toJumin(String str) 
   	{
        if(str == null) 
	        return ""; 
        if ( str.trim().length() == 13 ) {
        	return new String(str.substring(0,6) + " - " + str.substring(6,13));
        }else {
        	return str;
        }
   	}     	


    /**
    * String을 받아 천자리마다 ','로 구분하여 변환하는 메소드    <BR>
    * (예) toNumeric("10000000") --> "10,000,000"   <BR>
    * @param    str		String Source
    * @return   String  변경된 숫자 문자열.(구분자가 첨가된 숫자 형태)(100,000,000)         
    */
	public static String toNumeric(String str) 
	{
			if(str == null || str.trim().length()==0) return "0";

			String ts_tmp = str.trim().substring(0,1);
			
			double d = 0;
			
			if (ts_tmp.equals("-")) {
				d = new Double(str.trim().substring(1, str.trim().length()) ).doubleValue();
			}else{			
			    ts_tmp = "";
				d = new Double(str.trim()).doubleValue();
			}
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
			return ts_tmp + df.format(d);
	}


    /**
    * double형을 받아 천자리마다 ','로 구분하여 변환하는 메소드    <BR>
    * (예) toNumeric(1000) --> "1,000"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(1,000)         
    */
	public static String toNumeric(double d) 
	{
			//double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
			return df.format(d);
	}


    /**
    * String형을 받아 double형으로 반환    <BR>
    * (예) stod(1.00) --> 1.00   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static double stod(String str) {
		
			if(str==null || str.trim().length()==0 ) return 0;
			
			double d = new Double(str).doubleValue();
			return d;		
	}
	


    /**
    * 문자열을 받아 소수점을 포함한 형식으로 변환하는 메소드    <BR>
    * (예) toNumeric2("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)
    */
	public static String toNumeric2(String str) 
	{
			if(str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d);
	}


    /**
    * 문자열을 받아 비율로 변환하는 메소드    <BR>
    * (예) toPercent("10") --> "10.00%"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static String toPercent(String str) 
	{
		return toFraction(str) + " %";
	}


    /**
    * 문자열을 받아 비율로 변환하는 메소드    <BR>
    * (예) toFraction("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static String toFraction(String str) 
	{
			if(str==null || str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue() / 100;
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d);
	}


    /**
    * 문자열을 받아 비율로 변환하는 메소드    <BR>
    * (예) toFraction("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static String toFraction(String str, int idx) 
	{
			if(str==null || str.trim().length()==0) return "";
			if(str.length() < idx ) return str;
			return str.substring(0,idx) + "." + str.substring(idx,str.length());
	}


    /**
    * 문자열을 입력받은 수로 나눠 비율로 변환하는 메소드    <BR>
    * (예) toDivide("10", 1000) --> "0.01"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)
    */
	public static String toDivide(String str, int value) 
	{
			if(str==null || str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue() / value;
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
			return df.format(d);
	}


    /**
    * 문자열과 나눠줄 값을받아 비율로 변환하는 메소드    <BR>
    * (예) toDivide2("10") --> "0.01"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static String toDivide2(String str, int value) 
	{
			if(str==null || str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue() / value;
			DecimalFormat df;
			if ( itos(value).length() == 2 ) {
				df = new DecimalFormat("###,###,###,###,###,##0.0");
			}else if ( itos(value).length() == 3 ) {
				df = new DecimalFormat("###,###,###,###,###,##0.00");
			}else if ( itos(value).length() == 4 ) {
				df = new DecimalFormat("###,###,###,###,###,##0.000");
			}else {
				df = new DecimalFormat("###,###,###,###,###,##0.0000");
			}
			
			return df.format(d);
	}


    /**
    * 문자열을 받아 비율로 변환하는 메소드    <BR>
    * (예) toPercent("10") --> "10.00%"   <BR>
    * @param    str		String Source
    * @return   String  변경된 문자열.(구분자가 첨가된 형태)(10.00%)         
    */
	public static String toPercent(double d) 
	{
			//double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d)+" %";
	}


   /**
    * 현재(한국기준) 시간정보를 얻는다.    <BR>
    * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 <BR>
    *     셋팅하면 1998121011과 같이 Return.  			<BR>
    * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이 <BR>
    *      0~23시간 타입으로 Return. toTime과 같이 사용할 것을 권장.			<BR>
    * @param    format String 얻고자하는 현재시간의 Type
    * @return   String - 현재 한국 시간.
    */
    public static String getKST(String format)
    {
	  	//1hour(ms) = 60s * 60m * 1000ms
        int millisPerHour = 60 * 60 * 1000;
        SimpleDateFormat fmt= new SimpleDateFormat(format);

        SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
        fmt.setTimeZone(timeZone);

        long time=System.currentTimeMillis();
        String str=fmt.format(new java.util.Date(time));
        
        return str;
   }
   	

   /**
    * 현재(한국기준) 시간정보를 얻는다.                     	<BR>
    * informix전용의 DATE 관련자료형의 표기법을 따른다.		<BR>
    * 표기법은 yyyy-mm-dd 									<BR>
    * @param   N/A
    * @return   String 		yyyy-mm-dd형태의 현재 한국시간.             <BR>                
    */
	public static String getKSTDate()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("yyyyy-MM-dd");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method


   	/**
    * 현재(한국기준) 시간정보를 얻는다.   <BR>
    * 8자리의 DATE String.  				<BR>
    * 표기법은 yyyymmdd 					<BR>
    * @param   N/A
    * @return   String 		yyyymmdd형태의 현재 한국시간.
    */
	public static String getKSTSimpleDate()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("yyyyyMMdd");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method


   /**
    * 현재(한국기준) 시간정보를 얻는다.                     	<BR>
    * informix전용의 DATETIME 관련자료형의 표기법을 따른다. 	<BR>
    * 표기법은 yyyy-mm-dd hh:mm:ss 							<BR>
    * @param    N/A
    * @return   String 		yyyy-mm-dd hh:mm:ss형태의 현재 한국시간.
    */
	public static String getKSTDateTime()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("yyyyMMddHHmmss");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method
   	
   	/**
    * 현재(한국기준) 시간정보를 얻는다.   <BR>
    * 15자리의 DATETIME String.  				<BR>
    * 표기법은 yyyymmdd HHmmss 					<BR>
    * @param   N/A
    * @return   String 		20001010 201021형태의 현재 한국시간.
    */
    
   	public static String getKSTSimpleDateTime()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("yyyyMMdd HHmmss");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method
	
   /**
    * 현재(한국기준) 시간정보를 얻는다.                     	<BR>
    * informix전용의 DATETIME 관련자료형의 표기법을 따른다. 	<BR>
    * 표기법은 yyyy-mm-dd hh:mm:ss.SSSSS 							<BR>
    * @param    N/A														<BR>
    * @return   String 		yyyy-mm-dd hh:mm:ss형태의 현재 한국시간.    <BR>                
    */
	public static String getKSTDateTimeFraction()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSSSS");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method
   	

   /**
    * 현재(한국기준) 시간정보를 얻는다.                     	<BR>
    * informix전용의 DATETIME 관련자료형의 표기법을 따른다. 	<BR>
    * 표기법은 yyyy-mm-dd hh:mm:ss.SSSSS 							<BR>
    * @param    N/A														<BR>
    * @return   String 		hh:mm:ss형태의 현재 한국시간.    <BR>                
    */
	public static String getKSTSimpleTimeFraction()
   	{
   			SimpleTimeZone pdt = new SimpleTimeZone(9 * 60 * 60 * 1000, "KST");
					  
			// Format the current time.
			SimpleDateFormat formatter
				= new SimpleDateFormat ("HH:mm:ss");
			Date currentTime_1 = new Date();
			return formatter.format(currentTime_1);
	}//end method
   	        
	
   /**
    * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
    * (예) 19981210 --> 1998-12-10 	delimeter(-)		<BR>
    * @param    dateString String 날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyymmdd)
    * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태) (yyyy-mm-dd)
    */
    public static String makeDateType(String dateString)
    {
        if (dateString.length() == 0 || dateString == null ) return "";
        if (dateString.length() != 8) return "invalid length";

        String year = dateString.substring(0,4);
        String month = dateString.substring(4,6);
        String date = dateString.substring(6,8);
        
        return (year+"-"+month+"-"+date);
    }
	 

   /**
    * 날짜문자열을 DB DATETIME형태로 변환<BR>
    * (예) 19981210101010 --> 1998-12-10 10:10:10<BR>
    * @param    dateString  날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyymmddhhmmss)
    * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy-mm-dd hh:mm:ss)
    */
    public static String makeDateTimeType(String dateString)
    {
        if (dateString.length() == 0) return "";
        //if (dateString.length() < 21) return "invalid length";

        String year 	= dateString.substring(0,4);
        String month 	= dateString.substring(4,6);
        String date 	= dateString.substring(6,8);
        String hour 	=dateString.substring(8,10);
        String min	 	=dateString.substring(10,12);
        String sec		=dateString.substring(12,14);
        
        return (year+"-"+month+"-"+date+" "+hour+":"+min+":"+sec);
    }


	/**
    * 날짜문자열을 delimeter에 의 형태로 변환<BR>
    * (예) "199812101010","." --> 1999.12.20 10:10<BR>
    * @param    dateString  날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyymmddhhmmss)
    * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태)(yyyy.mm.dd hh:mm)
    */
    public static String makeDateTimeType(String dateString,String delimeter)
    {
        if( dateString==null) return "";
        
        int strlen = dateString.length();
        
        if (strlen < 0) return "";
        if (strlen <= 4) return dateString;
        //if (dateString.length() < 21) return "invalid length";
		
        String year  = getSubstring(dateString,0,4);
        String month = getSubstring(dateString,4,2);
        String date  = getSubstring(dateString,6,2);
        String hour  = getSubstring(dateString,8,2);
        String min	 = getSubstring(dateString,10,2);
        String sec   = getSubstring(dateString,12,2);
                
        if (strlen <= 6) {        	
        	return (year+delimeter+month);
        }
        if (strlen <= 8) {
        	return (year+delimeter+month+delimeter+date);
        }
        if (strlen <= 10) {
        	return (year+delimeter+month+delimeter+date+" "+hour);
        }
        if (strlen <= 12) {
        	return (year+delimeter+month+delimeter+date+" "+hour+":"+min);
        }        
        return (year+delimeter+month+delimeter+date+" "+hour+":"+min+":"+sec);        
    }

    
   /**
    * 날짜문자열을 날짜표시타입으로 변환한다. <BR>
    * (예) 1998-12-10 --> 12/10/1999		  <BR>
    * @param    dateString  날짜문자열 구분작 존재하지 않는 숫자로만 구성된 날짜 (yyyy-MM-dd)
    * @return   변경된 날짜 문자열.(구분자가 첨가된 날짜 형태) (MM/dd/yyyy)
    */
    public static String convertDateType(String dateString)
    {
        if (dateString.length() == 0 || dateString == null ) return "";
        if (dateString.length() != 10) return "invalid length";

        String year = dateString.substring(0,4);
        String month = dateString.substring(5,7);
        String date = dateString.substring(8,10);
        
        return (month+"/"+date+"/"+year);
    }
	 

   /**
    * 날짜문자열을 DB DATETIME형태로 변환<BR>
    * (예) 1998-12-10 10:10:10.0 --> 1998-12-10 10:10:10<BR>
    * @param    dateString  뒤에 .0을 얻애준다.
    * @return   변경된 날짜 문자열.
    */
    public static String convertDateTimeType(String dateString)
    {
        if (dateString.length() == 0) return "";
        //if (dateString.length() != 21) return "invalid length";

        String year 	= dateString.substring(0,4);
        String month 	= dateString.substring(5,7);
        String date 	= dateString.substring(8,10);
        String hour 	=dateString.substring(11,13);
        String min	 	=dateString.substring(14,16);
        String sec		=dateString.substring(17,19);
        
        return (year+"-"+month+"-"+date+" "+hour+":"+min+":"+sec);
    }


   /**
    * 숫자와 문자의 혼합 문자열을 숫자 문자열로 변환<BR>
    * (예) 1998-12-10 10:10:10.0 --> 19981210101010<BR>
    * @param    숫자 문자열로 변환.
    * @return   변경된 문자열.
    */
	public static String makeNoDateType(String dateString) 
	{
		int len = 0;
		String strRet = "";
		String strTemp = "";
		String[] limits = {"0","1","2","3","4","5","6","7","8","9"};

		len = dateString.length();
		
		if(dateString.length() == 0) return "";
		if(len < 1) return "";
		
		for(int x=0; x < len; x++) {
			strTemp = new String();
			strTemp = dateString.substring(x, x+1);
			
			for(int y=0; y < 10; y++) {
				if(limits[y].equals(strTemp)) {
					strRet += strTemp;
				}
			}
		}
		
		return strRet;
	}


   /**
    * 금액문자열을 금액표시타입으로 변환한다. <BR>
    * (예) 12345678 --> 12,345,678            <BR>
    * @param    moneyString 금액문자열.        
    * @param    delimeter   금액표시구분자.   
    * @return   변경된 금액 문자열.           
    */
    public static String makeMoneyType(String moneyString, String delimeter)
    {
        int len = 0;
        String temp=null;
        String money=null;
        String ret="";
        
        if(moneyString == null) return "";
        
        len = moneyString.length();
        
        if(len < 1) return moneyString;
        for(int x=len; x > 0; x -= 3) {
            if ((x-3) <= 0) {
                temp = moneyString.substring(0,x);   
	            money = temp;
	            ret = money + ret;
            }
            else {
                temp = moneyString.substring(x-3,x);
	            money = delimeter+temp;
	            ret = money + ret;                
            }
            //System.out.println("temp["+x+"]: "+temp);
        }
        
        if( (ret.charAt(0) == '+') || (ret.charAt(0) == '-') ) {
	        	
   			if( ret.charAt(1) == delimeter.charAt(0)) {   			     	
        		String tempMoneyStr = null;
        		tempMoneyStr = ret.substring(0,1) + ret.substring(2, ret.length());
        		
        		ret = null;	
  				ret = tempMoneyStr;
  			}
  		}
        
        return ret;
    }


   /**
    * 금액문자열을 금액표시타입으로 변환한다. <BR>
    * (예) 12345678 --> 12,345,678            <BR>
    * @param    intMoneyString int형 금액문자열.        
    * @param    delimeter   금액표시구분자.   
    * @return   변경된 금액 문자열.           
    */
    public static String makeMoneyType(int intMoneyString, String delimeter)
    {
        String temp=null;
        String money=null;
        String ret="";

        String moneyString = new Integer(intMoneyString).toString();
        int len = moneyString.length();
        if (len <= 0 || moneyString == null ) return "";
        
        for(int x=len; x > 0; x -= 3) {
            if ((x-3) <= 0) {
                temp = moneyString.substring(0,x);   
	            money = temp;
	            ret = money + ret;
            }
            else {
                temp = moneyString.substring(x-3,x);
	            money = delimeter+temp;
	            ret = money + ret;
                
            }
            //System.out.println("temp["+x+"]: "+temp);
        }
        
        if( (ret.charAt(0) == '+') || (ret.charAt(0) == '-') ) {
   			if( ret.charAt(1) == delimeter.charAt(0)) {   			     	
        		String tempMoneyStr = null;
        		tempMoneyStr = ret.substring(0,1) + ret.substring(2, ret.length());
        		
        		ret = null;	
  				ret = tempMoneyStr;
  			}
  		}
  		        
        return ret;
    }

	
   /**
    * 금액표시타입을 금액문자열로 변환한다. <BR>
    * (예) 12,345,678 --> 12345678          <BR>
    * @param    moneyString 금액표시문자열.        
    * @param    delimeter   금액표시구분자.   
    * @return   금액문자열.           
    */
    public static String makeNoMoneyType(String moneyString, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(moneyString,delimeter);
        String out = "";
        String temp = null;
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            out = out + temp;
        }    
        return out;
    }


   /**
    * String을 int값으로 변환한다.           <BR>
    * @param    str     int값으로 변환될 String문자열.
    * @return   변환된 int 값.
    */
    public static int stoi(String str)
    {
        if(str == null || str.trim().length()==0 ) {
        	return 0;        
        }        
        return (Integer.parseInt(str.trim()));
    }

	
   /**
    * int값을 String으로 변환한다.           <BR>
    * 
    * @param    i   String으로 변환될 int 값.
    * @return   변환된 String 값.
    */
    public static String itos(int i)
    {
        return (new Integer(i).toString());
    }	

	
	/**
	* 데이타베이스로 문자열을 저장하기 전에 변환 
	* (주의) 한글변환을 위하여 사용
	* @param	str			DB로 저장할 한글이 들어있는 문자열
	* @return	tmpStr		8859_1 codeset으로 변환된 문자열
	*/	
    public static String toDB(String str) 
    {
    	String tmpStr = "";
    	try {   
    		tmpStr = Ksc2Uni(str);
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace(System.err);	
		}
		
		return tmpStr;
    } 


	/**
	* 데이타베이스로부터 얻은 문자열을 변환
	* (주의) 한글변환을 위하여 사용함
	* @param	str			DB에서 가져온 한글이 들어있는 문자열
	* @return	tmpStr		KSC5601 codeset으로 변환된 문자열
	*/
    public static String fromDB(String str) 
    {
    	String tmpStr = "";
    	
    	try {   
    		return Uni2Ksc(str);
		}
		catch(UnsupportedEncodingException e) {
			e.printStackTrace(System.err);	
		}
		
		return tmpStr;
    } 
	
	
   /**
	* Ksc5601 ---> 8859_1 문자열로 변환
	* 
	* @param	KscStr		
	* @return	String			
	*/
    public static String Ksc2Uni(String KscStr) 
        throws java.io.UnsupportedEncodingException 
	{
		if(KscStr == null) {
			return "";	
		}
		else {
			return new String (KscStr.getBytes("KSC5601"), "8859_1");	
		}
	}

		 	
   /**
	* 8859_1 ---> Ksc5601 문자열로 변환
	* 
	* @param	UnicodeStr		
	* @return	String			
	*/
	public static String Uni2Ksc(String UnicodeStr)
		throws UnsupportedEncodingException
	{
		if(UnicodeStr == null) {
			return "";	
		}
		else {
			return new String (UnicodeStr.getBytes("8859_1"), "KSC5601");	
		}
	}//end Uni2Ksc method

   
   /**
	* <pre>
	* 문자열을 받아서 단일 따옴표로 감싸 반환한다.
	* - SQL문을 생성시 사용키 위함.
	* </pre>
	* @param	String		
	* @return	'String'			
	*/
	public static String quote(String str)
	{
		StringBuffer sqlStr = new StringBuffer();
		
		if(str == null) {
			return "''";	
		}
		else {
			sqlStr.append("'");
			for(int i = 0; i < str.length(); i++) {
				if(str.charAt(i) == '\'') {
					sqlStr.append("''");	
				}	
				else {
					sqlStr.append(str.charAt(i));	
				}
								
			}
			sqlStr.append("'");
			return sqlStr.toString();
		}
	}//end quote method

		 	
   /**
	* <pre>
	* 문자열을 받아서 Enter Key를 특정문자열(`)로 변환하거나
	* 특정문자열을 Enter key로 변환함...
	* - Informix thin driver Bug 때문에 SQL문을 생성시 사용키 위함.
	* </pre>
	* @param	String		변환 대상
	* @param	nFlag		변환 방향
	* @return	'String'			
	*/
	public static String convertRN(String str, int	nFlag)
	{
		StringBuffer dbStr = new StringBuffer();
		int i = 0;
		
		if(str == null) {
			return "";	
		}
		else {
			if( nFlag > 0) {	//양수이면 Enter Key를 `로 변환
	    		str = str.replace( '\r', '`');
	    		str = str.replace( '\n', '`');
	    		
	    		return str;
	    	}
	    	else {			//음수이면 `를 Enter Key로 변환
	    		for(; i < str.length(); i++) {					
					if(   (str.charAt(i) == '`') 
					   && ((i+1) < str.length()) 
					   &&  (str.charAt(i+1) == '`') ) {
						i++;
						dbStr.append("\r\n");
					}	
					else {
						dbStr.append(str.charAt(i));	
					}
									
				}//end for
				
	    	}//end sub if-else
	    	
	    }//end main if-else
	    
		return  dbStr.toString();
	}//end convertRN method



   /**
	* <pre>
	* 문자열을 받아서 Enter Key를 특정문자열(<BR>)로 변환하거나
	* 특정문자열을 Enter key로 변환함...
	* - Informix thin driver Bug 때문에 SQL문을 생성시 사용키 위함.
	* </pre>
	* @param	String		변환 대상
	* @param	nFlag		변환 방향 (양수이면 Enter Key를 `<BR>`로 변환)
	* @return	'String'			
	*/
	public static String convertBR(String str, int	nFlag)
	{
		StringBuffer fileStr = new StringBuffer();
		
		int i = 0;
		int lasti = 0;
		
		if(str == null) {
			return "";	
		}
		else {
			if( nFlag > 0) {	//양수이면 Enter Key를 `<BR>`로 변환
	    		for(; i < str.length(); i++) {					
					if( str.charAt(i) == '\r' )	{
						fileStr.append("<BR>");
					}	
					else if( str.charAt(i) == '\n' ) { 
						;
					}	
					else {
						fileStr.append(str.charAt(i));	
					}
									
				}//end for
								
				return fileStr.toString();
	    	}
	    	else {			//음수이면 <BR>를 Enter Key로 변환
	    		i = str.indexOf("<BR>"); 
				System.out.println("br == " + i);

				while( (i != -1) && (i < str.length()) ) {					
					fileStr.append(str.substring(lasti, i));
					fileStr.append("\r\n");
					
					i += 4;
					lasti = i;
					
					i = str.indexOf("<BR>", lasti); 
					System.out.println("br == " + i);
				} // end while
				
				if(i < str.length()) {
					fileStr.append( str.substring( lasti, str.length() ) );						
				}				
								
	    	}//end sub if-else
	    	
	    }//end main if-else
		
		System.out.println(" convertBR : " +  fileStr.toString() + "==");
		return  fileStr.toString();
	}//end convertRN method


   /**
	* <pre>
	* 문자열을 받아서 Enter Key를 유닉스용 Enter로 변환
	* </pre>
	* @param	String		변환 대상
	* @return	'String'			
	*/
	public static String convertUnixRN(String str)
	{
		StringBuffer fileStr = new StringBuffer();
		
		int i = 0;
		
		if(str == null) {
			return "";	
		}
		else {
			for(; i < str.length(); i++) {				
				if( str.charAt(i) == '\r' )	{ 
					;
				}	
				//if( str.charAt(i) == '\n' )
				//{ ;}	
				else {
					fileStr.append(str.charAt(i));	
				}
								
			}//end for
							
			return fileStr.toString();
	    	
	    }//end main if-else
		
	}//end convertRN method
  	
	/**
	* <pre>
	* 현재 시스템의 요일명을 return
	* </pre>
	* @return	String		요일명	
	*/
	public static String getDateName() {
		Calendar caln = Calendar.getInstance();
		int n_date = caln.get(Calendar.DAY_OF_WEEK);
		String rtn = "";
		
		switch (n_date) {
			case 1: rtn = "일"; break;
			case 2: rtn = "월"; break;
			case 3: rtn = "화"; break;
			case 4: rtn = "수"; break;
			case 5: rtn = "목"; break;
			case 6: rtn = "금"; break;
			case 7: rtn = "토"; break;
			default : rtn = ""; break;
		}
		return rtn;
	}
	
	/**
	* <pre>
	* 현재 시스템의 요일명을 return
	* </pre>
	* @return	String		요일명	
	*/
	public static String getDateNum() {
		Calendar caln = Calendar.getInstance();
		int n_date = caln.get(Calendar.DAY_OF_WEEK);
		
		return Integer.toString(n_date);
	}
	
	/**
	* <pre>
	* 1-7의 double Number를 받아들여 요일명을 return
	* </pre>
	* @param	double			읽어올 요일명의 순서
	* @return	String		요일명	
	*/
	public static String Order2monthName(double order)
	{
		
		double[] limits = {1,2,3,4,5,6,7};
 		String[] monthNames = {"일","월","화","수","목","금","토"};
 		ChoiceFormat form = new ChoiceFormat(limits, monthNames);
 		if ((order >= 1) && (order <= 7)) {
 			return form.format(order);
 		}
 		else {
 			return "";
 		} 		
	}

	
	/**
	* <pre>
	*  요일명을 받아들여 1-7의 double Number를 return /  -1: Error
	* </pre>
	* @param	String		읽어올 요일명
	* @return	double			요일명의 순번	
	*/
	public static double monthName2Order(String monthName)
	{
		String Name;
		int[] limits = {1,2,3,4,5,6,7};
 		String[] monthNames = {"일","월","화","수","목","금","토"};
 		
 		 		
 		if(monthName.length() < 1) {
 			return -1;
 		} 
 		Name = monthName.substring(0,1); 		
 		for(int i=0; i<7;i++) {
 			if( Name.equals(monthNames[i]) ) {
 				return limits[i];
 			}
 		}
 		return -1;
	}
	

   /**
    * delimeter에 의해서 구성된 String을 String[]로 변환한다. <BR>
    * (예) 12:23 --> String[0] = "12", String[1] = 23   <BR>
    * @param    String 		Delimeter를 포함한 String        
    * @param    String      구분자.   
    * @return   String[]           
    */
    public static String[] StrToStrArray(String Str, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(Str,delimeter);
        Vector 			vt = new Vector();
        
        String temp = null;
        // 끊어서 Vector에 집어넣는다.
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            vt.addElement(temp);
        }    
        
        String[] out = new String[vt.size()];
        
        // 집어넣은 data를 String[]로 변환한다.
        for ( int i = 0; i < vt.size() ; i ++ ) {
        	out[i] = (String)vt.elementAt(i);
        }
        
        return out;
    }
 
    
    /**
    * String[]를 delimeter에 의해서 구성된 String로 변환한다. <BR>
    * (예) String[0] = "12", String[1] = 23 --> 12:23         <BR>
    * @param    String[] 	변환할 String[]        
    * @param    String      구분자.   
    * @return   String           
    */
    public static String StrArrayToStr(String[] Str, String delimeter)
    {
        String out = "";
        
        for(int i=0; i<Str.length; i++) {
        	out += Str[i];
        	out += delimeter;
        }    
        out = out.substring(0,out.length()-1);
        
        return out;
    }

    /**
    * String안에 포함된 delimeter의 개수를 리턴한다.	<BR>
    * (예) countString("aaabaacaa", "aa", 0) = 4			<BR>
    *      countString("aaabaacaa", "aa", 1) = 2			<BR>
    *      countString("aaabaacaa", "aa", 2) = 3			<BR>
    * @param    String		변환할 String
    * @param    String      구분자.
    * @param	int			변환 방법
    * @return   int
    */
	public static int countString(String Str, String delimeter, int nFlag)
	{
		int nOut = 0;
		
		if(Str == null || Str.length() < 1) return 0;
		if(delimeter == null || delimeter.length() < 1) return 0;
		if(delimeter.length() >= Str.length()) return 0;

		if(nFlag == 0) {
			for(int i=0; i<Str.length()-delimeter.length()+1; i++) {
				if(Str.substring(i, i+delimeter.length()).equals(delimeter)) nOut++;
			}
		}
		else if(nFlag == 1) {
			for(int i=0; i<Str.length()-delimeter.length()+1; i += delimeter.length()) {
				if(Str.substring(i, i+delimeter.length()).equals(delimeter)) nOut++;
			}
		}
		else if(nFlag == 2) {
			int nPoint = -1;
			
			for(int i=0; i<Str.length()-delimeter.length()+1; i++) {
				if(Str.substring(i, i+delimeter.length()).equals(delimeter)) {
					if(nPoint < i || nPoint >= (i+delimeter.length())) {
						nOut++;
						nPoint = i;
					}
				}
			}
		}
	
		return nOut;
	}
 
    
    /**
    * String에서 " "를 &nbsp로 변환한다. <BR>
    * (예) String = " 2 " -> String = "&nbsp2&nbsp"         <BR>
    * @param    String 	변환할 String        
    * @return   String           
    */
    public static String SpaceToNbsp(String Str)
    {
        String out = "";
        
        for(int i=0; i<Str.length(); i++) {
        	if( Str.charAt(i) == ' ' )	{
				out +="&nbsp;";
			}	
			else {
				out +=Str.charAt(i);	
			}		
        }    
        return out;
    }

    
    /**
    * 에러대신 Zero String을 리턴하는 substring 함수    <BR>
    * (예) getSubstring("1234",4,2) --> ""   <BR>
    * @param    String 		String Source
    * @param    int      start index.   
    * @param    int      length   
    * @return   String           
    */
    public static String getSubstring(String Str, int start, int len)
    {
        if(Str == null) return "";
        int slen = Str.length();
        
        if( (slen < 1) || (start<0) || (len < 1) ) return "";
        
        if((slen-1) < start) return "";
        
        if( slen < (start+len) ) {
        	return Str.substring(start,Str.length());
        }
        else {
        	return Str.substring(start,start+len);
        }
    }
  
    
    /**
    * 총길이에 맞게 String 앞을 Space로 채워주는 함수    <BR>
    * (예) makeFSpace("1234",6) --> "  1234"   <BR>
    * @param    String 		String Source
    * @param    int      총길이
    * @return   String           
    */
    public static String makeFSpace(String Str, int totlen)
    {
        String retStr = "";
        
        if(Str == null) return "";
//        int slen = Str.length();
        byte[] b_data = Str.getBytes();

   		int slen = b_data.length;

        
        if( (totlen < 1) || (slen >= totlen) ) return Str;
        
        for(int i=0; i< (totlen-slen); i++) {
        	retStr += " ";
        }
        retStr += Str;
        
        return retStr;        
    }	


    /**
    * 총길이에 맞게 String 앞을 Space로 채워주는 함수    <BR>
    * (예) makeFSpace("1234",6) --> "  1234"   <BR>
    * @param    String 		String Source
    * @param    int      총길이
    * @return   String           
    */
    public static String makeBSpace(String Str, int totlen)
    {
        String retStr = "";
        
        if(Str == null) return "";
        
        byte[] b_data = Str.getBytes();

   		int slen = b_data.length;

        if( (totlen < 1) || (slen >= totlen) ) return Str;
        
        for(int i=slen; i< (totlen); i++) {
        	retStr += " ";
        }
        retStr = Str + retStr;
        
        return retStr;        
    }	


	/**
    * 총길이에 맞게 String 앞을 문자로 채워주는 함수    <BR>
    * (예) makeFPading("1234",6,"0") --> "001234"   <BR>
    * @param    String 		String Source
    * @param    int      총길이
    * @return   String           
    */
    public static String makeFPading(String Str, int totlen,String pad)
    {
        String retStr = "";
        
        if(Str == null) return "";

        byte[] b_data = Str.getBytes();

   		int slen = b_data.length;

        if( (totlen < 1) || (slen >= totlen) ) return Str;
        
        for(int i=0; i< (totlen-slen); i++) {
        	retStr += pad;
        }
        retStr += Str;
        
        return retStr;        
    }


    /**
    * 총길이에 맞게 String 앞을 0으로 채워주는 함수    <BR>
    * (예) makeFSpace("1234",6) --> "001234"   <BR>
    * @param    String 		String Source
    * @param    int      총길이
    * @return   String           
    */
    public static String makeFZero(String Str, int totlen)
    {
        String retStr = "";
        
        if(Str == null) return "";
//        int slen = Str.length();

        byte[] b_data = Str.getBytes();

   		int slen = b_data.length;

        
        if( (totlen < 1) || (slen >= totlen) ) return Str;
        
        for(int i=0; i< (totlen-slen); i++) {
        	retStr += "0";
        }
        retStr += Str;
        
        return retStr;        
    }	


	
   /** 
   	* 입력한 날짜 기준으로 몇일 전,후
   	* (주의)입력날짜는 구분자가 없는 string형
	* @param	date	String date (19991002) 기준일
	* @param	Span	int  구할 일수 ( 10 : 10일전, -10 : 10일 후)
	* @return	String
   	*/  
	public static String getPreDate( String date, int nday ){
		int total = 0;
		int [] year = { 0 };
		int [] month = { 0};
		int [] day = {0};

		int u_yyyymmcc = Integer.parseInt(date);
		int c_yyyymmcc = Integer.parseInt(Utils.getKSTSimpleDate());
		
		if(u_yyyymmcc - c_yyyymmcc <0 ) return date;
//		if(nday == 1) return date;
//		if(nday	== 9) return Utils.getKSTSimpleDate();

		total = totalDay( date ) - nday;
		dayToDate( total , year, month, day );
 		return  makeFPading(new Integer(year[0]).toString(),4,"0") +
 			makeFPading(new Integer(month[0]).toString(),2,"0") +
 			makeFPading(new Integer(day[0]).toString(),2,"0");

	}
 
	public static void dayToDate( int days, int [] year, int [] month, int [] day ){
		int n;
		int [] dd = { 31,28,31,30,31,30,31,31,30,31,30,31 };
		
		year[0] = 0; 
		for (;;) {
		    if (isYunNyun(year[0])) n = 366;
		    else n = 365;
		    if (days - n <= 0) break;
		    year[0] = year[0] + 1;
		    days -= n;
		}
		for (month[0] = 1; month[0] < 12; (month[0])++)
		{
		    if (month[0] ==2) n = isYunNyun( year[0] ) ? 29:28;
		    else n = dd[month[0] - 1];
		    if (days - n <= 0) {
		       break;
		    }
		    days -= n;
		}
		day[0] = days;
	}
  

   	public static int totalDay(String date)  	{
                		
		boolean isDay = true;
	
		int [] dd = { 31,28,31,30,31,30,31,31,30,31,30,31 };
		
		int i,year,month,day,total;
		
		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(4, 6));
		day = Integer.parseInt(date.substring(6, 8));

		total = 0;
		for (i=0; i <year; i++){

		    if (isYunNyun(i)) total += 366;
		    else total += 365;
		}
		for (i=1; i <month; i++) {
		    total += dd[i-1];
		}
		total += day;
		if (month > 2 && isYunNyun(year)) total ++;
		return total;

	}

	public static boolean isYunNyun(int yy){
		return ((yy%4 == 0) && ((yy%100 !=0 ) || (yy%400 == 0))) ? true : false;
	}


    /**
    * '.'이 포함된 IP를 12자리 0포함한 IP로 반환   <BR>
    * (예) getIP("128.50.232.216") --> "128050232216"   <BR>
    * @param    str		String Source
    * @return   String  명칭
    */	
    public static String getIP(String lt_IP) {


		if(lt_IP==null || lt_IP.trim().length()==0) return "000000000000";


		String[] tmpIP = Utils.StrToStrArray(lt_IP,".");
		
		String strIP = new String();
		for(int i = 0 ; i < tmpIP.length ; i++) {
			strIP += Utils.makeFZero(tmpIP[i],3);
		}

		return strIP;
	
	}
	
	/**
    * 년월일을 숫자요일로 바꾸는 함수    <BR>
    * (예) findWeekOrder("20001001") --> "0" 일-토 =  0-6  <BR>
    * @param    String 		String Source
    * @return   String           
    */
    public static String findWeekOrder(String DateStr)
    {
        if(DateStr.length() != 8) return "0";
        String tDate = makeDateTimeType(DateStr,"-");
		int ord = java.sql.Date.valueOf(tDate).getDay();
		return Integer.toString(ord);        
    }

	/**
	 * String변수값이 null일때 null대신 다른 값으로 변환
	 * (예) isNull(String1, String2) --> String1이 null일때 String2를 return
	 * @param	String1		Null Check대상
	 * @param	String2		Null일때 return
	 * @return	String		String2
	 */
	public static String isNull(String arg, String rtn)
	{
		if (arg == null) {
			return rtn;
		}
		else {
			return arg;
		}
	}
	
	/**
	 *	회원번호 11자리를 입력 받아서 회원번호 format으로 변환
	 *(예) toMbrID("00120000002") --> "0012-00-00002"
	 *@param	String	xxxxxxxxxxx 회원번호(11)
	 *@return	String	xxxx-xx-xxxxx
	 */
	public static String toMbrID(String arg) {
		String mbr_id = null;
		
		if(arg == null) return "";
		if(arg.length() != 11) return arg;
		
		mbr_id = arg.substring(0,4) + "-" + arg.substring(4,6) + "-" + arg.substring(6,11);
	
		return mbr_id;
	}
	
	/**
    * 방명록형태에서 리스트 인텍스 문자열을 만든다.. <BR>
    * @param    Properties properties which related with this method    <BR>
    *           1.LISTPAGE   리스트페이지명 							<BR>	
    *           2.TPAGE      시작페이지의 저장명						<BR>
    *           3.TPAGENO    시작페이지									<BR>
    *           4.CPAGE      현재페이지의 저장명						<BR>	
    *           5.CPAGENO    현재페이지									<BR>
    *           6.RCNT       조회한 총 ROW 수							<BR>
    * @param    String[]   조회조건										<BR>
    * @param    String[]   정렬순서										<BR>
    * @param    int        페이지당 열의 수								<BR>
    * @param    int        페이지인덱스의 크기							<BR>
    * @return   String     HTML      									<BR>
    */
    public static String makePageIndexStr(Properties param, String[] filters,String[] orders,int pageRow,int pageWidth)
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
		
		for(int l=0; l<orders.length; ) {
			if(orders[l].length() > 0) {
				added = added +"&" + orders[l] + "=" + orders[l+1];
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
		
			//strPages = "전체 " + totPage + "페이지 중 " + pageindex + "페이지 ";
			
			// 이전 인덱스페이지
			if( (indextop - pageWidth) > 0) { 
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop - pageWidth) + 
			                "&"+ cpage +"=" + (indextop - 1);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }			    
			    strPages += "\" onMouseOver=\"window.status=('이전 인덱스페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_02.gif\" border=\"0\"></a> ";
			}
			
			// 이전 페이지
			if((pageindex - 1) < indextop) {
				if( (indextop - pageWidth) > 0) { 
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop - pageWidth) + 
				                "&"+ cpage +"=" + (pageindex - 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }			    
				    strPages += "\" onMouseOver=\"window.status=('이전페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_01.gif\" border=\"0\"></a> ";
				}
			}
			else {
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop) + 
			                "&"+ cpage +"=" + (pageindex - 1);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }			    
			    strPages += "\" onMouseOver=\"window.status=('이전페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_01.gif\" border=\"0\"></a> ";
			}
			
			// 페이지 번호		
			for(int pg=indextop; (pg<=totPage) && (indexCount<pageWidth); pg++) {
				indexCount++;
				if(pg==pageindex) {
					strPages += "<font color=silver>[" + pg + "] </font>";
				}
				else {
					strPages += "[<a href=\""+listpage+"?"+tpage+"=" + indextop + 
											"&"+cpage+"=" + pg ;
					if (added.length() > 0) {
			    	strPages+= added;
			    	}			    
			    	strPages += "\">" + pg + "</a>] ";
				}
			}
			
			// 이후 페이지
			if( (pageindex + 1) >= (indextop+pageWidth)) { 
				if( (pageindex + 1) <= totPage) {
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop + pageWidth) + 
				                "&"+ cpage +"=" + (pageindex + 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }			    
				    strPages += "\" onMouseOver=\"window.status=('다음페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_01.gif\" border=\"0\"></a> ";
				}
			}
			else {
				if( (pageindex + 1) <= totPage) {
					strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop) + 
				                "&"+ cpage +"=" + (pageindex + 1);
				    if (added.length() > 0) {
				    	strPages+= added;
				    }			    
				    strPages += "\" onMouseOver=\"window.status=('다음페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_01.gif\" border=\"0\"></a> ";
				}	
			}
			
			// 이후 인덱스페이지
			if( (indextop + pageWidth) <= totPage) { 
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop + pageWidth) + 
			                                   "&"+cpage+"=" + (indextop + pageWidth);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }			    
			    strPages += "\" onMouseOver=\"window.status=('다음 인덱스페이지로'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_02.gif\" border=\"0\"></a> ";
			}
		}
    	return strPages;
    }
    
    /**
	* 문자열 replace 함수						<BR>
    * @param    String		text				<BR>
    * @param    int			start	시작 index	<BR>
    * @param    String		src		바뀔문자열	<BR>
    * @param    String		dest	새문자열	<BR>
    * @return   String		결과 문자열          
    */

	public static String replaceAll( String text, int start, String src, String dest )
    {
		if( text==null ) return null;
		if( src==null || dest==null )	return text;

		int textlen = text.length();
		int srclen	= src.length();
		int diff	= dest.length() - srclen;
		int d = 0;

		StringBuffer t = new StringBuffer( text );
	
		while( start<textlen ) {
			start = text.indexOf(src, start);
			if( start<0 ) break;
			t.replace(start+d, start+d+srclen, dest);
			start	+= srclen;
			d		+= diff;
		}
		return t.toString();
    }

}//end class

