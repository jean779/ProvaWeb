package com.example.provataniro.controlers;

import com.example.provataniro.models.Comidajap;
import com.example.provataniro.services.ComidajapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ClienteController {
    private ComidajapService cService;


    @Autowired
    public void setService(ComidajapService comidaService) {
        this.cService = comidaService;
    }

    @RequestMapping("/finalizarCompra")
    public String finalizarCompra(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/home";
    }

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public ModelAndView homePage(Comidajap comida, Model model, HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        Date date = new Date();
        Cookie cookie = new Cookie("Ultimavisita", dateFormat.format(date));
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);

        List<Comidajap> comidasAtivas = this.cService.findAllActiveComidajapList();
        model.addAttribute("comidasAtivas", comidasAtivas);
        ModelAndView modelAndView = new ModelAndView("cliente");
        modelAndView.addObject("comida", comida);
        return modelAndView;
    }

    @RequestMapping("/adicionarcarrinho")
    public String adicionarCarrinho(@RequestParam("id") Long id, final HttpServletRequest request) {
        System.out.println("ta chegando");
        Comidajap comida = cService.getById(id);
        System.out.println(comida.getNome());
        if (request.getSession().getAttribute("carrinho") == null) {
            ArrayList carrinho = new ArrayList();
            carrinho.add(comida);
            request.getSession().setAttribute("carrinho", carrinho);
        } else {
            ArrayList carrinho = (ArrayList) request.getSession().getAttribute("carrinho");
            carrinho.add(comida);
            request.getSession().setAttribute("carrinho", carrinho);
        }
        return "redirect:/cliente";
    }

    @RequestMapping("/vercarrinho")
    public ModelAndView listarItensdoCarrinho(HttpServletRequest request, Comidajap cjap) {
        ModelAndView carrinhoVazio = new ModelAndView();
        if (request.getSession().getAttribute("carrinho") == null) {
            carrinhoVazio.setViewName("carrinhoVazio");
            return carrinhoVazio;
        }
        ArrayList verCarrinho = (ArrayList) request.getSession().getAttribute("carrinho");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("carrinho", verCarrinho);
        modelAndView.addObject("comida", cjap);
        modelAndView.setViewName("/verCarrinho");

        return modelAndView;
    }
   /* @RequestMapping(value = "/verCarrinho", method = RequestMethod.GET)
    public String yourCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("cart") == null) {
            return "redirect:/carrinhovazio";
        }else{
            return "verCarrinho";
        }
    }*/

}
