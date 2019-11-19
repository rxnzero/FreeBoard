package dhlee.lib.util;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FileUpload {

	final static int INIT_VECTOR_CAPACITY = 3; // Vector 초기 용량
	
	final static long MAX_SIZE = 1024*1024*2; // file 의 최대크기(Byte) 2 MB

	String desDirectory 	= "d:/midas/www_home/upload/";		// 파일이 저장될 디렉토리
	String deliminator 	= null;
	Hashtable paramList	= new Hashtable(INIT_VECTOR_CAPACITY);	
	
	/**
	* 파라미터를 Hashtable 에 저장하고 파일은 업로드한다.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	req				HttpServletRequest				<BR>
	*			2.	res				HttpServletResponse				<BR>
	*			3.	ufName			업로드할 이름	"" 면 똑같이	<BR>
	* @return   int  	 											<BR>
	* 				-1: 에러										<BR>
	* 				0 : 성공										<BR>
	* 				1 : 화일을 입력 안함							<BR>
	* 				2 : 화일이 너무큼								<BR>
	*/    
	
	public int readForm(HttpServletRequest req,HttpServletResponse res,String ufName) throws IOException
	{
	// Request 객체와 upload된 파일을 form에서의 이름을 읽어온다.

		DataInputStream in 	= new DataInputStream(req.getInputStream());
		PrintStream out 	= new PrintStream(res.getOutputStream());
		String str=null,str_pre=null;
		String paramName = null;
		String paramValue = null;
		int nS=0,nE=0;
		String filename = "";
		int upRes = 0;
		
		// 1. 각 Parameter를 구분할 deliminator를 읽어드린다.
		deliminator = in.readLine();

		// 2. 모든 Parameter를 읽어서 paramList vector에 저장한다.
		while( (str=in.readLine())!=null){

			// 각 Parameter를 찾기 위해서 Deliminator를 찾는다.
			if(str.indexOf("Content-Disposition")==0){

				// form의 이름을 읽어온다.
				nS = str.indexOf("name=");
				nE = str.indexOf("\"",nS+6);
				paramName = str.substring(nS+6,nE);

				// Deliminator 부분에 filename= 이라는 부분이 있으면, 이 form객체를
				// file로 인식해서, 지정 디렉토리에 filename의 이름의 파일로 저장한다.
				if(str.indexOf("filename=")!=-1){
					nS = str.indexOf("filename=");
					nE = str.indexOf("\"",nS+10);
					paramValue = str.substring(nS+10,nE);

					nS = paramValue.lastIndexOf("\\");
					if(nS == -1) nS = paramValue.lastIndexOf("/");
					nS++;
					paramValue = paramValue.substring(nS,paramValue.length());
				    		
					paramList.put(fromDB(paramName),fromDB(paramValue));
					
					filename = fromDB(paramValue);
					if(filename.lastIndexOf("\\") != -1){
						filename=filename.substring(filename.lastIndexOf("\\")+1);						
				    }
				    
				    // 다른이름으로 저장하고싶을때
					if(ufName.length() > 0) {
						filename = ufName;
					}
						
				    System.out.println(filename);
				    
					str=in.readLine();		// Content-Type Header Skip
					if(str.indexOf("Content-Type")==0){
						str=in.readLine(); // Skip Empty Line
					}

					upRes = writeToFile(in,filename);	// writeToFile
				}else{
					// file이 아닌 일반 form인 경우
					str=in.readLine();			// Skip Line
					paramValue="";
					int step = 0;
					while( (str=in.readLine()) !=null ){
						// Deliminator가 나올때 까지 읽는다. 
						if(str.indexOf(deliminator)==0 ) break;
						if(step > 0) paramValue=paramValue+"\n";
						paramValue=paramValue+str;						
						step++;
					}// while

					// paramList에 paramName과 paramValue를 저장한다.
					paramList.put(fromDB(paramName),fromDB(paramValue));
				}// if
			}// if
		}// while
		return upRes;
	}// class readForm
	
	/**
	* 파라미터의 값을 반환한다.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	name			String				<BR>	
	* @return   String 	 											<BR>
	*/    
	public String getParameter(String name){
	// form에서 넘어온 일반 input type의 값을 name으로 travere해서 리턴한다.

		// request객체가 세팅 되어 있지 않으면 null을 리턴한다.
		String str = (String)paramList.get(name);
		if(str == null) return "";	// paramList에 없는 값이면 아무것도 리턴하지 않는다.

		return str;
	}// class getParameter
	
	/**
	* 화일을 서버의 특정위치에 쓴다.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	in				DataInputStream					<BR>	
	*			2.	filename		String							<BR>	
	* @return   int 	 											<BR>
	* 				-1: 에러										<BR>
	* 				0 : 성공										<BR>
	* 				1 : 화일을 입력 안함							<BR>
	* 				2 : 화일이 너무큼								<BR>
	*/
	protected int writeToFile(DataInputStream in,String filename){
		byte ch=0;
		byte[] buffer = new byte[1024];
		byte[] pre_buffer = new byte[1024];
		int x=0,delpos=0;
		int pre_x=0;
		long fsize = 0;
		boolean flag=true;
		
		if(deliminator == null) return 1; // deliminator가 비어있는지 여부를 체크

		try{
			FileOutputStream outFile = new FileOutputStream(desDirectory+filename);
			while(flag){
				ch=in.readByte();
				buffer[x++] = ch;
				
				if(fsize++ > MAX_SIZE) {
					outFile.close();
					File f = new File(desDirectory+filename);
					f.delete();
					System.out.println(" FIle is Too Large!");
					return 2;
										
				}
				if(ch =='\n' || x==1023){
					String strTmp = new String(buffer,0,x);
					if( (delpos=strTmp.indexOf(deliminator))!=-1){
						// Deliminator가 나오면 종료
						// Deliminator는 \r\n앞에 오기때문에 언제나 column=0에 위치한다.
						outFile.write(pre_buffer,0,pre_x-2); // CR/LF를 제거하기 위해 -2한다.
						flag=false;
					}else{
						outFile.write(pre_buffer,0,pre_x);
					}
					System.arraycopy(buffer,0,pre_buffer,0,x);
					pre_x = x;

					x=0;
				}
			}// while
			outFile.close();			
		}catch(EOFException eof){
		}catch(IOException e){
			System.out.println("ERROR : FILE UPLOAD FAILED (class fileUpload :: writeToFile");
			System.out.println(e);
			return -1;
		}// try
		return 0;
	}// writeToFile

	private String fromDB(String str) 
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
    
    private String Uni2Ksc(String UnicodeStr)
		throws UnsupportedEncodingException
	{
		if(UnicodeStr == null) {
			return "";	
		}
		else {
			return new String (UnicodeStr.getBytes("8859_1"), "KSC5601");	
		}
	}//end Uni2Ksc method

}
