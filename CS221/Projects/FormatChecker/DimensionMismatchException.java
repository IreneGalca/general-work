/**
 * Runtime exception thrown when the 
 * given dimensions do not match the data. 
 * 
 * @author  CS 221
 */
public class DimensionMismatchException extends RuntimeException
{
	private static final long serialVersionUID = 1173110179989747752L;

	/**
	 * Default constructor. 
	 */
	public DimensionMismatchException()
	{
		super(); 
	}
	
	/**
	 * Constructor with given message. 
	 * @param message - String 
	 */
	public DimensionMismatchException(String message)
	{
		super(message); 
	}
}
