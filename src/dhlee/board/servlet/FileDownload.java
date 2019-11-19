package dhlee.board.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
* File �� ������ �ٿ�ε� �ް� �Ѵ�.
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
            if(fileName == null) { //���丮 ����Ʈ�� �����ش�.
                PrintWriter out = new PrintWriter(new OutputStreamWriter(res.getOutputStream(), "KSC5601"));
                res.setContentType("text/html;charset=euc-kr");
                out.println("<html><body><p align=center>�ڷ��</p>[���� �ڷ�� ���丮]" + filePath + "<br>");

                File fileDir = new File(filePath);
                String[] fileList = fileDir.list();

                for(int i = 0; i < fileList.length; i++) {
                    out.println("<a href='download?filedir=" + filePath + "&filename=" + fileList[i] + "'>" + fileList[i] + "</a><br>");
                }

                out.println("<hr>");
                out.println("</body></html>");
                out.close();
            } else { //������ ������ �ٿ�ε� �Ѵ�.
                byte b[] = new byte[1024*4];
                File file = new File(filePath, fileName);
                FileInputStream in = new FileInputStream(file);

                //Http���Ŀ� �������� �ʴ� contentType�� ���������� �������� �ٿ�ް� �Ѵ�
                res.setContentType("application/smnet");
                //�������� �ٿ�ε��� �� �ߴ� Save As ���̾�α� �ڽ��� ����Ʈ �����̸��� filename���� �Ѵ�.
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