package com.hostmdy.recommendation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class WatchLaterDAO {
	
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
	
	public WatchLaterDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean addWatchLaterList(WatchLater watchLater) {
		boolean insertOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("insert into watchlater "
					+ "(userId,movieId) values(?,?);");
			pstmt.setLong(1, watchLater.getUserId());
			pstmt.setLong(2, watchLater.getMovieId());
			int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				insertOk = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return insertOk;
	}
	
	public boolean removeWatchLaterList(Long userId, Long movieId) {
		boolean removeOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("delete from watchlater where userId=? and movieId=?;");
			pstmt.setLong(1, userId);
	        pstmt.setLong(2, movieId);
	        int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				removeOk = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return removeOk;
	}
	
	public boolean existInWatchLaterList(Long userId, Long movieId) {
	    boolean result = false;

	    try {
	        connection = dataSource.getConnection();
	        pstmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM watchlater WHERE userId = ? AND movieId = ?");
	        pstmt.setLong(1, userId);
	        pstmt.setLong(2, movieId);

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            if (count > 0) {
	                result = true; 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exception as needed
	    } finally {
	        closeConnection();
	    }

	    return result;
	}
	
	public List<WatchLater> getUserWatchLaterListById(Long userId) {
		List<WatchLater> watchlaterList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from watchlater where userId='"+userId+"';");
			while (rs.next()) {
				watchlaterList.add(new WatchLater(
						rs.getLong("id"),
						rs.getLong("userId"),
						rs.getLong("movieId")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return watchlaterList;
		
	}

}
