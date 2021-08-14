package com.example.provataniro.controlers;

import com.example.provataniro.models.Comidajap;
import com.example.provataniro.services.ComidajapService;
import com.example.provataniro.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdmController {
    private ComidajapService cservice;
    private StorageService sService;

    @Autowired
    public void setCservice(ComidajapService comidaJapService) {
        this.cservice = comidaJapService;
    }

    @Autowired
    public void setStorageService(StorageService storageService){
        this.sService = storageService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView paginaPrincipal(Model model, Comidajap comida) {
        List<Comidajap> comidasAtivas = this.cservice.findAllActiveComidajapList();
        model.addAttribute("comidasAtivas", comidasAtivas);
        ModelAndView adm = new ModelAndView();
        ModelAndView modelAndView = new ModelAndView("listaAdm");
        modelAndView.addObject("comida", comida);
        return modelAndView;
    }

    @RequestMapping(value = "/cadastro")
    public String salvarcomida(Model model) {
        Comidajap pedido = new Comidajap();
        model.addAttribute("pedido", pedido);
        return "cadastro";
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("pedido") @Valid Comidajap pedido, Errors errors, @RequestParam ("file") MultipartFile file, RedirectAttributes redirectAtt) {
        if(errors.hasErrors()) {
            redirectAtt.addAttribute("redirectMessage","Erro ao enviar comida");
            return "cadastro";
        }else {
            if(file.isEmpty()) {
                String currentFileName = this.cservice.getById(pedido.getId()).getImgUri();
                pedido.setImgUri(currentFileName);
            } else {
                Integer hashCode = hashCode();
                pedido.setImgUri(hashCode + file.getOriginalFilename());
                this.sService.salvar(file, hashCode);
            }
            this.cservice.save(pedido);
            redirectAtt.addAttribute("redirectMessage","Comida registrada");
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/deletarcomida/{id}", method = RequestMethod.GET)
    public String deletarcomida(RedirectAttributes redirectAtt, @PathVariable (name = "id") Long id) {
        cservice.delete(id);
        redirectAtt.addAttribute("redirectMessage", "Comida Deletada");
        return "redirect:/admin";

    }

    @RequestMapping(value = "/editarcomida/{id}", method = RequestMethod.GET)
    public ModelAndView editarcomida(@ModelAttribute("pedido") @Valid Comidajap pedido, @PathVariable(name = "id") Long id) {
        pedido = this.cservice.getById(id);
        ModelAndView modelAndView = new ModelAndView("cadastro");
        modelAndView.addObject("comida", pedido);
        return modelAndView;
    }
}
