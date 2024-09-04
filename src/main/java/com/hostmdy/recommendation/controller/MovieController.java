package com.hostmdy.recommendation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.naming.java.javaURLContextFactory;

import com.hostmdy.recommendation.model.Favorite;
import com.hostmdy.recommendation.model.FavoriteDAO;
import com.hostmdy.recommendation.model.History;
import com.hostmdy.recommendation.model.HistoryDAO;
import com.hostmdy.recommendation.model.Mode;
import com.hostmdy.recommendation.model.Movie;
import com.hostmdy.recommendation.model.MovieDAO;
import com.hostmdy.recommendation.model.User;
import com.hostmdy.recommendation.model.WatchLater;
import com.hostmdy.recommendation.model.WatchLaterDAO;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/movie")
public class MovieController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/recommend_app")
	private DataSource dataSource;

	private MovieDAO movieDAO;
	private FavoriteDAO favoriteDAO;
	private HistoryDAO historyDAO;
	private WatchLaterDAO watchLaterDAO;
	private User user;

	@Override
	public void init() throws ServletException {
		movieDAO = new MovieDAO(dataSource);
		favoriteDAO = new FavoriteDAO(dataSource);
		historyDAO = new HistoryDAO(dataSource);
		watchLaterDAO = new WatchLaterDAO(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		user = (User) session.getAttribute("user");
		String param = req.getParameter("mode");
		req.setAttribute("mode", "MOVIE");
		Mode mode = null;
		if (param == null) {
			mode = Mode.LIST;
		} else {
			mode = Mode.valueOf(param);
		}

		switch (mode) {
		case LIST:
			showAllMovie(req, resp);
			break;
		case SINGLE:
			showMovie(req, resp);
			break;
		case MOVIE_FORM:
			showNewMovieForm(req, resp);
			break;
		case CREATE:
			createMovie(req, resp);
			break;
		case LOAD:
			loadMovie(req, resp);
			break;
		case UPDATE:
			updateMovie(req, resp);
			break;
		case DELETE:
			deleteMovie(req, resp);
			break;
		case ADD_FAVORITE:
			createFavorite(req, resp);
			break;
		 case ADD_HISTORY:
	        createHistory(req, resp);
	        break;
	    case ADD_WATCH_LATER:
	    	addWatchLaterList(req, resp);
	    	break;
		case SEARCH:
			searchMovie(req, resp);
			break;
		default:
			showAllMovie(req, resp);
			break;
		}
		
        //List<Movie> recommendedMovies = movieDAO.getRecommendedMovies((Long) req.getSession().getAttribute("userId"));
        
        //List<Movie> idolDramaMovies = movieDAO.getIdolDramaMovies();
        //List<Movie> animeMovies = movieDAO.getAnimeMovies();
        //req.setAttribute("recommendedMovies", recommendedMovies);
        //req.setAttribute("idolDramaMovies", idolDramaMovies);
        //req.setAttribute("animeMovies", animeMovies);
	}

	private void searchMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		List<Movie> movieList = movieDAO.searchMovies(query);
		req.setAttribute("movieList", movieList);
		req.setAttribute("user", user);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/search.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	private void addWatchLaterList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    Long userId = Long.parseLong(req.getParameter("userId"));
	    Long movieId = Long.parseLong(req.getParameter("movieId"));
	    boolean isInWatchLater = watchLaterDAO.existInWatchLaterList(userId, movieId);
	    req.setAttribute("isInWatchLater", isInWatchLater);
	    
	    boolean insertOk = false;
	    
	    if (isInWatchLater) {
	        boolean removeOk = watchLaterDAO.removeWatchLaterList(userId, movieId);
	        System.out.println("remove: " + removeOk);
	        if (removeOk) {
	            req.getSession().setAttribute("notification", "Movie removed from watch later list.");
	    	    resp.sendRedirect("movie");
	        }else {
				req.setAttribute("removeOk", false);
				req.getSession().setAttribute("notification", "Failed to remove movie from watch later list.");
				resp.sendRedirect("movie");
			}
	    } else {
	        WatchLater watchLater = new WatchLater(userId, movieId);
	        insertOk = watchLaterDAO.addWatchLaterList(watchLater);
	        System.out.println("insert: " + insertOk);
	        if (insertOk) {
	            req.getSession().setAttribute("notification", "Movie added to watch later list.");
	            req.setAttribute("insertOk", insertOk); 
	    	    resp.sendRedirect("movie");
	        }else {
				req.setAttribute("insertOk", false);
				req.getSession().setAttribute("notification", "Failed to add movie to watch later list.");
				resp.sendRedirect("movie");
			}
	    }
	    
	    
	}

	private void createFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		if (favoriteDAO.isFavorite(userId, movieId)) {
			boolean deleteOk = favoriteDAO.removeFavorite(userId, movieId);
			if (deleteOk) {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
				req.getSession().setAttribute("notification", "Movie removed from favorites.");
			} else {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			}

		} else {
			Favorite favorite = new Favorite(userId, movieId);
			boolean insertOk = favoriteDAO.addFavorite(favorite);
			if (insertOk) {
				req.getSession().setAttribute("notification", "Movie added to favorites.");
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			} else {
				req.setAttribute("insertOk", false);
				req.getSession().setAttribute("notification", "Failed to add movie to favorites.");
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			}
		}
	}
	
	private void createHistory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
		if (user != null) {
			Long userId = Long.parseLong(req.getParameter("userId"));
			
			LocalDate now = LocalDate.now();
			History history = new History(userId, movieId, now);
			System.out.println(history);
			boolean insertOk = historyDAO.addHistory(history);
			if (insertOk) {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			}else {
				resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
			}
		}else {
			resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
		}
		
	}

	

	private void createMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		user = (User) session.getAttribute("user");
		String title = req.getParameter("title");
		String synopsis = req.getParameter("synopsis");
		String casting = req.getParameter("casting");
		String director = req.getParameter("director");
		String genre = req.getParameter("genre");
		String releaseDateStr = req.getParameter("releaseDate");
		Integer duration_min = Integer.parseInt(req.getParameter("duration_min"));
		String poster = req.getParameter("poster");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate releaseDate = null;
		releaseDate = LocalDate.parse(releaseDateStr, formatter);

		Movie movie = new Movie(title, synopsis, casting, director, genre, releaseDate, duration_min,
				poster, user.getId());
		req.setAttribute("insertOk", movieDAO.createMovie(movie));
		showNewMovieForm(req, resp);
	}

	private void updateMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = Long.parseLong(req.getParameter("movieId"));
		String title = req.getParameter("title");
		String synopsis = req.getParameter("synopsis");
		String casting = req.getParameter("casting");
		String director = req.getParameter("director");
		String genre = req.getParameter("genre");
		String releaseDateStr = req.getParameter("releaseDate");
		Integer duration_min = Integer.parseInt(req.getParameter("duration_min"));
		String poster = req.getParameter("poster");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate releaseDate = null;
		releaseDate = LocalDate.parse(releaseDateStr, formatter);

		Movie movie = new Movie(id, title, synopsis, casting, director, genre, releaseDate, duration_min,poster);
		boolean updateOk = movieDAO.updateMovie(movie);
		if (updateOk) {
			resp.sendRedirect("movie?mode=SINGLE&movieId=" + id);
		} else {
			req.setAttribute("updateOk", false);
			req.setAttribute("movie", movie);
			RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/update-movie.jsp");
			dispatcher.forward(req, resp);
		}
	}

	private void showAllMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Movie> movieList = movieDAO.getAllMovies();
		List<Movie> limitedMovies = movieList.stream().limit(8).collect(Collectors.toList()); 
		req.setAttribute("movieList", movieList);
		List<Movie> editorChoiceMovies = movieDAO.getEditorChoiceMovies();
        req.setAttribute("editorChoiceMovies", editorChoiceMovies);
        List<Movie> trendingMovies = movieDAO.getTrendingMovies();
        req.setAttribute("trendingMovies", trendingMovies);
       
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/home.jsp");
		dispatcher.forward(req, resp);
	}

	private void showMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		Movie movie = movieDAO.getMovieById(movieId);
		req.setAttribute("movie", movie);
		req.setAttribute("user", user);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/movie-details.jsp");
		dispatcher.forward(req, resp);
	}

	private void showNewMovieForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("user", user);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/add-movie.jsp");
		dispatcher.forward(req, resp);
	}

	private void loadMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		Movie movie = movieDAO.getMovieById(movieId);
		req.setAttribute("movie", movie);
		req.setAttribute("user", user);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/movie/update-movie.jsp");
		dispatcher.forward(req, resp);
	}

	private void deleteMovie(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long movieId = Long.parseLong(req.getParameter("movieId"));
		boolean deleteOk = movieDAO.DeleteMovie(movieId);
		if (deleteOk) {
			resp.sendRedirect("movie");
		} else {
			resp.sendRedirect("movie?mode=SINGLE&movieId=" + movieId);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
