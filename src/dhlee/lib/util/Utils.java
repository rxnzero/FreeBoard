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
	 *	Utils class �ʱ�ȭ
	 */
    public static void init() throws Exception
    {
    	System.out.println("Utils class init start...");
    	if(m_instance == null) {
        	m_instance = new Utils();
        }
    }


    /**
    * String���� ������� �޾� �Էµ� �����ڸ� �ٿ� ��ȯ    <BR>
    * (��) toDate("20000331","/") --> "2000/03/31"   <BR>
    * @param    str		String Source
    * @param    strDel	String Delimeter
    * @return   String  ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����)(yyyy/mm/dd)         
    */
    public static String toDate(String str,String strDel)
   	{
        if (str == null || str.trim().length() == 0) 
	        return ""; 
        if (str.trim().length() == 8) {
        	if ( strDel.trim().length() == 1 ) {
        		return new String(str.substring(0,4) + strDel + str.substring(4,6) + strDel + str.substring(6,8));
        	} else {
        		//���� �����ڰ� �������� "/"�� ����Ѵ�.
	        	return new String(str.substring(0,4) + "/" + str.substring(4,6) +"/" + str.substring(6,8));
	        }
        }else {
        	return str;
        }
   	}     	    	


    /**
    * ��¥���� �����ڰ� "."�� ��� ��ȯ �޼ҵ�    <BR>
    * (��) toDate("20000331") --> "2000.03.31"   <BR>
    * @param    str		String Source
    * @return   String  ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����)(yyyy.mm.dd)         
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
    * �ð��������� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toTime("101010") --> "10:10:10"   <BR>
    * @param    str		String Source
    * @return   String  ����� �ð� ���ڿ�.(�����ڰ� ÷���� �ð� ����)(hh:mm:ss)         
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
    * �ð��������� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toLTime("20000330101010") --> "2000�� 03�� 31�� 10�� 10�� 10��"   <BR>
    * @param    str		String Source
    * @return   String  ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����)(yyyy�� mm�� dd�� hh�� mm�� ss��)         
    */
   	public static String toLTime(String str) throws java.io.UnsupportedEncodingException  
   	{
        if(str == null) 
	        return ""; 
        if ( str.trim().length() == 14 ) {
        	return new String(str.substring(0,4) + "�� " + str.substring(4,6)+ "�� " + str.substring(6,8) +"�� " + str.substring(8,10) +"�� " + str.substring(10,12) +"�� " + str.substring(12,14) +"��" );
        }else {
        	return str;
        }
   	} 


    /**
    * �ֹι�ȣ �������� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toJumin("7208031002415") --> "720803-1000000"   <BR>
    * @param    str		String Source
    * @return   String  ����� �ֹε�� ���ڿ�.(�����ڰ� ÷���� �ֹε�Ϲ�ȣ ����)(111111-1111111)         
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
    * String�� �޾� õ�ڸ����� ','�� �����Ͽ� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toNumeric("10000000") --> "10,000,000"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���� ���ڿ�.(�����ڰ� ÷���� ���� ����)(100,000,000)         
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
    * double���� �޾� õ�ڸ����� ','�� �����Ͽ� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toNumeric(1000) --> "1,000"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(1,000)         
    */
	public static String toNumeric(double d) 
	{
			//double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
			return df.format(d);
	}


    /**
    * String���� �޾� double������ ��ȯ    <BR>
    * (��) stod(1.00) --> 1.00   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
    */
	public static double stod(String str) {
		
			if(str==null || str.trim().length()==0 ) return 0;
			
			double d = new Double(str).doubleValue();
			return d;		
	}
	


    /**
    * ���ڿ��� �޾� �Ҽ����� ������ �������� ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toNumeric2("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)
    */
	public static String toNumeric2(String str) 
	{
			if(str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d);
	}


    /**
    * ���ڿ��� �޾� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toPercent("10") --> "10.00%"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
    */
	public static String toPercent(String str) 
	{
		return toFraction(str) + " %";
	}


    /**
    * ���ڿ��� �޾� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toFraction("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
    */
	public static String toFraction(String str) 
	{
			if(str==null || str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue() / 100;
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d);
	}


    /**
    * ���ڿ��� �޾� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toFraction("10") --> "10.00"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
    */
	public static String toFraction(String str, int idx) 
	{
			if(str==null || str.trim().length()==0) return "";
			if(str.length() < idx ) return str;
			return str.substring(0,idx) + "." + str.substring(idx,str.length());
	}


    /**
    * ���ڿ��� �Է¹��� ���� ���� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toDivide("10", 1000) --> "0.01"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)
    */
	public static String toDivide(String str, int value) 
	{
			if(str==null || str.trim().length()==0) return "0.00";
			double d = new Double(str).doubleValue() / value;
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0");
			return df.format(d);
	}


    /**
    * ���ڿ��� ������ �����޾� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toDivide2("10") --> "0.01"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
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
    * ���ڿ��� �޾� ������ ��ȯ�ϴ� �޼ҵ�    <BR>
    * (��) toPercent("10") --> "10.00%"   <BR>
    * @param    str		String Source
    * @return   String  ����� ���ڿ�.(�����ڰ� ÷���� ����)(10.00%)         
    */
	public static String toPercent(double d) 
	{
			//double d = new Double(str).doubleValue();
			DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");
			return df.format(d)+" %";
	}


   /**
    * ����(�ѱ�����) �ð������� ��´�.    <BR>
    * (��) �Է��ĸ������� format string�� "yyyyMMddhh"�� <BR>
    *     �����ϸ� 1998121011�� ���� Return.  			<BR>
    * (��) format string�� "yyyyMMddHHmmss"�� �����ϸ� 19990114232121�� ���� <BR>
    *      0~23�ð� Ÿ������ Return. toTime�� ���� ����� ���� ����.			<BR>
    * @param    format String ������ϴ� ����ð��� Type
    * @return   String - ���� �ѱ� �ð�.
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
    * ����(�ѱ�����) �ð������� ��´�.                     	<BR>
    * informix������ DATE �����ڷ����� ǥ����� ������.		<BR>
    * ǥ����� yyyy-mm-dd 									<BR>
    * @param   N/A
    * @return   String 		yyyy-mm-dd������ ���� �ѱ��ð�.             <BR>                
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
    * ����(�ѱ�����) �ð������� ��´�.   <BR>
    * 8�ڸ��� DATE String.  				<BR>
    * ǥ����� yyyymmdd 					<BR>
    * @param   N/A
    * @return   String 		yyyymmdd������ ���� �ѱ��ð�.
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
    * ����(�ѱ�����) �ð������� ��´�.                     	<BR>
    * informix������ DATETIME �����ڷ����� ǥ����� ������. 	<BR>
    * ǥ����� yyyy-mm-dd hh:mm:ss 							<BR>
    * @param    N/A
    * @return   String 		yyyy-mm-dd hh:mm:ss������ ���� �ѱ��ð�.
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
    * ����(�ѱ�����) �ð������� ��´�.   <BR>
    * 15�ڸ��� DATETIME String.  				<BR>
    * ǥ����� yyyymmdd HHmmss 					<BR>
    * @param   N/A
    * @return   String 		20001010 201021������ ���� �ѱ��ð�.
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
    * ����(�ѱ�����) �ð������� ��´�.                     	<BR>
    * informix������ DATETIME �����ڷ����� ǥ����� ������. 	<BR>
    * ǥ����� yyyy-mm-dd hh:mm:ss.SSSSS 							<BR>
    * @param    N/A														<BR>
    * @return   String 		yyyy-mm-dd hh:mm:ss������ ���� �ѱ��ð�.    <BR>                
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
    * ����(�ѱ�����) �ð������� ��´�.                     	<BR>
    * informix������ DATETIME �����ڷ����� ǥ����� ������. 	<BR>
    * ǥ����� yyyy-mm-dd hh:mm:ss.SSSSS 							<BR>
    * @param    N/A														<BR>
    * @return   String 		hh:mm:ss������ ���� �ѱ��ð�.    <BR>                
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
    * ��¥���ڿ��� ��¥ǥ��Ÿ������ ��ȯ�Ѵ�. <BR>
    * (��) 19981210 --> 1998-12-10 	delimeter(-)		<BR>
    * @param    dateString String ��¥���ڿ� ������ �������� �ʴ� ���ڷθ� ������ ��¥ (yyyymmdd)
    * @return   ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����) (yyyy-mm-dd)
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
    * ��¥���ڿ��� DB DATETIME���·� ��ȯ<BR>
    * (��) 19981210101010 --> 1998-12-10 10:10:10<BR>
    * @param    dateString  ��¥���ڿ� ������ �������� �ʴ� ���ڷθ� ������ ��¥ (yyyymmddhhmmss)
    * @return   ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����)(yyyy-mm-dd hh:mm:ss)
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
    * ��¥���ڿ��� delimeter�� �� ���·� ��ȯ<BR>
    * (��) "199812101010","." --> 1999.12.20 10:10<BR>
    * @param    dateString  ��¥���ڿ� ������ �������� �ʴ� ���ڷθ� ������ ��¥ (yyyymmddhhmmss)
    * @return   ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����)(yyyy.mm.dd hh:mm)
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
    * ��¥���ڿ��� ��¥ǥ��Ÿ������ ��ȯ�Ѵ�. <BR>
    * (��) 1998-12-10 --> 12/10/1999		  <BR>
    * @param    dateString  ��¥���ڿ� ������ �������� �ʴ� ���ڷθ� ������ ��¥ (yyyy-MM-dd)
    * @return   ����� ��¥ ���ڿ�.(�����ڰ� ÷���� ��¥ ����) (MM/dd/yyyy)
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
    * ��¥���ڿ��� DB DATETIME���·� ��ȯ<BR>
    * (��) 1998-12-10 10:10:10.0 --> 1998-12-10 10:10:10<BR>
    * @param    dateString  �ڿ� .0�� ����ش�.
    * @return   ����� ��¥ ���ڿ�.
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
    * ���ڿ� ������ ȥ�� ���ڿ��� ���� ���ڿ��� ��ȯ<BR>
    * (��) 1998-12-10 10:10:10.0 --> 19981210101010<BR>
    * @param    ���� ���ڿ��� ��ȯ.
    * @return   ����� ���ڿ�.
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
    * �ݾ׹��ڿ��� �ݾ�ǥ��Ÿ������ ��ȯ�Ѵ�. <BR>
    * (��) 12345678 --> 12,345,678            <BR>
    * @param    moneyString �ݾ׹��ڿ�.        
    * @param    delimeter   �ݾ�ǥ�ñ�����.   
    * @return   ����� �ݾ� ���ڿ�.           
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
    * �ݾ׹��ڿ��� �ݾ�ǥ��Ÿ������ ��ȯ�Ѵ�. <BR>
    * (��) 12345678 --> 12,345,678            <BR>
    * @param    intMoneyString int�� �ݾ׹��ڿ�.        
    * @param    delimeter   �ݾ�ǥ�ñ�����.   
    * @return   ����� �ݾ� ���ڿ�.           
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
    * �ݾ�ǥ��Ÿ���� �ݾ׹��ڿ��� ��ȯ�Ѵ�. <BR>
    * (��) 12,345,678 --> 12345678          <BR>
    * @param    moneyString �ݾ�ǥ�ù��ڿ�.        
    * @param    delimeter   �ݾ�ǥ�ñ�����.   
    * @return   �ݾ׹��ڿ�.           
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
    * String�� int������ ��ȯ�Ѵ�.           <BR>
    * @param    str     int������ ��ȯ�� String���ڿ�.
    * @return   ��ȯ�� int ��.
    */
    public static int stoi(String str)
    {
        if(str == null || str.trim().length()==0 ) {
        	return 0;        
        }        
        return (Integer.parseInt(str.trim()));
    }

	
   /**
    * int���� String���� ��ȯ�Ѵ�.           <BR>
    * 
    * @param    i   String���� ��ȯ�� int ��.
    * @return   ��ȯ�� String ��.
    */
    public static String itos(int i)
    {
        return (new Integer(i).toString());
    }	

	
	/**
	* ����Ÿ���̽��� ���ڿ��� �����ϱ� ���� ��ȯ 
	* (����) �ѱۺ�ȯ�� ���Ͽ� ���
	* @param	str			DB�� ������ �ѱ��� ����ִ� ���ڿ�
	* @return	tmpStr		8859_1 codeset���� ��ȯ�� ���ڿ�
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
	* ����Ÿ���̽��κ��� ���� ���ڿ��� ��ȯ
	* (����) �ѱۺ�ȯ�� ���Ͽ� �����
	* @param	str			DB���� ������ �ѱ��� ����ִ� ���ڿ�
	* @return	tmpStr		KSC5601 codeset���� ��ȯ�� ���ڿ�
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
	* Ksc5601 ---> 8859_1 ���ڿ��� ��ȯ
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
	* 8859_1 ---> Ksc5601 ���ڿ��� ��ȯ
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
	* ���ڿ��� �޾Ƽ� ���� ����ǥ�� ���� ��ȯ�Ѵ�.
	* - SQL���� ������ ���Ű ����.
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
	* ���ڿ��� �޾Ƽ� Enter Key�� Ư�����ڿ�(`)�� ��ȯ�ϰų�
	* Ư�����ڿ��� Enter key�� ��ȯ��...
	* - Informix thin driver Bug ������ SQL���� ������ ���Ű ����.
	* </pre>
	* @param	String		��ȯ ���
	* @param	nFlag		��ȯ ����
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
			if( nFlag > 0) {	//����̸� Enter Key�� `�� ��ȯ
	    		str = str.replace( '\r', '`');
	    		str = str.replace( '\n', '`');
	    		
	    		return str;
	    	}
	    	else {			//�����̸� `�� Enter Key�� ��ȯ
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
	* ���ڿ��� �޾Ƽ� Enter Key�� Ư�����ڿ�(<BR>)�� ��ȯ�ϰų�
	* Ư�����ڿ��� Enter key�� ��ȯ��...
	* - Informix thin driver Bug ������ SQL���� ������ ���Ű ����.
	* </pre>
	* @param	String		��ȯ ���
	* @param	nFlag		��ȯ ���� (����̸� Enter Key�� `<BR>`�� ��ȯ)
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
			if( nFlag > 0) {	//����̸� Enter Key�� `<BR>`�� ��ȯ
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
	    	else {			//�����̸� <BR>�� Enter Key�� ��ȯ
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
	* ���ڿ��� �޾Ƽ� Enter Key�� ���н��� Enter�� ��ȯ
	* </pre>
	* @param	String		��ȯ ���
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
	* ���� �ý����� ���ϸ��� return
	* </pre>
	* @return	String		���ϸ�	
	*/
	public static String getDateName() {
		Calendar caln = Calendar.getInstance();
		int n_date = caln.get(Calendar.DAY_OF_WEEK);
		String rtn = "";
		
		switch (n_date) {
			case 1: rtn = "��"; break;
			case 2: rtn = "��"; break;
			case 3: rtn = "ȭ"; break;
			case 4: rtn = "��"; break;
			case 5: rtn = "��"; break;
			case 6: rtn = "��"; break;
			case 7: rtn = "��"; break;
			default : rtn = ""; break;
		}
		return rtn;
	}
	
	/**
	* <pre>
	* ���� �ý����� ���ϸ��� return
	* </pre>
	* @return	String		���ϸ�	
	*/
	public static String getDateNum() {
		Calendar caln = Calendar.getInstance();
		int n_date = caln.get(Calendar.DAY_OF_WEEK);
		
		return Integer.toString(n_date);
	}
	
	/**
	* <pre>
	* 1-7�� double Number�� �޾Ƶ鿩 ���ϸ��� return
	* </pre>
	* @param	double			�о�� ���ϸ��� ����
	* @return	String		���ϸ�	
	*/
	public static String Order2monthName(double order)
	{
		
		double[] limits = {1,2,3,4,5,6,7};
 		String[] monthNames = {"��","��","ȭ","��","��","��","��"};
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
	*  ���ϸ��� �޾Ƶ鿩 1-7�� double Number�� return /  -1: Error
	* </pre>
	* @param	String		�о�� ���ϸ�
	* @return	double			���ϸ��� ����	
	*/
	public static double monthName2Order(String monthName)
	{
		String Name;
		int[] limits = {1,2,3,4,5,6,7};
 		String[] monthNames = {"��","��","ȭ","��","��","��","��"};
 		
 		 		
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
    * delimeter�� ���ؼ� ������ String�� String[]�� ��ȯ�Ѵ�. <BR>
    * (��) 12:23 --> String[0] = "12", String[1] = 23   <BR>
    * @param    String 		Delimeter�� ������ String        
    * @param    String      ������.   
    * @return   String[]           
    */
    public static String[] StrToStrArray(String Str, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(Str,delimeter);
        Vector 			vt = new Vector();
        
        String temp = null;
        // ��� Vector�� ����ִ´�.
        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            vt.addElement(temp);
        }    
        
        String[] out = new String[vt.size()];
        
        // ������� data�� String[]�� ��ȯ�Ѵ�.
        for ( int i = 0; i < vt.size() ; i ++ ) {
        	out[i] = (String)vt.elementAt(i);
        }
        
        return out;
    }
 
    
    /**
    * String[]�� delimeter�� ���ؼ� ������ String�� ��ȯ�Ѵ�. <BR>
    * (��) String[0] = "12", String[1] = 23 --> 12:23         <BR>
    * @param    String[] 	��ȯ�� String[]        
    * @param    String      ������.   
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
    * String�ȿ� ���Ե� delimeter�� ������ �����Ѵ�.	<BR>
    * (��) countString("aaabaacaa", "aa", 0) = 4			<BR>
    *      countString("aaabaacaa", "aa", 1) = 2			<BR>
    *      countString("aaabaacaa", "aa", 2) = 3			<BR>
    * @param    String		��ȯ�� String
    * @param    String      ������.
    * @param	int			��ȯ ���
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
    * String���� " "�� &nbsp�� ��ȯ�Ѵ�. <BR>
    * (��) String = " 2 " -> String = "&nbsp2&nbsp"         <BR>
    * @param    String 	��ȯ�� String        
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
    * ������� Zero String�� �����ϴ� substring �Լ�    <BR>
    * (��) getSubstring("1234",4,2) --> ""   <BR>
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
    * �ѱ��̿� �°� String ���� Space�� ä���ִ� �Լ�    <BR>
    * (��) makeFSpace("1234",6) --> "  1234"   <BR>
    * @param    String 		String Source
    * @param    int      �ѱ���
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
    * �ѱ��̿� �°� String ���� Space�� ä���ִ� �Լ�    <BR>
    * (��) makeFSpace("1234",6) --> "  1234"   <BR>
    * @param    String 		String Source
    * @param    int      �ѱ���
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
    * �ѱ��̿� �°� String ���� ���ڷ� ä���ִ� �Լ�    <BR>
    * (��) makeFPading("1234",6,"0") --> "001234"   <BR>
    * @param    String 		String Source
    * @param    int      �ѱ���
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
    * �ѱ��̿� �°� String ���� 0���� ä���ִ� �Լ�    <BR>
    * (��) makeFSpace("1234",6) --> "001234"   <BR>
    * @param    String 		String Source
    * @param    int      �ѱ���
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
   	* �Է��� ��¥ �������� ���� ��,��
   	* (����)�Է³�¥�� �����ڰ� ���� string��
	* @param	date	String date (19991002) ������
	* @param	Span	int  ���� �ϼ� ( 10 : 10����, -10 : 10�� ��)
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
    * '.'�� ���Ե� IP�� 12�ڸ� 0������ IP�� ��ȯ   <BR>
    * (��) getIP("128.50.232.216") --> "128050232216"   <BR>
    * @param    str		String Source
    * @return   String  ��Ī
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
    * ������� ���ڿ��Ϸ� �ٲٴ� �Լ�    <BR>
    * (��) findWeekOrder("20001001") --> "0" ��-�� =  0-6  <BR>
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
	 * String�������� null�϶� null��� �ٸ� ������ ��ȯ
	 * (��) isNull(String1, String2) --> String1�� null�϶� String2�� return
	 * @param	String1		Null Check���
	 * @param	String2		Null�϶� return
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
	 *	ȸ����ȣ 11�ڸ��� �Է� �޾Ƽ� ȸ����ȣ format���� ��ȯ
	 *(��) toMbrID("00120000002") --> "0012-00-00002"
	 *@param	String	xxxxxxxxxxx ȸ����ȣ(11)
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
    * �������¿��� ����Ʈ ���ؽ� ���ڿ��� �����.. <BR>
    * @param    Properties properties which related with this method    <BR>
    *           1.LISTPAGE   ����Ʈ�������� 							<BR>	
    *           2.TPAGE      ������������ �����						<BR>
    *           3.TPAGENO    ����������									<BR>
    *           4.CPAGE      ������������ �����						<BR>	
    *           5.CPAGENO    ����������									<BR>
    *           6.RCNT       ��ȸ�� �� ROW ��							<BR>
    * @param    String[]   ��ȸ����										<BR>
    * @param    String[]   ���ļ���										<BR>
    * @param    int        �������� ���� ��								<BR>
    * @param    int        �������ε����� ũ��							<BR>
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
		
			//strPages = "��ü " + totPage + "������ �� " + pageindex + "������ ";
			
			// ���� �ε���������
			if( (indextop - pageWidth) > 0) { 
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop - pageWidth) + 
			                "&"+ cpage +"=" + (indextop - 1);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }			    
			    strPages += "\" onMouseOver=\"window.status=('���� �ε�����������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/prev_02.gif\" border=\"0\"></a> ";
			}
			
			// ���� ������
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
			
			// ������ ��ȣ		
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
			
			// ���� ������
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
			
			// ���� �ε���������
			if( (indextop + pageWidth) <= totPage) { 
				strPages += "<a href=\""+listpage+"?"+tpage+"=" + (indextop + pageWidth) + 
			                                   "&"+cpage+"=" + (indextop + pageWidth);
			    if (added.length() > 0) {
			    	strPages+= added;
			    }			    
			    strPages += "\" onMouseOver=\"window.status=('���� �ε�����������'); return true;\" onMouseOut=\"window.status=(''); return true;\"><img src=\"/common/img/next_02.gif\" border=\"0\"></a> ";
			}
		}
    	return strPages;
    }
    
    /**
	* ���ڿ� replace �Լ�						<BR>
    * @param    String		text				<BR>
    * @param    int			start	���� index	<BR>
    * @param    String		src		�ٲ��ڿ�	<BR>
    * @param    String		dest	�����ڿ�	<BR>
    * @return   String		��� ���ڿ�          
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

