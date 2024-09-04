package com.hostmdy.recommendation.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.Favorite;
import com.hostmdy.recommendation.model.FavoriteDAO;
import com.hostmdy.recommendation.model.Mode;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addfavorite")
public class FavoriteController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;

	private FavoriteDAO favoriteDAO;

	@Override
	public void init() throws ServletException {
		favoriteDAO = new FavoriteDAO(dataSource);
	}

	private void createFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		if (favoriteDAO.isFavorite(userId, movieId)) {
			req.setAttribute("insertOk", false);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
			dispatcher.forward(req, resp);
			return;
		} else {
			Favorite favorite = new Favorite(userId, movieId);
			boolean insertOk = favoriteDAO.addFavorite(favorite);
			if (insertOk) {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			} else {
				req.setAttribute("insertOk", false);
				req.setAttribute("favorite", favorite);
				RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
				dispatcher.forward(req, resp);
			}
		}

//		req.setAttribute("insertOk", favoriteDAO.addFavorite(favorite));
//		RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
//		dispatcher.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null) {
			mode = Mode.FAVORITE;
		} else {
			mode = Mode.valueOf(param);
		}

		switch (mode) {
		case ADD_FAVORITE:
			createFavorite(req, resp);
			break;
		case FAVORITE:
			showAllUserFavorites(req, resp);
			break;
		default:
			createFavorite(req, resp);
			break;
		}
	}

	private void showAllUserFavorites(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		List<Favorite> favoriteList = favoriteDAO.getUserFavoriteById(userId);
		req.setAttribute("favoriteList", favoriteList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
