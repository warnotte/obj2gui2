/**
 * 
 */
/**
 * @author Wax
 *
 */
module obj2gui2
{
	exports org.warnotte.obj2gui2.Tests.mainEmbbeder;
	exports org.warnotte.obj2gui2.Validators;
	exports org.warnotte.obj2gui2.ArrayGeneratorForsingleObject;
	exports org.warnotte.obj2gui2;
	exports org.warnotte.obj2gui2.Tests.Complex;
	exports org.warnotte.obj2gui2.Tests.MigLayout;
	exports org.warnotte.obj2gui2.Plugins;
	exports org.warnotte.obj2gui2.Tests.Calculatrice;
	exports org.warnotte.obj2gui2.Tests.SimpleTypeValidationTest;
	exports org.warnotte.obj2gui2.Tests.CustomiseLikeInITS;
	exports org.warnotte.obj2gui2.Plugins.tests;
	exports org.warnotte.obj2gui2.Tests.SimpleType;
	exports org.warnotte.obj2gui2.Tests.SimpleTypeDecimalFormattedFields;

	requires java.desktop;
	requires java.xml;
	requires jdom2;
	requires org.apache.logging.log4j;
	requires org.warnotte.waxlib2;
	requires org.warnotte.waxlibswingcomponentsMVN;
	requires swingx.all;
	requires vecmath;
	requires com.miglayout.swing;
}