package Sai.Expense;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("sai")
public class Resource {
	database acces_ele = new database();
	
	
	@Path("users")	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<user> getusers() throws SQLException
	{
		return acces_ele.getusers();
	}
	
	
	@Path("users/user/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public user get_user(@PathParam("user_id") int user_id) throws SQLException
	{
		return acces_ele.get_user(user_id);
	}
	
	@Path("users")	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createuser(user a) throws SQLException
	{
		acces_ele.createuser(a);
		return "User added succesfully";
	}
	
	
	@Path("users/user/{user_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateuser(@PathParam("user_id") int id, user a) throws SQLException
	{
		acces_ele.updateuser(id, a);
		return "User details updated successfully";
	}
	
	
	@Path("users/user/{user_id}")
	@DELETE
	public String deleteuser(@PathParam("user_id") int id) throws SQLException
	{
		acces_ele.deleteuser(id);
		return "User have been succesfully deleted";
	}
	
	
	@Path("merchants")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<merchant> getmerchants() throws SQLException
	{
		return acces_ele.getmerchants();
	}
	
	
	@Path("merchants/merchant/{merchant_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public merchant get_merchant(@PathParam("merchant_id") int id) throws SQLException
	{
		return acces_ele.get_merchant(id);
	}
	
	
	@Path("merchants")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createmerchant(merchant a) throws SQLException
	{
		acces_ele.createuser(a);
		return "Merchant created succesfully";
	}
	
	
	@Path("merchants/merchant/{merchant_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatemerchant(@PathParam("merchant_id") int id, merchant a) throws SQLException
	{
		acces_ele.updatemerchant(id, a);
		return "Merchant details updated succesfully";
	}
	
	
	
	@Path("merchants/merchant/{merchant_id}")
	@DELETE
	public String deletemerchant(@PathParam("merchant_id") int id) throws SQLException
	{
		acces_ele.deletemerchant(id);
		return "Merchant details deleted";
	}
	
	
	@Path("currencies")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<currency> getcurrency() throws SQLException
	{
		return acces_ele.getcurrency();
	}
	

	@Path("currencies/currency/{curr_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public currency get_currency(@PathParam("curr_id") int id) throws SQLException
	{
		return acces_ele.get_currency(id);
	}
	
	
	@Path("currencies")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createcurrency(currency a) throws SQLException
	{
		acces_ele.createcurrency(a);
		return "Currency created";
	}
	
	@Path("currencies/currency/{curr_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatecurrency(@PathParam("curr_id") int id, currency a) throws SQLException
	{
		acces_ele.updatecurrency(id, a);
		return "updated succesfully";
	}
	
	@Path("currencies/currency/{curr_id}")
	@DELETE
	public String deletecurrency(@PathParam("curr_id") int id) throws SQLException
	{
		acces_ele.deletecurrency(id);
		return "User deleted";
	}
	
	@Path("categories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<category> getcategory() throws SQLException
	{
		return acces_ele.getcategory();
	}
	
	@Path("categories/category/{category_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public category get_1category(@PathParam("category_id") int id) throws SQLException
	{
		return acces_ele.get_1category(id);
	}
	
	@Path("categories")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createcategory(category a) throws SQLException
	{
		acces_ele.createcategory(a);
		return "created succesfully";
	}
	
	@Path("categories/category/{category_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatecategory(@PathParam("category_id") int id, category a) throws SQLException
	{
		acces_ele.updatecategory(id, a);
		return "Upadted successfully";
	}

	@Path("categories/category/{category_id}")
	@DELETE
	public String deletecategory(@PathParam("category_id") int id) throws SQLException
	{
		acces_ele.deletecategory(id);
		return "Deleted succesfully";
	}
	
	@Path("expenses")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<expensee> getexpenses() throws SQLException
	{
		return acces_ele.getexpenses();
	}
	
	@Path("expenses/expense/{expense_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public expensee get_expense(@PathParam("expense_id") int exp_id) throws SQLException
	{
		return acces_ele.get_expense(exp_id);
	}
	
	@Path("expenses/expense/{expense_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateexpense(@PathParam("expense_id") int id, expense a) throws SQLException
	{
		acces_ele.updateexpense(id, a);
		return "updated succesfully";
	}
	
	@Path("expenses")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createexpense(expense a) 
	{
		return acces_ele.createexpense(a);
	}
	
	@Path("expenses/expense/{expense_id}")
	@DELETE
	public String deleteexpense(@PathParam("expense_id") int id) throws SQLException
	{
		acces_ele.deleteexpense(id);
		return "deleted succesfully";
	}
	
	@Path("reports/currency")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_currency_report(@QueryParam("user_id") int id) throws SQLException
	{
		return acces_ele.get_currency_report(id);
	}
	
	
	@Path("reports/user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_user_report(@QueryParam("user_id") int id) throws SQLException
	{
		return acces_ele.get_user_report(id);
	}
	
	@Path("reports/merchant")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_merchant_report( @QueryParam("user_id") int usr_id) throws SQLException
	{
		return acces_ele.get_merchant_report(usr_id);
	}
	
	@Path("reports/category")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_category_report(@QueryParam("user_id") int usr_id) throws SQLException
	{
		return acces_ele.get_category_report(usr_id);
	}
	
	@Path("reports/user/category")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_userreport(@QueryParam("user_id") int usr_id, @QueryParam("category_id") int cat_id) throws SQLException
	{
		return acces_ele.get_userreport(usr_id, cat_id);
	}
	
	@Path("reports/categories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<display> get_categories(@QueryParam("user_id") int usr_id) throws SQLException
	{
		return acces_ele.get_categories(usr_id);
	}
}