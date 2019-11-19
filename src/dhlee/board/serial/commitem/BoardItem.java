package dhlee.board.serial.commitem;

import  java.io.Serializable;

public class BoardItem implements Serializable {

        public String   table_name;
        public int      disp_no;
        public int      pk;
        public int      seq_no;
        public String   title;
        public String	emp_no;
        public String	del_key;
        public String	ctnt_type;
        public String   author;
        public String   passwd;
        public String   email;
        public String   afile;
        public String	location;
        public String	file_name;
        public String   regdate;
        public String   answer;
        public int      readcnt;
        public String   ctnt;
        public String   lvl_no;
        public int      lvl;
        public int      sort_seq;
        public int      max_row;
        public int      total_row;

        public String   mode;

        public void printMember() {
                System.out.println("==========> BoardItem Start <===============");
                System.out.println("table_name = [" + table_name + "]");
                System.out.println("disp_no = [" + disp_no + "]");
                System.out.println("pk = [" + pk + "]");
                System.out.println("seq_no = [" + seq_no + "]");
                System.out.println("title = [" + title + "]");
                System.out.println("author = [" + author + "]");
                System.out.println("passwd = [" + passwd + "]");
                System.out.println("email = [" + email + "]");
                System.out.println("afile = [" + afile + "]");
                System.out.println("readcnt = [" + readcnt + "]");
                System.out.println("ctnt = [" + ctnt + "]");
                System.out.println("lvl_no = [" + lvl_no + "]");
                System.out.println("lvl = [" + lvl + "]");
                System.out.println("empno = [" + emp_no + "]");
                System.out.println("del_key = [" + del_key + "]");
                System.out.println("ctnt_type = [" + ctnt_type + "]");
                System.out.println("file_name = [" + file_name + "]");
                System.out.println("mode = [" + mode + "]");
        }

        public void init() {
                table_name = "";
                pk = 0;
                seq_no = 0;
                title = "";
                author = "";
                passwd = "";
                email = "";
                afile = "";
                regdate = "";
                readcnt = 0;
                ctnt = "";
                max_row = 0;
                emp_no = "";
                del_key = "";
                ctnt_type = "";
                file_name = "";
                answer = "";
                mode = "";
        }

        public void ItemKsc2Uni() {
                title = Ksc2Uni(title) ;
                author = Ksc2Uni(author) ;
                passwd = Ksc2Uni(passwd) ;
                afile = Ksc2Uni(afile) ;
                ctnt = Ksc2Uni(ctnt) ;
                email = Ksc2Uni(email) ;
                file_name = Ksc2Uni(file_name);
        }

        public void ItemUni2Ksc() {
                title = Uni2Ksc(title);
                author = Uni2Ksc(author);
                passwd = Uni2Ksc(passwd);
                afile = Uni2Ksc(afile);
                ctnt = Uni2Ksc(ctnt);
                email = Uni2Ksc(email);
                file_name = Uni2Ksc(file_name);
        }

        public static String Uni2Ksc(String src) {
                String  ret = null ;
                try {
                        if (src == null) return "";
                        ret = new String(src.getBytes("8859_1"), "KSC5601");
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                }
                return ret;
        }

        public static String Ksc2Uni(String src) {
                String  ret = null ;
                try {
                        if (src == null) return "";
                        ret =  new String(src.getBytes("KSC5601"), "8859_1");
                }
                catch (Exception ex) {
                        ex.printStackTrace();
                }
                return ret;
        }
}