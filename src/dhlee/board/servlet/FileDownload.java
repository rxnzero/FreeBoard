package dhlee.board.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
* File 을 강제로 다운로드 받게 한다.
*/
public class FileDownload extends HttpServlet {
    private String to_ksc(String str) throws UnsupportedEncodingException {
            return new String(str.getBytes("8859_1"), "KSC5601");
    }

    private String to_uni(String str) throws UnsupportedEncodingException {
            return new String(str.getBytes("KSC5601"), "8859_1");
    }

	private String filePath;
    
	public void init() {
        this.filePath = this.getServletConfig().getInitParameter("upload");        
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String fileName = req.getParameter("filename");
        String toFileName = req.getParameter("tofilename");


        //filePath = ((ServletContext)getServletContext()).getRealPath(tmpPath);

        try {
            if(fileName == null) { //디렉토리 리스트를 보여준다.
                PrintWriter out = new PrintWriter(new OutputStreamWriter(res.getOutputStream(), "KSC5601"));
                res.setContentType("text/html;charset=euc-kr");
                out.println("<html><body><p align=center>자료실</p>[실제 자료실 디렉토리]" + filePath + "<br>");

                File fileDir = new File(filePath);
                String[] fileList = fileDir.list();

                for(int i = 0; i < fileList.length; i++) {
                    out.println("<a href='download?filedir=" + filePath + "&filename=" + fileList[i] + "'>" + fileList[i] + "</a><br>");
                }

                out.println("<hr>");
                out.println("</body></html>");
                out.close();
            } else { //실제로 파일을 다운로드 한다.
                byte b[] = new byte[1024*4];
                File file = new File(filePath, fileName);
                FileInputStream in = new FileInputStream(file);

                //Http형식에 존재하지 않는 contentType을 지정함으로 브라우저가 다운받게 한다
                res.setContentType("application/smnet");
                //브라우저가 다운로드할 때 뜨는 Save As 다이얼로그 박스의 디폴트 파일이름을 filename으로 한다.
                res.setHeader("Content-Disposition", "attachment;filename=" + to_uni(toFileName) + ";");

                ServletOutputStream out = res.getOutputStream();

                int numRead = 0;
                while((numRead = in.read(b)) != -1) {
                        out.write(b, 0, numRead);
                }

                out.close();
            }
        } catch(Exception e) {
                new PrintStream(res.getOutputStream()).println("Error is:" + e.toString());
        }
    }
}