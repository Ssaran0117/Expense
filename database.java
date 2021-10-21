package Sai.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;



public class database 
{
	Connection con = null;
	public database()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense","root","Saran@123");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
//	Get All Users
	public List<user> getusers() throws SQLException
	{
		List<user> users = new ArrayList<user>();
		String sql = "SELECT * FROM user";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		Jedis jedis = new Jedis();
		while(rs.next())
		{
			user a = new user();
			String p = rs.getString(1);
			String s = "user"+p;
			if(jedis.exists(s))
			{
				a.setUser_id(jedis.lindex(s, 0));
				a.setUser_name(jedis.lindex(s,1));
				a.setUser_email(jedis.lindex(s, 2));
				a.setRole(jedis.lindex(s, 3));
				users.add(a);
			}
			else
			{
				a.setUser_id(p);
				a.setUser_name(rs.getString(2));
				a.setUser_email(rs.getString(3));
				a.setRole(rs.getString(4));
				jedis.lpush(s, a.getUser_id());
				jedis.rpush(s, a.getUser_name());
				jedis.rpush(s, a.getUser_email());
				jedis.rpush(s, a.getRole());
				users.add(a);
			}
		}
		return users;
	}

	
//Get One User	
	@SuppressWarnings("unused")
	public user get_user(int id) throws SQLException
	{
		String sql = "SELECT * FROM user WHERE user_id="+id;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		Jedis jedis = new Jedis();
		user a = new user();
		String s = "user"+Integer.toString(id);
		if(jedis.exists(s))
		{
			a.setUser_id(jedis.lindex(s, 0));
			a.setUser_name(jedis.lindex(s,1));
			a.setUser_email(jedis.lindex(s, 2));
			a.setRole(jedis.lindex(s, 3));
		}
		else if(rs.next())
			{
				a.setUser_id(rs.getString(1));
				a.setUser_name(rs.getString(2));
				a.setUser_email(rs.getString(3));
				a.setRole(rs.getString(4));
				jedis.lpush(s, a.getUser_id());
				jedis.rpush(s, a.getUser_name());
				jedis.rpush(s, a.getUser_email());
				jedis.rpush(s, a.getRole());
			}
		return a;
	}

	
//Create a User
	public void createuser(user a) throws SQLException 
	{
		String sql = "insert into user(user_name, user_email, role) values(?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, a.getUser_name());
		st.setString(2,a.getUser_email());
		st.setString(3, a.getRole());
		st.executeUpdate();
	}

	
	
//Update user details
	public void updateuser(int id, user a) throws SQLException 
	{
		String sql = "update user set user_name=?, user_email=?, role=? where user_id="+id;
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, a.getUser_name());
		st.setString(2, a.getUser_email());
		st.setString(3, a.getRole());
		st.executeUpdate();
	}

	
//Delete User
	public void deleteuser(int id) throws SQLException {
		Statement st = con.createStatement();
		String sql = "delete from user where user_id="+id;
		st.executeUpdate(sql);
	}

	
//Get all merchant
	public List<merchant> getmerchants() throws SQLException 
	{
		List<merchant> merchants = new ArrayList<merchant>();
		String sql = "select * from merchant";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		Jedis jedis = new Jedis();
		while(rs.next())
		{
			merchant a = new merchant();
			String s = "merchant"+Integer.toString(rs.getInt(1));
			if(jedis.exists(s))
			{
				a.setMerchant_id(Integer.parseInt(jedis.lindex(s,0)));
				a.setMerchant_name(jedis.lindex(s, 1));
				a.setDescription(jedis.lindex(s, 2));
			}
			else
				{
					a.setMerchant_id(rs.getInt(1));
					a.setMerchant_name(rs.getString(2));
					a.setDescription(rs.getString(3));
					jedis.lpush(s, Integer.toString(a.getMerchant_id()));
					jedis.rpush(s, a.getMerchant_name());
					jedis.rpush(s, a.getDescription());
				}
			merchants.add(a);
		}
		return merchants; 
	}

	
