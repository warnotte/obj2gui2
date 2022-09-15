/**
 * 
 */
package org.warnotte.obj2gui2.Validators;

/**
 * @author Warnotte Renaud
 *
 */
public class ValidatorImpl<T extends Double> implements Validator<T>
{
	/* (non-Javadoc)
	 * @see GuiGenerator.Validator#valideValue()
	 */
	
	public void valideValue(T o ) throws ValidationException
	{
		System.err.println("i'll validate = "+o);// TODO Auto-generated method stub
		
		if (o<0.50)
			throw new ValidationException("Value must be greater than 0.5");
			
		
	}


}
