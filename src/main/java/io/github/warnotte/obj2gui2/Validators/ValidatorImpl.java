/**
 * 
 */
package io.github.warnotte.obj2gui2.Validators;

/**
 * @author Warnotte Renaud
 *
 */
public class ValidatorImpl<O, T extends Double> implements Validator<O, T>
{
	/* (non-Javadoc)
	 * @see GuiGenerator.Validator#valideValue()
	 */
	
	public T valideValue(O o, T value ) throws ValidationException
	{
		System.err.println("i'll validate = "+value);// TODO Auto-generated method stub
		
		// TELL THE USER SOMETHING IS WRONG AND DON'T CHANGE VALUE
		if (value<0.50)
			throw new ValidationException("Value must be greater than 0.5");
		// OR 
		//if (value<0.50)
		//	throw new ValidationException("Value must be greater than 0.5");
			
		return value;
		
	}


}