// Get merchant details
	public merchant get_merchant(int id) throws SQLException {
		String sql = "select * from merchant where merchant_id="+id;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		merchant a = new merchant();
		if(rs.next())
			{
				a.setMerchant_id(rs.getInt(1));
				a.setMerchant_name(rs.getString(2));
				a.setDescription(rs.getString(3));
			}
		return a;
	}

	
	
//Create a merchant
	public void createuser(merchant a) throws SQLException {
		String sql = "insert into merchant(merchant_name, description) values(?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, a.getMerchant_name());
		st.setString(2, a.getDescription());
		st.executeUpdate();
	}

	
	
//	Update merchant details
	public void updatemerchant(int id, merchant a) throws SQLException {
		String sql = "update merchant set merchant_name=?, description=? where merchant_id="+id;
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, a.getMerchant_name());
		st.setString(2, a.getDescription());
		st.executeUpdate();
	}

	
	
//	Delete merchant details
	public void deletemerchant(int id) throws SQLException {
		String sql = "delete from merchant where merchant_id="+id;
		Statement st = con.createStatement();
		st.executeUpdate(sql);
	}

//	 Get all currencies
	public List<currency> getcurrency() throws SQLException {
		List<currency> all = new ArrayList<currency>();
		Statement st = con.createStatement();
		String sql = "select * from currency";
		ResultSet rs = st.executeQuery(sql);
		Jedis jedis = new Jedis();
		while(rs.next())
		{
			currency a = new currency();
			String s = "currency"+Integer.toString(rs.getInt(1));
			if(jedis.exists(s))
			{
				a.setCurr_id(Integer.parseInt(jedis.lindex(s,0)));
				a.setCurr_code(jedis.lindex(s, 1));
				a.setCurr_symbol(jedis.lindex(s, 2));
			}
			else
				{
					a.setCurr_id(rs.getInt(1));
					a.setCurr_code(rs.getString(2));
					a.setCurr_symbol(rs.getString(3));
					jedis.lpush(s, Integer.toString(a.getCurr_id()));
					jedis.rpush(s, a.getCurr_code());
					jedis.rpush(s, a.getCurr_symbol());
				}
			all.add(a);
		}
		return all;
	}

//	Get a currency details
	public currency get_currency(int id) throws SQLException {
		currency a = new currency();
		String sql = "select * from currency where curr_id="+id;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		Jedis jedis = new Jedis();
		String s = "currency"+Integer.toString(id);
		if(jedis.exists(s))
		{
			a.setCurr_id(Integer.parseInt(jedis.lindex(s,0)));
			a.setCurr_code(jedis.lindex(s, 1));
			a.setCurr_symbol(jedis.lindex(s, 2));
		}
		else if(rs.next())
			{
				a.setCurr_id(rs.getInt(1));
				a.setCurr_code(rs.getString(2));
				a.setCurr_symbol(rs.getString(3));
				jedis.lpush(s, Integer.toString(a.getCurr_id()));
				jedis.rpush(s, a.getCurr_code());
				jedis.rpush(s, a.getCurr_symbol());
			}
		return a;
	}

		
//		Create a currency
		public void createcurrency(currency a) throws SQLException {
			String sql = "insert into currency(curr_code, curr_symbol) values(?,?)";
			PreparedStatement st = con.prepareStatement(sql);
			Jedis jedis = new Jedis();
			String s = "currency_"+a.getCurr_code(), p = "";
			st.setString(1, a.getCurr_code());
			st.setString(2, a.getCurr_symbol());
			st.executeUpdate();
			if(!jedis.exists(s))
			{
				p += Integer.toString(a.getCurr_id())+"__"+ a.getCurr_code()+"__"+ a.getCurr_symbol();
				jedis.hset("currency",s, p);
			}
		}

