package com.hostmdy.recommendation.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.Mode;
import com.hostmdy.recommendation.model.Movie;
import com.hostmdy.recommendation.model.MovieDAO;
import com.hostmdy.recommendation.model.ReviewDAO;
import com.hostmdy.recommendation.model.Reviews;
import com.hostmdy.recommendation.model.User;
import com.hostmdy.recommendation.model.UserDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/review")
public class ReviewController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;
	
	private ReviewDAO reviewDAO;
	private MovieDAO movieDAO;
	private UserDAO userDAO;
	private Movie movie;
	private User user;
	
	@Override
	public void init() throws ServletException {
		movieDAO = new MovieDAO(dataSource);
		reviewDAO = new ReviewDAO(dataSource);
		userDAO = new UserDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		user = (User) session.getAttribute("user");
		movie = (Movie) session.getAttribute("movie");
		if (movie == null) {
	        Long movieId = Long.parseLong(req.getParameter("movieId"));
	        movie = movieDAO.getMovieById(movieId);  
	        session.setAttribute("movie", movie);    
	    }
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null) {
			mode = Mode.REVIEW;
		}else {
			mode = Mode.valueOf(param);
		}
		
		switch (mode) {
		case REVIEW:
			showReview(req,resp,user);
			break;
//		case REVIEWLIST:
//			showReviewDashboard(req, resp);
//			break;
		case REVIEW_FORM:
			showReviewForm(req,resp);
			break;
		case ADD_REVIEW:
			createReview(req, resp);
		default:
			break;
		}
	}
	
	private void createReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String reviewtext = req.getParameter("reviewtext");
		Integer rating = Integer.parseInt(req.getParameter("rating")); 
        LocalDate createdAt = LocalDate.now();
        //Reviews review = new Reviews(title, reviewtext, rating, createdAt, movie.getId(), user.getId());
        Reviews review = new Reviews(title, reviewtext, rating, createdAt, movie.getId(), user.getId(), user.getUsername());
		req.setAttribute("insertOk", reviewDAO.addReviews(review));
		showReviewForm(req, resp);
	}
	
	private void showReviewDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Reviews> reviewList = reviewDAO.getAllReviews();
		List<Movie> movieList = movieDAO.getAllMovies();
		req.setAttribute("reviewList", reviewList);
		req.setAttribute("movieList", movieList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/review/user-review.jsp");
		dispatcher.forward(req, resp);
	}
	
	private void showReview(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		movie = movieDAO.getMovieById(movieId);
		List<Reviews> reviewList = reviewDAO.getAllReviewsByMovieId(movieId);
		System.out.println("ReviewList :"+reviewList);
		req.setAttribute("reviewList", reviewList);
		req.setAttribute("user", user);
		req.setAttribute("movie", movie);
		req.setAttribute("movieId", movieId);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/review/user-review.jsp");
		dispatcher.forward(req, resp);
	}
	
	

	private void showReviewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("user", user);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/review/review-form.jsp");
		dispatcher.forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
