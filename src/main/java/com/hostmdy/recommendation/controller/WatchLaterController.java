package com.hostmdy.recommendation.controller;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import com.hostmdy.recommendation.model.Favorite;
import com.hostmdy.recommendation.model.Mode;
import com.hostmdy.recommendation.model.WatchLater;
import com.hostmdy.recommendation.model.WatchLaterDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet
public class WatchLaterController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;
	
	private WatchLaterDAO watchLaterDAO;
	
	@Override
	public void init() throws ServletException {
		watchLaterDAO = new WatchLaterDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("mode");
		Mode mode = null;
		if (param == null ) {
			mode = Mode.WATCH_LATER;
		}else {
			mode = Mode.valueOf(param);
		}
		switch (mode) {
		/*
		 * case WATCH_LATER: showAllUserWatchLaterList(req,resp); break;
		 */
		case ADD_WATCH_LATER:
			addWatchLaterList(req,resp);
			break;
		default:
			showAllUserWatchLaterList(req, resp);
			break;
		}
	}
	
	private void addWatchLaterList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong("userId");
		Long movieId = Long.parseLong("movieId");
		if (watchLaterDAO.existInWatchLaterList(userId, movieId)) {
			req.setAttribute("insertOk", false);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
			dispatcher.forward(req, resp);
			return;
		}else {
			WatchLater watchLater = new WatchLater(userId, movieId);
			boolean insertOk = watchLaterDAO.addWatchLaterList(watchLater);
			if (insertOk) {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			} else {
				req.setAttribute("insertOk", false);
				req.setAttribute("watchLater", watchLater);
				RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
				dispatcher.forward(req, resp);
			}
		}
		
	}

	private void showAllUserWatchLaterList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		List<WatchLater> watchlaterList = watchLaterDAO.getUserWatchLaterListById(userId);
		req.setAttribute("watchlaterList", watchlaterList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/user/profile.jsp");
		dispatcher.forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
