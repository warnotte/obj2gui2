/**
 * 
 */
package io.github.warnotte.obj2gui2.Validators;

/**
 * @author Warnotte Renaud
 *
 */
public interface Validator<O, T>
{

	/**
	 * Will return the corrected value (if user decide it).
	 * Otherwise, user can throw a ValidationException that will thell the user a message in a popupbox.
	 * @param o
	 * @param value
	 * @return
	 * @throws ValidationException
	 */
	public T valideValue(O o, T value) throws ValidationException;
}