//		Update a currency
		public void updatecurrency(int id, currency a) throws SQLException {
			String sql = "update currency set curr_code=?, curr_symbol=? where curr_id="+id;
			PreparedStatement st = con.prepareStatement(sql);
			Jedis jedis = new Jedis();
			String s = "", p = "";
			jedis.del("currency");
			st.setString(1, a.getCurr_code());
			st.setString(2, a.getCurr_symbol());
			st.executeUpdate();
			s = "currency_"+a.getCurr_code();
			p += Integer.toString(a.getCurr_id())+"__"+ a.getCurr_code()+"__"+ a.getCurr_symbol();
			jedis.hset("currency",s, p);
		}

//		Delete a currency
		public void deletecurrency(int id) throws SQLException {
			String sql = "delete from currency where curr_id="+id;
			Statement st = con.createStatement();
			st.executeUpdate(sql);
		}

//		Get all category List
		public List<category> getcategory() throws SQLException {
			List<category> all = new ArrayList<category>();
			String sql = "select * from category";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				String s = "category"+Integer.toString(rs.getInt(1));
				category a = new category();
				if(jedis.exists(s))
				{
					a.setCategory_id(Integer.parseInt(jedis.lindex(s,0)));
					a.setName(jedis.lindex(s, 1));
					a.setDescription(jedis.lindex(s, 2));
				}
				else
					{
						a.setCategory_id(rs.getInt(1));
						a.setName(rs.getString(2));
						a.setDescription(rs.getString(3));
						jedis.lpush(s, Integer.toString(a.getCategory_id()));
						jedis.rpush(s, a.getName());
						jedis.rpush(s, a.getDescription());
					}
				all.add(a);
			}
			return all;
		}

//		Get a category details
		public category get_1category(int id) throws SQLException {
			String sql = "select * from category where category_id="+id;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			category a = new category();
			if(rs.next())
					a.setCategory_id(rs.getInt(1));
					a.setName(rs.getString(2));
					a.setDescription(rs.getString(3));
			return a;
		}

//		Create a category
		public void createcategory(category a) throws SQLException {
			String sql = "insert into category(name, description) select * from(select ? as name, ? as description) as temp where not exists(select name from category where name='"+a.getName()+"')";
			PreparedStatement st = con.prepareStatement(sql);
			String s = "category_"+a.getName();
			Jedis jedis = new Jedis();
			st.setString(1, a.getName());
			st.setString(2, a.getDescription());
			st.executeUpdate();
			if(!jedis.exists(s))	{
				jedis.lpush(s, Integer.toString(a.getCategory_id()), a.getName(), a.getDescription());
			}
		}

//		Update a category
		public void updatecategory(int id, category a) throws SQLException {
			String sql = "update category set name=?, description=? where category_id="+id;
			PreparedStatement st = con.prepareStatement(sql);
			category p = get_1category(id);
			String s = "category_"+p.getName();
			Jedis jedis = new Jedis();
			jedis.del(s);
			st.setString(1, a.getName());
			st.setString(2, a.getDescription());
			st.executeUpdate();
			s = "category_"+a.getName();
			jedis.lpush(s, Integer.toString(a.getCategory_id()), a.getName(), a.getDescription());
		}

//		Delete a category
		public void deletecategory(int id) throws SQLException {
			String sql = "delete from category where category_id="+id;
			Statement st = con.createStatement();
			st.executeUpdate(sql);
		}

