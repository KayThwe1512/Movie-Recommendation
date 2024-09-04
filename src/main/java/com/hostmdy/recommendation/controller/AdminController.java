package com.hostmdy.recommendation.controller;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/admin")
public class AdminController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name ="jdbc/recommend_app")
	private DataSource dataSource;
	
	
	private UserDAO userDAO;
	private MovieDAO movieDAO;
	private ReviewDAO reviewDAO;
	
	@Override 
	public void init() throws ServletException { 
		movieDAO = new MovieDAO(dataSource);
		userDAO = new UserDAO(dataSource);
		reviewDAO = new ReviewDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null) {
			mode = Mode.LIST;
		} else {
			mode = Mode.valueOf(param);
		}

		switch (mode) {
		case LIST:
			showList(req, resp);
			break;
		case USERLIST:
			showList(req, resp);
			break;
		case MOVIELIST:
			showList(req, resp);
			break;
		case REVIEWLIST:
			showList(req, resp);
			break;
		case ENABLE:
			enableUser(req,resp);
			break;
		case DISABLE:
			disableUser(req, resp);
			break;
		default:
			showList(req, resp);
			break;
		}
	}
	
	private void enableUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.enableUser(userId);
		System.out.println("en1able ok"+ok);
		if (ok) {
			resp.sendRedirect(req.getContextPath() + "/admin?mode=USERLIST");
		}
		
	}
	
	private void disableUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.disableUser(userId);
		System.out.println("disable ok"+ok);
		if (ok) {
			resp.sendRedirect(req.getContextPath() + "/admin?mode=USERLIST");
		}
	}

	private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> userList = userDAO.getAllUsers();
		List<Reviews> reviewList = reviewDAO.getAllReviews();
		List<Movie> movieList = movieDAO.getAllMovies();
		List<Movie> movieReviewDetailsList = new ArrayList<>();
		for (Reviews review : reviewList) {
	        Movie movie = movieDAO.getMovieById(review.getMovieId());
	        System.out.println("movie "+movie);
	        System.out.println("reviews.movieId = movie.id"+review.getMovieId());
	        movieReviewDetailsList.add(movie);
	    }
		String mode = req.getParameter("mode");
		req.setAttribute("mode", mode);
		req.setAttribute("userList", userList);
		req.setAttribute("reviewList", reviewList);
		req.setAttribute("movieList", movieList);
		req.setAttribute("movieReviewDetailsList", movieReviewDetailsList);

		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/dashboard.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
