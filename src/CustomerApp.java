import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerApp {
	public static void main(String[] args){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet cities = null;
		//Should parse and white list here
		String sql = "select c.cid,c.fullname,c.title,c.streetaddress,t.city,c.state,c.zipcode, c.emailaddress, s.company,p.position "
				+ "from customers c "
				+ "inner join companies s on s.companyid=c.companyid "
				+ "inner join position p on p.positionid=c.positionid "
				+ "inner join cities t on t.citiesid=c.citiesid";
   
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
                        con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			//result.next advance to next record includign first one
			//while(rs.next()){
				//starts at 1, column number
			int next = 1;
			Scanner scanner = new Scanner(System.in);
			while(next == 1){
				rs.next();
				System.out.print("Customer Number: " + rs.getString(1) + "\n");
				System.out.print(rs.getString(3) + " ");
				System.out.print(rs.getString(2) + "\n");				
				System.out.print(rs.getString(4) + "\n"); //address
											 
				System.out.print(rs.getString(5) + ", "); //city
				System.out.print(rs.getString(6) + " "); //state
				System.out.print(rs.getString(7) + "\n"); //zip
				
				System.out.print(rs.getString(8) + "\n"); //email
				System.out.print(rs.getString(10) + " at "); //position
				System.out.print(rs.getString(9) + "\n"); //company
				System.out.println("Press (1) to search for another customer or press (2) to Edit the customer's address.");
				next = scanner.nextInt();
			}
			//}
			}catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
