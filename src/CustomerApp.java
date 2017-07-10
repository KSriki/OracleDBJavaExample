import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerApp {
	public static void main(String[] args){
		Customer c = new Customer();
		c.connectDB();
		
	}
}
