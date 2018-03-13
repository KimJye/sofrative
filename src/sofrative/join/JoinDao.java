package sofrative.join;

/*
 * getInstance 메소드 : JoinDao 객체을 리턴하는 메소드
 * insert 메소드 : 회원가입을 위해 필요한 정보를 DB에 저장하며 회원 가입을 시키는 메소드
 * overlapId 메소드 : 아이디 중복 확인을 위한 메소드
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sofrative.DB.DBUtil;
import sofrative.member.Member;

public class JoinDao {

   public static JoinDao instance = new JoinDao();

   public static JoinDao getInstance() {
      return instance;
   }


   public void insert(Connection conn, Member member) throws SQLException{
	   //회원가입시 필요한 정보를 Member객체에 저장하여 전달받고 DB에 삽입하는 메소드
      try (PreparedStatement pstmt = conn.prepareStatement(
            "insert into member values(?,?,?,?,?,?,?,null)")){
         pstmt.setString(1, member.getId());
         pstmt.setString(2, member.getPassword());
         pstmt.setString(3, member.getName());
         pstmt.setString(4, member.getDepartment());
         pstmt.setString(5, member.getEmail()+member.getUrl());
         pstmt.setString(6, member.getPhone());
         pstmt.setInt(7, 0);
         pstmt.executeUpdate();
      }
   }

   public int overlapId(String id) throws SQLException {
	   //쿼리문을 이용하여 매개변수로 전달받은 ID가 존재 유무를 확인한 후 정수값을 리턴하는 메소드
      Connection conn = DBUtil.getConnection();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int check = 0;

      try {
         pstmt = conn.prepareStatement(
               "select password from member where memberid=?");
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();

         if(rs.next()) check = 1;
         
         else check = 0;
      }finally {
         DBUtil.close(conn);
         DBUtil.close(pstmt);
         DBUtil.close(rs);
      }
      return check;
   }
}