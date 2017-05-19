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
    public String getIndex(Model model) {
        return "index";
    }

    private Response<List<Dish>> getDishes(){
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<List<Dish>> response = new Response<>();

        DataEnvironment.run((Void) -> {
            List<Dish> dishes = dishDAO.getAll();

            for(Dish dish : dishes) {
                dish.calcAverageEstimation();
            }

            response.setContent(dishes);
        });

        return response;
    }

    private Response<Boolean> addDish(DishDTO dishDTO) {
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<Boolean> responseError = new Response<>();
        final Dish dish = dishDTO.convert();

        DataEnvironment.run((Void) -> {
            responseError.setContent(dishDAO.add(dish));

        });

        return responseError;
    }

    private Response<Boolean> updateDish(DishDTO dishDTO){
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<Boolean> responseError = new Response<>();
        final Dish dishConverted = dishDTO.convert();

        DataEnvironment.run((Void) -> {
            final Dish dish = dishDAO.getByName(dishConverted.getName());
            responseError.setContent(dishDAO.update(dishConverted, dish.getId()));

        });

        return responseError;
    }

    @GetMapping("/dishes")
    public String getList(Model model) {
        Response<List<Dish>> response = getDishes();
        model.addAttribute("dishes", response.getContent());
        model.addAttribute("dishDTO", new DishDTO());
        model.addAttribute("showError", false);

        return "dishes";
    }

    @PostMapping("/dishes")
    public String postList(Model model, @ModelAttribute DishDTO dishDTO){
        Response<List<Dish>> responseDishes = getDishes();
        Response<Boolean> responseError = updateDish(dishDTO);
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
