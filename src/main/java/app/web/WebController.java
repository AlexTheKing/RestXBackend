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

    @RequestMapping("/dish/list")
    public String getDishList(Model model) {
        DishDAO dishDAO = DataEnvironment.getDishDAO();
        Response<List<Dish>> response = new Response<>();

        DatabaseHandler.run((Void) -> response.setContent(dishDAO.getAll()));

        model.addAttribute("dishes", response.getContent());

        return "dishlist";
    }

}
