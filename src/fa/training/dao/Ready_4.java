package fa.training.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

import fa.training.entities.Immigrant;
import fa.training.entities.LaborExport;
import fa.training.entities.OverseaStudent;
import fa.training.entities.Traveler;
import fa.training.exception.BirthDateException;
import fa.training.exception.IDDuplicateException;
import fa.training.exception.PassportException;
import fa.training.utils.ConnectionUtil_CN;

public class Ready_4 {

	public static int numberRecords;

	/**
	 * THINHNV30 1993-03-03 THÊM THÔNG TIN HÀNH KHÁCH TỪ FILE
	 * 
	 * @throws Exception
	 */
	public static void insert() throws Exception {

		File f = new File("D:\\HOC_TAP\\FILE_FULLSTACK\\JPE\\JAVA CODE\\JPE_1\\Ready_4\\src\\data.csv");
		String line = "";

		try {
			Connection connection = ConnectionUtil_CN.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO Ready (Type,immigrantID, Name, BirthDate, passport, nationality, immigrantDate, stayLength, university, majors, company, laborType,stayPlace, discountRate) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			BufferedReader br = Files.newBufferedReader(f.toPath(), StandardCharsets.UTF_8);

			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");

				String type = data[0];
				String immigrantID = data[1];
				String name = data[2];
				String birthDateStr = data[3];
				String passport = data[4];
				String nationality = data[5];
				String immigrantDate = data[6];
				String stayLength = data[7];
				String discountRate = data[13];

				// Đây là biến boolean cho biết bản ghi có hợp lệ hay không
				boolean isValid = true;
				try {

					// Kiểm tra birthDate
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date birthDate = dateFormat.parse(birthDateStr);
					Date currentDate = new Date();
					if (birthDate.after(currentDate)) {
//						System.out.println("BirthDate must be before current date !!!");
//						continue; // Bỏ qua dòng dữ liệu không hợp lệ
						throw new BirthDateException("BirthDate is invalid!!! " + birthDateStr);
					}

					// Kiểm tra passport
					if (!passport.matches("^(?!^0+$)[a-zA-Z0-9]{3,20}$")) {
						throw new PassportException("Passport is invalid!!! " + passport);
					}

					// Kiểm tra PassengerID trùng lặp
					if (isImmigrantIDDuplicate(connection, immigrantID)) {
//						System.out.println("Immigrant " + immigrantID + " has duplicate ID");
//						continue; // Bỏ qua dòng dữ liệu trùng lặp
						throw new IDDuplicateException("Immigrant " + immigrantID + " has duplicate ID");
					}
				} catch (BirthDateException e) { // Đây là một lớp ngoại lệ cho biết lỗi xác thực

					// This is a statement that sets the isValid variable to false
					isValid = false;

					// Đây là câu lệnh in thông báo lỗi ra bàn điều khiển
					System.out.println(e.getMessage());
				} catch (PassportException e) { // Đây là một lớp ngoại lệ cho biết lỗi xác thực

					// This is a statement that sets the isValid variable to false
					isValid = false;

					// Đây là câu lệnh in thông báo lỗi ra bàn điều khiển
					System.out.println(e.getMessage());
				} catch (IDDuplicateException e) { // Đây là một lớp ngoại lệ cho biết lỗi xác thực

					// This is a statement that sets the isValid variable to false
					isValid = false;

					// Đây là câu lệnh in thông báo lỗi ra bàn điều khiển
					System.out.println(e.getMessage());
				}
				if (isValid) {

					// Nếu tất cả kiểm tra đều qua, thì thực hiện insert
					preparedStatement.setString(1, type);
					preparedStatement.setString(2, immigrantID);
					preparedStatement.setString(3, name);
					preparedStatement.setString(4, birthDateStr);
					preparedStatement.setString(5, passport);
					preparedStatement.setString(6, nationality);
					preparedStatement.setString(7, immigrantDate);
					preparedStatement.setString(8, stayLength);
					preparedStatement.setString(9, data[8]);

					preparedStatement.setString(10, data[9]); // Job
					preparedStatement.setString(11, data[10]); // School
					preparedStatement.setString(12, data[11]); // Grade

					preparedStatement.setString(13, data[12]); // PretermBirth
					preparedStatement.setString(14, discountRate); // PretermBirth

					preparedStatement.executeUpdate();
					numberRecords++;
				}
			}

			br.close();
			preparedStatement.close();
			connection.close();

			System.out.println("Data has been inserted into the database.");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Program have an unexpected error occurred !!!");
			e.printStackTrace();
		}
		if (numberRecords == 0) {
			System.out.println("Insert that bai");
		}
		System.out.println("Số dòng insert thành công là: " + numberRecords);

	}

	/**
	 * THINHNV30 1993-03-03 HÀM KIỂM TRA TRÙNG LẶP PassengerID
	 */
	private static boolean isImmigrantIDDuplicate(Connection connection, String immigrantID) throws SQLException {
		String query = "SELECT immigrantID FROM ConNguoi WHERE immigrantID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, immigrantID);
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}

	/**
	 * THINHNV30 1993-03-03 Lấy thông tin từ database
	 */
	public static ArrayList<Immigrant> getImmigrantsFromDatabase(Connection connection) {
		ArrayList<Immigrant> immigrant = new ArrayList<>();
		try {
			String query = "SELECT * FROM ConNguoi";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// Tạo đối tượng Passenger từ dữ liệu ResultSet
				int type = resultSet.getInt("Type");
				String immigrantID = resultSet.getString("ImmigrantID");
				String name = resultSet.getString("Name");
				String birthDate = resultSet.getString("BirthDate");
				String passport = resultSet.getString("Passport");
				String nationality = resultSet.getString("Nationality");
				String immigrantDate = resultSet.getString("ImmigrantDate");
				String stayLength = resultSet.getString("StayLength");

				String university = resultSet.getString("university");
				String majors = resultSet.getString("majors");

				String company = resultSet.getString("company");
				String laborType = resultSet.getString("laborType");
				String stayPlace = resultSet.getString("stayPlace");

				Double discountRate = resultSet.getDouble("discountRate");

				if (type == 1) {
					OverseaStudent overseaStudent = new OverseaStudent(type, immigrantID, name, birthDate, passport,
							nationality, immigrantDate, stayLength, discountRate, university, majors);

					immigrant.add(overseaStudent);

				} else if (type == 2) {
					LaborExport laborExport = new LaborExport(type, immigrantID, name, birthDate, passport, nationality,
							immigrantDate, stayLength, discountRate, company, laborType);
					immigrant.add(laborExport);

				} else if (type == 3) {
					Traveler traveler = new Traveler(type, immigrantID, name, birthDate, passport, nationality,
							immigrantDate, stayLength, type, company, stayPlace);
					immigrant.add(traveler);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return immigrant;
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 */
	public static void showInformation(ArrayList<Immigrant> immigrants) {
		for (Immigrant passenger : immigrants) {
			passenger.showInfo();
			System.out.println(); // In một dòng trống để phân tách giữa các hành khách
		}
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 */
	public static void getInformation() {

		Connection conn = ConnectionUtil_CN.getConnection();
		// Tạo một danh sách hành khách
		ArrayList<Immigrant> passengers = new ArrayList<Immigrant>();
		passengers = getImmigrantsFromDatabase(conn);
		if (passengers.isEmpty()) {
			System.out.println("danh sách rỗng!!!");
		}
		// Gọi phương thức showInformation để hiển thị thông tin của hành khách
		showInformation(passengers);
	}

	/**
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:cap nhat thong tin giam gia
	 * @return:
	 */
	public static void UpdateDiscount() {
		Connection con = ConnectionUtil_CN.getConnection();
		PreparedStatement pstm = null;
		int type = 0;
		int check = 0;
		int number = 0;
		try {
			String sql1 = "select type from ConNguoi";
			pstm = con.prepareStatement(sql1);
			ResultSet rs = pstm.executeQuery();
			Random ran = new Random();
			while (rs.next()) {
				type = rs.getInt("type");

				if (type == 1) {
					number = ran.nextInt(30) + 1;
					String sql = "update ConNguoi set discountRate=? where type =?";
					pstm = con.prepareStatement(sql);
					pstm.setDouble(1, number);
					pstm.setInt(2, type);
					check = pstm.executeUpdate();
				}
				if (type == 2) {
					number = ran.nextInt(20) + 1;
					String sql2 = "update ConNguoi set discountRate=? where type =?";
					pstm = con.prepareStatement(sql2);
					pstm.setDouble(1, number);
					pstm.setInt(2, type);
					check = pstm.executeUpdate();
				}
				if (type == 3) {
					number = ran.nextInt(10) + 1;
					String sql3 = "update ConNguoi set discountRate=? where type =?";
					pstm = con.prepareStatement(sql3);
					pstm.setDouble(1, number);
					pstm.setInt(2, type);
					check = pstm.executeUpdate();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (check == 0) {
			System.out.println("Update failed");
		} else {
			System.out.println("Update successfully!");
		}

	}

	/**
	 * THINHNV30 1993-03-03 SẮP XẾP DANH SÁCH HÀNH KHÁCH
	 */
	public static void sortConNguoi() {
		try {
			Connection connection = ConnectionUtil_CN.getConnection();

			// Truy vấn dữ liệu hành khách từ cơ sở dữ liệu
			ArrayList<Immigrant> passengers = getImmigrantsFromDatabase(connection);

			// Sắp xếp danh sách hành khách theo yêu cầu
			Collections.sort(passengers, new Comparator<Immigrant>() {
				@Override
				public int compare(Immigrant p1, Immigrant p2) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						// So sánh theo ImmigrantDate giảm dần
						int imd = dateFormat.parse(p2.getImmigrantDate())
								.compareTo(dateFormat.parse(p1.getImmigrantDate()));
						if (imd != 0) {
							return imd;
						}

						// So sánh theo DiscountRate tăng dần
						return (int) (p2.getDiscountRate() - p1.getDiscountRate());

					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
				}
			});

			// Hiển thị danh sách đã sắp xếp
			showInformation(passengers);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * THINHNV30 1993-03-03 XÓA THÔNG TIN CON NGƯỜI
	 */
	public static void deleteHK() {
		
		try (
			Connection connection = ConnectionUtil_CN.getConnection();
			// Xóa du khách (Passport có giá trị null)
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ConNguoi WHERE Passport IS NULL")){
			preparedStatement.executeUpdate();

			System.out.println("Deleted passengers with null Passport.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * THINHNV30 1993-03-03 XÓA THÔNG TIN CON NGƯỜI
	 */
	public static void deleteAll() {

		try (Connection connection = ConnectionUtil_CN.getConnection();
		// Xóa du khách (Passport có giá trị null)
//			String query = "DELETE FROM ConNguoi ";
				PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ConNguoi ")) {
			preparedStatement.executeUpdate();

			System.out.println("Deleted passengers !!!.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
