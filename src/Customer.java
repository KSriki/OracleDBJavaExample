import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Customer {
	
	private static final String getFullCustomer = "select c.cid,c.fullname,c.title,c.streetaddress,t.city,c.state,c.zipcode, c.emailaddress, s.company,p.position "
			+ "from customers c "
			+ "inner join companies s on s.companyid=c.companyid "
			+ "inner join position p on p.positionid=c.positionid "
			+ "inner join cities t on t.citiesid=c.citiesid";

	private int cid;
	
	public void updateDB(){
		Connection con = null;
		PreparedStatement pt = null;
		PreparedStatement pstmtStreet = null;
		PreparedStatement pstmtC = null;
		PreparedStatement pstmtState = null;
		PreparedStatement pstmtZ = null;
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		//Should parse and white list here
		String street ="";
		String city = "";
		String state = "";
		String zip = "";
		String sql = "update customers set ";
				
		System.out.print("Street: ");
		street = scan.nextLine();
		System.out.print("City: ");
		city = scan.nextLine();
		System.out.print("State: ");
		state = scan.nextLine();
		System.out.print("Zip: ");
		zip = scan.nextLine();
//		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
            //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
            con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
          
            //street
            pstmtStreet = con.prepareStatement("update customers set Streetaddress = ? where cid = ?");
            pstmtStreet.setString(1, street);
            pstmtStreet.setInt(2, cid);
            pstmtStreet.executeUpdate();
            
            pt = con.prepareStatement("select count(*) from cities where city = ?");
            pt.setString(1, city);
            rs = pt.executeQuery();
            rs.next();
            
         
            
            if(Integer.parseInt(rs.getString(1)) > 0 ) {
            	pstmtC = con.prepareStatement("update customers set customers.citiesid = (select t.citiesid from cities t where t.city = ?) where customers.cid = ?");
                pstmtC.setString(1, city);	
                pstmtC.setInt(2, cid);
                pstmtC.executeUpdate();
            }
            else{
            	pstmtC = con.prepareStatement("insert into cities(city) values (?)");
                pstmtC.setString(1, city);	
                pstmtC.executeUpdate();
                pstmtC.close();
                pstmtC = con.prepareStatement("update customers set customers.citiesid = (select t.citiesid from cities t where t.city = ?) where customers.cid = ?");
                pstmtC.setString(1, city);
                pstmtC.setInt(2, cid);	
                pstmtC.executeUpdate();             
            	
            }
           
            
            //cities
       /*     pstmtC = con.prepareStatement("update t2 set city = ? from "
            		+ " customers t1 inner join cities t2 on t1.citiesid=t2.citiesid where t1.cid = ?");
            pstmtC.setString(1, city);	
            pstmtC.setInt(2, cid);
            pstmtC.executeUpdate();
         */
            
            
            //state
            pstmtState = con.prepareStatement("update customers set state = ? where cid = ?");
            pstmtState.setString(1, state);	
            pstmtState.setInt(2, cid);
            pstmtState.executeUpdate();
            
            //zipcode
            pstmtZ = con.prepareStatement("update customers set Zipcode = ? where cid = ?"); 
            pstmtZ.setString(1, zip);	
            pstmtZ.setInt(2, cid);
            pstmtZ.executeUpdate();
            
            
            
            		
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
	} finally {
		try {
			
			pt.close();
			pstmtStreet.close();;
			pstmtC.close();
			pstmtState.close();
			pstmtZ.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
		
//		System.out.println(street);
//		System.out.println(city);
//		System.out.println(state);
//		System.out.println(zip);
//		
		
//		try{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
//                        con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
//			pstmt = con.createStatement();
//			rs = pstmt.executeQuery(getFullCustomer);
//		
		
		
	}
	
	public void connectDB(){
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		//Should parse and white list here
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
                        //con = DriverManager.getConnection("jdbc:oracle:thin:sys as sysdba/oracle@localhost:1521:orcl");
                        con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			stmt = con.createStatement();
			rs = stmt.executeQuery(getFullCustomer);
			//result.next advance to next record includign first one
			//while(rs.next()){
				//starts at 1, column number
			int next = 1;
			Scanner scanner = new Scanner(System.in);
			while(next == 1){
				rs.next();
				System.out.print("Customer Number: " + rs.getString(1) + "\n");
				cid = Integer.parseInt(rs.getString(1));
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
			
			if(next ==2){
				updateDB();
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
