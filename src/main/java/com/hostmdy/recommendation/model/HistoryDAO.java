package com.hostmdy.recommendation.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class HistoryDAO {
	
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
	
	public HistoryDAO(DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}
	
	public boolean addHistory(History history) {
		boolean insertOk = false;
		try {
			if (!isHistory(history.getUserId(), history.getMovieId())) {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("INSERT INTO history "
					+ "(userId, movieId,statusTime)"
					+ " VALUES (?, ?, ?)");
			pstmt.setLong(1, history.getUserId());
            pstmt.setLong(2, history.getMovieId());
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            int rowEffected = pstmt.executeUpdate();
			if (rowEffected > 0) {
				insertOk = true;
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return insertOk;
		
	}
	
	public List<History> getAllHistory(Long userId) {
	    List<History> histories = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        pstmt = connection.prepareStatement("SELECT m.*, h.* FROM movie m "
	                + "INNER JOIN history h ON m.id = h.movieId WHERE h.userId = ? ORDER BY h.statusTime DESC");
	        pstmt.setLong(1, userId);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            History history = new History();
	            history.setUserId(rs.getLong("userId"));
	            history.setMovieId(rs.getLong("movieId"));
	            history.setStatusTime(rs.getDate("statusTime").toLocalDate());

	            histories.add(history);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	    return histories;
	}

	public List<Movie> getMoviesByHistory(List<History> histories) {
	    List<Movie> movies = new ArrayList<>();
	    try {
	        connection = dataSource.getConnection();
	        for (History history : histories) {
	            pstmt = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
	            pstmt.setLong(1, history.getMovieId());
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                Movie movie = new Movie(
	                        rs.getLong("id"),
	                        rs.getString("title"),
	                        rs.getString("synopsis"),
	                        rs.getString("casting"),
	                        rs.getString("director"),
	                        rs.getString("genre"),
	                        rs.getDate("releaseDate").toLocalDate(),
	                        rs.getInt("duration_min"),
	                        rs.getString("poster"),
	                        rs.getLong("userId"),
	                        rs.getDouble("averageRating")
	                );
	                movies.add(movie);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection();
	    }
	    return movies;
	}

	public boolean isHistory(Long userId, Long movieId) {
	    boolean result = false;

	    try {
	        connection = dataSource.getConnection();
	        pstmt = connection.prepareStatement("SELECT COUNT(*) AS count FROM history WHERE userId = ? AND movieId = ?");
	        pstmt.setLong(1, userId);
	        pstmt.setLong(2, movieId);

	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt("count");
	            if (count > 0) {
	                result = true; // Movie is already in favorites
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

}
