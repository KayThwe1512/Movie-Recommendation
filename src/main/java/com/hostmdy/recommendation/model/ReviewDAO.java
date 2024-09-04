package com.hostmdy.recommendation.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ReviewDAO {

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
	
	public ReviewDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	public List<Reviews> getAllReviews() {
		List<Reviews> reviewList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from reviews;");
			
			while (rs.next()) {
				reviewList.add(new Reviews(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("reviewtext"),
						rs.getInt("rating"),
						rs.getDate("createdAt").toLocalDate(),
						rs.getLong("userId"),
						rs.getLong("movieId"),
						rs.getString("username")));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return reviewList;
	}
	
	public List<Reviews> getAllReviewsByMovieId(Long movieId) {
		List<Reviews> reviewList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * from reviews join movie on reviews.movieId = movie.id where reviews.movieId='"+movieId+"';");
			
			while (rs.next()) {
				reviewList.add(new Reviews(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("reviewtext"),
						rs.getInt("rating"),
						rs.getDate("createdAt").toLocalDate(),
						rs.getLong("userId"),
						rs.getLong("movieId"),
						rs.getString("username")));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		return reviewList;
	}
	
	public boolean addReviews(Reviews reviews) {
		boolean insertOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("insert into reviews "
					+ "(title,reviewtext,rating,createdAt,userId,movieId,username) "
					+ "values(?,?,?,?,?,?,?);");
			pstmt.setString(1, reviews.getTitle());
			pstmt.setString(2, reviews.getReviewtext());
			pstmt.setInt(3, reviews.getRating());
			pstmt.setDate(4, Date.valueOf(reviews.getCreatedAt()));
			pstmt.setLong(5, reviews.getUserId());
			pstmt.setLong(6, reviews.getMovieId());
			pstmt.setString(7, reviews.getUsername());
			
			
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

}
