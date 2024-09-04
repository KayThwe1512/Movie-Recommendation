package com.hostmdy.recommendation.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class MovieDAO {
	
	private DataSource dataSource;
	private Connection connection;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;  
	private Movie movie;
	
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
	
	public MovieDAO(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	} 
	
	
	public List<Movie> getTrendingMovies() {
	    List<Movie> movies = new ArrayList<>();
	    String query = "SELECT * FROM movie ORDER BY averageRating DESC LIMIT 8";
	    movies = getMovies(query);
	    return movies;
	}
	
	public List<Movie> getEditorChoiceMovies() {
	    List<Movie> movies = new ArrayList<>();
	    String query = "SELECT * FROM movie ORDER BY RAND() LIMIT 8";
	    movies = getMovies(query);
	    return movies;
	}
	
	public List<Movie> getMovies(String query) {
	    List<Movie> movies = new ArrayList<>();
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement pstmt = connection.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
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

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return movies;
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
	
	
	
	public List<Movie> searchMovies(String query){
		List<Movie> movieList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("select * from movie"
					+ " where title like ? or casting like ? or director like ? or genre like ?;");
			
			pstmt.setString(1,  "%" + query + "%");
			pstmt.setString(2,  "%" + query + "%");
			pstmt.setString(3,  "%" + query + "%");
			pstmt.setString(4,  "%" + query + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				movieList.add(new Movie(
						rs.getLong("id"),
						rs.getString("title"),
						rs.getString("synopsis"),
						rs.getString("casting"),
						rs.getString("director"),
						rs.getString("genre"),
						rs.getDate("releaseDate").toLocalDate(),
						rs.getInt("duration_min"),
						rs.getString("poster"),
						rs.getLong("userId")
						));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		
		return movieList;
	}
	
	public List<Movie> getAllMovies(){
	        List<Movie> movies = new ArrayList<>();
	        String sql = "SELECT m.*, COALESCE(AVG(r.rating), 0) AS averageRating FROM movie m LEFT JOIN reviews r ON m.id = r.movieId GROUP BY m.id";

	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement stmt = connection.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	            	movie = new Movie(
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
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return movies;
	    }
	
		
	public Movie getMovieById(Long movieId){
		Movie movie = null;
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from movie where id='"+movieId+"';");
			
			while (rs.next()) {
				movie = new Movie(
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
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
		
		return movie;
	}
	
	public boolean createMovie(Movie movie) {
		boolean insertOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("insert into movie "
					+ "(title,synopsis,casting,director,genre,releaseDate,"
					+ "duration_min,poster,userId) "
					+ "values(?,?,?,?,?,?,?,?,?);");
			pstmt.setString(1, movie.getTitle());
			pstmt.setString(2, movie.getSynopsis());
			pstmt.setString(3, movie.getCasting());
			pstmt.setString(4, movie.getDirector());
			pstmt.setString(5, movie.getGenre());
			pstmt.setDate(6, Date.valueOf(movie.getReleaseDate()));
			pstmt.setInt(7, movie.getDuration_min());
			pstmt.setString(8, movie.getPoster());
			pstmt.setLong(9, movie.getUserId());
			
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

	public boolean updateMovie(Movie movie) {
		boolean insertOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("update movie set "
					+ "title=?,"
					+ "synopsis=?,"
					+ "casting=?,"
					+ "director=?,"
					+ "genre=?,"
					+ "releaseDate=?,"
					+ "duration_min=?,"
					+ "poster=? where id=?;"
					);
			pstmt.setString(1, movie.getTitle());
			pstmt.setString(2, movie.getSynopsis());
			pstmt.setString(3, movie.getCasting());
			pstmt.setString(4, movie.getDirector());
			pstmt.setString(5, movie.getGenre());
			pstmt.setDate(6, Date.valueOf(movie.getReleaseDate()));
			pstmt.setInt(7, movie.getDuration_min());
			pstmt.setString(8, movie.getPoster());
			pstmt.setLong(9, movie.getId());
			
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
	
	public boolean DeleteMovie(Long movieId) {
		boolean deleteOk = false;
		try {
			connection = dataSource.getConnection();
			pstmt = connection.prepareStatement("delete from movie where id=?;");
			
			pstmt.setLong(1, movieId);
			
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
	
	/*
	 * public List<Movie> averageRating(Long movieId) { List<Movie>
	 * averageRatingList = new ArrayList<>(); String selectSQL =
	 * "SELECT AVG(rating) AS average_rating FROM reviews WHERE movieId = ?"; String
	 * updateSQL = "UPDATE movie SET averageRating = ? WHERE id = ?"; try {
	 * connection = dataSource.getConnection(); PreparedStatement selectStmt =
	 * connection.prepareStatement(selectSQL); PreparedStatement updateStmt =
	 * connection.prepareStatement(updateSQL); selectStmt.setLong(1, movieId); rs =
	 * selectStmt.executeQuery(); while (rs.next()) { double averageRating =
	 * rs.getDouble("average_rating");
	 * 
	 * updateStmt.setDouble(1, averageRating); updateStmt.setLong(2, movieId);
	 * updateStmt.executeUpdate(); } } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return averageRatingList; }
	 */
	
	/*
	 * public List<Movie> getAllMoviesByUser(Long userId){ List<Movie> movieList =
	 * new ArrayList<>(); try { connection = dataSource.getConnection(); stmt =
	 * connection.createStatement(); rs =
	 * stmt.executeQuery("select * from movie where userId='"+userId+"';");
	 * 
	 * while (rs.next()) { movieList.add(new Movie( rs.getLong("id"),
	 * rs.getString("title"), rs.getString("synopsis"), rs.getString("casting"),
	 * rs.getString("director"), rs.getString("genre"),
	 * rs.getDate("releaseDate").toLocalDate(), rs.getInt("duration_min"),
	 * rs.getString("trailerUrl"), rs.getString("poster"), rs.getLong("userId") ));
	 * }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }finally { closeConnection(); }
	 * 
	 * return movieList; }
	 */
	
}
