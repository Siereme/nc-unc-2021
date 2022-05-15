package com.app.controller;

import com.app.model.role.Role;
import com.app.model.user.User;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("ALL")
@Validated
@Controller
@RequestMapping(path = "/admin")
@SessionAttributes("errors")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/all")
    public String userList(Model model) {
        model.addAttribute("allUsers", repository.findAll());
        return "admin";
    }

    @PostMapping(value = "/handle/delete/{id}")
    public ModelAndView delete(@PathVariable @NotNull int id) {
//        repository.delete(id);
        return new ModelAndView("redirect:/admin/all");
    }

    @PostMapping(value = "/find")
    public ModelAndView get(@RequestParam @NotBlank String tittle, ModelMap model) {
//        Collection<User> userList = repository.findByContains(tittle);
//        model.addAttribute("allUsers", userList);
        return new ModelAndView("admin", model);
    }

    @PostMapping(value = "/handle/{commandType}")
    public String renderHandlePage(@ModelAttribute User user, ModelMap model,
                                   @PathVariable @NotBlank String commandType) {
        Collection<Role> roles = roleRepository.findAll();
        if (Objects.equals(commandType, "page-add")) {
            model.addAttribute("roleList", roles);
            model.addAttribute("modalTitle", "Add");
            model.addAttribute("eventType", "handle/add");
        }
        if (Objects.equals(commandType, "page-edit")) {
            int id = user.getId();
            user = repository.findById(id);
            Collection<Role> userRoleList = user.getRoles();
            roles.removeIf(role -> userRoleList.stream().anyMatch(userRole -> userRole.getId() == role.getId()));
            model.addAttribute("roleList", roles);
            model.addAttribute("userRoles", userRoleList);
            model.addAttribute("modalTitle", "Edit");
            model.addAttribute("eventType", "handle/edit");
            model.addAttribute("admin", user);
        }
        return "admin-handle";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        return new ModelAndView("redirect:/admin/all");
    }

    @PostMapping(value = "/handle/add")
    public String add(@Validated @ModelAttribute User user, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(user, map, "page-add");
        }
        repository.saveUser(user);
        return "redirect:/admin/all";
    }

    @PostMapping(value = "/handle/edit")
    public String edit(@Validated @ModelAttribute User user, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            map.addAttribute("result", result);
            return renderHandlePage(user, map, "page-edit");
        }
//        repository.edit(user);
        return "redirect:/admin/all";
    }

}
