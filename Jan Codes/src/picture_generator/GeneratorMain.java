package picture_generator;

public class GeneratorMain {
	
	private PictureGen_GUI view;
	
	public void start() {
		view = new PictureGen_GUI();
		GeneratorController controller = new GeneratorController(view);
		
		view.addActionController(controller);
	}
	
	public static void main(String[] args) {
		PictureGen_GUI.systemLookandFeel();
		GeneratorMain picGen = new GeneratorMain();
		picGen.start();
	}
	
}
