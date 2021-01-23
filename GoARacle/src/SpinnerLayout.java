import java.awt.BorderLayout;
import java.awt.Component;
//import java.awt.LayoutManager;

public class SpinnerLayout extends BorderLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override public void addLayoutComponent(Component comp, Object constraints) {
	    if("Editor".equals(constraints)) {
	      constraints = "Center";
	    } else if("Next".equals(constraints)) {
	      constraints = "East";
	    } else if("Previous".equals(constraints)) {
	      constraints = "West";
	    }
	    super.addLayoutComponent(comp, constraints);
	  }



}
