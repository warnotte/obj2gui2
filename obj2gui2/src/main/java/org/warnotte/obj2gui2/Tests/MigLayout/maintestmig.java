/**
 * 
 */
package org.warnotte.obj2gui2.Tests.MigLayout;

import java.util.ArrayList;
import java.util.List;

import org.warnotte.obj2gui2.Tests.mainEmbbeder.mainTesteur;

/**
 * @author Warnotte Renaud
 *
 */
public class maintestmig
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		final List<DTOMig> selection = new ArrayList<>();
		selection.add(new DTOMig());
		
		mainTesteur.CreateExemple(selection);
		
		final List<DTOMig2> selection2 = new ArrayList<>();
		selection2.add(new DTOMig2());
		
		mainTesteur.CreateExemple(selection2);
		
		final List<DTOMig3> selection3 = new ArrayList<>();
		selection3.add(new DTOMig3());
		
		mainTesteur.CreateExemple(selection3);
		
		final List<DTOMig4> selection4 = new ArrayList<>();
		selection4.add(new DTOMig4());
		
		mainTesteur.CreateExemple(selection4);
	}



	
}