//		get all expenses
		public List<expensee> getexpenses() throws SQLException {
			List<expensee> all = new ArrayList<expensee>();
			String sql = "select e.reference,e.amount,u.*,c.*,m.*,cr.*,e.expense_id  from expense as e inner join user as u on u.user_id=e.user_id inner join category as c on c.category_id=e.category_id inner join currency as cr on cr.curr_id=e.curr_id inner join merchant as m on m.merchant_id=e.merchant_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				expensee a = new expensee();
				String s = "expense"+rs.getInt(16);
				if(jedis.exists(s))
				{
					a.setReference(jedis.lindex(s, 0));
					a.setAmount(jedis.lindex(s, 1));
					a.setUsr_id(Integer.parseInt(jedis.lindex(s, 2)));
					a.setUsr_name(jedis.lindex(s, 3));
					a.setUsr_email(jedis.lindex(s, 4));
					a.setUsr_role(jedis.lindex(s, 5));
					a.setCat_id(Integer.parseInt(jedis.lindex(s, 6)));
					a.setCat_name(jedis.lindex(s, 7));
					a.setCat_desc(jedis.lindex(s, 8));
					a.setMrc_id(Integer.parseInt(jedis.lindex(s, 9)));
					a.setMrc_name(jedis.lindex(s, 10));
					a.setMrc_desc(jedis.lindex(s, 11));
					a.setCur_id(Integer.parseInt(jedis.lindex(s, 12)));
					a.setCur_code(jedis.lindex(s, 13));
					a.setCur_sym(jedis.lindex(s, 14));
					a.setExp_id(Integer.parseInt(jedis.lindex(s, 15)));
				}
				else 
				{
					
					a.setReference(rs.getString(1));
					a.setAmount(rs.getString(15)+" "+rs.getString(2));
					a.setUsr_id(rs.getInt(3));
					a.setUsr_name(rs.getString(4));
					a.setUsr_email(rs.getString(5));
					a.setUsr_role(rs.getString(6));
					a.setCat_id(rs.getInt(7));
					a.setCat_name(rs.getString(8));
					a.setCat_desc(rs.getString(9));
					a.setMrc_id(rs.getInt(10));
					a.setMrc_name(rs.getString(11));
					a.setMrc_desc(rs.getString(12));
					a.setCur_id(rs.getInt(13));
					a.setCur_code(rs.getString(14));
					a.setCur_sym(rs.getString(15));
					a.setExp_id(rs.getInt(16));
					jedis.rpush(s, a.getReference(),a.getCur_sym()+" "+a.getAmount(),Integer.toString(a.getUsr_id()),a.getUsr_name(),a.getUsr_email(),a.getUsr_role(),Integer.toString(a.getCat_id()),a.getCat_name(),a.getCat_desc(),Integer.toString(a.getMrc_id()),a.getMrc_name(),a.getMrc_desc(),Integer.toString(a.getCur_id()),a.getCur_code(),a.getCur_sym(),Integer.toString(a.getExp_id()));
				}
					all.add(a);
			}
			return all;
		}

//		Get expense details
		public expensee get_expense(int exp_id) throws SQLException {
			String sql = "select e.reference,e.amount,u.*,c.*,m.*,cr.*,e.expense_id  from expense as e inner join user as u on u.user_id=e.user_id and e.expense_id='"+exp_id+"'inner join category as c on c.category_id=e.category_id inner join currency as cr on cr.curr_id=e.curr_id inner join merchant as m on m.merchant_id=e.merchant_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			expensee a = new expensee();
			Jedis jedis = new Jedis();
			String s = "expense"+Integer.toString(exp_id);
			if(jedis.exists(s))
			{
				a.setReference(jedis.lindex(s, 0));
				a.setAmount(jedis.lindex(s, 1));
				a.setUsr_id(Integer.parseInt(jedis.lindex(s, 2)));
				a.setUsr_name(jedis.lindex(s, 3));
				a.setUsr_email(jedis.lindex(s, 4));
				a.setUsr_role(jedis.lindex(s, 5));
				a.setCat_id(Integer.parseInt(jedis.lindex(s, 6)));
				a.setCat_name(jedis.lindex(s, 7));
				a.setCat_desc(jedis.lindex(s, 8));
				a.setMrc_id(Integer.parseInt(jedis.lindex(s, 9)));
				a.setMrc_name(jedis.lindex(s, 10));
				a.setMrc_desc(jedis.lindex(s, 11));
				a.setCur_id(Integer.parseInt(jedis.lindex(s, 12)));
				a.setCur_code(jedis.lindex(s, 13));
				a.setCur_sym(jedis.lindex(s, 14));
				a.setExp_id(Integer.parseInt(jedis.lindex(s, 15)));
			}
			else if(rs.next())
			{
				
				a.setReference(rs.getString(1));
				a.setAmount(rs.getString(15)+" "+rs.getString(2));
				a.setUsr_id(rs.getInt(3));
				a.setUsr_name(rs.getString(4));
				a.setUsr_email(rs.getString(5));
				a.setUsr_role(rs.getString(6));
				a.setCat_id(rs.getInt(7));
				a.setCat_name(rs.getString(8));
				a.setCat_desc(rs.getString(9));
				a.setMrc_id(rs.getInt(10));
				a.setMrc_name(rs.getString(11));
				a.setMrc_desc(rs.getString(12));
				a.setCur_id(rs.getInt(13));
				a.setCur_code(rs.getString(14));
				a.setCur_sym(rs.getString(15));
				a.setExp_id(rs.getInt(16));
				jedis.rpush(s, a.getReference(),a.getCur_sym()+" "+a.getAmount(),Integer.toString(a.getUsr_id()),a.getUsr_name(),a.getUsr_email(),a.getUsr_role(),Integer.toString(a.getCat_id()),a.getCat_name(),a.getCat_desc(),Integer.toString(a.getMrc_id()),a.getMrc_name(),a.getMrc_desc(),Integer.toString(a.getCur_id()),a.getCur_code(),a.getCur_sym(),Integer.toString(a.getExp_id()));
			}
			return a;
		}

