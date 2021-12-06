package ioc;

public class TodoService {

//	������ ����
//	private final ITodoPersistance persisitance;
	
//	public TodoService(ITodoPersistance persistance) {
//		this.persisitance = new FileTodoPersistance();
//	}
	

//	Setter ����
	private ITodoPersistance persisitance;
	
	public void setITodoPersistance(ITodoPersistance persistance) {
		this.persisitance = persistance;
	}
	
//	����
	public void create() {
		persisitance.create();
	}
	
	public static void main(String[] args) {
		
//		������ ����
//		ITodoPersistance persistance = new FileTodoPersistance();
//		TodoService service = new TodoService(persistance);
	
//		Setter ����
		ITodoPersistance persistance = new FileTodoPersistance();
		TodoService service = new TodoService();
		service.setITodoPersistance(persistance);
		
//		����
		service.create();
	}
}
