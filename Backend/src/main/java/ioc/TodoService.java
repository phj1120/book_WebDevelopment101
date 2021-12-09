package ioc;

public class TodoService {

//	생성자 주입
//	private final ITodoPersistance persisitance;
	
//	public TodoService(ITodoPersistance persistance) {
//		this.persisitance = new FileTodoPersistance();
//	}
	

//	Setter 주입
	private ITodoPersistance persisitance;
	
	public void setITodoPersistance(ITodoPersistance persistance) {
		this.persisitance = persistance;
	}
	
//	공통
	public void create() {
		persisitance.create();
	}
	
	public static void main(String[] args) {
		
//		생성자 주입
//		ITodoPersistance persistance = new FileTodoPersistance();
//		TodoService service = new TodoService(persistance);
	
//		Setter 주입
		ITodoPersistance persistance = new FileTodoPersistance();
		TodoService service = new TodoService();
		service.setITodoPersistance(persistance);
		
//		공통
		service.create();
	}
}