//		Update expense
		public void updateexpense(int id, expense a) throws SQLException {
			String sql = "update expense set amount=?, reference=?, merchant_id=?, user_id=?, curr_id=?, category_id=? where expense_id="+id;
			PreparedStatement st = con.prepareStatement(sql);
			st.setFloat(1, a.getAmount());
			st.setString(2, a.getReference());
			st.setInt(3, a.getMerchant_id());
			st.setInt(4, a.getUser_id());
			st.setInt(5, a.getCurr_id());
			st.setInt(6, a.getCategory_id());
			st.executeUpdate();
		}

//		create a expense
		public String createexpense(expense a) {
			String sql = "insert expense set amount=?, reference=?, merchant_id=?, user_id=?, curr_id=?, category_id=?";
			String x = "";
			try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setFloat(1, a.getAmount());
			st.setString(2, a.getReference());
			st.setInt(3, a.getMerchant_id());
			st.setInt(4, a.getUser_id());
			st.setInt(5, a.getCurr_id());
			st.setInt(6, a.getCategory_id());
			st.executeUpdate();
			x = x+"User added successfully";
			}
			catch(Exception e1)
			{
				x = x+"Please first add the below details in the respective table\n";
				List<Integer> list = new ArrayList<Integer>();
				String sq = "select user_id from user";
				try {
				Statement st = con.createStatement();
				ResultSet r = st.executeQuery(sq);
				while(r.next())
				{
					int p = r.getInt(1);
					list.add(p);
				}
				if(!(list.contains(a.getUser_id())))
					x = x+"User\n";
				}
				catch(Exception e2)
				{}
				list.clear();
				sq = "select merchant_id from merchant";
				try {
					Statement st = con.createStatement();
					ResultSet r = st.executeQuery(sq);
				while(r.next())
				{
					int p = r.getInt(1);
					list.add(p);
				}
				if(!(list.contains(a.getMerchant_id())))
					x = x+"Merchant\n";
				}
				catch(Exception e3)
				{}
				list.clear();
				sq = "select curr_id from currency";
				try {
					Statement st = con.createStatement();
					ResultSet r = st.executeQuery(sq);
				while(r.next())
				{
					int p = r.getInt(1);
					list.add(p);
				}
				if(!(list.contains(a.getCurr_id())))
					x = x+"Currency\n";
				}
				catch(Exception e4)
				{}
				list.clear();
				sq = "select category_id from category";
				try {
					Statement st = con.createStatement();
					ResultSet r = st.executeQuery(sq);
				while(r.next())
				{
					int p = r.getInt(1);
					list.add(p);
				}
				if(!(list.contains(a.getCategory_id())))
					x = x+"Category\n";
				}
				catch(Exception e5)
				{}
			}
			return x;
		}
		
		
