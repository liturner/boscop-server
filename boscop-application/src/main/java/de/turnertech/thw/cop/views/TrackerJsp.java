package de.turnertech.thw.cop.views;

import de.turnertech.thw.cop.trackers.Tracker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tracker")
public class TrackerJsp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tracker> criteriaQuery = criteriaBuilder.createQuery(Tracker.class);
        criteriaQuery.select(criteriaQuery.from(Tracker.class));
        List<Tracker> trackers = entityManager.createQuery(criteriaQuery).getResultList();

        request.setAttribute("trackers", trackers);
        request.getRequestDispatcher("/WEB-INF/jsp/tracker.jsp").forward(request, response);
    }

}
