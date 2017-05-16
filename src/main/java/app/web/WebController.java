package app.web;

import app.database.DatabaseHandler;
import app.model.DataEnvironment;
import app.model.dao.DishDAO;
import app.model.entities.dish.Dish;
import app.util.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WebController {

    @RequestMapping("/")
    public String getMain(Model model) {
        return "index";
    }

    @RequestMapping("/dishes")
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

        return "dishes";
    }

}
