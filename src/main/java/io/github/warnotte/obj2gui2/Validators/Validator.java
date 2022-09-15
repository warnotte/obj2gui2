/**
 * 
 */
package io.github.warnotte.obj2gui2.Validators;

/**
 * @author Warnotte Renaud
 *
 */
public interface Validator<T>
{

	public void valideValue(T value) throws ValidationException;
}
