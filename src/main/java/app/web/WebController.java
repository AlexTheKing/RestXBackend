package app.web;

import app.database.DatabaseHandler;
import app.model.DataEnvironment;
import app.model.dao.CommentDAO;
import app.model.dao.DishDAO;
import app.model.dao.RateDAO;
import app.model.dto.DishDTO;
import app.model.entities.comment.Comment;
import app.model.entities.dish.Dish;
import app.model.entities.rate.Rate;
import app.util.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.crypto.Data;
import java.util.List;

@Controller
public class WebController {

    @RequestMapping("/")
    public String getMain(Model model) {
        return "index";
    }

    @GetMapping("/dishes")
    public String getDishList(Model model) {
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<List<Dish>> response = new Response<>();

        DataEnvironment.run((Void) -> {
            List<Dish> dishes = dishDAO.getAll();

            for(Dish dish : dishes) {
                dish.calcAverageEstimation();
            }

            response.setContent(dishes);
        });

        model.addAttribute("dishes", response.getContent());
        model.addAttribute("dishDTO", new DishDTO());
        model.addAttribute("showError", false);

        return "dishes";
    }

    @PostMapping("/dishes")
    public String addDish(Model model, @ModelAttribute DishDTO dishDTO){
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<List<Dish>> responseDishes = new Response<>();
        Response<Boolean> responseError = new Response<>();
        final Dish dish = dishDTO.convert();

        DataEnvironment.run((Void) -> {
            responseError.setContent(dishDAO.add(dish));
            List<Dish> dishes = dishDAO.getAll();

            for(Dish d : dishes) {
                d.calcAverageEstimation();
            }

            responseDishes.setContent(dishes);
        });

        model.addAttribute("dishes", responseDishes.getContent());
        model.addAttribute("dishDTO", new DishDTO());
        model.addAttribute("showError", !responseError.getContent());

        return "dishes";
    }

    @RequestMapping("/rates")
    public String getRatesList(Model model) {
        RateDAO rateDAO = DataEnvironment.getRateDAO();
        Response<List<Rate>> response = new Response<>();
        DataEnvironment.run((Void) -> response.setContent(rateDAO.getAll()));
        model.addAttribute("rates", response.getContent());

        return "rates";
    }

    @RequestMapping("/comments")
    public String getCommentList(Model model) {
        CommentDAO commentDAO = DataEnvironment.getCommentDAO();
        Response<List<Comment>> response = new Response<>();
        DataEnvironment.run((Void) -> response.setContent(commentDAO.getAll()));
        model.addAttribute("comments", response.getContent());

        return "comments";
    }

}
