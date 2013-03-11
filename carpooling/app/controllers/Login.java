public interface Login{
	
	public boolean logIn(String username, String password);
	
	public boolean isConnected();
	
	public boolean logOut();
}