package sofrative.login;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sofrative.DB.DBUtil;

/*
 * 로그아웃 시 필요한 클래스
 * getInstance() : LogoutDB객체를 리턴하는 메소드
 * logoutCheck() : 해당 id의 LOGIN 컬럼 값을 변경하고 변경 유무를 리턴하는 메소드
 * 
 */
public class LogoutDB {

	private static LogoutDB instance = new LogoutDB();

	public static LogoutDB getInstance() {
		return instance;
	}

	public boolean logoutCheck(String id) throws Exception {
		//boolean함수를 통해 LOGIN컬럼의 값을 변경하고 변경 유무를 리턴한다. 리턴한 값은 TMessageAction.jsp 에서 팝업창으로 로그아웃 성공/실패를 나타낸다.
		String memberId = id; 
		boolean result = false;
		int x = -1; //DB쿼리가 실행되었는지 알려주는 변수(1이면 실행된거임.)

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;


		try {
			String query="update member set LOGIN=? where MEMBERID='" + id + "'";

			connection = DBUtil.getConnection();

			stmt = connection.prepareStatement(query);

			stmt.setString(1, "0");
			//LOGIN 컬럼의 값을 0으로 바꿔줌.

			x = stmt.executeUpdate();

			if(x==1) {// LOGIN 컬럼의 값을 0으로 바꿨으면
				result = true;
			}
		}catch (SQLException ex) {
			System.out.print(ex.getMessage());
			ex.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(stmt);
			DBUtil.close(rs);
		}

		return result;
	}

}