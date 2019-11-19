package dhlee.board.io;

import java.io.*;
import java.util.Hashtable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import dhlee.lib.util.*;

public class FileUpLoad {
    static final int INIT_VECTOR_CAPACITY = 3;
    String desDirectory;
    String deliminator;
    Hashtable paramList;
    String realName;

    public FileUpLoad() {
        desDirectory = "./";
        deliminator = null;
        paramList = new Hashtable(3);
    }

    public int getIntParameter(String s) {
        String s1 = getParameter(s);
        if(s1 == null) {
            return 0;
        }
        else {
            s1 = s1.substring(0, s1.length() - 1);
            return Integer.parseInt(s1);
        }
    }

    public String getParameter(String s) {
        String s1 = (String)paramList.get(s);
        if(s1 == null) {
            return "";
        }
        else {
            return s1;
        }
    }

    public String getTruncParameter(String s) {
        String s1 = getParameter(s);
        s1 = s1.substring(0, s1.length() - 1);
        return s1;
    }

    public void readForm(HttpServletRequest req, HttpServletResponse res) throws IOException {

        DataInputStream datainputstream = new DataInputStream(req.getInputStream());
        PrintStream printstream = new PrintStream(res.getOutputStream());
        String s = null;
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        boolean flag = false;
        boolean flag1 = false;
        Object obj3 = null;

        deliminator = datainputstream.readLine();

        while((s = datainputstream.readLine()) != null) {

            if(s.indexOf("Content-Disposition") == 0) {

                int i = s.indexOf("name=");
                int k = s.indexOf("\"", i + 6);
                String s1 = s.substring(i + 6, k);

                if(s.indexOf("filename=") != -1) {
                    int j = s.indexOf("filename=");
                    int l = s.indexOf("\"", j + 10);
                    String s2 = s.substring(j + 10, l);

                    j = s2.lastIndexOf("\\");

                    if(j == -1) {
                        j = s2.lastIndexOf("/");
                    }

                    j++;
                    s2 = s2.substring(j, s2.length());
                    paramList.put(Utils.fromDB(s1), Utils.fromDB(s2));
					String originalFileName = null;
                    if(Utils.fromDB(s1).equals("afile")) {
                    	originalFileName = Utils.fromDB(s2);
                    	int pointIdx = originalFileName.lastIndexOf(".");
                    	if(pointIdx != -1) {
                    	    realName = getKST("yyyyMMddHHmmss") +
                    	      originalFileName.substring(pointIdx, originalFileName.length());	
                    	}
                    	else {
                    	    realName = getKST("yyyyMMddHHmmss");
                    	}
                    }

                    String s4 = Utils.fromDB(s2);

                    if(s4.lastIndexOf("\\") != -1) {
                        s4 = s4.substring(s4.lastIndexOf("\\") + 1);
                    }

                    s = datainputstream.readLine();

                    if(s.indexOf("Content-Type") == 0) {
                        s = datainputstream.readLine();
                    }
					if(originalFileName != null && originalFileName.length() > 0) {
						writeToFile(datainputstream, s4);
					}
                }
                else {
                    s = datainputstream.readLine();
                    String s3;

                    for(s3 = ""; (s = datainputstream.readLine()) != null; s3 = s3 + s) {
                        s = s + "\n";

                        if(s.indexOf(deliminator) == 0) {
                            break;
                        }
                    }

                    boolean flag2 = false;

                    try {
                        flag2 = paramList.containsKey(Utils.fromDB(s1));
                    }
                    catch(Exception exception) {
                        exception.printStackTrace();
                        flag2 = false;
                    }

                    if(!flag2) {
                        paramList.put(Utils.fromDB(s1), Utils.fromDB(s3));
                        System.out.println("paramName = [" + Utils.fromDB(s1) + "]=["+Utils.fromDB(s3) +"]");
                    }
                }
            }
		}
    }

    public void setSaveDir(String s) {
        desDirectory = s + "/";
    }

    public void setRealName(String s) {
    	realName = s;
    }

    public String getRealName() {
    	return realName;
    }

    protected void writeToFile(DataInputStream datainputstream, String s) {
        boolean flag = false;
        byte abyte0[] = new byte[1024];
        byte abyte1[] = new byte[1024];
        int i = 0;
        boolean flag1 = false;
        int k = 0;
        boolean flag2 = true;

        if(deliminator == null) {
            return;
        }

        try {
            FileOutputStream fileoutputstream = new FileOutputStream(desDirectory + realName);
            while(flag2) {
                byte byte0 = datainputstream.readByte();
                abyte0[i++] = byte0;

                if(byte0 == 10 || i == 1023) {
                    String s1 = new String(abyte0, 0, i);
                    int j;

                    if((j = s1.indexOf(deliminator)) != -1) {
                        fileoutputstream.write(abyte1, 0, k - 2);
                        flag2 = false;
                    }
                    else {
                        fileoutputstream.write(abyte1, 0, k);
                    }

                    System.arraycopy(abyte0, 0, abyte1, 0, i);
                    k = i;
                    i = 0;
                }
            }
        }
        catch(EOFException _ex) {
        	;
        }
        catch(IOException ioexception) {
            System.out.println("ERROR : FILE UPLOAD FAILED (class fileUpload :: writeToFile");
            System.out.println(ioexception);
        }
    } // end of


   /**
    * 현재(한국기준) 시간정보를 얻는다.                     <BR>
    * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 셋팅하면 1998121011과 같이 Return.  <BR>
    * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이 
    *      0~23시간 타입으로 Return. <BR>
    * @param    format      얻고자하는 현재시간의 Type      
    * @return   String      현재 한국 시간.                             
    */
    public String getKST(String format)
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleDateFormat fmt= new SimpleDateFormat(format);
        SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
        fmt.setTimeZone(timeZone);
        long time=System.currentTimeMillis();
        String str=fmt.format(new java.util.Date(time));
        return str;
   }
}
