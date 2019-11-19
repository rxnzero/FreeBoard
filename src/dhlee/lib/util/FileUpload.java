package dhlee.lib.util;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FileUpload {

	final static int INIT_VECTOR_CAPACITY = 3; // Vector �ʱ� �뷮
	
	final static long MAX_SIZE = 1024*1024*2; // file �� �ִ�ũ��(Byte) 2 MB

	String desDirectory 	= "d:/midas/www_home/upload/";		// ������ ����� ���丮
	String deliminator 	= null;
	Hashtable paramList	= new Hashtable(INIT_VECTOR_CAPACITY);	
	
	/**
	* �Ķ���͸� Hashtable �� �����ϰ� ������ ���ε��Ѵ�.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	req				HttpServletRequest				<BR>
	*			2.	res				HttpServletResponse				<BR>
	*			3.	ufName			���ε��� �̸�	"" �� �Ȱ���	<BR>
	* @return   int  	 											<BR>
	* 				-1: ����										<BR>
	* 				0 : ����										<BR>
	* 				1 : ȭ���� �Է� ����							<BR>
	* 				2 : ȭ���� �ʹ�ŭ								<BR>
	*/    
	
	public int readForm(HttpServletRequest req,HttpServletResponse res,String ufName) throws IOException
	{
	// Request ��ü�� upload�� ������ form������ �̸��� �о�´�.

		DataInputStream in 	= new DataInputStream(req.getInputStream());
		PrintStream out 	= new PrintStream(res.getOutputStream());
		String str=null,str_pre=null;
		String paramName = null;
		String paramValue = null;
		int nS=0,nE=0;
		String filename = "";
		int upRes = 0;
		
		// 1. �� Parameter�� ������ deliminator�� �о�帰��.
		deliminator = in.readLine();

		// 2. ��� Parameter�� �о paramList vector�� �����Ѵ�.
		while( (str=in.readLine())!=null){

			// �� Parameter�� ã�� ���ؼ� Deliminator�� ã�´�.
			if(str.indexOf("Content-Disposition")==0){

				// form�� �̸��� �о�´�.
				nS = str.indexOf("name=");
				nE = str.indexOf("\"",nS+6);
				paramName = str.substring(nS+6,nE);

				// Deliminator �κп� filename= �̶�� �κ��� ������, �� form��ü��
				// file�� �ν��ؼ�, ���� ���丮�� filename�� �̸��� ���Ϸ� �����Ѵ�.
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
				    
				    // �ٸ��̸����� �����ϰ������
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
					// file�� �ƴ� �Ϲ� form�� ���
					str=in.readLine();			// Skip Line
					paramValue="";
					int step = 0;
					while( (str=in.readLine()) !=null ){
						// Deliminator�� ���ö� ���� �д´�. 
						if(str.indexOf(deliminator)==0 ) break;
						if(step > 0) paramValue=paramValue+"\n";
						paramValue=paramValue+str;						
						step++;
					}// while

					// paramList�� paramName�� paramValue�� �����Ѵ�.
					paramList.put(fromDB(paramName),fromDB(paramValue));
				}// if
			}// if
		}// while
		return upRes;
	}// class readForm
	
	/**
	* �Ķ������ ���� ��ȯ�Ѵ�.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	name			String				<BR>	
	* @return   String 	 											<BR>
	*/    
	public String getParameter(String name){
	// form���� �Ѿ�� �Ϲ� input type�� ���� name���� travere�ؼ� �����Ѵ�.

		// request��ü�� ���� �Ǿ� ���� ������ null�� �����Ѵ�.
		String str = (String)paramList.get(name);
		if(str == null) return "";	// paramList�� ���� ���̸� �ƹ��͵� �������� �ʴ´�.

		return str;
	}// class getParameter
	
	/**
	* ȭ���� ������ Ư����ġ�� ����.  			<BR>
	*
	* @param  	Parameter properties which related with this method <BR>
	*			1.	in				DataInputStream					<BR>	
	*			2.	filename		String							<BR>	
	* @return   int 	 											<BR>
	* 				-1: ����										<BR>
	* 				0 : ����										<BR>
	* 				1 : ȭ���� �Է� ����							<BR>
	* 				2 : ȭ���� �ʹ�ŭ								<BR>
	*/
	protected int writeToFile(DataInputStream in,String filename){
		byte ch=0;
		byte[] buffer = new byte[1024];
		byte[] pre_buffer = new byte[1024];
		int x=0,delpos=0;
		int pre_x=0;
		long fsize = 0;
		boolean flag=true;
		
		if(deliminator == null) return 1; // deliminator�� ����ִ��� ���θ� üũ

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
						// Deliminator�� ������ ����
						// Deliminator�� \r\n�տ� ���⶧���� ������ column=0�� ��ġ�Ѵ�.
						outFile.write(pre_buffer,0,pre_x-2); // CR/LF�� �����ϱ� ���� -2�Ѵ�.
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
