package fa.training.main;

import java.util.Scanner;

import fa.training.dao.Ready_4;


public class Menu {

	
	public static void menu() {
		
		Scanner sc = new Scanner(System.in);

		String st = null;
		int choice;
		do {
			System.out.println("----- Chào mừng bạn đến với chương trình quản lí bài tập của THINHNV30 -----");
			System.out.println("-----CHÚC BẠN NGÀY MỚI TỐT LÀNH-------");
			System.out.println(" 1.Chức năng Input Data----- ");
			System.out.println(" 2.Giới thiệu thông tin của các hành khách hiện có ");
			System.out.println(" 3.Cập nhật ngày giảm giá ");
			System.out.println(" 4.Sắp xếp danh sách Con Người ");
			System.out.println(" 5.Xóa những Con Người không có passport ");
			System.out.println(" 6.Xóa tấc cả hành khách hiện có. ");
			System.out.println("======Nhập vào số nguyên tương ứng với bài tập bạn muốn chạy :");
			System.out.println();
			
			
			while (true) {
				try {
					st = sc.nextLine();
					choice = Integer.parseInt(st);
					System.out.println("Số bạn nhập là : " + choice);
					break;
				} catch (Exception ex) {
					System.out.println(st + "Không phải số nguyên");
					System.out.println("Vui lòng nhập lại ");
				}
			}
			switch (choice) {
			case 1:// Cau 1
				try {
					Ready_4.insert();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
				
				break;

			case 2:// Cau 2
				Ready_4.getInformation();
				System.out.println();
				
				break;

			case 3:// Cau 3
				Ready_4.UpdateDiscount();
				System.out.println();
				
				break;
				
			case 4:// Cau 4
				Ready_4.sortConNguoi();
				System.out.println();
				
				break;
				
			case 5:// Cau 5
				Ready_4.deleteHK();
				System.out.println();
				
				break;
				
			case 6:// Cau 5
				Ready_4.deleteAll();
				System.out.println();
				
				break;
				
			case 0:
				// 0. Thoát
				System.out.println("====Kết thúc chương trình======");
				System.out.println();
				
				break;

			default:
				System.out.println("Lựa chọn không hợp lệ");
				System.out.println();
				
				break;
			}
		} while (choice != 0);
		
	}
	
	
	public static void main(String[] args) {
		
		try {
			menu();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Program have an unexpected error occurred !!!");
					
			System.out.println("Program is Exit");

		}
	}
	
	
}
