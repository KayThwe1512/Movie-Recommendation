package com.hostmdy.recommendation.model;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.hostmdy.recommendation.utility.PasswordEncoder;
import com.hostmdy.recommendation.utility.PasswordValidator;

public class UserDAO {

	private DataSource dataSource;
	private Connection connection;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public UserDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public boolean disableUser(Long userId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update user set "
					+ "enable=? where id=?;");
			pstmt.setBoolean(1, false);
			pstmt.setLong(2, userId);
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected> 0) {
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return ok;
		
	}
	
	public boolean enableUser(Long userId) {
		boolean ok = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update user set "
					+ "enable=? where id=?;");
			pstmt.setBoolean(1, true);
			pstmt.setLong(2, userId);
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected> 0) {
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return ok;
		
	}
	
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from user where role='user';");

			while (rs.next()) {
				LocalDate dateOfBirth = null;  
			    Date dbDate = rs.getDate("dateOfBirth");  

			    if (dbDate != null) {
			        dateOfBirth = dbDate.toLocalDate(); 
			    }
				userList.add(new User(
						rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role"),
						rs.getBoolean("enable"),
						rs.getTimestamp("createdAt").toLocalDateTime(),
						rs.getString("biography"),
						rs.getString("gender"),
						dateOfBirth,
	            		rs.getString("profile_pic")
						));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return userList;
	}
	
	public User getUserById(Long userId) {
		User user = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from user where id='"+userId+"';");

			while (rs.next()) {
				user = new User(
						rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role"),
						rs.getBoolean("enable"),
						rs.getTimestamp("createdAt").toLocalDateTime(),
						rs.getString("biography"),
						rs.getString("gender"),
	            		rs.getDate("dateOfBirth").toLocalDate(),
	            		rs.getString("profile_pic"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return user;
	}

	public Optional<User> getUserByUsernameOrEmail(String username) {
		Optional<User> userOptional = Optional.empty();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from user where username='" + username + "' or email='" + username + "';");

			while (rs.next()) {
				LocalDate dateOfBirth = null;  // Initialize as null
			    Date dbDate = rs.getDate("dateOfBirth");  // Fetch the date from the result set

			    // Check if dbDate is not null before converting it to LocalDate
			    if (dbDate != null) {
			        dateOfBirth = dbDate.toLocalDate();  // Convert to LocalDate if it's not null
			    }
				userOptional = Optional.of(new User(
						rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("role"),
						rs.getBoolean("enable"),
						rs.getTimestamp("createdAt").toLocalDateTime(),
						rs.getString("biography"),
						rs.getString("gender"),
						dateOfBirth,
	            		rs.getString("profile_pic")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return userOptional;
	}

	public boolean createUser(User user) {
		boolean insertOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("insert into user " 
					+ "(firstname,lastname,username,email,password,role,enable,createdAt,biography,gender,dateOfBirth) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?);");
			pstmt.setString(1, user.getFirstname());
			pstmt.setString(2, user.getLastname());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getEmail());
			try {
				pstmt.setString(5, PasswordEncoder.encode(user.getPassword()));
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt.setString(6, user.getRole());
			pstmt.setBoolean(7, user.getEnable());
			pstmt.setTimestamp(8, Timestamp.valueOf(user.getCreatedAt()));
			pstmt.setString(9, user.getBiography());
			pstmt.setString(10, user.getGender());
			pstmt.setDate(11, user.getDateOfBirth() != null ? java.sql.Date.valueOf(user.getDateOfBirth()) : null);
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				insertOk = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return insertOk;
	}
	
	public boolean deleteUser(User user) {
		boolean deleteOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("delete from user where id=?");
			pstmt.setLong(1, user.getId());
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				deleteOk = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return deleteOk;
	}
	
	public boolean updateUserProfile(User user) {
		boolean updateOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update user set "
					+ "firstname=?, lastname=?, username=?, email=?, biography=?, gender=?, dateOfBirth=? where id=?;");
			pstmt.setString(1, user.getFirstname());
			pstmt.setString(2, user.getLastname());
			pstmt.setString(3, user.getUsername());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getBiography());
			pstmt.setString(6, user.getGender());
			pstmt.setDate(7, Date.valueOf(user.getDateOfBirth()));
			pstmt.setLong(8, user.getId());
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				updateOk = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return updateOk;
	}
	
	public boolean updateUserPassword(User user) {
		boolean updateOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update user set "
					+ "password=? where id=?;");
			pstmt.setString(1, user.getPassword());
			pstmt.setLong(2, user.getId());
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				updateOk = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return updateOk;
	}
	
	public boolean uploadPicture(Long userId, String picFile) {
	    boolean updateOk = false;

	    try {
	    	connection = dataSource.getConnection();
	        String sql = "UPDATE user SET profile_pic = ? WHERE id = ?";
	        PreparedStatement pstmt = connection.prepareStatement(sql);
	        pstmt.setString(1, picFile);  
	        pstmt.setLong(2, userId);

	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            updateOk = true; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return updateOk;  
	}
	
	public String getUserProfilePic(Long userId) {
		String profilePic = null;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("select profile_pic from user where id=?;");
			pstmt.setLong(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				profilePic = rs.getString("profile_pic");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profilePic;
		
	}
	
	public User getUserProfileById(Long userId) {
	    User user = null;
	    try { 
	    	connection = dataSource.getConnection();
	        pstmt = connection.prepareStatement("SELECT * FROM user WHERE id = ?;"); 

	        pstmt.setLong(1, userId);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            user = new User(
	            		rs.getLong("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("username"),
						rs.getString("email"),
						rs.getString("role"),
						rs.getBoolean("enable"),
						rs.getTimestamp("createdAt").toLocalDateTime(),
						rs.getString("biography"),
						rs.getString("gender"),
	            		rs.getDate("dateOfBirth").toLocalDate(),
	            		rs.getString("profile_pic"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}

	public boolean validatePassword(Long userId, String currentPassword) {
		boolean validateOk = false;
		String storedPassword = null;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("select password from user where id=?;");
			pstmt.setLong(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
                storedPassword = rs.getString("password");
            }if (storedPassword != null) {
                try {
                	 validateOk = PasswordValidator.validatePassword(currentPassword, storedPassword);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return validateOk;
	}
	
	public boolean changePassword(Long userId, String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		boolean changeOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update user set password=? where id=?;");
			String encodedPassword = PasswordEncoder.encode(newPassword);
			pstmt.setString(1, encodedPassword);
			pstmt.setLong(2, userId);
			int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	        	changeOk = true; 
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return changeOk;
	}

	public boolean authenticate(String username, String password) {
		Optional<User> userOptional = getUserByUsernameOrEmail(username);
		if (userOptional.isEmpty()) {
			return false;
		}

		User user = userOptional.get();
		try {
			if (PasswordValidator.validatePassword(password, user.getPassword()) && user.getEnable()) {
				return true;
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