// 		delete a expense
		public void deleteexpense(int id) throws SQLException {
			String sql = "delete from expense where expense_id="+id;
			Statement st = con.createStatement();
			st.executeUpdate(sql);
		}
		
		
//		generate currency report
		public List<display> get_currency_report(int id) throws SQLException {
			List<display> all = new ArrayList<display>();
			user a = get_user(id);
			String sql =(a.getRole().equalsIgnoreCase("admin"))?"select c.curr_code,count(e.amount),sum(e.amount) from expense as e inner join currency as c on c.curr_id=e.curr_id group by c.curr_id":
				"select c.curr_code,count(e.amount),sum(e.amount) from expense as e inner join currency as c on c.curr_id=e.curr_id and e.user_id='"+id+"'group by c.curr_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				display d = new display();
				d.setName(rs.getString(1));
				d.setCount(rs.getInt(2));
				d.setAmount(rs.getFloat(3));
				all.add(d);
			}
			return all;
			
		}

//		generate user report
		public List<display> get_user_report(int id) throws SQLException {
			List<display> all = new ArrayList<display>();
			user a = get_user(id);
			String sql =(a.getRole().equalsIgnoreCase("admin"))?"select u.user_name,count(e.amount),sum(e.amount) from expense as e inner join user as u on u.user_id=e.user_id group by u.user_id":
				"select u.user_name,count(e.amount),sum(e.amount) from expense as e inner join user as u on u.user_id=e.user_id and e.user_id='"+id+"'group by u.user_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				display d = new display();
				String s = "report_user"+rs.getString(1);
				if(jedis.exists(s))
				{
					d.setName(jedis.lindex(s, 0));
					d.setCount(Integer.parseInt(jedis.lindex(s, 1)));
					d.setAmount(Float.parseFloat(jedis.lindex(s, 2)));
				}
				else 
					{
						d.setName(rs.getString(1));
						d.setCount(rs.getInt(2));
						d.setAmount(rs.getFloat(3));
						jedis.rpush(s, d.getName());
						jedis.rpush(s, Integer.toString(d.getCount()));
						jedis.rpush(s, Float.toString(d.getAmount()));
					}
				all.add(d);
			}
			return all;
		}

//		generate merchant report
		public List<display> get_merchant_report(int usr_id) throws SQLException {
			List<display> all = new ArrayList<display>();
			user a = get_user(usr_id);
			String sql =(a.getRole().equalsIgnoreCase("admin"))?"select merchant.merchant_name, count(expense.expense_id), sum(expense.amount) from merchant inner join expense on expense.merchant_id= merchant.merchant_id group by merchant.merchant_id":
				"select merchant.merchant_name, count(expense.expense_id), sum(expense.amount) from merchant inner join expense on expense.merchant_id= merchant.merchant_id and expense.user_id='"+usr_id+"'group by merchant.merchant_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				display d = new display();
				String s = "report_merchant"+rs.getString(1);
				if(jedis.exists(s))
				{
					d.setName(jedis.lindex(s, 0));
					d.setCount(Integer.parseInt(jedis.lindex(s, 1)));
					d.setAmount(Float.parseFloat(jedis.lindex(s, 2)));
				}
				else 
					{
						d.setName(rs.getString(1));
						d.setCount(rs.getInt(2));
						d.setAmount(rs.getFloat(3));
						jedis.rpush(s, d.getName());
						jedis.rpush(s, Integer.toString(d.getCount()));
						jedis.rpush(s, Float.toString(d.getAmount()));
					}
				all.add(d);
			}
			return all;
		}

