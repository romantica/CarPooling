package controllers.interfaces;

public interface ILogin{
	
	public boolean logIn(String username, String password);
	
	public boolean isConnected();
	
	public boolean logOut();
}