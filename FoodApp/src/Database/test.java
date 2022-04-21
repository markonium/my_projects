package Database;



public class test {

	public static void main() {
		// TODO Auto-generated method stub
		try {
//			App app = new App();
			//app.sendEmail("markonium0000@gmail.com", "fares");
//			DBMenuOrder db = new DBMenuOrder();
//			
//			db.set_menu_flag(0);
//			System.out.println(db.get_menu_flag());
			/*
			ArrayList<Order> orders = db.getqueue();
			for(int i=0;i<orders.size();i++) {
				Order o = orders.get(i);
				System.out.println(o.getId());
			}
			
			/*
			Item i = new Item(0,"blah" ,"type","desc",50.20);
			Item i2 = new Item(0,"blah2" ,"type","desc",50.20);
			Item i3 = new Item(0,"blah3" ,"type","desc",50.20);
			Item i4 = new Item(0,"blah4" ,"type","desc",50.20);
			db.updateitem(i); db.updateitem(i2); db.updateitem(i3); db.updateitem(i4);
			
			//db.removeitem(2);
			ArrayList<Item> menu = db.getMenu();
			
			for(int j=0;j<menu.size();j++) {
				Item m = menu.get(j);
				System.out.println("id = "+m.getId()+ " name = "+m.getName()+" type = "+m.getType()+" price = "+m.getPrice()+" desc = "+m.getDescription());
			}
			*/
			/*
			ArrayList<Item> items = new ArrayList<Item>();
			Item i = new Item(5,"blah" ,"type","desc",50.20);
			Item i4 = new Item(8,"blah4" ,"type","desc",50.20);
			items.add(i); items.add(i4);
			
			ArrayList<Integer> in = new ArrayList<Integer>();
		    in.add(1); in.add(3);
			Order o = new Order(0,"katrin","address",items,in,"22-12-2021");
			
			db.addOrder(o);
			*//*
			ArrayList<Order> orders = db.getOrder("katrin");
			
			for(int i =0;i<orders.size();i++) {
				Order o = orders.get(i);
				System.out.println("id = "+o.getId());
				ArrayList<Item> items = o.getItems();
				ArrayList<Integer> in = o.getItemsNum();
				for(int j=0;j<items.size();j++) {
					System.out.println("itemid ="+items.get(j)+" number = "+in.get(j));
				}
			}
			*/
			
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