//		generate category report
		public List<display> get_category_report(int usr_id) throws SQLException {
			List<display> all = new ArrayList<display>();
			String sql="";
			user a = get_user(usr_id);
			sql = (a.getRole().equalsIgnoreCase("admin"))?"select category.name, count(expense.expense_id), sum(expense.amount) from category inner join expense on expense.category_id= category.category_id group by category.category_id":
				"select category.name, count(expense.expense_id), sum(expense.amount) from category inner join expense on expense.category_id= category.category_id and expense.user_id='"+usr_id+"'group by category.category_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				display d = new display();
				String s = "report_category"+rs.getString(1);
				if(jedis.exists(s))
				{
					d.setName(jedis.lindex(s, 0));
					d.setCount(Integer.parseInt(jedis.lindex(s, 1)));
					d.setAmount(Float.parseFloat(jedis.lindex(s, 2)));
				}
				else 
					{
						d.setName(rs.getString(1));
						d.setCount(rs.getInt(2));
						d.setAmount(rs.getFloat(3));
						jedis.rpush(s, d.getName());
						jedis.rpush(s, Integer.toString(d.getCount()));
						jedis.rpush(s, Float.toString(d.getAmount()));
					}
				all.add(d);
			}
			return all;
		}

		public List<display> get_userreport(int usr_id, int cat_id) throws SQLException {
			List<display> all = new ArrayList<display>();
			String sql="";
			user a = get_user(usr_id);
			sql = (a.getRole().equalsIgnoreCase("admin"))?"select user.user_name, count(expense.expense_id), sum(expense.amount) from user inner join expense on expense.user_id= user.user_id and expense.category_id='"+cat_id+"' group by user.user_id":
				"select user.user_name, count(expense.expense_id), sum(expense.amount) from user inner join expense on expense.user_id= user.user_id and expense.category_id='"+cat_id+"' and expense.user_id='"+usr_id+"' group by user.user_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				display d = new display();
				String s = "report_user_"+rs.getString(1);
				if(jedis.exists(s))
				{
					d.setName(jedis.lindex(s, 0));
					d.setCount(Integer.parseInt(jedis.lindex(s, 1)));
					d.setAmount(Float.parseFloat(jedis.lindex(s, 2)));
				}
				else 
					{
						d.setName(rs.getString(1));
						d.setCount(rs.getInt(2));
						d.setAmount(rs.getFloat(3));
						jedis.rpush(s, d.getName());
						jedis.rpush(s, Integer.toString(d.getCount()));
						jedis.rpush(s, Float.toString(d.getAmount()));
					}
				all.add(d);
			}
			return all;
		}

		public List<display> get_categories(int usr_id) throws SQLException {
			List<display> all = new ArrayList<display>();
			String sql="";
			user a = get_user(usr_id);
			sql = (a.getRole().equalsIgnoreCase("admin"))?"select category.name, user.user_name, count(expense.expense_id), sum(expense.amount) from expense inner join user on expense.user_id= user.user_id inner join category on category.category_id=expense.category_id group by user.user_id, category.category_id":
				"select category.name, user.user_name, count(expense.expense_id), sum(expense.amount) from expense inner join user on expense.user_id= user.user_id inner join category on category.category_id=expense.category_id and expense.user_id='"+usr_id+"' group by user.user_id, category.category_id";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			Jedis jedis = new Jedis();
			while(rs.next())
			{
				display d = new display();
				String s = "report_categories"+rs.getString(1)+rs.getString(2);
				if(jedis.exists(s))
				{
					d.setName(jedis.lindex(s, 0));
					d.setCount(Integer.parseInt(jedis.lindex(s, 1)));
					d.setAmount(Float.parseFloat(jedis.lindex(s, 2)));
				}
				else 
					{
						d.setName(rs.getString(1)+"--"+rs.getString(2));
						d.setCount(rs.getInt(3));
						d.setAmount(rs.getFloat(4));
						jedis.rpush(s, d.getName());
						jedis.rpush(s, Integer.toString(d.getCount()));
						jedis.rpush(s, Float.toString(d.getAmount()));
					}
				all.add(d);
			}
			return all;
		}
}